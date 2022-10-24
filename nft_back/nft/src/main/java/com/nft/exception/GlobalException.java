package com.nft.exception;

import com.nft.entity.Result;
import com.nft.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RestControllerAdvice
public class GlobalException {

    @GetMapping("/noAuthorization")
    public Result noAuthorization() {
        return new Result(ResultCode.UNAUTHORISE);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public Result handler(UnauthorizedException e) {
        log.error("运行时异常：----------------{}", e.getMessage());
        return new Result(ResultCode.UNAUTHORISE);
    }

    @ExceptionHandler(value = ExpiredCredentialsException.class)
    public Result handler(ExpiredCredentialsException e) {
        log.error("运行时异常：----------------{}", e.getMessage());
        return new Result(ResultCode.UNAUTHENTICATED);
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    public Result handler(UnauthenticatedException e) {
        log.error("运行时异常：----------------{}", "未登录异常捕获");
        return new Result(ResultCode.UNAUTHENTICATED);
    }

    @ExceptionHandler(value = MyException.class)
    public Result handler(MyException e) {
        log.error("自定义异常：----------------{}", e.getMessage());

        MyException ce = (MyException) e;
        Result result = new Result(ce.getResultCode());
        if (20003 == result.getCode()) {
            return new Result(ResultCode.INCORRECT_CREDENTAIL_ERROR);
        }
        if (50002 == result.getCode()) {
            return new Result(ResultCode.INSUFFICIENT_BALANCE);
        }
        return null;
    }

}
