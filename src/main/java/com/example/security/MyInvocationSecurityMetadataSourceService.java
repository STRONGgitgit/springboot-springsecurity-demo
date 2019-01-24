package com.example.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangyibo on 17/1/19.
 */
@Service
public class MyInvocationSecurityMetadataSourceService  implements FilterInvocationSecurityMetadataSource {

    public static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    //因为我不想每一次来了请求，都先要匹配一下权限表中的信息是不是包含此url，
    // 我准备直接拦截，不管请求的url 是什么都直接拦截，然后在MyAccessDecisionManager的decide 方法中做拦截还是放行的决策。
    //这个方法的返回值不能为null,否则会重复请求，并且在第三次直接返回error异常
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    String requestUrl = ((FilterInvocation) object).getRequestUrl();
    //获取请求的request
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String method = request.getMethod();
        if(method.equals("POST")){//如果等于post请求就有可能是delete或者是put请求
        String methods = request.getParameter("_method");
        if(!StringUtils.isEmpty(methods)){//如果不为空或者Null则满足条件
            method = methods;
        }
    }
        if(requestUrl.indexOf("?")>-1){//也就是携带了参数
        requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
        //AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);//表示我们需要跳转的页面;
    }
    //指定正则表达式
    Pattern pattern = Pattern.compile(".+/\\d+");
    Matcher matcher = pattern.matcher(requestUrl);
        if(matcher.matches()){//匹配则为true
        requestUrl = requestUrl.substring(0,requestUrl.lastIndexOf("/")+1)+"*";
    }
    Collection<ConfigAttribute> configAttributes = resourceMap.get(requestUrl+","+method);//根据url获得配置属性集合
        /*if(configAttributes == null){//如果等于则放空
            configAttributes = new ArrayList<>();
            configAttributes.add(new SecurityConfig("&&&&"));
        }*/
        return configAttributes;//如果返回为null则不需要拦截
}


    /*@Autowired
    private  HttpServletRequest request;*/

   /* public static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    //因为我不想每一次来了请求，都先要匹配一下权限表中的信息是不是包含此url，
    // 我准备直接拦截，不管请求的url 是什么都直接拦截，然后在MyAccessDecisionManager的decide 方法中做拦截还是放行的决策。
    //所以此方法的返回值不能返回 null 此处我就随便返回一下。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //获取请求的request
        //System.out.println(request);
       *//* if(requestUrl.equals("/error")){
            Collection<ConfigAttribute> co=new ArrayList<>();
            co.add(new SecurityConfig("null"));
            return co;
        }*//*
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String method = request.getMethod();
        if(method.equals("POST")){//如果等于post请求就有可能是delete或者是put请求
            String methods = request.getParameter("_method");
            if(!StringUtils.isEmpty(methods)){//如果不为空或者Null则满足条件
                method = methods;
            }
        }
        if(requestUrl.indexOf("?")>-1){//也就是携带了参数
            requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
            //AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);//表示我们需要跳转的页面;
        }
        //指定正则表达式
        Pattern pattern = Pattern.compile(".+/\\d+");
        Matcher matcher = pattern.matcher(requestUrl);
        if(matcher.matches()){//匹配则为true
            requestUrl = requestUrl.substring(0,requestUrl.lastIndexOf("/")+1)+"*";
        }
        Collection<ConfigAttribute> configAttributes = resourceMap.get(requestUrl+","+method);//根据url获得配置属性集合
        return configAttributes;//如果返回为null则不需要拦截
    }
*/
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
