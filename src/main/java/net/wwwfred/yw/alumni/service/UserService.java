package net.wwwfred.yw.alumni.service;

import net.wwwfred.yw.alumni.model.UserModel;

public interface UserService {

	UserModel queryUser(String parameter);

	void insertUser(UserModel userModel);

	void updateUser(UserModel user);

}
