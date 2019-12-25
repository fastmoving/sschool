package com.usoft.sschool_manage.controller;

import com.usoft.sschool_manage.service.DownloadExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 导出excel模板控制层
 * @Author jijh
 * @Date 2019/5/17 10:12
 */
@RestController
@RequestMapping("manage/downloadexcel")
public class DownExcelController extends  BaseController{

    @Autowired
    private DownloadExcelUtil downloadExcelUtil;


    @GetMapping("get")
    public void getExcel(Integer type, HttpServletRequest request, HttpServletResponse response){
         downloadExcelUtil.getExcelTemplateUtil(type, request, response);
    }

}
