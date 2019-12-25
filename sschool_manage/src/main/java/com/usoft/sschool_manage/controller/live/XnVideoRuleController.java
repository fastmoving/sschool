package com.usoft.sschool_manage.controller.live;

import com.usoft.sschool_manage.service.live.XnVideoRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 视频缴费规则控制
 * @Author jijh
 * @Date 2019/5/22 16:37
 */
@RestController
@RequestMapping("/manage/videorule")
public class XnVideoRuleController {

    @Autowired
    private XnVideoRuleService xnVideoRuleService;

    /**
     * 查看视频缴费规则
     * @param userType
     * @return
     */
    @GetMapping("select")
    public Object selectXnVideoRule(Integer userType){
        return xnVideoRuleService.selectXnVideoRule(userType);
    }

    /**
     * 新增或者修改视频缴费规则
     * @param userType  用户类型 1.学生/家长 2.教师
     * @param payRule 规则  1.收费+试用期  2.收费  3.免费
     * @param testDays 当规则为1时。 此字段代表试用天数
     * @param ownApply 是否需要审核 1.是  0 否
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateXnVideoRule(Integer userType, Integer payRule, Integer testDays, Integer ownApply,String isOpen){
        return xnVideoRuleService.addOrUpdateXnVideoRule(userType, payRule, testDays, ownApply,isOpen);
    }

    /**
     * 新增或者修改规则缴费
     * @param id 缴费id
     * @param ruleId 规则id
     *
     * @param timeType 事件类型 1.年 2.月
     * @param number 数量
     * @param price 价格
     * @return
     */
    @PostMapping("addorupdatepay")
    public Object addOrUpdateXnVideoRulePay(Integer id, Integer ruleId, Integer timeType, Integer number, String price){
        return xnVideoRuleService.addOrUpdateXnVideoRulePay(id, ruleId, timeType, number, price);
    }
}
