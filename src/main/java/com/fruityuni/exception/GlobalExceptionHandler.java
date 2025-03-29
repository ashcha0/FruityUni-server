package com.fruityuni.exception;

import com.fruityuni.vo.Result;
import com.fruityuni.vo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理器
 *
 * @author fruityuni
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return Result.failed(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常（@Valid注解）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常：{}", e.getMessage());
        return Result.failed(ResultCode.PARAM_ERROR, getBindingResultMessage(e.getBindingResult()));
    }

    /**
     * 处理参数校验异常（@Validated注解）
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        log.error("参数校验异常：{}", e.getMessage());
        return Result.failed(ResultCode.PARAM_ERROR, getBindingResultMessage(e.getBindingResult()));
    }

    /**
     * 处理参数校验异常（@Validated注解，方法参数）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("参数校验异常：{}", e.getMessage());
        return Result.failed(ResultCode.PARAM_ERROR, getConstraintViolationMessage(e.getConstraintViolations()));
    }

    /**
     * 处理认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result<Void> handleAuthenticationException(AuthenticationException e) {
        log.error("认证异常：{}", e.getMessage());
        return Result.failed(e.getCode(), e.getMessage());
    }

    /**
     * 处理授权异常
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result<Void> handleAuthorizationException(AuthorizationException e) {
        log.error("授权异常：{}", e.getMessage());
        return Result.failed(e.getCode(), e.getMessage());
    }

    /**
     * 处理文件上传异常
     */
    @ExceptionHandler(FileException.class)
    public Result<Void> handleFileException(FileException e) {
        log.error("文件上传异常：{}", e.getMessage());
        return Result.failed(ResultCode.PARAM_ERROR, e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.failed(ResultCode.SYSTEM_ERROR, "系统异常，请联系管理员");
    }

    /**
     * 获取参数校验错误信息
     */
    private String getBindingResultMessage(BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError != null) {
            return fieldError.getDefaultMessage();
        }
        return "参数校验失败";
    }

    /**
     * 获取参数校验错误信息
     */
    private String getConstraintViolationMessage(Set<ConstraintViolation<?>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            return constraintViolations.iterator().next().getMessage();
        }
        return "参数校验失败";
    }
}