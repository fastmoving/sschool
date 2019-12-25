package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 校园简介
 * @Author jijh
 * @Date 2019/5/7 11:24
 */
public interface XnSchoolIntroduceService {


    /**
     * 查看校园简介
     * @return
     */
    MyResult selectXnSchoolIntroduce();

    /**
     * 编辑校园简介
     * @param img 图片
     * @param des 描述
     * @return
     */
    MyResult editXnSchoolIntroduce(String img, String des);
}
