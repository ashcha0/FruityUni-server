package com.fruityuni.exception;

import com.fruityuni.vo.ResultCode;
import lombok.Getter;

/**
 * 认证异常
 *
 * @author fruityuni
 */
@Getter
public class AuthenticationException extends RuntimeException {

    private final Integer code;

    public AuthenticationException(String message) {
        super(message);
        this.code = 401;
    }

    public AuthenticationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    
    public AuthenticationException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}