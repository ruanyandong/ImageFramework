package com.miracle.library.exception;

/**
 * @author miracle
 * @date 2019-09-25
 * @email ruanyandongai@gmail.com
 * @blog https://ruanyandong.github.io
 */
public class GlideException extends Exception{

    public GlideException() {
    }

    public GlideException(String message) {
        super(message);
    }

    public GlideException(String message, Throwable cause) {
        super(message, cause);
    }
}
