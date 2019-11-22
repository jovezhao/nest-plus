package com.zhaofujun.nest.demo.infrastructure.springboot;

import com.zhaofujun.nest.CustomException;
import com.zhaofujun.nest.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {

    @ExceptionHandler
    public ErrorResponse customException(CustomException e) {
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse systemException(SystemException e) {
        return new ErrorResponse(500, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse unknownException(Exception e) {
        return new ErrorResponse(500, e.getMessage());
    }
}
