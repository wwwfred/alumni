package net.wwwfred.yw.alumni.model;

import net.wwwfred.framework.core.dao.TableAnnotaion;
import net.wwwfred.framework.model.BaseModel;

import org.springframework.stereotype.Component;

@Component
@TableAnnotaion(value="t_user",seqTableName="",dbValue="alumni_db")
public class UserModel extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	private String mobilephone;
	
	/** 10访客，11普通注册用户，20已认领用户，21已认领超级用户，30黑名单用户（如访客一直发短信不注册） */
	private String userLevel;
	
	private String password;
	
	/** 注册短信发送次数 */
	private Integer regMsgCount;
	
	/** 注册短信发送次数最大值，默认3 */
	private Integer regMsgMaxCount;
	
	/** 登录次数 */
	private Integer loginCount;
	
	private String remark;
	
	private Long studentID;

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRegMsgCount() {
		return regMsgCount;
	}

	public void setRegMsgCount(Integer regMsgCount) {
		this.regMsgCount = regMsgCount;
	}

	public Integer getRegMsgMaxCount() {
		return regMsgMaxCount;
	}

	public void setRegMsgMaxCount(Integer regMsgMaxCount) {
		this.regMsgMaxCount = regMsgMaxCount;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Long getStudentID() {
		return studentID;
	}

	public void setStudentID(Long studentID) {
		this.studentID = studentID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
