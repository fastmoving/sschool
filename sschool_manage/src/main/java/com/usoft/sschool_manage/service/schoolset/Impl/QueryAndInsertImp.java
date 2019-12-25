package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnIntegralRule;
import com.usoft.smartschool.pojo.XnTeacherIntegral;
import com.usoft.sschool_manage.mapper.base.XnIntegralRuleMapper;
import com.usoft.sschool_manage.mapper.base.XnTeacherIntegralMapper;
import com.usoft.sschool_manage.service.schoolset.IQueryAndInsertClassService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/7/25 17:23
 * @Version 1.0
 */
@Service
public class QueryAndInsertImp  implements IQueryAndInsertClassService {

    @Resource
    private XnIntegralRuleMapper integralRuleMapper;

    @Resource
    private XnTeacherIntegralMapper integralMapper;

    private final byte ADD = 1;

    /**
     * 查询规则
     * @return
     */
    @Override
    public XnIntegralRule queryIntegralRule(Integer function) {
        int schoolId = SystemParam.getSchoolId();
        Map key = new HashMap();
        key.put("schoolId",schoolId);
        key.put("function",function);
        List<XnIntegralRule> rule = integralRuleMapper.queryIntegral(key);
        if (rule.isEmpty())return null;
        XnIntegralRule integralRule = rule.get(0);
        return integralRule;
    }

    /**
     * 添加积分
     * @return
     */
    @Override
    @Transactional
    public int insertIntegral(Integer function,Integer num) {
        Integer teacherId = SystemParam.getUserId();
        Integer schoolId = SystemParam.getSchoolId();
        XnTeacherIntegral integral = new XnTeacherIntegral();
        integral.setSid(schoolId);
        integral.setTid(teacherId);
        integral.setType(ADD);
        integral.setCreatetime(new Date());
        integral.setNumber(num);
        return integralMapper.insertSelective(integral);
    }
}
