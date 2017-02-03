package net.wwwfred.yw.alumni.controller.po;

import net.wwwfred.framework.po.BasePO;

public class StudentPO extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String idCard;
	private String headUrl;
	private String birthday;
	private String sex;
	
	private String jobTitle;
	private String company;
	private String city;
	
	private String mobilephone;
	private String email;
	
	private YearSchoolPO[] yearSchool;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public YearSchoolPO[] getYearSchool() {
		return yearSchool;
	}

	public void setYearSchool(YearSchoolPO[] yearSchool) {
		this.yearSchool = yearSchool;
	}
	
}
