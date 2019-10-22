package org.coody.framework.context.enm;

import java.lang.reflect.Field;

import org.coody.framework.util.PropertUtil;

public enum MsgEnum {

	AUDIO_ORDER(1, "来订单啦"),// 成功标志
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
		this.msg = msg;
	}
	MsgEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		try {
			Field f=PropertUtil.getField(MsgEnum.class, "AUDIO_ORDER");
			System.out.println(f.getName()+"="+f.get(null));
			System.out.println(Enum.class.isAssignableFrom(f.getType()));
			Object value=f.get(null);
			System.out.println(Enum.class.isAssignableFrom(value.getClass()));
			/*System.out.println(Enum.class.isAssignableFrom(MsgEnum.class));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", 2);
			map.put("msg", "测试");
			System.out.println("修改前枚举信息:"+JSON.toJSONString(PropertUtil.loadEnumRecord(MsgEnum.class)));
			//修改AUDIO_ORDER字段的内容
			PropertUtil.setEnumValue(MsgEnum.class, "AUDIO_ORDER", map);
			//修改AUDIO_ORDER字段名为TEST_NEW_NAME
			PropertUtil.setEnumFieldName(MsgEnum.class, "AUDIO_ORDER", "TEST_NEW_NAME");
			System.out.println("修改后枚举信息:"+JSON.toJSONString(PropertUtil.loadEnumRecord(MsgEnum.class)));
			MsgEnum enm=MsgEnum.AUDIO_ORDER;
			System.out.println(enm.code);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
