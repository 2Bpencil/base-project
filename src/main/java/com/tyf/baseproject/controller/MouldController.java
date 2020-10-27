package com.tyf.baseproject.controller;
import com.alibaba.fastjson.JSONObject;
import com.tyf.baseproject.base.datapage.DataPage;
import com.tyf.baseproject.entity.Mould;
import com.tyf.baseproject.service.MouldService;
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
 * @description: 模板管理
 * @author: tyf
 * @create: 2020-07-27 10:30
 **/
@Controller
@RequestMapping("/mould")

public class MouldController {

    private final static Logger logger = LoggerFactory.getLogger(MouldController.class);
    @Autowired
    private MouldService mouldService;

    @RequestMapping(value = "mouldManage",method = RequestMethod.GET)
    public String mouldManage(){
        return "business/mould";
    }



    /**
     * 分页查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "getTableJson",method = RequestMethod.POST)
    public void getTableJson(HttpServletRequest request, HttpServletResponse response){
        Map<String,String[]> parameterMap = request.getParameterMap();
        DataPage<Mould> pages = mouldService.getDataPage(parameterMap);
        String json = JSONObject.toJSONString(pages);
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 保存编辑实体
     * @Param: [mould, request, response]
     * @return: void
     * @Author: tyf
     * @Date: 2020/7/28 9:42
     */ 
    @RequestMapping(value = "saveOrEditEntity",method = RequestMethod.POST)
    public void saveOrEditEntity(@ModelAttribute("mould") Mould mould, HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String oprate = "新增";
        if(mould.getId()!=null){
            oprate = "编辑";
        }
        try{
            mouldService.saveEntity(mould);
            logger.info(SecurityUtil.getCurUserName()+oprate+"模板成功");
        }catch (Exception e){
            flag = 0;
            logger.error(SecurityUtil.getCurUserName()+oprate+"模板失败");
        }
        try {
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * @Description: 删除实体
     * @Param: [request, response]
     * @return: void
     * @Author: tyf
     * @Date: 2020/7/28 9:50
     */
    @RequestMapping(value = "deleteEntity",method = RequestMethod.POST)
    public void deleteEntity(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String id = request.getParameter("id");
        try{
            mouldService.deleteEntity(Integer.parseInt(id));
            logger.info(SecurityUtil.getCurUserName()+"删除模板成功");
        }catch (Exception e){
            flag = 0;
            logger.error(SecurityUtil.getCurUserName()+"删除模板失败");
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
        Mould mould = mouldService.getEntityById(Integer.parseInt(id));
        String json = JSONObject.toJSONString(mould);
        try {
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取数据库中的表
     * @Param: []
     * @return: void
     * @Author: tyf
     * @Date: 2020/10/21 9:25
     */
    @RequestMapping(value = "getTables",method = RequestMethod.POST)
    public void getTables(HttpServletRequest request, HttpServletResponse response){
        String databaseName = request.getParameter("databaseName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String databaseIp = request.getParameter("databaseIp");
        String json = mouldService.getTables(databaseIp,databaseName,username,password);
        try {
            response.getWriter().println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @Description: 生成文件
     * @Param: [request, response]
     * @return: void
     * @Author: tyf
     * @Date: 2020/10/21 11:16
     */
    @RequestMapping(value = "createFiles",method = RequestMethod.POST)
    public void createFiles(HttpServletRequest request, HttpServletResponse response){
        int flag = 1;
        String databaseIp = request.getParameter("databaseIp");
        String mouldIds = request.getParameter("mouldIds");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String tableNames = request.getParameter("tableNames");
        String databaseName = request.getParameter("databaseName");
        try{
            mouldService.createFiles(databaseIp,tableNames,username,password,mouldIds,databaseName);
        }catch (Exception e){
            flag = 0;
        }
        try{
            response.getWriter().print(flag);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
