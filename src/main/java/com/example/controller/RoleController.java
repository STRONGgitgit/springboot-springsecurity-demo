package com.example.controller;

import com.example.model.Role;
import com.example.model.RoleResources;
import com.example.model.URole;
import com.example.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: king
 * @Date: 2018/11/30 10:42
 * @Description:
 */
@Controller
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 角色管理分页查询
     * @param role
     * @param draw
     * @param start
     * @param length
     * @return
     * /roleList.do
     */
    @RequestMapping(value = "/roleList")
    public Map<String,Object> userList(Role role, String draw,
                                       @RequestParam(required = false, defaultValue = "1") int start,
                                       @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        PageInfo<Role> pageInfo = roleService.selectByPage(role, start, length);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     /*
     * 查询角色列表，并且返回指定用户是否拥有该角色
     * @param uid 用户id
     * @return
     */
    /*@RequestMapping("/roleListWithUser.do")*/
    @RequestMapping(value = "/roleListWithUser")
    public List<URole> roleListWithUser(Integer uid){
        List<URole> list = roleService.queryRoleListWithUser(uid);
        return list;
    }

    /**
     * 保存角色的权限
     * @param roleResources
     *  roleResources 中的resourcesId 现在是以“,”分隔的字符串
     * @return
     */
    /*@RequestMapping("/saveRoleResources.do")*/
    @RequestMapping(value = "/saveRoleResources")
    public String saveRoleResources(RoleResources roleResources){
        if(StringUtils.isEmpty(roleResources.getRoleId()))
            return "error";
        try {
            roleService.saveRoleResources(roleResources);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 添加角色
     * @return
     */
    /*@RequestMapping( "/addRole.do")*/
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public String addRole(Role role){
        try {
            roleService.addRole(role);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    /*@RequestMapping("/delRole.do")*/
    @RequestMapping(value = "/delRole/{id}",method = RequestMethod.DELETE)
    public String delRole(@PathVariable("id")Integer id){
        try {
            roleService.delRole(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
