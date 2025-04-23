package com.liu.project.exception;

import com.liu.project.constant.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private Integer code = 0;

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Integer code) {
        super(msg);
        this.code = code;
    }

    public BaseException(ErrorCode code) {
        super(code.getMsg());
        this.code = code.getCode();
    }

    public BaseException(ErrorCode code, String message) {
        super(message);
        this.code = code.getCode();
    }
}