package com.example.controller;

import com.example.model.User;
import com.example.model.UserRole;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: king
 * @Date: 2018/11/30 10:42
 * @Description:
 */
@Controller
@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;
    /**
     * 获取用户列表
     * @param user 用户对象
     * @param draw dataTables特有参数，原样返回即可
     * @param start 起始数据
     * @param length 每页的长度
     * @return
     */
    /*@RequestMapping("/userList.do")*/
    @RequestMapping(value = "/userList",method = RequestMethod.GET)
    public Map<String,Object> userList(User user,String draw,
                                       @RequestParam(required = false, defaultValue = "1") int start,
                                       @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        PageInfo<User> pageInfo = userService.selectByPage(user, start, length);
        System.out.println("pageInfo.getTotal():"+pageInfo.getTotal());
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 保存用户角色
     * @param userRole 用户角色
     *  	  此处获取的参数的角色id是以 “,” 分隔的字符串
     * @return
     */
    /*@RequestMapping("/saveUserRoles.do")*/
    @RequestMapping(value = "/saveUserRoles",method = RequestMethod.POST)
    public String saveUserRoles(UserRole userRole){
        if(StringUtils.isEmpty(userRole.getUserId()))
            return "error";
        try {
            userService.saveUserRoles(userRole);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     *添加用户
     * @param user
     * @return
     */
    /*@RequestMapping("/addUser.do")*/
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(User user){
        User u = userService.queryByName(user.getUsername());
        if(u != null)
            return "error";
        try {
            userService.addUser(user);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    /*@RequestMapping("/delUser.do")*/
    @RequestMapping(value = "/delUser/{id}",method = RequestMethod.DELETE)
    public String delUser(@PathVariable("id")Integer id){
        try {
            userService.delUser(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
