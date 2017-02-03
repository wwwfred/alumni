package net.wwwfred.yw.alumni.controller;
//package com.teshehui.alumni.app.controller;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.teshehui.client.common.po.UploadFilePO;
//import com.teshehui.client.common.spi.request.BaseRequest;
//import com.teshehui.client.common.spi.response.BaseResponse;
//import com.teshehui.client.demo.manager.MyManager;
//import com.teshehui.client.demo.spi.po.UserPO;
//import com.teshehui.client.demo.spi.request.CreateUserRequest;
//import com.teshehui.client.demo.spi.request.DeleteUserRequest;
//import com.teshehui.client.demo.spi.request.FileUploadRequest;
//import com.teshehui.client.demo.spi.request.GetUserRequest;
//import com.teshehui.client.demo.spi.request.QueryUserInfoRequest;
//import com.teshehui.client.demo.spi.request.QueryUserListRequest;
//import com.teshehui.client.demo.spi.request.SayHelloRequest;
//import com.teshehui.client.demo.spi.request.UpdateUserRequest;
//import com.teshehui.framework.web.ServletUtil;
//import com.teshehui.util.io.IOUtil;
//import com.teshehui.util.json.JSONUtil;
//
//@Controller
//@RequestMapping("/test")
//public class MyController {
//	
//	@Resource
//	private MyManager myManager;
//	
//	@RequestMapping("/hello.do")
//	@ResponseBody
//	public String hello(HttpServletRequest request, HttpServletResponse response)
//	{
//		String responseData = "hello world controller do";
//		return JSONUtil.toString(new BaseResponse<String>(responseData));
//	}
//	
//	@RequestMapping("/fileUpload.do")
//	@ResponseBody
//	public String fileUpload(HttpServletRequest request, HttpServletResponse response)
//	{        
//        FileUploadRequest requestPO = ServletUtil.getModelFromRequest(request, FileUploadRequest.class);
//        UserPO user = requestPO.getData();
//        UploadFilePO userHead = requestPO.getUserHead();
//        byte[] userHeadData = userHead.getFielData();
//        String fileName = userHead.getFileName();
//		
//        String filePath = IOUtil.writeLocalData(userHeadData, "/temp/fileUpload/", fileName);
//		user.setUserHeadUrl(filePath);
//		return JSONUtil.toString(new BaseResponse<UserPO>(user));
//	}
//	
//	@RequestMapping("/fileDownload.do")
//	@ResponseBody
//	public String fileDownload(HttpServletRequest request, HttpServletResponse response)
//	{        
//        byte[] fileData = IOUtil.readLoacalData("/temp/fileUpload/", "test.jpg");
//        ServletUtil.response(request, fileData, response);
//		
//		return JSONUtil.toString(new BaseResponse<Object>(null));
//	}
//	
//	@RequestMapping("/sayHello.do")
//	@ResponseBody
//	public String sayHello(HttpServletRequest request, HttpServletResponse response)
//	{
//		SayHelloRequest requestPO = ServletUtil.getModelFromRequest(request, SayHelloRequest.class);
//		return JSONUtil.toString(myManager.sayHello(requestPO));
//	}
//	
//	@RequestMapping("/getDataFromCache.do")
//	@ResponseBody
//	public String getDataFromCache(HttpServletRequest request, HttpServletResponse response)
//	{
//		BaseRequest requestPO = ServletUtil.getModelFromRequest(request, BaseRequest.class);
//		return JSONUtil.toString(myManager.getDataFromCache(requestPO));
//	}
//	
//	@RequestMapping("/createUser.do")
//	@ResponseBody
//	public String createUser(HttpServletRequest request, HttpServletResponse response)
//	{
//		CreateUserRequest requestPO = ServletUtil.getModelFromRequest(request, CreateUserRequest.class);
//		return JSONUtil.toString(myManager.createUser(requestPO));
//	}
//	
//	@RequestMapping("/queryUserInfo.do")
//	@ResponseBody
//	public String queryUserInfo(HttpServletRequest request, HttpServletResponse response)
//	{
//		QueryUserInfoRequest requestPO = ServletUtil.getModelFromRequest(request, QueryUserInfoRequest.class);
//		return JSONUtil.toString(myManager.queryUserInfo(requestPO));
//	}
//	
//	@RequestMapping("/getUserInfo.do")
//	@ResponseBody
//	public String getUserInfo(HttpServletRequest request, HttpServletResponse response)
//	{
//		GetUserRequest requestPO = ServletUtil.getModelFromRequest(request, GetUserRequest.class);
//		return JSONUtil.toString(myManager.getUser(requestPO));
//	}
//	
//	@RequestMapping("/getUserByNameInfo.do")
//	@ResponseBody
//	public String getUserByNameInfo(HttpServletRequest request, HttpServletResponse response)
//	{
//		QueryUserListRequest requestPO = ServletUtil.getModelFromRequest(request, QueryUserListRequest.class);
//		return JSONUtil.toString(myManager.queryMemberByName(requestPO));
//	}
//	
//	@RequestMapping("/updateUser.do")
//	@ResponseBody
//	public String updateUser(HttpServletRequest request, HttpServletResponse response)
//	{
//		UpdateUserRequest requestPO = ServletUtil.getModelFromRequest(request, UpdateUserRequest.class);
//		return JSONUtil.toString(myManager.updateUser(requestPO));
//	}
//	
//	@RequestMapping("/deleteUser.do")
//	@ResponseBody
//	public String deleteUser(HttpServletRequest request, HttpServletResponse response)
//	{
//		DeleteUserRequest requestPO = ServletUtil.getModelFromRequest(request, DeleteUserRequest.class);
//		return JSONUtil.toString(myManager.deleteUser(requestPO));
//	}
//	
//}
