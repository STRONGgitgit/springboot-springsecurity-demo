package com.example.dao;


import com.example.model.RoleResources;

import java.util.List;

/**
 * @Auther: king
 * @Date: 2018/11/30 10:41
 * @Description:
 */
public interface RoleDao extends BaseDao{
    public List<com.example.model.URole> queryRoleListWithUser(Integer id);

    public void deleteRoleResources(Integer id);

    public void addRoleResources(RoleResources roleResources);


}
