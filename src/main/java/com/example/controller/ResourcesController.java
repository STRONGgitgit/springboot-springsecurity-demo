package com.example.controller;

import com.example.model.RResources;
import com.example.model.Resources;
import com.example.service.ResourcesService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/resources")
public class ResourcesController {
	
	@Resource
	private ResourcesService resourcesService;
	
	
	/**
	 * 查询资源列表，并且返回指定角色是否拥有该资源的权限
	 * @param rid 角色id
	 * @return
     * /resourcesListWithRole.do
	 */
	@RequestMapping(value="/resourcesListWithRole")
	public List<RResources> resourcesListWithRole(Integer rid){
		List<RResources> list = resourcesService.resourcesListWithRole(rid);
		return list;
	}

    /**
     * 分页查询
     * @param resources
     * @param draw
     * @param start
     * @param length
     * @return
     * resourcesList.do
     */
	@RequestMapping(value="/resourcesList",method = RequestMethod.GET)
	public Map<String,Object> resourcesList(Resources resources, String draw,
											@RequestParam(required = false, defaultValue = "1") int start,
											@RequestParam(required = false, defaultValue = "10") int length){
		Map<String,Object> map = new HashMap<>();
		PageInfo<Resources> pageInfo = resourcesService.selectByPage(resources, start, length);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
		return map;
	}

    /**
     * 添加资源
     * @param resources
     * @return
     * addResources.do
     */
	@RequestMapping(value = "/addResources",method = RequestMethod.POST)
	public String addResources(Resources resources){
		try {
			resourcesService.addResources(resources);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

    /**
     * 删除资源
     * @param id
     * @return
     * delResources.do
     */
	@RequestMapping(value = "/delResources/{id}",method = RequestMethod.DELETE)
	public String delResources(@PathVariable("id")Integer id){
		try {
			resourcesService.delResources(id);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

    /**
     * 菜单
     * @param request
     * @return
     * loadMenu.do
     */
	@RequestMapping(value = "/loadMenu")
	public List<Resources> loadMenu(HttpServletRequest request){
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
				.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		String name = securityContextImpl.getAuthentication().getName();
		Resources resources= new Resources();
		resources.setUsername(name);
		resources.setType(1);
		List<Resources> menu = resourcesService.loadMenu(resources);
		return menu;
	}
}
