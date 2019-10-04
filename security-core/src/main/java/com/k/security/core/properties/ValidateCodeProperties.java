package com.k.security.core.properties;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-04
 * Time: 11:46
 */
@Data
public class ValidateCodeProperties {
    private ImageCodeProperties image = new ImageCodeProperties();

    private int expireIn = 60;
    private int length = 4;
    private String urls;

    @Data
    private static class ImageCodeProperties {
        private int width = 67;
        private int height = 23;
    }
}
