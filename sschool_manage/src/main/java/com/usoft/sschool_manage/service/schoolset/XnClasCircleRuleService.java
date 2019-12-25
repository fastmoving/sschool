package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 班级圈规则表
 * @Author jijh
 * @Date 2019/5/7 15:57
 */
public interface XnClasCircleRuleService {


    /**
     * 查看班级圈审核规则
     * @return
     */
    MyResult selectXnClasCircleRule();


    /**
     * 编辑班级圈审核规则
     * @param isOpen 是否开启 0否 1是
     * @param checkType 是否需要审核 0否 1.是
     * @return
     */
    MyResult editXnClasCircleRule(Integer isOpen, Integer checkType);



}
