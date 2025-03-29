package com.fruityuni.exception;

import com.fruityuni.vo.ResultCode;
import lombok.Getter;

/**
 * 授权异常
 *
 * @author fruityuni
 */
@Getter
public class AuthorizationException extends RuntimeException {

    private final Integer code;

    public AuthorizationException(String message) {
        super(message);
        this.code = 403;
    }

    public AuthorizationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    
    public AuthorizationException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}