package org.coody.framework.core.jdbc;

import org.coody.framework.util.AspectUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @author Coody
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource  {
	@Override
	protected Object determineCurrentLookupKey() {
		String template=AspectUtil.getCurrDBTemplate();
		if(StringUtil.isNullOrEmpty(template)){
			return "defaultTargetDataSource";
		}
		return template;
		//通过此处获得当前数据源 
	}
}
