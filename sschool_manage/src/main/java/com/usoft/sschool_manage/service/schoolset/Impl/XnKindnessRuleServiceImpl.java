package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnKindnessRule;
import com.usoft.smartschool.pojo.XnKindnessRuleExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnKindnessRuleMapper;
import com.usoft.sschool_manage.service.schoolset.XnKindnessRuleService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/5/7 16:47
 */
@Service
public class XnKindnessRuleServiceImpl implements XnKindnessRuleService {


    @Resource
    private XnKindnessRuleMapper xnKindnessRuleMapper;


    @Override
    public MyResult selectXnKindnessRule() {
        Integer schoolId = SystemParam.getSchoolId();
        XnKindnessRuleExample xnKindnessRuleExample = new XnKindnessRuleExample();
        XnKindnessRuleExample.Criteria criteria = xnKindnessRuleExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        List<XnKindnessRule> xnKindnessRules = xnKindnessRuleMapper.selectByExample(xnKindnessRuleExample);
        if(ObjectUtil.isEmpty(xnKindnessRules))return MyResult.failure("暂无数据");
        XnKindnessRule xnKindnessRule = xnKindnessRules.get(0);
        Map<String,Object> map = new HashMap<>();
        map.put("isOpen",xnKindnessRule.getIsopen());
        map.put("perKind",xnKindnessRule.getPerkind());
        return MyResult.success(map);
    }

    @Override
    public MyResult editXnKindnessRule(Integer isOpen, Integer perKind) {
        if(ObjectUtil.isEmpty(isOpen))return MyResult.failure("请选择是否开启");
        if(ObjectUtil.isEmpty(perKind))return MyResult.failure("请输入每笔爱心所获得的爱心数量");
        Integer schoolId = SystemParam.getSchoolId();
        XnKindnessRuleExample xnKindnessRuleExample = new XnKindnessRuleExample();
        XnKindnessRuleExample.Criteria criteria = xnKindnessRuleExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        List<XnKindnessRule> xnKindnessRules = xnKindnessRuleMapper.selectByExample(xnKindnessRuleExample);
        try{
            if(ObjectUtil.isEmpty(xnKindnessRules)){
                XnKindnessRule xnKindnessRule = new XnKindnessRule();
                xnKindnessRule.setCreatetime(new Date());
                xnKindnessRule.setSid(schoolId);
                xnKindnessRule.setUid(SystemParam.getUserId());
                xnKindnessRule.setPerkind(perKind);
                int io = isOpen;
                xnKindnessRule.setIsopen((byte)io);
                xnKindnessRuleMapper.insert(xnKindnessRule);
            }else{
                XnKindnessRule xnKindnessRule = xnKindnessRules.get(0);
                int io = isOpen;
                xnKindnessRule.setIsopen((byte)io);
                xnKindnessRule.setPerkind(perKind);
                xnKindnessRuleMapper.updateByPrimaryKeySelective(xnKindnessRule);
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");

        }
        return MyResult.success("操作成功");
    }
}
