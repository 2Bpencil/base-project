package com.tyf.baseproject.controller;
import com.alibaba.fastjson.JSONObject;
import com.tyf.baseproject.base.datapage.DataPage;
import com.tyf.baseproject.entity.Role;
import com.tyf.baseproject.service.RoleService;
import com.tyf.baseproject.ustil.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**

/**
 * @ClassName RoleController
 * @Description: 角色管理
 * @Author tyf
 * @Date 2019/8/7 
 * @Version V1.0
 **/
@Controller
@RequestMapping("/role")
public class RoleController {

    private final static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @RequestMapping(value = "roleManage",method = RequestMethod.GET)
    public String roleManage(){
        return "system/role";
    }
    /**
     * 分页查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "getTableJson",method = RequestMethod.POST)
    public void getTableJson(HttpServletRequest request, HttpServletResponse response){
        Map<String,String[]> parameterMap = request.getParameterMap();
        DataPage<Role> pages = roleService.getDataPage(parameterMap);
        String json = JSONObject.toJSONString(pages);
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存或者编辑实体
     */
    @RequestMapping(value = "saveOrEditEntity",method = RequestMethod.POST)
    public void saveOrEditEntity(@ModelAttribute("role") Role role, HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String oprate = "新增";
        if(role.getId()!=null){
            oprate = "编辑";
        }
        try{
            roleService.saveEntity(role);
            logger.info(SecurityUtil.getCurUserName()+oprate+"角色成功");
        }catch (Exception e){
            flag = 0;
            logger.error(SecurityUtil.getCurUserName()+oprate+"角色失败");
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取实体信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "getEntityInfo",method = RequestMethod.POST)
    public void getEntityInfo(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        Role role = roleService.getRoleById(Integer.parseInt(id));
        String json = JSONObject.toJSONString(role);
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除角色
     * @param request
     * @param response
     */
    @RequestMapping(value = "deleteRole",method = RequestMethod.POST)
    public void deleteRole(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String id = request.getParameter("id");
        try{
            roleService.deleteRole(Integer.parseInt(id));
            logger.info(SecurityUtil.getCurUserName()+"删除角色成功");
        }catch (Exception e){
            flag = 0;
            logger.error(SecurityUtil.getCurUserName()+"删除角色失败");
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取角色的菜单
     * @param request
     * @param response
     */
    @RequestMapping(value = "getMenusByRoleId",method = RequestMethod.POST)
    public void getMenusByRoleId(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String json = roleService.getMenusByRoleId(Integer.parseInt(id));
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "saveRoleAndMenu",method = RequestMethod.POST)
    public void saveRoleAndMenu(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String roleId = request.getParameter("roleId");
        String menuIds = request.getParameter("menuIds");
        try{
            roleService.saveRsRoleMenu(Integer.parseInt(roleId),menuIds );
            logger.info(SecurityUtil.getCurUserName()+"保存菜单成功");
        }catch (Exception e){
            flag = 0;
            logger.info(SecurityUtil.getCurUserName()+"保存菜单失败");
            e.printStackTrace();
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断角色是否被使用
     */
    @RequestMapping(value = "checkRoleUsed",method = RequestMethod.POST)
    public void checkRoleUsed(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        boolean flag =roleService.checkRoleUsed(Integer.parseInt(id));
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 验证重复
     * @param request
     * @param response
     */
    @RequestMapping(value = "verifyTheRepeat",method = RequestMethod.POST)
    public void verifyTheRepeat(HttpServletRequest request, HttpServletResponse response){
        String authority = request.getParameter("authority");
        String id = request.getParameter("id");
        boolean isExist = roleService.verifyTheRepeat(authority, id);
        try {
            response.getWriter().print(isExist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取角色树
     * @param request
     * @param response
     */
    @RequestMapping(value = "getAllRoleTree",method = RequestMethod.GET)
    public void getAllRoleTree(HttpServletRequest request, HttpServletResponse response){

        String json = roleService.findRoleTree();
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
    