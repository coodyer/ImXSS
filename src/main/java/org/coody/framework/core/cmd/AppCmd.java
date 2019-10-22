package org.coody.framework.core.cmd;

import org.coody.framework.context.base.BaseRespVO;
import org.coody.framework.context.enm.ResCodeEnum;
import org.coody.framework.context.entity.Header;
import org.coody.framework.util.SpringContextHelper;
import org.coody.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

/**
 * 面向APP的CMD
 * @author admin
 *
 */
public class AppCmd extends RootCmd {

		public String execute(Header header,String body) {
			logger.debug("命令:" + header.getCmd());
			AppCmd api = (AppCmd) SpringContextHelper.getBean(header.getCmd());
			if (StringUtil.isNullOrEmpty(api)) {
				logger.info("命令不存在:" + header.getCmd());
				return new BaseRespVO(ResCodeEnum.ACTION_NOT_FOUND).toJson();
			}
			Object result = doActions(header, body);
			return JSON.toJSONString(result);
		}

}
