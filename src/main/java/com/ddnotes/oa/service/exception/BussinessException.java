package com.ddnotes.oa.service.exception;

/**
 * 业务逻辑异常
 */
public class BussinessException extends RuntimeException {
    private String code;
    private String message;

    public BussinessException(String code, String message) {
        super(code + ":" +message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
