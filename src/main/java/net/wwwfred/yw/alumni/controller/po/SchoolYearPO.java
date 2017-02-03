package net.wwwfred.yw.alumni.controller.po;

import net.wwwfred.framework.po.BasePO;

public class SchoolYearPO extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String school;
	private String schoolCode;
	private String schoolWX;
	private String schoolLogoUrl;
	private YearPO[] year;
	
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public YearPO[] getYear() {
		return year;
	}
	public void setYear(YearPO[] year) {
		this.year = year;
	}
	public String getSchoolWX() {
		return schoolWX;
	}
	public void setSchoolWX(String schoolWX) {
		this.schoolWX = schoolWX;
	}
	public String getSchoolLogoUrl() {
		return schoolLogoUrl;
	}
	public void setSchoolLogoUrl(String schoolLogoUrl) {
		this.schoolLogoUrl = schoolLogoUrl;
	}
	
}
