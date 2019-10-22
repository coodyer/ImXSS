package org.coody.framework.context.base;

import org.coody.framework.context.entity.Header;

import com.alibaba.fastjson.annotation.JSONField;

@SuppressWarnings("serial")
public  class BaseReqVO extends Header {
	
	@JSONField(serialize=false)
	public Header getHeader(){
		return (Header)this;
	}
}
