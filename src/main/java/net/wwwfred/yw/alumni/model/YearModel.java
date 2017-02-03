package net.wwwfred.yw.alumni.model;

import net.wwwfred.framework.core.dao.TableAnnotaion;
import net.wwwfred.framework.model.BaseModel;

import org.springframework.stereotype.Component;

@Component
@TableAnnotaion(value="t_year",seqTableName="",dbValue="alumni_db")
public class YearModel extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String name;
	
	private Long schoolID;

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

	public Long getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(Long schoolID) {
		this.schoolID = schoolID;
	}
	
}
