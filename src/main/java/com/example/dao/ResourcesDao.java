package com.example.dao;

import com.example.model.RResources;
import com.example.model.Resources;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @Auther: king
 * @Date: 2018/11/30 10:41
 * @Description:
 */
public interface ResourcesDao extends BaseDao{
    public List<RResources> resourcesListWithRole(Integer rid);

    public List<Resources> findAllResourcesWithRoles();

    public List<Resources> loadMenu(Resources resources);

}
