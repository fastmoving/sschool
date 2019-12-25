package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.pojo.HlEnum;
import com.usoft.smartschool.pojo.HlEnumitem;
import com.usoft.smartschool.util.MyResult;

import java.util.List;

/**
 * @Author jijh
 * @Date 2019/4/23 14:48
 */
public interface BasicDataSelectService {

    /**
     * 基类查询
     * @return
     */
    MyResult findAll(Integer pageNo, Integer pageSize);

    /**
     * 分支列表
     * @return
     */
    MyResult findByEnumId(Integer enumId);
}
