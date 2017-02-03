package net.wwwfred.yw.alumni.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wwwfred.framework.spi.response.BaseResponse;
import net.wwwfred.framework.util.code.CodeUtil;
import net.wwwfred.framework.util.json.JSONUtil;
import net.wwwfred.framework.util.reflect.ReflectUtil;
import net.wwwfred.framework.util.sort.SortUtil;
import net.wwwfred.framework.util.string.StringUtil;
import net.wwwfred.yw.alumni.controller.po.SchoolYearPO;
import net.wwwfred.yw.alumni.controller.po.YearPO;
import net.wwwfred.yw.alumni.model.SchoolModel;
import net.wwwfred.yw.alumni.model.YearModel;
import net.wwwfred.yw.alumni.model.YearStudentModel;
import net.wwwfred.yw.alumni.service.SchoolService;
import net.wwwfred.yw.alumni.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("school")
public class SchoolController {
	
	@Resource
	private SchoolService schoolService;
	
	@Resource
	private StudentService studentService;
	
	@RequestMapping("schoolList.do")
	@ResponseBody
	public String schoolList(HttpServletRequest request, HttpServletResponse response)
	{
		List<SchoolYearPO> schoolYearPOList = new ArrayList<SchoolYearPO>();
		
		Boolean nocache = StringUtil.getValueFromString(request.getParameter("nocache"), Boolean.class,false);
		
		List<SchoolModel> schoolModelList = schoolService.querySchoolList(nocache,request.getParameter("provinceCode"),request.getParameter("schoolTypeCode"),new LinkedHashSet<String>(Arrays.asList(request.getParameterValues("schoolCode"))));
		for (SchoolModel schoolModel : schoolModelList) {
			SchoolYearPO schoolYearPO = new SchoolYearPO();
			schoolYearPO.setSchoolWX(schoolModel.getSchoolWX());
			schoolYearPO.setSchool(schoolModel.getSchoolName());
			schoolYearPO.setSchoolCode(schoolModel.getCode());
			schoolYearPO.setSchoolLogoUrl(schoolModel.getSchoolLogoUrl());
			List<YearPO> yearPOList = new ArrayList<YearPO>();
			Map<YearModel,List<YearStudentModel>> yearStudentMap = studentService.queryYearStudentBySchoolID(nocache,schoolModel.getId());
			for (YearModel yearModel : yearStudentMap.keySet()) {
				List<YearStudentModel> studentModelList = yearStudentMap.get(yearModel);
				int notNullStudentIDSize = 0;
				List<Long> studentIDList = ReflectUtil.getFieldList(studentModelList, "studentID");
				for (Long studentID : studentIDList) {
					if(!CodeUtil.isEmpty(studentID))
						notNullStudentIDSize++;
				}
				YearPO yearPO = new YearPO();
				yearPO.setYear(yearModel.getName());
				yearPO.setYearCode(yearModel.getCode());
				yearPO.setClaimResult1(notNullStudentIDSize);
				yearPO.setClaimResult2(studentModelList.size());
				yearPOList.add(yearPO);
			}
			yearPOList = SortUtil.getSortedList(yearPOList, new Comparator<YearPO>() {

				@Override
				public int compare(YearPO o1, YearPO o2) {
					return o2.getYear().compareTo(o1.getYear());
				}
			});
			schoolYearPO.setYear(yearPOList.toArray(new YearPO[]{}));
			schoolYearPOList.add(schoolYearPO);
		}
		
		return JSONUtil.toString(new BaseResponse<List<SchoolYearPO>>(schoolYearPOList));
			
	}

	
}
