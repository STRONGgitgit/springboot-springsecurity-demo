package com.example.service;

import com.example.model.RResources;
import com.example.model.Resources;
import com.github.pagehelper.PageInfo;


import java.util.List;

public interface ResourcesService {
	
	public List<RResources> resourcesListWithRole(Integer rid);

	public PageInfo<Resources> selectByPage(Resources resources, int start, int length);

	public void addResources(Resources resources);

	public void delResources(Integer id);

	public List<Resources> loadMenu(Resources resources);

	public List<Resources>queryAll();
}
