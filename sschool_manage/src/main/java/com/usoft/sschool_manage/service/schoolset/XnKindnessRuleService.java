package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 爱心捐赠规则
 * @Author jijh
 * @Date 2019/5/7 16:44
 */
public interface XnKindnessRuleService {

    /**
     * 查看
     * @return
     */
    MyResult selectXnKindnessRule();

    /**
     * 编辑爱心捐赠规则
     * @param isOpen 是否开启 0否 1.是
     * @param perKind 每笔爱心数
     * @return
     */
    MyResult editXnKindnessRule(Integer isOpen, Integer perKind);
}
