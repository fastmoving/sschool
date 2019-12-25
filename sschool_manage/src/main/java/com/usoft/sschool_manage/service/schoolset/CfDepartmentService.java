package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

import java.util.List;
import java.util.Map;

/**
 * 学校信息
 * @Author jijh
 * @Date 2019/4/22 18:17
 */
public interface CfDepartmentService {

    /**
     * 所有学校列表
     * @return
     */
    MyResult listAllSchool(String name, Integer pageNo,Integer pageSize);
}
