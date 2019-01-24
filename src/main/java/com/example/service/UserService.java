package com.example.service;

import com.example.model.User;
import com.example.model.UserRole;
import com.github.pagehelper.PageInfo;


public interface UserService {

	public User findUserByName(String name);
	
	 /**
     * 根据条件分页查询
     *
     * @param user
     * @param
     * @param
     * @return
     */
    public PageInfo<User> selectByPage(User user, int start, int length);
    
	public void saveUserRoles(UserRole userRole);
	
	public void addUser(User user);
	
	public void delUser(Integer id);
	
	public User queryByName(String username);
}
