package com.example.security;

import com.example.model.Resources;
import com.example.model.User;
import com.example.service.ResourcesService;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 验证用户名是否存在,密码是否正确
 *
 */
@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourcesService resourcesService;

    @PostConstruct
    private void loadResourceDefine() {
        if (MyInvocationSecurityMetadataSourceService.resourceMap == null) {//这里只会初始化时进入一次
            MyInvocationSecurityMetadataSourceService.resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            List<Resources> list = resourcesService.queryAll();//根据所给的对象返回对象的list的权限集合
            for (Resources resources : list) {
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                // 通过资源名称来表示具体的权限 注意：必须"ROLE_"开头	这里加了个指定前缀
                ConfigAttribute configAttribute = new SecurityConfig(resources.getResUrl()+","+resources.getMethod());
                configAttributes.add(configAttribute);
                MyInvocationSecurityMetadataSourceService.resourceMap.put(resources.getResUrl()+","+resources.getMethod(), configAttributes);//将url为key，configAttributes对象为值存储到map
            }
        }
    }

    /**
     * 判断用户是否存在,拥有什么角色与权限
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
//    	User user = userService.queryByName(account);
//        if (user != null) {
//            List<URole> roles = roleService.queryRoleListWithUser(user.getId());
//            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//            for (URole uRole : roles) {
//                if (uRole != null && uRole.getRoleDesc() != null) {
//                	List<RResources> powers = resourcesService.resourcesListWithRole(uRole.getId());
//                	for (RResources power : powers) {
//                		//							controller中mapping中的的访问路径(value="/user"),mapping中的访问方法(method="GET,POST..")
//                		GrantedAuthority grantedAuthority = new MyGrantedAuthority(power.getResUrl(), power.getMethod());
//                		grantedAuthorities.add(grantedAuthority);
//					}
//                }
//            }
//            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
//        } else {
//            throw new UsernameNotFoundException("admin: " + account + " do not exist!");
//        }
        User user = userService.findUserByName(username);//根据用户名获得用户对象
        if(user ==null) {//等于null则不存在
            throw new UsernameNotFoundException(username + " not exist!");
        }
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();//实例化一个Set<GrantedAuthority>对象
        //将用户名放入Resources对象，获取权限集合
        Resources resources = new Resources();
        resources.setUsername(username);
        List<Resources> list = resourcesService.loadMenu(resources);
        for (Resources r : list) {
            GrantedAuthority grantedAuthority = new MyGrantedAuthority(r.getResUrl(), r.getMethod());
            authSet.add(grantedAuthority);//添加权限
        }
        //返回一个用户详细信息，里面包括用户名，密码，用户状态，用户所对应的权限集合		验证用户密码
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getEnable()==1?true:false,
                true,
                true,
                true,
                authSet);
    }

}
