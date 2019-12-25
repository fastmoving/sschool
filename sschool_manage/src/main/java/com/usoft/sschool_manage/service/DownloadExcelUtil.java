package com.usoft.sschool_manage.service;

import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.util.MyResult;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Author jijh
 * @Date 2019/5/17 10:13
 */

@Service
public class DownloadExcelUtil {


    /**
     * 教师信息模板
     */
    private static final String TEACHER ="excel/teacherinformation.xlsx";
    /**
     * 班级信息模板
     */
    private static final String CLASS ="excel/classinformation.xlsx";
    /**
     * 课程信息模板
     */
    private static final String COURSE="excel/courseinformation.xlsx";
    /**
     * 菜单信息模板
     */
    private static final String FOOD = "excel/foodinformation.xlsx";
    /**
     * 成绩信息模板
     */
    private static final String SCORE = "excel/scoreinformation.xlsx";
    /**
     * 学生信息模板
     */
    private static final String STUDENT= "excel/studentinformation.xlsx";


    /**
     * 获取模板文件
     * @param type
     * @param request
     * @param response
     */
    public void getExcelTemplateUtil(Integer type, HttpServletRequest request, HttpServletResponse response){
        Resource resource = null;
        switch (type){
            case 1: resource = new ClassPathResource(TEACHER);break;
            case 2: resource = new ClassPathResource(CLASS);break;
            case 3: resource = new ClassPathResource(COURSE);break;
            case 4: resource = new ClassPathResource(FOOD);break;
            case 5: resource = new ClassPathResource(SCORE);break;
            case 6: resource = new ClassPathResource(STUDENT);break;
        }
        ServletOutputStream out = null;
        try{
            out = response.getOutputStream();
            File file = resource.getFile();
            String fileName = file.getName();
            String userAgent = request.getHeader("user-agent");//判断浏览器类型

            if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0
                    || userAgent.indexOf("Safari") >= 0) {
                fileName= new String((fileName).getBytes(), "ISO8859-1");
            } else {
                fileName=URLEncoder.encode(fileName,"UTF8"); //其他浏览器
            }
            response.setContentType("application/vnd.ms-excel;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Cache-Control", "no-cache");


            FileInputStream fis = new FileInputStream(file);
            byte[] bt = new byte[fis.available()];
            int len = 0;
            while((len=fis.read(bt))>0){
                out.write(bt,0,len);
            }
        }catch (IOException e){
            e.printStackTrace();

            try{
                Object result = JSONObject.toJSON(MyResult.outOfDate("模板文件不存在"));
                out.write(String.valueOf(result).getBytes("UTF-8"));
            }catch (IOException e2){
                e2.getStackTrace();
            }
        }



    }






}
