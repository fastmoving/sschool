package com.usoft.sschool_manage.controller.schoolset;

import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.XnIntegralRule;
import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnIntegralRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 教师获取积分规则
 * @Author jijh
 * @Date 2019/5/9 14:30
 */
@RestController
@RequestMapping("/manage/integralrule")
public class XnIntegralRuleController  extends BaseController {


    @Autowired
    private XnIntegralRuleService xnIntegralRuleService;

    /**
     * 教师积分获取规则列表
     * @return
     */
    @GetMapping("select")
    public Object selectXnIntegralRule(){
        return xnIntegralRuleService.listXnIntegralRule();
    }

    /**
     * 编辑教师获取积分规则
     *
     *  integralNumber 积分数量
     *  function 功能 1.发布作业 2.班级圈审核 3.上传成绩 4.班级相册 5.请假审批
     *  isOpen 是否开启 0否 1是
     * @return
     */
    @PostMapping("edit")
    public Object editXnIntegralRule(String xnIntegralRuleList){
        XnIntegralRule[] xnIntegralRules = JSONObject.parseObject(xnIntegralRuleList,XnIntegralRule[].class);
        return xnIntegralRuleService.editXnIntegralRule( Arrays.asList(xnIntegralRules));
    }

//    public static void main(String[] args){
//        String xnIntegralRuleList = "[{\"isopen\":0,\"function\":1,\"id\":1,\"integralnumer\":10},{\"isopen\":1,\"function\":2,\"id\":2,\"integralnumer\":10},{\"isopen\":1,\"function\":3,\"id\":3,\"integralnumer\":10},{\"isopen\":1,\"function\":4,\"id\":4,\"integralnumer\":10},{\"isopen\":1,\"function\":5,\"id\":5,\"integralnumer\":10}]";
//        XnIntegralRule xnIntegralRules[] = JSONObject.parseObject(xnIntegralRuleList,XnIntegralRule[].class);
//        for(XnIntegralRule xnIntegralRule : xnIntegralRules){
//            System.out.println(xnIntegralRule);
//        }
//    }
}
