package net.wwwfred.yw.alumni.controller.po;

import net.wwwfred.framework.po.BasePO;

public class YearSchoolPO extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String schoolCode;
	private String schoolName;
	private String schoolTypeName;
	private String schoolTypeCode;
	private String year;
	public String getSchoolCode() {
		return schoolCode;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getSchoolTypeName() {
		return schoolTypeName;
	}
	public void setSchoolTypeName(String schoolTypeName) {
		this.schoolTypeName = schoolTypeName;
	}
	public String getSchoolTypeCode() {
		return schoolTypeCode;
	}
	public void setSchoolTypeCode(String schoolTypeCode) {
		this.schoolTypeCode = schoolTypeCode;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
