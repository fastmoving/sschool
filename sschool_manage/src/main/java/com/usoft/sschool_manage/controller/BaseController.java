package com.usoft.sschool_manage.controller;

import com.usoft.smartschool.util.MyResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jijh
 * @Date 2019/5/29 17:51
 */
@RestController
public class BaseController {


    @ExceptionHandler(Exception.class)
    public MyResult handleException(Exception e){
        if(e!=null){
            e.printStackTrace();
            return MyResult.failure("系统故障");
        }else{
            return MyResult.failure("未知错误");
        }

    }
}
