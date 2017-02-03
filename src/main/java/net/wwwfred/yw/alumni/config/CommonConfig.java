package net.wwwfred.yw.alumni.config;

import net.wwwfred.framework.util.properties.PropertiesUtil;
import net.wwwfred.framework.util.string.StringUtil;

public class CommonConfig {

	public static String CONFIG_FILE_NAME = "config.properties";
	public static String CONFIG_SERVER_FILE_NAME = "config-server.properties";
	
	public static boolean SWITCH_SENND_MSG = StringUtil.getValueFromString(PropertiesUtil.getValue(CONFIG_SERVER_FILE_NAME, "switch_sennd_msg","false"),Boolean.class);
	
	public static String IAMGE_BASE_URL_STUDENT = StringUtil.getValueFromString(PropertiesUtil.getValue(CONFIG_SERVER_FILE_NAME, "IAMGE_BASE_URL_STUDENT","http://image.wwwfred.net/alumni"),String.class);
	
}
