package com.k.security.core.validate.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-03
 * Time: 23:56
 */

public class ImageCode extends ValidateCode {
    private BufferedImage bufferedImage;

    public ImageCode(String code, int expireIn, BufferedImage bufferedImage) {
        super(code, expireIn);
        this.bufferedImage = bufferedImage;
    }

    public ImageCode(String code, LocalDateTime expireTime, BufferedImage bufferedImage) {
        super(code, expireTime);
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
