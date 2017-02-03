package net.wwwfred.yw.alumni.model;

import net.wwwfred.framework.core.dao.TableAnnotaion;
import net.wwwfred.framework.model.BaseModel;

import org.springframework.stereotype.Component;

@Component
@TableAnnotaion(value="t_year_student",seqTableName="",dbValue="alumni_db")
public class YearStudentModel extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String name;
	
	private String shortPinyinName;
	private String pinyinName;
	
	/** 所在班级 */
	private String yearClass;
	
	private Long yearID;
	
	private Long studentID;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYearClass() {
		return yearClass;
	}
	

	public Long getStudentID() {
		return studentID;
	}

	public void setStudentID(Long studentID) {
		this.studentID = studentID;
	}

	public void setYearClass(String yearClass) {
		this.yearClass = yearClass;
	}

	public Long getYearID() {
		return yearID;
	}

	public void setYearID(Long yearID) {
		this.yearID = yearID;
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
