package com.usoft.sschool_manage.service.live;

import com.usoft.smartschool.util.MyResult;

/**
 * 视频缴费规则
 * @Author jijh
 * @Date 2019/5/22 15:16
 */
public interface XnVideoRuleService {


    /**
     * 查看视频缴费规则
     * @param userType
     * @return
     */
    MyResult selectXnVideoRule(Integer userType);

    /**
     * 新增或者修改视频缴费规则
     * @param userType  用户类型 1.学生/家长 2.教师
     * @param payRule 规则  1.收费+试用期  2.收费  3.免费
     * @param testDays 当规则为1时。 此字段代表试用天数
     * @param ownApply 是否需要审核 1.是  0 否
     * @param isOpen 是否开启 1是 0否
     * @return
     */
    MyResult addOrUpdateXnVideoRule(Integer userType, Integer payRule, Integer testDays, Integer ownApply, String isOpen);


    /**
     * 新增或者修改规则缴费
     * @param id 缴费id
     * @param ruleId 规则id
     * @param timeType 事件类型 1.年 2.月
     * @param number 数量
     * @param price 价格
     * @return
     */
    MyResult addOrUpdateXnVideoRulePay(Integer id, Integer ruleId, Integer timeType, Integer number, String price);
}
