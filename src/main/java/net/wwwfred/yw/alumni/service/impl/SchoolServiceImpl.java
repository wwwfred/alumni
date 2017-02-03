package net.wwwfred.yw.alumni.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import net.wwwfred.framework.core.aop.cache.CacheAnnotaion;
import net.wwwfred.framework.core.dao.DaoQueryCondition;
import net.wwwfred.framework.core.dao.DaoQueryOperator;
import net.wwwfred.framework.core.dao.mybatis.MybatisDao;
import net.wwwfred.framework.util.code.CodeUtil;
import net.wwwfred.yw.alumni.model.SchoolModel;
import net.wwwfred.yw.alumni.model.YearModel;
import net.wwwfred.yw.alumni.service.SchoolService;

import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Resource
	private MybatisDao mybatisDao;
	
	@Override
	@CacheAnnotaion
	public List<SchoolModel> querySchoolList(Boolean nocache,String parameter,
			String parameter2, Collection<String> collection) {
		
		List<DaoQueryCondition> conditionList = new ArrayList<DaoQueryCondition>();
		if(!CodeUtil.isEmpty(parameter))
		{
			conditionList.add(new DaoQueryCondition("provinceCode", DaoQueryOperator.EQ, parameter));
		}
		if(!CodeUtil.isEmpty(parameter2))
		{
			conditionList.add(new DaoQueryCondition("schoolTypeCode", DaoQueryOperator.EQ, parameter2));
		}
		if(!CodeUtil.isEmpty(collection))
		{
			conditionList.add(new DaoQueryCondition("code", DaoQueryOperator.IN, collection));
		}
		return mybatisDao.selectList(null, false, SchoolModel.class, null, null, conditionList.toArray(new DaoQueryCondition[]{}));
		
	}
	

	@Override
	public SchoolModel getSchool(Long schoolID) {
		return mybatisDao.get(SchoolModel.class, schoolID);
	}
	

	@Override
	public YearModel getYear(Long yearId) {
		return mybatisDao.get(YearModel.class, yearId);
	}

//	@Override
//	public List<YearModel> queryYearBySchoolId(List<Long> schoolIdList) {
//		
//		return mybatisDao.selectList(null, false, YearModel.class, null, null, new DaoQueryCondition("schoolID", DaoQueryOperator.IN, schoolIdList));
//		
//	}
//
//	@Override
//	public Map<Long, Long> countYearStudentByYearId(List<Long> yearIdList) {
//		
//		Map<Long,Long> result = new HashMap<Long, Long>();
//		for (Long yearId : yearIdList) {
//			
//			result.put(yearId, mybatisDao.count(null, false, YearStudentModel.class, new DaoQueryCondition("yearID", DaoQueryOperator.EQ, yearId)));
//		}
//		
//		return result;
//	}
//
//	@Override
//	public Map<Long, Long> countYearClaimStudentByYearId(List<Long> yearIdList) {
//		Map<Long,Long> result = new HashMap<Long, Long>();
//		for (Long yearId : yearIdList) {
//			
//			result.put(yearId, mybatisDao.count(null, false, YearStudentModel.class, new DaoQueryCondition[]{
//				new DaoQueryCondition("yearID", DaoQueryOperator.EQ, yearId)
//				,new  DaoQueryCondition("studentID", DaoQueryOperator.NIS, null)
//			}));
//		}
//		
//		return result;
//	}

}
