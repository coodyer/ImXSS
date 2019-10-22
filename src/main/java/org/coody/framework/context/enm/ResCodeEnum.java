package org.coody.framework.context.enm;

/**
 * 消息响应码枚举
 * 
 * @author deng
 *
 */
public enum ResCodeEnum {

	SUCCESS(0, "操作成功"),// 成功标志
	LOGIN_OUT(1,  "登录超时"),// 登录超时
	API_NOT_EXISTS(2,  "请求action不存在"),// 登录超时
	PARA_ERROR(3,  "参数验证不通过"), // 参数有误
	SYSTEM_ERROR(4,  "系统繁忙，请稍后再试"), //系统繁忙
	PARA_IS_NULL(5,"参数为空"),//自动验参标志
	PARAS_IS_NULL(6,"参数不能同时为空"),//自动验参标志
	ACTION_NOT_FOUND(7,"请求action不存在"),
	OTHER(-1,"其他错误"),
	;
	private int code;
	private String msg;

	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg=msg;
	}
	ResCodeEnum(int code,  String msg) {
		this.code = code;
		this.msg = msg;
	}

}
