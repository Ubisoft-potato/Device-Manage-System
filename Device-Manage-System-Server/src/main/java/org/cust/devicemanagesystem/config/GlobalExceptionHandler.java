package org.cust.devicemanagesystem.config;


import org.cust.devicemanagesystem.exception.ResponseMessage;
import org.cust.devicemanagesystem.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * create by: long
 * description:
 * create time: 2020/1/5 下午9:45
 */
@RestControllerAdvice(basePackages = "org.cust.devicemanagesystem.controller")
public class GlobalExceptionHandler {

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return ErrorMessage
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage validExceptionHandler(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new ResponseMessage().setMessage(message.toString()).setHttpCode("400");
    }

    /**
     * 统一处理业务异常
     *
     * @param e
     * @return ResponseMessage
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage serviceExceptionHandler(ServiceException e) {
        return new ResponseMessage().setHttpCode("400").setMessage(e.getMessage());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return ErrorMessage
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseMessage().setHttpCode("400").setMessage("请填写必填参数");
    }
}
