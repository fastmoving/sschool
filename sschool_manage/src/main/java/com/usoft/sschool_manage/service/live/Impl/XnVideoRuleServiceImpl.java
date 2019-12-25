package com.usoft.sschool_manage.service.live.Impl;

import com.usoft.smartschool.pojo.XnVideoRule;
import com.usoft.smartschool.pojo.XnVideoRuleExample;
import com.usoft.smartschool.pojo.XnVideoRulePay;
import com.usoft.smartschool.pojo.XnVideoRulePayExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnVideoRuleMapper;
import com.usoft.sschool_manage.mapper.base.XnVideoRulePayMapper;
import com.usoft.sschool_manage.service.live.XnVideoRuleService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/5/22 15:22
 */

@Service
public class XnVideoRuleServiceImpl implements XnVideoRuleService {

    @Resource
    private XnVideoRuleMapper xnVideoRuleMapper;

    @Resource
    private XnVideoRulePayMapper xnVideoRulePayMapper;

    @Override
    public MyResult selectXnVideoRule(Integer userType) {
        if(ObjectUtil.isEmpty(userType)){
            return MyResult.failure("请选择查看的用户类型");
        }
        Integer schoolId = SystemParam.getSchoolId();
        XnVideoRuleExample xnVideoRuleExample = new XnVideoRuleExample();
        XnVideoRuleExample.Criteria criteria= xnVideoRuleExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        int ut = userType;
        criteria.andUsertypeEqualTo((byte)ut);
        List<XnVideoRule> xnVideoRules = xnVideoRuleMapper.selectByExample(xnVideoRuleExample);
        //封装返回数据
        Map<String,Object> map = new HashMap<>();
        map.put("isOpen",null);
        map.put("payRule",null);
        map.put("ischeck",null);
        if(!ObjectUtil.isEmpty(xnVideoRules)){
            XnVideoRule xnVideoRule = xnVideoRules.get(0);
            map.put("isOpen",xnVideoRule.getAttr1());
            map.put("payRule",xnVideoRule.getPayrule());
            if(xnVideoRule.getPayrule()==1){
                map.put("testDay",xnVideoRule.getTestdays());
            }
            map.put("ischeck",xnVideoRule.getOwnapply());
            map.put("id",xnVideoRule.getId());
            //查询缴费规则
            XnVideoRulePayExample xnVideoRulePayExample = new XnVideoRulePayExample();
            XnVideoRulePayExample.Criteria criteria1 = xnVideoRulePayExample.createCriteria();
            criteria1.andRuleidEqualTo(xnVideoRule.getId());
            List<XnVideoRulePay> xnVideoRulePays = xnVideoRulePayMapper.selectByExample(xnVideoRulePayExample);
            if(ObjectUtil.isEmpty(xnVideoRulePays)) {
                map.put("payDetail",new String[]{});
            } else{
                List<Map<String,Object>> payDetail = new ArrayList<>();
                for(XnVideoRulePay xnVideoRulePay : xnVideoRulePays){
                    Map<String,Object> m = new HashMap<>();
                    m.put("timeType",xnVideoRulePay.getTimetype()==1?"年":"月");
                    m.put("price",xnVideoRulePay.getPrice());
                    m.put("number",xnVideoRulePay.getNumber());
                    payDetail.add(m);
                }
                map.put("payDetail",payDetail);
            }
        }
        return MyResult.success(map);
    }

    @Override
    public MyResult addOrUpdateXnVideoRule(Integer userType, Integer payRule, Integer testDays, Integer ownApply, String isOpen) {
        if(ObjectUtil.isEmpty(userType)){
            return MyResult.failure("请选择用户类型");
        }
        if(userType<1 || userType >2){
            return MyResult.failure("未知的用户类型");
        }
        if(ObjectUtil.isEmpty(payRule)){
            return MyResult.failure("请选择规则");
        }
        if(payRule<1 || payRule >3){
            return MyResult.failure("未知的规则");
        }
        if(payRule ==1){
            if(ObjectUtil.isEmpty(testDays)){
                return MyResult.failure("请输入试用天数");
            }
            if(testDays<0){
                return MyResult.failure("非法的试用天数");
            }
        }
        if(ObjectUtil.isEmpty(ownApply)){
            return MyResult.failure("请选择是否需要审核");
        }
        if(ownApply<0 || ownApply>1){
            return MyResult.failure("未知的审核条件");
        }
        if(ObjectUtil.isEmpty(isOpen)){
            return MyResult.failure("请选择是否开启");
        }

        Integer schoolId = SystemParam.getSchoolId();
        XnVideoRuleExample xnVideoRuleExample = new XnVideoRuleExample();
        XnVideoRuleExample.Criteria criteria = xnVideoRuleExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        int t = userType;
        criteria.andUsertypeEqualTo((byte)t);
        Integer id = null;
        List<XnVideoRule> xnVideoRules = xnVideoRuleMapper.selectByExample(xnVideoRuleExample);
        if(ObjectUtil.isEmpty(xnVideoRules)){
            XnVideoRule xnVideoRule = new XnVideoRule();
            xnVideoRule.setCreatetime(new Date());
            xnVideoRule.setUid(SystemParam.getUserId());
            xnVideoRule.setSid(schoolId);
            int oa = ownApply;
            xnVideoRule.setOwnapply((byte)oa);
            int pr = payRule;
            xnVideoRule.setPayrule((byte)pr);
            int ut = userType;
            xnVideoRule.setUsertype((byte)ut);
            if(payRule == 1){
                xnVideoRule.setTestdays(testDays);
            }
            xnVideoRule.setAttr1(isOpen);
            xnVideoRuleMapper.insert(xnVideoRule);
            id = xnVideoRule.getId();
        }else{
            XnVideoRule xnVideoRule = xnVideoRules.get(0);
            xnVideoRule.setSid(schoolId);
            int oa = ownApply;
            xnVideoRule.setOwnapply((byte)oa);
            int pr = payRule;
            xnVideoRule.setPayrule((byte)pr);
            int ut = userType;
            xnVideoRule.setUsertype((byte)ut);
            xnVideoRule.setAttr1(isOpen);
            if(payRule == 1){
                xnVideoRule.setTestdays(testDays);
            }
            xnVideoRuleMapper.updateByPrimaryKeySelective(xnVideoRule);
            id = xnVideoRule.getId();
        }
        Map<String,Object> result = new HashMap<>();
        result.put("id",id);
        return MyResult.success("操作成功",result);
    }

    @Override
    public MyResult addOrUpdateXnVideoRulePay(Integer id, Integer ruleId, Integer timeType, Integer number, String price) {
        if(ObjectUtil.isEmpty(ruleId))return MyResult.failure("请选择规则");
        if(ObjectUtil.isEmpty(timeType))return MyResult.failure("请选择时间类型");
        if(ObjectUtil.isEmpty(number))return MyResult.failure("请选择数量");
        if(ObjectUtil.isEmpty(price))return MyResult.failure("请选择类型");

        if(timeType<1 || timeType>2)return MyResult.failure("未知的时间类型");
        if(number<0){
            return MyResult.failure("非法数量");
        }
        Integer schoolId = SystemParam.getSchoolId();
        XnVideoRule xnVideoRule = xnVideoRuleMapper.selectByPrimaryKey(ruleId);
        if(ObjectUtil.isEmpty(xnVideoRule))return MyResult.failure("未找到相关的规则");
        if(!xnVideoRule.getSid().equals(schoolId))return MyResult.failure("未知的规则");
        XnVideoRulePay xnVideoRulePay = new XnVideoRulePay();

        int tt = timeType;
        xnVideoRulePay.setTimetype((byte)tt);
        xnVideoRulePay.setNumber(number);

        String message = "";
        try{
            BigDecimal prices = new BigDecimal(price);
            xnVideoRulePay.setPrice(prices);
            xnVideoRulePay.setRuleid(ruleId);
            if(ObjectUtil.isEmpty(id)){
                XnVideoRulePayExample xnVideoRulePayExample = new XnVideoRulePayExample();
                XnVideoRulePayExample.Criteria criteria = xnVideoRulePayExample.createCriteria();
                criteria.andRuleidEqualTo(ruleId);
                criteria.andTimetypeEqualTo((byte)tt);
                List<XnVideoRulePay> xnVideoRulePays = xnVideoRulePayMapper.selectByExample(xnVideoRulePayExample);
                if(!ObjectUtil.isEmpty(xnVideoRulePays)){
                    XnVideoRulePay xnVideoRulePay1 = xnVideoRulePays.get(0);
                    xnVideoRulePay1.setPrice(prices);
                    xnVideoRulePay1.setNumber(number);
                    xnVideoRulePayMapper.updateByPrimaryKey(xnVideoRulePay1);
                }else{
                    xnVideoRulePay.setCreatetime(new Date());
                    xnVideoRulePayMapper.insert(xnVideoRulePay);
                    message = "添加成功";
                }


            }else{
                XnVideoRulePay isExist = xnVideoRulePayMapper.selectByPrimaryKey(id);
                if(ObjectUtil.isEmpty(isExist)) MyResult.failure("未找到当前数据，无法修改");
                xnVideoRulePay.setId(id);
                xnVideoRulePayMapper.updateByPrimaryKeySelective(xnVideoRulePay);
                message = "修改成功";
            }

        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("未知价格");
        }
        return MyResult.success(message);
    }
}
