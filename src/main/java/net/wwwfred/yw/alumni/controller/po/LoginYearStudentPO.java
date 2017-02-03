package net.wwwfred.yw.alumni.controller.po;

import net.wwwfred.framework.po.BasePO;

public class LoginYearStudentPO extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private YearStudentPO[] yearStudent;
	private LoginPO loginPO;
	
	public YearStudentPO[] getYearStudent() {
		return yearStudent;
	}
	public void setYearStudent(YearStudentPO[] yearStudent) {
		this.yearStudent = yearStudent;
	}
	public LoginPO getLoginPO() {
		return loginPO;
	}
	public void setLoginPO(LoginPO loginPO) {
		this.loginPO = loginPO;
	}
	
}
