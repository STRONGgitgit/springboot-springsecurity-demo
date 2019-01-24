package com.example.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.dao.RoleDao;
import com.example.model.Role;
import com.example.model.RoleResources;
import com.example.model.URole;
import com.example.service.RoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService{
	
	@Resource
	private RoleDao roleDao;
	/*@Resource
	private UserDao userDao;*/
	
	@Override
	public PageInfo<Role> selectByPage(Role role,int start, int length) {
		int page = start/length+1;  
		//分页查询
        PageHelper.startPage(page, length);
        List<Role> rolelist = roleDao.queryAll(role);
        return new PageInfo<>(rolelist);
	}

	@Override
	public List<URole> queryRoleListWithUser(Integer id) {
		return roleDao.queryRoleListWithUser(id);
	}

	@Override
	@CacheEvict(cacheNames="myCache",allEntries=true)
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
	public void saveRoleResources(RoleResources roleResources) {
		String roleId = roleResources.getRoleId();
		String resourcesIds = roleResources.getResourcesId();
		//先删除
		roleDao.deleteRoleResources(Integer.valueOf(roleId));
		//添加
		if(!StringUtils.isEmpty(resourcesIds)){
			String[] resourcesIdArray = resourcesIds.split(",");
			for (String resourcesId : resourcesIdArray) {
				roleResources.setResourcesId(resourcesId);
				roleDao.addRoleResources(roleResources);
			}
		}
	}

	@Override
	public void addRole(Role role) {
		roleDao.addEntity(role);
	}

	@Override
	public void delRole(Integer id) {
		roleDao.deleteEntity(id);
	}
	
}
