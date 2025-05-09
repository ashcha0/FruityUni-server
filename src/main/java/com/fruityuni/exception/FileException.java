package com.fruityuni.exception;

/**
 * 文件异常
 *
 * @author fruityuni
 */
public class FileException extends RuntimeException {

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}