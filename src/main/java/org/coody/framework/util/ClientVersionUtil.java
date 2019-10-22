package org.coody.framework.util;

import java.text.MessageFormat;

public class ClientVersionUtil {

	public static String parseVersionToString(Integer version){
		Integer versionHeader=version/10000;
		Integer versionBody=version%10000/100;
		Integer versionFoot=version%10000%100;
		String versionStr=MessageFormat.format("{0}.{1}.{2}", versionHeader.toString(),versionBody.toString(),versionFoot.toString());
		return versionStr;
	}
	public static Integer parseStringToVersion(String versionStr){
		String [] versionSection=versionStr.split("\\.");
		if(versionSection.length!=3){
			return null;
		}
		Integer versionHeader=StringUtil.toInteger(versionSection[0])*10000;
		Integer versionBody=StringUtil.toInteger(versionSection[1])*100;
		Integer versionFoot=StringUtil.toInteger(versionSection[2]);
		Integer version=versionHeader+versionBody+versionFoot;
		return version;
	}
	public static void main(String[] args) {
		parseStringToVersion("11.1.23");
	}
}
