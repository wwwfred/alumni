package net.wwwfred.yw.alumni.enumeration;

import java.util.HashMap;
import java.util.Map;

/** 10访客，11普通注册用户，20已认领用户，21已认领超级用户，30黑名单用户（如访客一直发短信不注册） */
public enum UserLevelEnum {

	visitor("10","访客"),register("11","普通注册用户"),claimedUser("20","已认领用户"),blacker("30","黑名单"),deprecated("31","已注销");
	private String code;
	private String name;
	private static Map<String,UserLevelEnum> map = initMap();
	
	private UserLevelEnum(String code,String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.code = code;
	}
	private static Map<String, UserLevelEnum> initMap() {
		// TODO Auto-generated method stub
		Map<String,UserLevelEnum> map = new HashMap<String, UserLevelEnum>();
		for (UserLevelEnum one : UserLevelEnum.values()) {
			map.put(one.code, one);
		}
		return map;
	}
	public static UserLevelEnum getInstance(String code)
	{
		return map.get(code);
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
