package org.coody.framework.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.coody.framework.constant.ParaCheckFinal;
import org.coody.framework.context.annotation.ParamCheck;
import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.base.BaseModel;
import org.coody.framework.context.base.BaseRespVO;
import org.coody.framework.context.enm.ResCodeEnum;
import org.coody.framework.context.entity.BeanEntity;
import org.coody.framework.context.entity.Header;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class CmdUtil {
	
	private static final BaseLogger logger = BaseLogger.getLogger(CmdUtil.class);

	public static Object getAPIParas(Method api, String json, Header header)
			throws InstantiationException, IllegalAccessException {
		List<BeanEntity> entitys = PropertUtil.getMethodParas(api);
		if (StringUtil.isNullOrEmpty(entitys)) {
			return null;
		}
		Object[] paras = new Object[entitys.size()];
		Map<String, Object> paraMap = null;
		for (int i = 0; i < entitys.size(); i++) {
			try {
				if (Map.class.isAssignableFrom(entitys.get(i).getFieldType())) {
					paras[i] = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
					});
					continue;
				}
				// 请求bean装载
				if (BaseModel.class.isAssignableFrom(entitys.get(i).getFieldType())) {
					Object objValue = JSON.parseObject(json, entitys.get(i).getFieldType());
					if(objValue==null){
						objValue=entitys.get(i).getFieldType().newInstance();
					}
					if (!StringUtil.isNullOrEmpty(header)) {
						BeanUtils.copyProperties(header, objValue);
					}
					paras[i] = objValue;
					if (!StringUtil.isNullOrEmpty(paras[i])) {
						BaseRespVO respVO = checkPara((BaseModel) paras[i]);
						if (!StringUtil.isNullOrEmpty(respVO)) {
							return respVO;
						}
					}
					continue;
				}
				if (paraMap == null) {
					paraMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
					});
				}
				// 其他零散参数装载
				Object value = paraMap.get(entitys.get(i).getFieldName());
				ParamCheck check = (ParamCheck) entitys.get(i).getAnnotation(ParamCheck.class);
				BaseRespVO respVO = checkPara(check, entitys.get(i).getFieldName(), value, paraMap);
				if (!StringUtil.isNullOrEmpty(respVO)) {
					return respVO;
				}
				Object obj = PropertUtil.parseValue(value, entitys.get(i).getFieldType());
				paras[i] = obj;
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
		}
		return paras;
	}
	

	public static BaseRespVO checkPara(BaseModel reqVO) {
		List<BeanEntity> entitys = PropertUtil.getBeanFields(reqVO);
		for (BeanEntity entity : entitys) {
			ParamCheck check = entity.getSourceField().getAnnotation(ParamCheck.class);
			if (StringUtil.isNullOrEmpty(check)) {
				continue;
			}
			Object obj = PropertUtil.getFieldValue(reqVO, entity.getFieldName());
			String error = check.errorMsg();
			// 数据可空验证
			if (!check.allowNull()) {
				if (StringUtil.isNullOrEmpty(obj)) {
					return getParaErrResp(ResCodeEnum.PARA_IS_NULL, entity.getFieldName(), error);
				}
				if (!StringUtil.isNullOrEmpty(check.orNulls())) {
					List<Object> values = PropertUtil.getFieldValues(reqVO, check.orNulls());
					if (!StringUtil.isAllNull(values)) {
						return getParaErrResp(ResCodeEnum.PARAS_IS_NULL,
								entity.getFieldName() + ":" + check.orNulls().toString(), error);
					}
				}
			}
			if (StringUtil.isNullOrEmpty(obj)) {
				continue;
			}
			if (StringUtil.isNullOrEmpty(check.format())) {
				continue;
			}
			// 数据格式验证
			String currMatcher = null;
			for (String matcher : check.format()) {
				if (StringUtil.isMatcher(obj.toString(), matcher)) {
					currMatcher=null;
					break;
				}
				currMatcher = matcher;
			}
			if (!StringUtil.isNullOrEmpty(currMatcher)) {
				return getParaErrResp(ResCodeEnum.PARA_ERROR, entity.getFieldName() + ":" + obj.toString(), error);
			}
		}
		return null;
	}

	public static BaseRespVO checkPara(ParamCheck check, String fieldName, Object fieldValue, Map<String, Object> allParas) {
		if (StringUtil.isNullOrEmpty(check)) {
			return null;
		}
		String error = check.errorMsg();
		// 数据可空验证
		if (!check.allowNull()) {
			if (StringUtil.isNullOrEmpty(fieldValue)) {
				return getParaErrResp(ResCodeEnum.PARA_IS_NULL, fieldName, error);
			}
			if (!StringUtil.isNullOrEmpty(check.orNulls())) {
				List<Object> values = PropertUtil.getFieldValues(allParas, check.orNulls());
				if (!StringUtil.isAllNull(values)) {
					return getParaErrResp(ResCodeEnum.PARAS_IS_NULL, fieldName + ":" + check.orNulls().toString(),
							error);
				}
			}
		}
		// 数据格式验证
		if (StringUtil.isNullOrEmpty(fieldValue)) {
			return null;
		}
		if (StringUtil.isNullOrEmpty(check.format())) {
			return null;
		}
		// 数据格式验证
		String currMatcher = null;
		for (String matcher : check.format()) {
			if (StringUtil.isMatcher(fieldValue.toString(), matcher)) {
				currMatcher=null;
				break;
			}
			currMatcher = matcher;
		}
		if (!StringUtil.isNullOrEmpty(currMatcher)) {
			return getParaErrResp(ResCodeEnum.PARA_ERROR, fieldName + ":" + fieldValue.toString(), error);
		}
		return null;
	}

	public static BaseRespVO getParaErrResp(ResCodeEnum enm, String msg, String error) {
		BaseRespVO respVO = new BaseRespVO(enm);
		respVO.setMsg(respVO.getMsg() + "，" + msg);
		if (!StringUtil.isNullOrEmpty(error)) {
			respVO.setMsg(respVO.getMsg() + "(" + error + ")");
		}
		return respVO;
	}
	
	public static void main(String[] args) {
		String currMatcher = null;
		String [] format={ParaCheckFinal.EMAIL,ParaCheckFinal.USER_NAME};
		String fieldValue="38069189@qq.com";
		for (String matcher : format) {
			if (StringUtil.isMatcher(fieldValue.toString(), matcher)) {
				currMatcher=null;
				break;
			}
			currMatcher = matcher;
		}
		if (!StringUtil.isNullOrEmpty(currMatcher)) {
			return;
		}
	}
}
