package com.zhaofujun.nest.demo.infrastructure.springboot;

import com.zhaofujun.nest.standard.CustomException;
import com.zhaofujun.nest.standard.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {

    private Logger logger=LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public ErrorResponse customException(CustomException e) {
        logger.info(e.getMessage(),e);
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse systemException(SystemException e) {
        logger.error(e.getMessage(),e);

        return new ErrorResponse(500, e.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse unknownException(Exception e) {
        logger.error(e.getMessage(),e);

        return new ErrorResponse(500, e.getMessage());
    }
}
