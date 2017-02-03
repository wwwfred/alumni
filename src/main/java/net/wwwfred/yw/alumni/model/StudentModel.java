package net.wwwfred.yw.alumni.model;

import net.wwwfred.framework.core.dao.TableAnnotaion;
import net.wwwfred.framework.model.BaseModel;

import org.springframework.stereotype.Component;

@Component
@TableAnnotaion(value="t_student",seqTableName="",dbValue="alumni_db")
public class StudentModel extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	private String idCard;
	
	private String name;
	
	private String sex;
	
	private String birthday;
	
	private String addressCity;
	
	private String company;
	
	private String jobTitle;

	private String mobilephone;
	
	private String email;
	
	private String headUrl;

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
