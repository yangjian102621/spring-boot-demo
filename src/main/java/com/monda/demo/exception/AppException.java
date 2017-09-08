package com.monda.demo.exception;

import com.monda.demo.enums.ResultEnum;

/**
 * 通用异常类
 * @author yangjian
 * @since 2017-08-23
 */
public class AppException extends RuntimeException {

    private String code;

    public AppException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public AppException(String message) {
        super(message);
        this.code = ResultEnum.FAIL.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
