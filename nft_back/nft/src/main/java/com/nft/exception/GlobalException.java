package com.nft.exception;

import com.nft.entity.Result;
import com.nft.entity.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
