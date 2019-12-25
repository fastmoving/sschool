package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.pojo.XnIntegralRule;
import com.usoft.smartschool.util.MyResult;

import java.util.List;

/**
 * 教师获取积分规则表
 * @Author jijh
 * @Date 2019/5/7 17:19
 */
public interface XnIntegralRuleService {

    /**
     * 教师积分获取规则列表
     * @return
     */
    MyResult listXnIntegralRule();


    /**
     * 编辑教师获取积分规则
     *
     *  integralNumber 积分数量
     *  function 功能 1.发布作业 2.班级圈审核 3.上传成绩 4.班级相册 5.请假审批
     *  isOpen 是否开启 0否 1是
     * @return
     */
    MyResult editXnIntegralRule(List<XnIntegralRule> xnIntegralRuleList);


}
