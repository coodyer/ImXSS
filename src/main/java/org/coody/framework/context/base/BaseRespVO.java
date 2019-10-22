package org.coody.framework.context.base;

import org.coody.framework.context.enm.ResCodeEnum;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class BaseRespVO extends BaseModel{

	private int code=100;
	private String msg;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public BaseRespVO(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public BaseRespVO(ResCodeEnum enm) {
		super();
		this.code = enm.getCode();
		this.msg=enm.getMsg();
	}
	@SuppressWarnings("unchecked")
	public <T> T pushEnum(ResCodeEnum enm) {
		this.code = enm.getCode();
		this.msg=enm.getMsg();
		return (T) this;
	}
	public static BaseRespVO getBaseRespVO(ResCodeEnum enm){
		BaseRespVO resp=new BaseRespVO();
		resp.pushEnum(enm);
		return resp;
	}
	public BaseRespVO(){
		pushEnum(ResCodeEnum.SUCCESS);
	}
	
	public String toJson(){
		return JSON.toJSONString(this);
	}
}
