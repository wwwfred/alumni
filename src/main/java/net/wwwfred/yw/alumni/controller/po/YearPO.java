package net.wwwfred.yw.alumni.controller.po;

import net.wwwfred.framework.po.BasePO;

public class YearPO extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String year;
	private String yearCode;
	private Integer claimResult1;
	private Integer claimResult2;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getYearCode() {
		return yearCode;
	}
	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}
	public Integer getClaimResult1() {
		return claimResult1;
	}
	public void setClaimResult1(Integer claimResult1) {
		this.claimResult1 = claimResult1;
	}
	public Integer getClaimResult2() {
		return claimResult2;
	}
	public void setClaimResult2(Integer claimResult2) {
		this.claimResult2 = claimResult2;
	}
	
}
