package com.tyf.baseproject.controller;

import com.alibaba.fastjson.JSONObject;
import com.tyf.baseproject.entity.Menu;
import com.tyf.baseproject.service.MenuService;
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


@Controller
@RequestMapping("/menu")
public class MenuController {

    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "menuManage",method = RequestMethod.GET)
    public String menuManage(){
        return "system/menu";
    }

    /**
     * 获取所有菜单
     * @param request
     * @param response
     */
    @RequestMapping(value = "getAllMenus",method = RequestMethod.POST)
    public void getAllMenus(HttpServletRequest request, HttpServletResponse response){
        String json = menuService.getAllMenus();
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ztree
     * @param request
     * @param response
     */
    @RequestMapping(value = "getAllMenusForTree",method = RequestMethod.GET)
    public void getAllMenusForTree(HttpServletRequest request, HttpServletResponse response){
        String json = menuService.getAllMenusForTree();
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
    public void saveOrEditEntity(@ModelAttribute("menu") Menu menu, HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String oprate = "新增";
        if(menu.getId()!=null){
            oprate = "编辑";
        }else{
            if(menu.getPid()==null){
                menu.setPid(0);
            }
        }
        try{
            menuService.saveEntity(menu);
            logger.info(SecurityUtil.getCurUserName()+"---"+oprate+"菜单成功");
        }catch (Exception e){
            flag = 0;
            logger.error(SecurityUtil.getCurUserName()+"---"+oprate+"菜单失败");
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
        String code = request.getParameter("code");
        String id = request.getParameter("id");
        boolean isExist = menuService.verifyTheRepeat(code, id);
        try {
            response.getWriter().print(isExist);
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
        Menu menu = menuService.getMenuById(Integer.parseInt(id));
        String json = JSONObject.toJSONString(menu);
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取编辑信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "getEditEntityInfo",method = RequestMethod.POST)
    public void getEditEntityInfo(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String json = menuService.getEditInfo(Integer.parseInt(id));
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 判断是否被使用
     * @param request
     * @param response
     */
    @RequestMapping(value = "checkMenuUsed",method = RequestMethod.POST)
    public void checkMenuUsed(HttpServletRequest request, HttpServletResponse response){
        String ids = request.getParameter("ids");
        boolean flag =menuService.checkMenuUsed(ids);
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除菜单
     * @param request
     * @param response
     */
    @RequestMapping(value = "deleteMenu",method = RequestMethod.POST)
    public void deleteMenu(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String ids = request.getParameter("ids");
        try{
            menuService.deleteMenu(ids);
            logger.info(SecurityUtil.getCurUserName()+"删除菜单成功");
        }catch (Exception e){
            e.printStackTrace();
            flag = 0;
            logger.error(SecurityUtil.getCurUserName()+"删除菜单失败");
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
