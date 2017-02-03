package net.wwwfred.yw.alumni.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wwwfred.framework.core.cache.RedisCache;
import net.wwwfred.framework.core.exception.TeshehuiRuntimeException;
import net.wwwfred.framework.spi.response.BaseResponse;
import net.wwwfred.framework.util.code.CodeUtil;
import net.wwwfred.framework.util.code.NumberingRuleUtil;
import net.wwwfred.framework.util.json.JSONUtil;
import net.wwwfred.framework.util.log.LogUtil;
import net.wwwfred.framework.util.properties.PropertiesUtil;
import net.wwwfred.framework.util.secret.EncryptUtil;
import net.wwwfred.framework.util.string.StringUtil;
import net.wwwfred.yw.alumni.config.CommonConfig;
import net.wwwfred.yw.alumni.controller.po.LoginPO;
import net.wwwfred.yw.alumni.controller.po.UserRegPO;
import net.wwwfred.yw.alumni.enumeration.UserLevelEnum;
import net.wwwfred.yw.alumni.model.UserModel;
import net.wwwfred.yw.alumni.service.StudentService;
import net.wwwfred.yw.alumni.service.UserService;
import net.wwwfred.yw.alumni.util.CommonUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	private UserService userService;
	
	@Resource
	private StudentService studentService;
	
	@Resource
	private RedisCache redisCache;

	private static final String sessionAttributeLoginUserKey = "loginUser";

	private static final String userRegCheckCodePrefix = "MobileRegCheckCode_";
	
	private Integer userRegMaxCount = StringUtil.getValueFromString(PropertiesUtil.getValue(CommonConfig.CONFIG_FILE_NAME, "user_reg_max_count","3"),Integer.class);
	private Long userCheckCodeValidTime = StringUtil.getValueFromString(PropertiesUtil.getValue(CommonConfig.CONFIG_FILE_NAME, "userCheckCodeValidTime"),Long.class,10*60*1000L);
	
	@RequestMapping("isMobileReg.do")
	@ResponseBody
	public String isMobileReg(HttpServletRequest request,
			HttpServletResponse response) {
		UserRegPO userRegPO = new UserRegPO();
		
		String mobilephone = request.getParameter("mobilephone");
		UserModel user = userService.queryUser(mobilephone);
		userRegPO.setMobilephone(mobilephone);
		userRegPO.setRegResult(user==null?"0"
				:((UserLevelEnum.visitor.getCode().equals(user.getUserLevel())
						||UserLevelEnum.blacker.getCode().equals(user.getUserLevel()))?"0":"1"));

		return JSONUtil.toString(new BaseResponse<UserRegPO>(userRegPO));
	}
	
	@RequestMapping("sendMobileCheckCode.do")
	@ResponseBody
	public String sendMobileCheckCode(HttpServletRequest request,
			HttpServletResponse response) {
		String mobilephone = request.getParameter("mobilephone");
		String schoolWX = request.getParameter("schoolWX");
		
		if(CodeUtil.isEmpty(mobilephone))
		{
			throw new TeshehuiRuntimeException("发送短信验证码，手机号不能为空");
		}
		
		UserModel user = userService.queryUser(mobilephone);
		if(user!=null)
		{
			if(!user.getUserLevel().equals(UserLevelEnum.visitor.getCode()))
			{
				throw new TeshehuiRuntimeException("该手机号的用户已经注册，不能用户手机号验证码登录，请改用账号密码登录");
			}
			if(user.getUserLevel()==UserLevelEnum.deprecated.getCode())
			{
				throw new TeshehuiRuntimeException("该手机号用户已被系统注销");
			}
			if(user.getUserLevel()==UserLevelEnum.blacker.getCode()
					||user.getRegMsgCount()>=user.getRegMsgMaxCount())
			{
				if(user.getUserLevel()!=UserLevelEnum.blacker.getCode()&&user.getRegMsgCount()==user.getRegMsgMaxCount())
				{
					user.setUserLevel(UserLevelEnum.blacker.getCode());
					user.setRemark("该手机号多次发短信未注册为用户");
					user.setUpdateTime(new Date());
					userService.updateUser(user);
				}
				throw new TeshehuiRuntimeException("该手机号多次发短信未注册为用户，已被系统自动拉入黑名单，如有疑问请在微信公众号“"+schoolWX+"”给我们留言，将第一时间答复你");
			}
		}
		
		String checkCode = NumberingRuleUtil.newBaseCode("", false, "", 6, "");
		redisCache.setObject(userRegCheckCodePrefix+mobilephone, checkCode,userCheckCodeValidTime);
		LogUtil.i("set cache mobile checkCode:"+checkCode+",get cache result="+redisCache.getObject(userRegCheckCodePrefix+mobilephone));
		if(CommonConfig.SWITCH_SENND_MSG)
			CommonUtil.sendMessage2(mobilephone,checkCode);
		
		if(user==null)
		{
			UserModel userModel = new UserModel();
			userModel.setMobilephone(mobilephone);
			userModel.setUserLevel(UserLevelEnum.visitor.getCode());
			userModel.setRegMsgCount(1);
			userModel.setPassword(EncryptUtil.getMD5(checkCode));
			userModel.setRegMsgMaxCount(userRegMaxCount);
			userService.insertUser(userModel);
		}
		else
		{
			user.setRegMsgCount(user.getRegMsgCount()+1);
			user.setPassword(EncryptUtil.getMD5(checkCode));
			user.setUpdateTime(new Date());
			userService.updateUser(user);
		}
		return JSONUtil.toString(new BaseResponse<Object>(null));
	}

	@RequestMapping("userReg.do")
	@ResponseBody
	public String userReg(HttpServletRequest request,
			HttpServletResponse response) {

		String mobilephone = request.getParameter("mobilephone");
		String checkCode = request.getParameter("checkCode");
		if(CodeUtil.isEmpty(mobilephone,checkCode))
		{
			throw new TeshehuiRuntimeException("用户注册，手机号、验证码都不能为空");
		}
		
		UserModel user = userService.queryUser(mobilephone);
		if(user==null)
		{
			throw new TeshehuiRuntimeException("注册的手机号还未发送过验证码，请确认！");
		}
		if(user.getUserLevel()==UserLevelEnum.deprecated.getCode())
		{
			throw new TeshehuiRuntimeException("该手机号用户已被系统注销");
		}
		if(!UserLevelEnum.visitor.getCode().equals(user.getUserLevel())
				&&!UserLevelEnum.blacker.getCode().equals(user.getUserLevel()))
		{
			throw new TeshehuiRuntimeException("用户已注册不能重复注册");
		}
		
		String validCheckCode = (String) redisCache.getObject(userRegCheckCodePrefix+mobilephone);
		if(!checkCode.equals(EncryptUtil.getMD5(validCheckCode)))
		{
			throw new TeshehuiRuntimeException("验证码不正确或已失效");
		}
		
		user.setUserLevel(UserLevelEnum.register.getCode());
		user.setLoginCount(1);
		userService.updateUser(user);
		
		LoginPO loginPO = new LoginPO();
		loginPO.setMobilephone(mobilephone);
		request.getSession().setAttribute(sessionAttributeLoginUserKey, loginPO);

		return JSONUtil.toString(new BaseResponse<Object>(null));
	}
	
	@RequestMapping("userLogin.do")
	@ResponseBody
	public String userLogin(HttpServletRequest request,
			HttpServletResponse response) {

		String mobilephone = request.getParameter("mobilephone");
		String password = request.getParameter("password");
		if(CodeUtil.isEmpty(mobilephone,password))
		{
			throw new TeshehuiRuntimeException("用户登录，手机号、密码都不能为空");
		}
		
		UserModel user = userService.queryUser(mobilephone);
		if(user==null||UserLevelEnum.visitor.getCode().equals(user.getUserLevel()))
		{
			throw new TeshehuiRuntimeException("该手机号还未注册，不能登录");
		}
		if(user.getUserLevel()==UserLevelEnum.deprecated.getCode())
		{
			throw new TeshehuiRuntimeException("该手机号用户已被系统注销");
		}
		if(UserLevelEnum.blacker.getCode().equals(user.getUserLevel()))
		{
			throw new TeshehuiRuntimeException("该用户有违规操作，已被列入黑名单暂不允许登录");
		}
		
		if(!password.equals(user.getPassword()))
		{
			throw new TeshehuiRuntimeException("用户密码不正确");
		}
		user.setLoginCount(user.getLoginCount()+1);
		userService.updateUser(user);
		
		Long studentID = user.getStudentID();
		String studentName = studentID==null?null:studentService.getStudent(studentID).getName();
		
		LoginPO loginPO = new LoginPO();
		loginPO.setMobilephone(mobilephone);
		loginPO.setStudentID(StringUtil.toString(studentID));
		loginPO.setStudentName(studentName);
		request.getSession().setAttribute(sessionAttributeLoginUserKey, loginPO);

		return JSONUtil.toString(new BaseResponse<Object>(null));
	}
	
	@RequestMapping("editPassword.do")
	@ResponseBody
	public String editPassword(HttpServletRequest request,
			HttpServletResponse response) {

		String mobilephone = request.getParameter("mobilephone");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		if(CodeUtil.isEmpty(mobilephone,password,newPassword))
		{
			throw new TeshehuiRuntimeException("修改密码，手机号、密码都不能为空");
		}
		
		UserModel user = userService.queryUser(mobilephone);
		if(user==null||UserLevelEnum.visitor.getCode().equals(user.getUserLevel()))
		{
			throw new TeshehuiRuntimeException("该手机号还未注册，不能修改密码");
		}
		if(user.getUserLevel()==UserLevelEnum.deprecated.getCode())
		{
			throw new TeshehuiRuntimeException("该手机号用户已被系统注销");
		}
		if(UserLevelEnum.blacker.getCode().equals(user.getUserLevel()))
		{
			throw new TeshehuiRuntimeException("该用户有违规草操作，已被列入黑名单暂不允许登录");
		}
		
		if(!password.equals(user.getPassword()))
		{
			throw new TeshehuiRuntimeException("用户密码不正确");
		}
		user.setPassword(newPassword);
		user.setLoginCount(user.getLoginCount()+1);
		userService.updateUser(user);
		
		Long studentID = user.getStudentID();
		String studentName = studentID==null?null:studentService.getStudent(studentID).getName();
		
		LoginPO loginPO = new LoginPO();
		loginPO.setMobilephone(mobilephone);
		loginPO.setStudentID(StringUtil.toString(studentID));
		loginPO.setStudentName(studentName);
		request.getSession().setAttribute(sessionAttributeLoginUserKey, loginPO);
		
		return JSONUtil.toString(new BaseResponse<Object>(null));
	}

}
