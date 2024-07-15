package com.nageoffer.shortlink.project.common.enums;

import com.nageoffer.shortlink.project.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnum implements IErrorCode {
    USER_TOKEN_FAIL("A000200","用户Token验证失败"),
    USER_EXIST("B000001","用户记录已存在"),
    USER_NULL("B000200","用户记录不存在"),
    USER_NAME_EXIST("B000201","用户名已存在"),
    USER_SAVE_ERROR("B000202","用户记录新增失败");


    private final String code;
    private final String message;
    UserErrorCodeEnum(String code, String message){
        this.code=code;
        this.message=message;
    }
    /**
     * 错误码
     */
    @Override
    public String code() {
        return code;
    }

    /**
     * 错误信息
     */
    @Override
    public String message() {
        return message;
    }
}