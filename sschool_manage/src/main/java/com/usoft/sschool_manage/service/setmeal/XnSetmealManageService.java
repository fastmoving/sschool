package com.usoft.sschool_manage.service.setmeal;

import com.usoft.smartschool.util.MyResult;

/**
 * 套餐管理
 * @Author jijh
 * @Date 2019/5/9 15:46
 */
public interface XnSetmealManageService {



    MyResult listXnSetMealManageBase();

    /**
     * 套餐查看
     * @return
     */
    MyResult listXnSetmealManage();


    /**
     * 套餐添加
     * @param id 套餐ID
     * @param name 套餐名称
     * @param price 套餐价格
     * @param timeType 套餐时间
     * @param bindMan 绑定人数
     * @param des 描述介绍
     * @param attr1 使用用户 1、学生 2、老师
     * @return
     */
    MyResult addOrUpdateXnSetmealManage(Integer id,String name, String price, String beginDate,
                                        String endDate, Integer bindMan, String des,Integer timeType,Integer attr1);


    /**
     * 删除套餐
     * @param ids 套餐名称
     * @return
     */
    MyResult deleteXnSetmealManage(Integer[] ids);

    /**
     * 查看套餐
     * @param id
     * @return
     */
    MyResult selectXnSetmealManage(Integer id);

}
