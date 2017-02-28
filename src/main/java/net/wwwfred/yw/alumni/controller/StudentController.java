package net.wwwfred.yw.alumni.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wwwfred.framework.core.exception.FrameworkRuntimeException;
import net.wwwfred.framework.core.web.HttpServletRequestWrapper;
import net.wwwfred.framework.core.web.ServletUtil;
import net.wwwfred.framework.core.web.UploadFilePO;
import net.wwwfred.framework.spi.response.BaseResponse;
import net.wwwfred.framework.util.charset.PinyinUtil;
import net.wwwfred.framework.util.code.CodeUtil;
import net.wwwfred.framework.util.io.IOUtil;
import net.wwwfred.framework.util.json.JSONUtil;
import net.wwwfred.framework.util.sort.SortUtil;
import net.wwwfred.framework.util.string.StringUtil;
import net.wwwfred.yw.alumni.config.CommonConfig;
import net.wwwfred.yw.alumni.controller.po.LoginPO;
import net.wwwfred.yw.alumni.controller.po.LoginYearStudentPO;
import net.wwwfred.yw.alumni.controller.po.StudentPO;
import net.wwwfred.yw.alumni.controller.po.YearSchoolPO;
import net.wwwfred.yw.alumni.controller.po.YearStudentPO;
import net.wwwfred.yw.alumni.model.SchoolModel;
import net.wwwfred.yw.alumni.model.StudentModel;
import net.wwwfred.yw.alumni.model.UserModel;
import net.wwwfred.yw.alumni.model.YearModel;
import net.wwwfred.yw.alumni.model.YearStudentModel;
import net.wwwfred.yw.alumni.service.SchoolService;
import net.wwwfred.yw.alumni.service.StudentService;
import net.wwwfred.yw.alumni.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("student")
public class StudentController {
	
	@Resource
	private StudentService studentService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private SchoolService schoolService;
	
	private static final String sessionAttributeLoginUserKey = "loginUser";
	
	private static final String studentImagePath = "/data/image/alumni";
	
	@RequestMapping("studentList.do")
	@ResponseBody
	public String studentList(HttpServletRequest request, HttpServletResponse response)
	{
		LoginYearStudentPO loginYearStudentPO = new LoginYearStudentPO();
		
		LoginPO loginPO = (LoginPO) request.getSession().getAttribute(sessionAttributeLoginUserKey);
		loginYearStudentPO.setLoginPO(loginPO);
		
		Boolean nocache = StringUtil.getValueFromString(request.getParameter("nocache"), Boolean.class,false);
		String name = request.getParameter("name");
		Map<YearStudentModel,StudentModel> yearStudentMap = studentService.queryYearStudentByYearCode(nocache,request.getParameter("yearCode"));
		if(!CodeUtil.isEmpty(name))
		{
			Map<YearStudentModel,StudentModel> newYearStudentMap = new LinkedHashMap<YearStudentModel, StudentModel>();
			for (YearStudentModel yearStudentModel : yearStudentMap.keySet()) {
				if(!yearStudentModel.getName().contains(name))
				{
					continue;
				}
				newYearStudentMap.put(yearStudentModel, yearStudentMap.get(yearStudentModel));
			}
			yearStudentMap = newYearStudentMap;
		}
		
		List<YearStudentPO> yearStudentPOList = new ArrayList<YearStudentPO>();
		for (YearStudentModel yearStudentModel : yearStudentMap.keySet()) {
			YearStudentPO yearStudentPO = new YearStudentPO();
			yearStudentPO.setCode(yearStudentModel.getCode());
			yearStudentPO.setName(yearStudentModel.getName());
			yearStudentPO.setShortPinyinName(yearStudentModel.getShortPinyinName());
			yearStudentPO.setPinyinName(yearStudentModel.getPinyinName());
			
			StudentModel studentModel = yearStudentMap.get(yearStudentModel);
			if(studentModel!=null)
			{
				yearStudentPO.setStudentID(StringUtil.toString(studentModel.getId()));
				yearStudentPO.setStudentName(studentModel.getName());
				yearStudentPO.setStudentHeadUrl(studentModel.getHeadUrl());
			}
			
			yearStudentPOList.add(yearStudentPO);
		}
		yearStudentPOList = SortUtil.getSortedList(yearStudentPOList,
				new Comparator<YearStudentPO>() {

					@Override
					public int compare(YearStudentPO o1, YearStudentPO o2) {
						if (CodeUtil.isEmpty(o1.getStudentID())
								&& !CodeUtil.isEmpty(o2.getStudentID()))
							return 1;
						else if (!CodeUtil.isEmpty(o1.getStudentID())
								&& CodeUtil.isEmpty(o2.getStudentID()))
							return -1;

						String s1 = CodeUtil.isEmpty(o1.getShortPinyinName()) ? PinyinUtil
								.getShortPinyin(o1.getName()) : o1
								.getShortPinyinName();
						String s2 = CodeUtil.isEmpty(o1.getShortPinyinName()) ? PinyinUtil
								.getShortPinyin(o2.getName()) : o2
								.getShortPinyinName();
						if (s1.compareTo(s2) == 0) {
							// StringBuilder sb = new StringBuilder();
							// sb.append(s1+"&"+s2+",");
							s1 = CodeUtil.isEmpty(o1.getPinyinName()) ? PinyinUtil
									.convertToPinyinString(o1.getName(), " ")
									: o1.getPinyinName();
							s2 = CodeUtil.isEmpty(o1.getPinyinName()) ? PinyinUtil
									.convertToPinyinString(o2.getName(), " ")
									: o2.getPinyinName();
							// sb.append(s1+"&"+s2);
							// System.out.println(sb.toString());
						}
						return s1.compareTo(s2);
					}
				});
		loginYearStudentPO.setYearStudent(yearStudentPOList.toArray(new YearStudentPO[]{}));
		
		return JSONUtil.toString(new BaseResponse<LoginYearStudentPO>(loginYearStudentPO));
	}
	
	@RequestMapping("studentClaim.do")
	@ResponseBody
	public String studentClaim(HttpServletRequest request, HttpServletResponse response)
	{
		HttpServletRequestWrapper httpRequest = new HttpServletRequestWrapper(request);
		Map<String,List<Object>> httpRequestMap = ServletUtil.getMapFromRequest(httpRequest);
		Map<String,Object> requestMap = new HashMap<String, Object>();
		for (Entry<String, List<Object>> entry : httpRequestMap.entrySet()) {
			requestMap.put(entry.getKey(), CodeUtil.isEmpty(entry.getValue())?null:entry.getValue().get(0));
		}
		
		UploadFilePO userHeadPO = (!CodeUtil.isEmpty(requestMap.get("userHead"))&&requestMap.get("userHead") instanceof UploadFilePO)
				?(UploadFilePO)requestMap.get("userHead"):null;
		
		String studentCode = StringUtil.toString(requestMap.get("studentCode"));
		String mobilephone = StringUtil.toString(requestMap.get("mobilephone"));
		if(CodeUtil.isEmpty(studentCode,mobilephone))
		{
			throw new FrameworkRuntimeException("认领学生，学生姓名与登录的用户都不能为空");
		}
		
		LoginPO loginPO = (LoginPO) request.getSession().getAttribute(sessionAttributeLoginUserKey);
		if(loginPO==null||!loginPO.getMobilephone().equals(mobilephone))
		{
			throw new FrameworkRuntimeException("认领学生必须先登录");
		}
		
		YearStudentModel yearStudentModel = studentService.queryYearStudent(studentCode);
		if(!CodeUtil.isEmpty(yearStudentModel.getStudentID()))
		{
			throw new FrameworkRuntimeException("该学生已被认领不能再次被认领");
		}
		
		UserModel userModel = userService.queryUser(mobilephone);
		if(!CodeUtil.isEmpty(userModel.getStudentID()))
		{
			throw new FrameworkRuntimeException("该登录的用户已认领了一名学生，不可同时认领多名学生");
		}
		
		String headUrl = null;
		if(userHeadPO!=null)
		{
			String path = studentCode.replace("_", "/");
			IOUtil.writeLocalData(userHeadPO.getFielData(), studentImagePath+"/"+path, userHeadPO.getFileName());
			headUrl = CommonConfig.IAMGE_BASE_URL_STUDENT+"/"+path+"/"+userHeadPO.getFileName();
		}
		
		StudentModel studentModel = new StudentModel();
		studentModel.setIdCard(StringUtil.toString(requestMap.get("idCard")));
		studentModel.setName(StringUtil.toString(requestMap.get("student")));
		studentModel.setMobilephone(StringUtil.toString(requestMap.get("mobilephone")));
		studentModel.setSex(StringUtil.toString(requestMap.get("sex")));
		studentModel.setBirthday(StringUtil.toString(requestMap.get("birthday")));
		studentModel.setCompany(StringUtil.toString(requestMap.get("company")));
		studentModel.setJobTitle(StringUtil.toString(requestMap.get("jobTitle")));
		studentModel.setAddressCity(StringUtil.toString(requestMap.get("city")));
		studentModel.setEmail(StringUtil.toString(requestMap.get("email")));
		studentModel.setHeadUrl(headUrl);
		studentService.insertStudentAndUpdateUserYearStudent(studentModel,yearStudentModel,userModel);
		
		// 更新缓存
		YearModel yearModel = schoolService.getYear(yearStudentModel.getYearID());
		SchoolModel schoolModel = schoolService.getSchool(yearModel.getSchoolID());
		studentService.queryYearStudentByYearCode(true, yearModel.getCode());
		schoolService.querySchoolList(true, null, null, Arrays.asList(schoolModel.getCode()));
		studentService.queryYearStudentBySchoolID(true, schoolModel.getId());
		
		loginPO.setStudentID(StringUtil.toString(studentModel.getId()));
		loginPO.setStudentName(studentModel.getName());
		
		return JSONUtil.toString(new BaseResponse<Object>(null));
	}
	
	@RequestMapping("getStudentInfo.do")
	@ResponseBody
	public String getStudentInfo(HttpServletRequest request, HttpServletResponse response)
	{
//		Boolean nocache = StringUtil.getValueFromString(request.getParameter("nocache"), Boolean.class,false);
		String studentCode = request.getParameter("studentCode");
		if(studentCode==null)
		{
			throw new FrameworkRuntimeException("查看学生详细信息，学生编码不能为空");
		}
		
		LoginPO loginPO = (LoginPO) request.getSession().getAttribute(sessionAttributeLoginUserKey);
		if(loginPO==null||CodeUtil.isEmpty(loginPO.getMobilephone()))
		{
			throw new FrameworkRuntimeException("查看学生详细信息，必须先登录");
		}
		else if(CodeUtil.isEmpty(loginPO.getStudentID()))
		{
			throw new FrameworkRuntimeException("查看学生详细信息，登录后必须先认领了自己");
		}
		
		YearStudentModel yearStudentModel = studentService.queryYearStudent(studentCode);
		Long studentId = yearStudentModel.getStudentID();
		if(CodeUtil.isEmpty(studentId))
		{
			throw new FrameworkRuntimeException("查看学生详细信息，该学生必须被认领");
		}
		List<StudentModel> studentModelList = studentService.queryStudent(Arrays.asList(studentId));
		StudentModel studentModel = CodeUtil.isEmpty(studentModelList)?null:studentModelList.get(0);
				
		StudentPO studentPO = new StudentPO();
		studentPO.setHeadUrl(studentModel.getHeadUrl());
		studentPO.setName(studentModel.getName());
		studentPO.setIdCard(studentModel.getIdCard());
		studentPO.setSex(studentModel.getSex());
		studentPO.setBirthday(studentModel.getBirthday());
		
		studentPO.setCompany(studentModel.getCompany());
		studentPO.setJobTitle(studentModel.getJobTitle());
		studentPO.setCity(studentModel.getAddressCity());
		
		studentPO.setMobilephone(studentModel.getMobilephone());
		studentPO.setEmail(studentModel.getEmail());
		
		List<YearSchoolPO> yearSchoolPOList = new ArrayList<YearSchoolPO>();
		List<YearStudentModel> yearStudentModelList = studentService.queryYearStudentByStudentID(studentId);
		for (YearStudentModel oneYearStudentModel : yearStudentModelList) {
			YearModel yearModel = schoolService.getYear(oneYearStudentModel.getYearID());
			SchoolModel schoolModel = schoolService.getSchool(yearModel.getSchoolID());
			YearSchoolPO yearSchoolPO = new YearSchoolPO();
			yearSchoolPO.setSchoolName(schoolModel.getSchoolName());
			yearSchoolPO.setSchoolCode(schoolModel.getCode());
			yearSchoolPO.setSchoolTypeName(schoolModel.getSchoolTypeName());
			yearSchoolPO.setSchoolTypeCode(schoolModel.getSchoolTypeCode());
			yearSchoolPO.setYear(yearModel.getName());
			yearSchoolPOList.add(yearSchoolPO);
		}
		studentPO.setYearSchool(yearSchoolPOList.toArray(new YearSchoolPO[]{}));
		
		return JSONUtil.toString(new BaseResponse<Object>(studentPO));
	}
}
