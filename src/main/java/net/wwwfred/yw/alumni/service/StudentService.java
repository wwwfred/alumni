package net.wwwfred.yw.alumni.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.wwwfred.yw.alumni.model.StudentModel;
import net.wwwfred.yw.alumni.model.UserModel;
import net.wwwfred.yw.alumni.model.YearModel;
import net.wwwfred.yw.alumni.model.YearStudentModel;

public interface StudentService {

//	YearModel queryYear(String parameter);

//	List<YearStudentModel> queryYearStudent(Long id);

	List<StudentModel> queryStudent(Collection<Long> studentIdSet);

	YearStudentModel queryYearStudent(String studentCode);

	void insertStudentAndUpdateUserYearStudent(StudentModel studentModel,
			YearStudentModel yearStudentModel, UserModel userModel);

	List<YearStudentModel> queryYearStudentByStudentID(Long studentId);

	StudentModel getStudent(Long studentID);

	Map<YearStudentModel, StudentModel> queryYearStudentByYearCode(
			Boolean nocache, String yearCode);

	Map<YearModel, List<YearStudentModel>> queryYearStudentBySchoolID(
			Boolean nocache, Long id);

	void insertYearStudent(
			Map<YearModel, List<YearStudentModel>> yearStudentModelMap);

}
