package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 非校园爱心管理
 * @Author jijh
 * @Date 2019/5/7 11:47
 */
public interface XnKindnessUnschoolService {

    /**
     * 非校园爱心列表
     * @param type 类型 1.爱心人士 2.爱心企业
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listXnKindnessUnschool(Integer type, Integer pageNo, Integer pageSize);


    /**
     * 添加或者修改非校园爱心
     * @param id 校园爱心id 修改时需要
     * @param  type 校园爱心类型 1.爱心人士 2.爱心企业
     * @param name 名称
     * @param img 图片
     * @param description 描述
     * @return
     */
    MyResult addOrUpdateXnKindnessUnschool(Integer id, Integer type, String name,String img, String description);


    /**
     * 非校园爱心删除
     * @param ids
     * @return
     */
    MyResult deleteXnKindnessUnschool(Integer[] ids);

}
