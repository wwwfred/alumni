package net.wwwfred.yw.alumni.manager.impl;
//package com.teshehui.alumni.app.manager.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Component;
//
//import com.teshehui.alumni.app.manager.util.CheckBaseRequestUtil;
//import com.teshehui.alumni.app.service.MyService;
//import com.teshehui.client.common.po.ResultSetPO;
//import com.teshehui.client.common.spi.request.BaseRequest;
//import com.teshehui.client.common.spi.response.BaseResponse;
//import com.teshehui.client.demo.manager.MyManager;
//import com.teshehui.client.demo.spi.po.UserPO;
//import com.teshehui.client.demo.spi.request.CreateUserRequest;
//import com.teshehui.client.demo.spi.request.DeleteUserRequest;
//import com.teshehui.client.demo.spi.request.GetUserCashAccountRequest;
//import com.teshehui.client.demo.spi.request.GetUserFromNameRequest;
//import com.teshehui.client.demo.spi.request.GetUserRequest;
//import com.teshehui.client.demo.spi.request.QueryUserInfoRequest;
//import com.teshehui.client.demo.spi.request.QueryUserListRequest;
//import com.teshehui.client.demo.spi.request.SayHelloRequest;
//import com.teshehui.client.demo.spi.request.UpdateUserRequest;
//
//@Component("myManager")
//public class MyManagerImpl implements MyManager{
//
//	@Resource
//	private Map<String, MyService> myServiceMap;
//	
//	public BaseResponse<String> sayHello(SayHelloRequest requestPO) {
//		
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		return new BaseResponse<String>(myServiceMap.get(requestPO.getVersion()).sayHello(requestPO.getData()));
//
//	}
//	
//	public BaseResponse<ResultSetPO<UserPO>> getDataFromCache(
//			BaseRequest requestPO) {
//		
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		List<UserPO> userList = myServiceMap.get(requestPO.getVersion()).getDataFromCache();
//		ResultSetPO<UserPO> responseData = new ResultSetPO<UserPO>(1, userList.size(), userList);
//		return new BaseResponse<ResultSetPO<UserPO>>(responseData);
//	}
//	
//	@Override
//	public BaseResponse<UserPO> createUser(CreateUserRequest requestPO) {
//		
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		UserPO user = requestPO.getData();
//		myServiceMap.get(requestPO.getVersion()).createUser(user.getName(), user.getBirthday(), user.getSex(), user.getMobilePhone(), user.getBalance());
//		
//		return new BaseResponse<UserPO>(user);
//	}
//	
//	@Override
//	public BaseResponse<UserPO> getUser(GetUserRequest requestPO) {
//		
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		String mobilePhone = requestPO.getData();
//		UserPO user = null;
//		
//		return new BaseResponse<UserPO>(user);
//	}
//	
//	@Override
//	public BaseResponse<UserPO> getUser(GetUserFromNameRequest requestPO) {
//		
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		String userName = requestPO.getData();
//		UserPO user = null;
//		
//		return new BaseResponse<UserPO>(user);
//	}
//	
//	@Override
//	public BaseResponse<UserPO> getUserCashAccount(GetUserCashAccountRequest requestPO) {
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		String mobilePhone = requestPO.getData();
//		UserPO user = new UserPO();
//		
//		return new BaseResponse<UserPO>(user);
//	}
//	
//	@Override
//	public BaseResponse<UserPO> queryUserInfo(QueryUserInfoRequest requestPO) {
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		return new BaseResponse<UserPO>(null);
//	}
//	
//	@Override
//	public BaseResponse<ResultSetPO<UserPO>> queryMemberByName(QueryUserListRequest requestPO) {
//		
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		List<UserPO> list = new ArrayList<UserPO>();
//		ResultSetPO<UserPO> rs = new ResultSetPO<UserPO>(null, null, list);
//		return new BaseResponse<ResultSetPO<UserPO>>(rs);
//
//	}
//	
//	@Override
//	public BaseResponse<Object> updateUser(UpdateUserRequest requestPO) {
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		UserPO user = requestPO.getData();
//		myServiceMap.get(requestPO.getVersion()).updateUserNameByMobilePhone(user.getName(), user.getMobilePhone());
//		return new BaseResponse<Object>(null);
//	}
//	
//	@Override
//	public BaseResponse<Object> deleteUser(DeleteUserRequest requestPO) {
//		CheckBaseRequestUtil.checkRequestVersion(requestPO, myServiceMap);
//		
//		String mobilePhone = requestPO.getData();
//		myServiceMap.get(requestPO.getVersion()).deleteUserByMobilePhone(mobilePhone);
//		return new BaseResponse<Object>(null);
//	}
//	
//}
