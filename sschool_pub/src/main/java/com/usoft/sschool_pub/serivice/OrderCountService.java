package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OrderCountService {
    MyResult count(String time,Integer pageNo,Integer pageSize);

    void downloadExcel(String time,HttpServletRequest request, HttpServletResponse response);
}
