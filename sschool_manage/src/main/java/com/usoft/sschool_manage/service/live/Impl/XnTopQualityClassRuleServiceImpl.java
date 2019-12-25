package com.usoft.sschool_manage.service.live.Impl;

import com.usoft.smartschool.pojo.XnTopqualityClassRule;
import com.usoft.smartschool.pojo.XnTopqualityClassRuleExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnTopqualityClassRuleMapper;
import com.usoft.sschool_manage.service.live.XnTopQualityClassRuleService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Author jijh
 * @Date 2019/7/9 14:21
 */

@Service
public class XnTopQualityClassRuleServiceImpl implements XnTopQualityClassRuleService {

    @Resource
    private XnTopqualityClassRuleMapper xnTopqualityClassRuleMapper;

    @Override
    public MyResult selectXnTopQualiyClassRule() {
        Integer schoolId = SystemParam.getSchoolId();
        XnTopqualityClassRuleExample xnTopqualityClassRuleExample = new XnTopqualityClassRuleExample();
        XnTopqualityClassRuleExample.Criteria criteria = xnTopqualityClassRuleExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        List<XnTopqualityClassRule> xnTopqualityClassRules = xnTopqualityClassRuleMapper.selectByExample(xnTopqualityClassRuleExample);
        Map<String,Object> result = new HashMap<>();
        if (ObjectUtil.isEmpty(xnTopqualityClassRules)){
            result.put("isPay",0);
            result.put("price",0);
        }else{
            XnTopqualityClassRule xnTopqualityClassRule = xnTopqualityClassRules.get(0);
            result.put("isPay",xnTopqualityClassRule.getIspay());
            result.put("price",xnTopqualityClassRule.getPrice());
        }


        return MyResult.success(result);
    }

    @Override
    public MyResult addOrUpdateXnTopQualiyClassRule(Integer isPay, String price) {
        BigDecimal prices  = null;
        try{
            prices = new BigDecimal(price);

        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("价格格式有误");
        }
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();
        XnTopqualityClassRuleExample xnTopqualityClassRuleExample = new XnTopqualityClassRuleExample();
        XnTopqualityClassRuleExample.Criteria criteria = xnTopqualityClassRuleExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        List<XnTopqualityClassRule> xnTopqualityClassRules = xnTopqualityClassRuleMapper.selectByExample(xnTopqualityClassRuleExample);
        if(ObjectUtil.isEmpty(xnTopqualityClassRules)){
            XnTopqualityClassRule xnTopqualityClassRule = new XnTopqualityClassRule();
            xnTopqualityClassRule.setCreatetime(new Date());
            xnTopqualityClassRule.setPrice(prices);
            xnTopqualityClassRule.setSid(schoolId);
            xnTopqualityClassRule.setIspay(isPay);
            xnTopqualityClassRule.setUid(userId);
            xnTopqualityClassRuleMapper.insert(xnTopqualityClassRule);
        }else{
            XnTopqualityClassRule xnTopqualityClassRule = xnTopqualityClassRules.get(0);
            xnTopqualityClassRule.setIspay(isPay);
            xnTopqualityClassRule.setPrice(prices);
            xnTopqualityClassRuleMapper.updateByPrimaryKey(xnTopqualityClassRule);
        }
        return MyResult.success("操作成功");
    }
}
