package com.example.dao;


import com.example.model.User;
import com.example.model.UserRole;

/**
 * @Auther: king
 * @Date: 2018/11/30 10:42
 * @Description:
 */
public interface UserDao extends BaseDao{

    public User findUserByName(String username);

    public void delRolesByUserId(String userId);

    public void addUserRole(UserRole userRole);

    public User queryByName(String username);

}
