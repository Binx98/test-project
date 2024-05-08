package com.clothes.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author Author
 * @Date: 2024/5/8 21:59
 * @Version 1.0
 * @Description: BaseResponse
 */
@Getter
@Setter
public class BaseResponse {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;

    protected BaseResponse() {
    }

    protected BaseResponse(ResponseEnum code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }
}