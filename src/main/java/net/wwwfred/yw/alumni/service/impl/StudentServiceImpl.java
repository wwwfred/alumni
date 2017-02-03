package net.wwwfred.yw.alumni.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.wwwfred.framework.core.aop.cache.CacheAnnotaion;
import net.wwwfred.framework.core.dao.DaoQueryCondition;
import net.wwwfred.framework.core.dao.DaoQueryOperator;
import net.wwwfred.framework.core.dao.mybatis.MybatisDao;
import net.wwwfred.framework.util.code.CodeUtil;
import net.wwwfred.yw.alumni.model.StudentModel;
import net.wwwfred.yw.alumni.model.UserModel;
import net.wwwfred.yw.alumni.model.YearModel;
import net.wwwfred.yw.alumni.model.YearStudentModel;
import net.wwwfred.yw.alumni.service.StudentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class StudentServiceImpl implements StudentService {

	@Resource
	private MybatisDao mybatisDao;
	
//	@Override
//	public YearModel queryYear(String parameter) {
//		return mybatisDao.selectOne(null, YearModel.class, new DaoQueryCondition("code", DaoQueryOperator.EQ, parameter));
//	}
//
//	@Override
//	public List<YearStudentModel> queryYearStudent(Long id) {
//		return mybatisDao.selectList(null, false, YearStudentModel.class, null, null, new DaoQueryCondition("yearID", DaoQueryOperator.EQ, id));
//	}


	@Override
	public List<StudentModel> queryStudent(Collection<Long> studentIdSet) {
		return mybatisDao.selectList(null,false, StudentModel.class, null, null, new DaoQueryCondition("id", DaoQueryOperator.IN, studentIdSet));
	}

	@Override
	public YearStudentModel queryYearStudent(String studentCode) {
		return mybatisDao.selectOne(null, YearStudentModel.class, new DaoQueryCondition("code", DaoQueryOperator.EQ, studentCode));
	}

	@Override
	@Transactional
	public void insertStudentAndUpdateUserYearStudent(
			StudentModel studentModel, YearStudentModel yearStudentModel,
			UserModel userModel) {
		studentModel = mybatisDao.insertOne(studentModel);
		yearStudentModel.setStudentID(studentModel.getId());
		userModel.setStudentID(studentModel.getId());
		mybatisDao.updateOne(yearStudentModel);
		mybatisDao.updateOne(userModel);
	}

	@Override
	public List<YearStudentModel> queryYearStudentByStudentID(Long studentId) {
		return mybatisDao.selectList(null, false, YearStudentModel.class, null, null, new DaoQueryCondition("studentID", DaoQueryOperator.EQ, studentId));
	}

	@Override
	public StudentModel getStudent(Long studentID) {
		return mybatisDao.get(StudentModel.class, studentID);
	}

	@Override
	@CacheAnnotaion
	public Map<YearStudentModel, StudentModel> queryYearStudentByYearCode(Boolean nocache,
			String yearCode) {
		Map<YearStudentModel, StudentModel> studentMap = new HashMap<YearStudentModel, StudentModel>();
		YearModel year = mybatisDao.selectOne(null, YearModel.class, new DaoQueryCondition("code", DaoQueryOperator.EQ, yearCode));
		List<YearStudentModel> yearStudentList = mybatisDao.selectList(null, false, YearStudentModel.class, null, null, new DaoQueryCondition("yearID", DaoQueryOperator.EQ, year.getId()));
		for (YearStudentModel yearStudent : yearStudentList) {
			studentMap.put(yearStudent, CodeUtil.isEmpty(yearStudent.getStudentID())?null:mybatisDao.get(StudentModel.class,yearStudent.getStudentID()));
		}
		return studentMap;
	}

	@Override
	@CacheAnnotaion
	public Map<YearModel, List<YearStudentModel>> queryYearStudentBySchoolID(
			Boolean nocache, Long id) {
		Map<YearModel, List<YearStudentModel>> yearStudentMap = new HashMap<YearModel, List<YearStudentModel>>();
		List<YearModel> yearList = mybatisDao.selectList(null, false, YearModel.class, null, null, new DaoQueryCondition("schoolID", DaoQueryOperator.EQ, id));
		for (YearModel yearModel : yearList) {
			List<YearStudentModel> yearStudentList = mybatisDao.selectList(null, false, YearStudentModel.class, null, null, new DaoQueryCondition("yearID", DaoQueryOperator.EQ, yearModel.getId()));
			yearStudentMap.put(yearModel, yearStudentList);
		}
		return yearStudentMap;
	}

	@Override
	@Transactional
	public void insertYearStudent(
			Map<YearModel, List<YearStudentModel>> yearStudentModelMap) {
		
		for (YearModel yearModel : yearStudentModelMap.keySet()) {
			yearModel = mybatisDao.insertOne(yearModel);
			List<YearStudentModel> yearStudentModelList = yearStudentModelMap.get(yearModel);
			for (YearStudentModel yearStudentModel : yearStudentModelList) {
				yearStudentModel.setYearID(yearModel.getId());
				mybatisDao.insertOne(yearStudentModel);
			}
		}
		
	}

}
