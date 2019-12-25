package com.usoft.sschool_teacher.service;

import com.usoft.smartschool.util.MyResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: 陈秋
 * @Date: 2019/5/16 16:06
 * @Version 1.0
 */
//@FeignClient(value="SSCHOOL_MANAGE")
public interface XnSetmealService {


    @RequestMapping(value= "/manage/binding/list",method = RequestMethod.GET)
    MyResult getXnSetmealList(Integer classId, String token,Integer pageNo, Integer pageSize);
}
