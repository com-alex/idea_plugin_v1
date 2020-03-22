package com.fromLab.exception;

/**
 * @author wsh
 * @date 2020-03-21
 */
public class BusinessException extends Exception {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
