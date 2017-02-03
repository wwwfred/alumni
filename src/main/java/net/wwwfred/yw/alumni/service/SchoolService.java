package net.wwwfred.yw.alumni.service;

import java.util.Collection;
import java.util.List;

import net.wwwfred.yw.alumni.model.SchoolModel;
import net.wwwfred.yw.alumni.model.YearModel;

public interface SchoolService {

	List<SchoolModel> querySchoolList(Boolean nocache,String parameter, String parameter2,
			Collection<String> collection);
	
	SchoolModel getSchool(Long schoolID);

	YearModel getYear(Long yearId);

//	List<YearModel> queryYearBySchoolId(List<Long> schoolIdList);

//	Map<Long, Long> countYearStudentByYearId(List<Long> yearIdList);

//	Map<Long, Long> countYearClaimStudentByYearId(List<Long> yearIdList);

}
