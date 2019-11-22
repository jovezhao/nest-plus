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
    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public ResultResponse customException(CustomException e) {
        return new ResultResponse(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler
    public ResultResponse systemException(SystemException e) {
        return new ResultResponse(500, e.getMessage());
    }

    @ExceptionHandler
    public ResultResponse unknownException(Exception e) {
        return new ResultResponse(500, e.getMessage());
    }
}
