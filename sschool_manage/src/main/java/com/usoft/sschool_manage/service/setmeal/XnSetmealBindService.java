package com.usoft.sschool_manage.service.setmeal;

import com.usoft.smartschool.util.MyResult;

import java.util.List;
import java.util.Map;

/**
 * 绑定用户业务
 * @Author jijh
 * @Date 2019/5/15 17:10
 */
public interface XnSetmealBindService {


    /**
     * 绑定信息列表
     * @param classId 班级id
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listXnSeatmealBinding(Integer classId, Integer pageNo, Integer pageSize);


    /**
     * 绑定用户
     * @param setId 套餐id
     * @param stuId 学生id
     *  relation 关系
     *  phone 电话号码
     * @return
     */
    MyResult bindingPerson(Integer setId, Integer stuId, List<Map<String,Object>> p);



    MyResult editPerson(String bindPer);


    /**
     * 查询关联用户
     * @param stuId
     * @return
     */
    MyResult selectPerson(Integer stuId);
}
