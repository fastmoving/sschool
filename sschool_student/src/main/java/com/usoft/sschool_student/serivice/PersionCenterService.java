package com.usoft.sschool_student.serivice;

import com.usoft.smartschool.util.MyResult;

import javax.servlet.http.HttpServletRequest;

public interface PersionCenterService {
    //查询学生信息
    MyResult stuInfo(Integer schoolId,Integer userId);
    //上传头像
    MyResult addSphoto(HttpServletRequest request,Integer schoolId,Integer userId);
    //上传图片
    MyResult uploadFile(HttpServletRequest request);
    //上传风采照
    MyResult addIdImg(String idImg,Integer schoolId,Integer userId);

}
