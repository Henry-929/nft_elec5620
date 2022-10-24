package com.nft.exception;

import com.nft.entity.ResultCode;
import lombok.Getter;

@Getter
public class MyException extends RuntimeException{

    private ResultCode resultCode;

    public MyException(ResultCode resultCode) {
        super(resultCode.message());
        this.resultCode = resultCode;
    }
}
