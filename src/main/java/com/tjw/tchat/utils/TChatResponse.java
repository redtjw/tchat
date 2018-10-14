package com.tjw.tchat.utils;

import lombok.Data;

/**
 * @Description: 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 */
@Data
public class TChatResponse {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;
    
    private String ok;	// 不使用

    public static TChatResponse build(Integer status, String msg, Object data) {
        return new TChatResponse(status, msg, data);
    }

    public static TChatResponse ok(Object data) {
        return new TChatResponse(data);
    }

    public static TChatResponse ok() {
        return new TChatResponse(null);
    }
    
    public static TChatResponse errorMsg(String msg) {
        return new TChatResponse(500, msg, null);
    }
    
    public static TChatResponse errorMap(Object data) {
        return new TChatResponse(501, "error", data);
    }
    
    public static TChatResponse errorTokenMsg(String msg) {
        return new TChatResponse(502, msg, null);
    }
    
    public static TChatResponse errorException(String msg) {
        return new TChatResponse(555, msg, null);
    }

    public TChatResponse() {

    }

    public TChatResponse(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public TChatResponse(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }
}
