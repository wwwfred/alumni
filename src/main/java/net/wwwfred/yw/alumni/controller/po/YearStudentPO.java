package net.wwwfred.yw.alumni.controller.po;

import net.wwwfred.framework.po.BasePO;

public class YearStudentPO extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String studentID;
	private String studentName;
	private String studentHeadUrl;
	
	private String name;
	private String shortPinyinName;
	private String pinyinName;
	private String code;
	
	public String getStudentHeadUrl() {
		return studentHeadUrl;
	}
	public void setStudentHeadUrl(String studentHeadUrl) {
		this.studentHeadUrl = studentHeadUrl;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getShortPinyinName() {
		return shortPinyinName;
	}
	public void setShortPinyinName(String shortPinyinName) {
		this.shortPinyinName = shortPinyinName;
	}
	public String getPinyinName() {
		return pinyinName;
	}
	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}
	
}
