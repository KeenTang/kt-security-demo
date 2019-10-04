package com.k.security.core.validate.code;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 13:31
 */
public class NumberValidateCodeGenerator implements ValidateCodeGenerator {

    @Value("#{securityProperties.code.image.width}")
    private int width;

    @Value("#{securityProperties.code.image.height}")
    private int height;

    @Value("#{securityProperties.code.length}")
    private int length;

    @Value("#{securityProperties.code.expireIn}")
    private int expireIn;

    @Override
    public ImageCode generate(ServletWebRequest request) {
        return this.createImageCode(request.getRequest());
    }

    private ImageCode createImageCode(HttpServletRequest request) {
        int width = ServletRequestUtils.getIntParameter(request, "width", this.width);
        int height = ServletRequestUtils.getIntParameter(request, "height", this.height);
        int length = ServletRequestUtils.getIntParameter(request, "length", this.length);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(sRand, this.expireIn, image);
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
