package net.wwwfred.yw.alumni.controller.po;

import net.wwwfred.framework.po.BasePO;

public class UserRegPO extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mobilephone;
	
	private String regResult;

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getRegResult() {
		return regResult;
	}

	public void setRegResult(String regResult) {
		this.regResult = regResult;
	}

}
