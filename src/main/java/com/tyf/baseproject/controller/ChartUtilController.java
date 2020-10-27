package com.tyf.baseproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description: 图表工具
 * @author: tyf
 * @create: 2020-10-27 09:48
 **/
@Controller
@RequestMapping("/chart")
public class ChartUtilController {

    private final static Logger logger = LoggerFactory.getLogger(ChartUtilController.class);

    @RequestMapping(value = "chartUtil",method = RequestMethod.GET)
    public String chartUtilManage(){
        return "business/chartUtil";
    }





}
