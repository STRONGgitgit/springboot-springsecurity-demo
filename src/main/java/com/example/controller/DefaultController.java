package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * @Auther: king
 * @Date: 2018/11/30 10:42
 * @Description:
 */
@Controller
public class DefaultController {


    //登录后跳转欢迎页面
    @RequestMapping("/")
    public String jump(){
        return "index";
    }

    /**
     * 跳转用户页面
     * @return
     */
    //@RequestMapping(value={"/index.do",""})
    @RequestMapping("/user/index")
    public String index(){
        return "user/userList";
    }

    /**
     * 跳转角色管理页面
     * @return
     */
    //@RequestMapping("roles.do")
    @RequestMapping("/role/roles")
    public String roles(){
        return "role/roleList";
    }

    /**
     * 跳转资源管理页面
     * @return
     */
    //@RequestMapping("resources.do")
    @RequestMapping("/power/resources")
    public String resources(){
        return "resources/resourceList";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestParam(required = false)String error, Map<String,Object>map){
        if (error !=null ){
            map.put("error",error);
        }
        return "login";
    }

    /**
     * 跳转到没有权限页面
     * @return
     */
    @RequestMapping("/Access_Denied")
    public String noauthority(){
        return "noauthority";
    }

    /**
     * 跳转到404页面
     * @return
     */
    @RequestMapping("/404")
    public String jump404(){
        return "404";
    }

}
