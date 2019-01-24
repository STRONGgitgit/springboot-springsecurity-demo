package com.example.tag;

import com.example.model.Resources;
import com.example.service.ResourcesService;
import com.example.until.SpringContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * 自定义标签  用于前台判断按钮权限
 * @author A
 */
@Component
public class AuthorizeTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String buttonUrl;
	public String getButtonUrl() {
		return buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	@SuppressWarnings("static-access")
	@Override
	public int doStartTag() {
		//获取请求的request
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//从session中获得上下文安全对象(包括用户的所有权限)
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
				.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		//获取当前登录名
		String name = securityContextImpl.getAuthentication().getName();

		//如果数据库里有该链接，并且该用户的权限拥有该链接，则显示 。如果数据库没有该链接则不显示
		//通过工具类获取指定的bean
		ResourcesService resourcesService= (ResourcesService) SpringContextHolder.getBean("resourcesService");
		List<Resources> queryAll = resourcesService.queryAll();//查询所有权限
		boolean flag = true;
		for (Resources resources : queryAll) {
			//这里如果相等就将flag设为false
			if((resources.getResUrl()+","+resources.getMethod()).equals(buttonUrl)) {
				flag = false;
			}
		}
		if(flag) { //数据库中没有该链接，直接显示
			return EVAL_BODY_INCLUDE;
		}else{
			Resources resources = new Resources();//实例化Resources对象
			resources.setUsername(name);
			String[] strings = buttonUrl.split(",");
			resources.setResUrl(strings[0]);//第一个值为url
			resources.setMethod(strings[1]);//第二个为method
			List<Resources> resourcesList = resourcesService.loadMenu(resources);//如果list有值表示有该权限，可以显示
			if(resourcesList.size()>0){
				return EVAL_BODY_INCLUDE;//数据库中有该链接，并且该用户拥有该角色，显示
			}
		}
		return this.SKIP_BODY;  //不显示
	}
}
