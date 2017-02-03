package net.wwwfred.yw.alumni.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import net.wwwfred.framework.core.dao.DaoQueryCondition;
import net.wwwfred.framework.core.dao.DaoQueryOperator;
import net.wwwfred.framework.core.dao.mybatis.MybatisDao;
import net.wwwfred.yw.alumni.model.UserModel;
import net.wwwfred.yw.alumni.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private MybatisDao mybatisDao;
	
	@Override
	public UserModel queryUser(String parameter) {
		// TODO Auto-generated method stub
		return mybatisDao.selectOne(null, UserModel.class, new DaoQueryCondition("mobilephone", DaoQueryOperator.EQ, parameter));
	}

	@Override
	public void insertUser(UserModel userModel) {
		// TODO Auto-generated method stub
		mybatisDao.insertOne(userModel);
	}

	@Override
	public void updateUser(UserModel user) {
		// TODO Auto-generated method stub
		user.setUpdateTime(new Date());
		mybatisDao.updateOne(user);
	}

}
