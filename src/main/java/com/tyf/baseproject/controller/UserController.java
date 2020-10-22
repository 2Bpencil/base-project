package com.tyf.baseproject.controller;


import com.alibaba.fastjson.JSONObject;
import com.tyf.baseproject.base.datapage.DataPage;
import com.tyf.baseproject.entity.User;
import com.tyf.baseproject.service.UserService;
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
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value = "userManage",method = RequestMethod.GET)
    public String userManage(){
        return "system/user";
    }
    /**
     * 获取用户列表
     * @param request
     * @param response
     */
    @RequestMapping(value = "getTableJson",method = RequestMethod.POST)
    public void getTableJson(HttpServletRequest request, HttpServletResponse response){

        Map<String,String[]> parameterMap = request.getParameterMap();
        DataPage<User> pages = userService.getDataPage(parameterMap);
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
    public void saveOrEditEntity(@ModelAttribute("user") User user, HttpServletResponse response){
        int flag = 1;
        String oprate = "新增";
        if(user.getId()!=null){
            User u = userService.getUserById(user.getId());
            u.setUsername(user.getUsername());
            u.setPhone(user.getPhone());
            u.setSex(user.getSex());
            u.setRealName(user.getRealName());
            user = u;
            oprate = "编辑";
        }else{
            String password = "123456";

            user.setPassword(password);
        }
        try{
            userService.saveEntity(user);
            logger.info(SecurityUtil.getCurUserName()+"---"+oprate+"用户成功");
        }catch (Exception e){
            flag = 0;
            logger.error(SecurityUtil.getCurUserName()+"---"+oprate+"用户失败");
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
        User user = userService.getUserById(Integer.parseInt(id));
        Map map = new HashMap();
        map.put("id",user.getId());
        map.put("username",user.getUsername());
        map.put("phone",user.getPhone());
        map.put("sex",user.getSex());
        map.put("realName",user.getRealName());
        String json = JSONObject.toJSONString(map);
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
    @RequestMapping(value = "deleteUser",method = RequestMethod.POST)
    public void deleteUser(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String id = request.getParameter("id");
        try{
            userService.deleteUserById(Integer.parseInt(id));
            logger.info(SecurityUtil.getCurUserName()+"---删除用户成功");
        }catch (Exception e){
            flag = 0;
            logger.error(SecurityUtil.getCurUserName()+"---删除用户失败");
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
    @RequestMapping(value = "getRolesByUserId",method = RequestMethod.POST)
    public void getRolesByUserId(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String json = userService.getRolesByUserId(Integer.parseInt(id));
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *保存用户角色关系
     * @param request
     * @param response
     */
    @RequestMapping(value = "saveUserAndRole",method = RequestMethod.POST)
    public void saveUserAndRole(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String userId = request.getParameter("userId");
        String roleIds = request.getParameter("roleIds");
        try{
            userService.saveUserAndRole(Integer.parseInt(userId),roleIds );
            logger.info(SecurityUtil.getCurUserName()+"---保存角色配置成功");
        }catch (Exception e){
            flag = 0;
            logger.info(SecurityUtil.getCurUserName()+"---保存角色配置失败");
            e.printStackTrace();
        }
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
        String username = request.getParameter("username");
        String id = request.getParameter("id");
        boolean isExist = userService.verifyTheRepeat(username, id);
        try {
            response.getWriter().print(isExist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改密码
     * @param request
     * @param response
     */
    @RequestMapping(value = "modifyPassword",method = RequestMethod.POST)
    public void modifyPassword(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String password = request.getParameter("repeat_password");
        try{
            userService.savePassword(password);
            logger.info(SecurityUtil.getCurUserName()+"---修改密码成功");
        }catch (Exception e){
            flag = 0;
            logger.info(SecurityUtil.getCurUserName()+"---修改密码失败");
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 检查密码是否正确
     * @param request
     * @param response
     */
    @RequestMapping(value = "checkPassword",method = RequestMethod.POST)
    public void checkPassword(HttpServletRequest request, HttpServletResponse response){
        String password = request.getParameter("old_password");
        boolean flag = userService.checkPassword(password);
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     *保存班级
     */
    @RequestMapping(value = "saveClassSet",method = RequestMethod.POST)
    public void saveClassSet(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String userId = request.getParameter("userId");
        String classIds = request.getParameter("classIds");
        try{
            userService.saveClassSet(Integer.parseInt(userId),classIds );
            logger.info(SecurityUtil.getCurUserName()+"---保存班级配置成功");
        }catch (Exception e){
            flag = 0;
            logger.info(SecurityUtil.getCurUserName()+"---保存班级配置失败");
            e.printStackTrace();
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置密码
     * @param request
     * @param response
     */
    @RequestMapping(value = "reSetPassword",method = RequestMethod.POST)
    public void reSetPassword(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String userId = request.getParameter("userId");
        try{
            userService.reSetPassword(Integer.parseInt(userId));
        }catch (Exception e){
            flag = 0;
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
