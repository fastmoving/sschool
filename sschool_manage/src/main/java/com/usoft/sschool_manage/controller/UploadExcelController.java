package com.usoft.sschool_manage.controller;

import com.usoft.sschool_manage.service.UploadExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

/**
 * 导入excel模板数据
 * @Author jijh
 * @Date 2019/5/27 10:39
 */

@RestController
@RequestMapping("manage/uploadexcel")
public class UploadExcelController extends  BaseController {


    @Autowired
    private UploadExcelUtil uploadExcelUtil;

    @RequestMapping("getExcel/{type}")
    public Object getExcel(@PathVariable("type")Integer type, MultipartFile multipartFile, HttpServletRequest request){
        return uploadExcelUtil.uploadExcel(type, multipartFile, request);
    }
}
