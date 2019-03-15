package com.emp.pojo.result;

import com.emp.enums.HttpResultEnum;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Joker
 * @Date: 2019/2/13 13:30
 */
public class ApiResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private Object result;

    public ApiResult() {
        this.code = HttpResultEnum.SUCCESS.getCode();
        this.message = HttpResultEnum.SUCCESS.getMessage();
    }

    public ApiResult(Object result) {
        this.code =  HttpResultEnum.SUCCESS.getCode();
        this.message = HttpResultEnum.SUCCESS.getMessage();
        this.result = result;
    }

    public ApiResult(HttpResultEnum resultEnum){
        this.code =  resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public ApiResult(HttpResultEnum resultEnum, Object result){
        this.code =  resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.result = result;
    }

    public ApiResult(String code) {
        this.code = code;
    }

    public ApiResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public ApiResult(String code, String message, Object result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
