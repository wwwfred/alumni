package net.wwwfred.yw.alumni.model;

import net.wwwfred.framework.core.dao.TableAnnotaion;
import net.wwwfred.framework.model.BaseModel;

import org.springframework.stereotype.Component;

@Component
@TableAnnotaion(value="t_school",seqTableName="",dbValue="alumni_db")
public class SchoolModel extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	private String provinceCode;
	
	private String provinceName;
	
	/**　10初中，20高中，30大学，40硕士，50博士　*/
	private String schoolTypeCode;
	
	private String schoolTypeName;
	
	private String code;
	
	private String schoolName;
	
	private String schoolLogoUrl;
	
	private String schoolWX;

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolLogoUrl() {
		return schoolLogoUrl;
	}

	public void setSchoolLogoUrl(String schoolLogoUrl) {
		this.schoolLogoUrl = schoolLogoUrl;
	}

	public String getSchoolWX() {
		return schoolWX;
	}

	public void setSchoolWX(String schoolWX) {
		this.schoolWX = schoolWX;
	}
	
	public String getSchoolTypeCode() {
		return schoolTypeCode;
	}

	public void setSchoolTypeCode(String schoolTypeCode) {
		this.schoolTypeCode = schoolTypeCode;
	}

	public String getCode() {
		return code;
	}

	public String getSchoolTypeName() {
		return schoolTypeName;
	}

	public void setSchoolTypeName(String schoolTypeName) {
		this.schoolTypeName = schoolTypeName;
	}

}
