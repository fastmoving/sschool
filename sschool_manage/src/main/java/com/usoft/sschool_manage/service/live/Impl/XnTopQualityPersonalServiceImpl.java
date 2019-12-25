package com.usoft.sschool_manage.service.live.Impl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.*;
import com.usoft.sschool_manage.service.live.XnTopQualityPersonalService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/7/9 15:14
 */
@Service
public class XnTopQualityPersonalServiceImpl implements XnTopQualityPersonalService {

    @Resource
    private XnTopqualityPersonalRuleMapper xnTopqualityPersonalRuleMapper;

    @Resource
    private CfDepartmentMapper cfDepartmentMapper ;

    @Resource
    private HlTeacherMapper hlTeacherMapper;

    @Resource
    private XnTopqualityClassRuleMapper xnTopqualityClassRuleMapper;


    /**
     * 个性化配置
     */
    private static final String PERSONAL = "1";

    /**
     * 默认配置
     */
    private static final String NORMAL = "0";


    @Override
    public MyResult listXnTopQualityPersonalClass(Integer pageNo, Integer pageSize) {
        XnTopqualityPersonalRuleExample xnTopqualityPersonalRuleExample = new XnTopqualityPersonalRuleExample();
        List<XnTopqualityPersonalRule> xnTopqualityPersonalRules = xnTopqualityPersonalRuleMapper.selectByExample(xnTopqualityPersonalRuleExample);
        if(ObjectUtil.isEmpty(xnTopqualityPersonalRules))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(XnTopqualityPersonalRule xnTopqualityPersonalRule : xnTopqualityPersonalRules){
            Map<String, Object> map = new HashMap<>();
            map.put("id",xnTopqualityPersonalRule.getId());
            Integer schoolId = xnTopqualityPersonalRule.getSid();
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);
            map.put("schoolId",schoolId);
            map.put("schoolName", cfDepartment.getDeptname());
            HlTeacherKey hlTeacherKey = new HlTeacherKey();
            hlTeacherKey.setSchoolid(schoolId);
            hlTeacherKey.setId(xnTopqualityPersonalRule.getUid());
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
            map.put("teacherId",hlTeacher.getId());
            map.put("teacherName",hlTeacher.getTname());
            map.put("videoUrl",xnTopqualityPersonalRule.getAttr1());
            map.put("subject",xnTopqualityPersonalRule.getSubject());
            map.put("tPrice",xnTopqualityPersonalRule.getTprice());
            map.put("sPrice",xnTopqualityPersonalRule.getSprice());
            result.add(map);

        }
        return ResultPage.getPageResult(result,pageNo,pageSize);
    }

    @Override
    public MyResult addOrUpdateXnTopQualityPersonalClass(Integer id, String subject, Integer tid, String tPrice, String sPrice, String videoUrl, String isPersonal) {
        if(ObjectUtil.isEmpty(subject))return MyResult.failure("请传入科目");
        if(ObjectUtil.isEmpty(tid))return MyResult.failure("请传入教师id");
        if(ObjectUtil.isEmpty(videoUrl))return MyResult.failure("请传入视频");
        Integer schoolId = SystemParam.getSchoolId();
        if(NORMAL.equals(isPersonal)){
            XnTopqualityClassRuleExample xnTopqualityClassRuleExample = new XnTopqualityClassRuleExample();
            XnTopqualityClassRuleExample.Criteria criteria = xnTopqualityClassRuleExample.createCriteria();

            criteria.andSidEqualTo(schoolId);
            List<XnTopqualityClassRule> xnTopqualityClassRules = xnTopqualityClassRuleMapper.selectByExample(xnTopqualityClassRuleExample);
            if(ObjectUtil.isEmpty(xnTopqualityClassRules)){
                tPrice ="0";
                sPrice = "0";

            }else{
                XnTopqualityClassRule XnTopqualityClassRule = xnTopqualityClassRules.get(0);
                tPrice = XnTopqualityClassRule.getPrice().toString();
                sPrice = XnTopqualityClassRule.getPrice().toString();

            }
        }
        String message = "";
        XnTopqualityPersonalRule xnTopqualityPersonalRule = new XnTopqualityPersonalRule();
        xnTopqualityPersonalRule.setAttr1(videoUrl);
        xnTopqualityPersonalRule.setAttr2(isPersonal);
        xnTopqualityPersonalRule.setSid(schoolId);
        xnTopqualityPersonalRule.setTid(tid);
        xnTopqualityPersonalRule.setSubject(subject);
        xnTopqualityPersonalRule.setTprice(new BigDecimal(tPrice));
        xnTopqualityPersonalRule.setSprice(new BigDecimal(sPrice));
        if(id == null){
            xnTopqualityPersonalRule.setCreatetime(new Date());
            xnTopqualityPersonalRule.setUid(SystemParam.getUserId());
            xnTopqualityPersonalRuleMapper.insert(xnTopqualityPersonalRule);
            message = "添加成功";
        }else{
            XnTopqualityPersonalRule xnTopqualityPersonalRuleExist = xnTopqualityPersonalRuleMapper.selectByPrimaryKey(id);
            if(ObjectUtil.isEmpty(xnTopqualityPersonalRuleExist))return MyResult.failure("未找到当前数据，无法修改");
            xnTopqualityPersonalRule.setId(id);
            xnTopqualityPersonalRuleMapper.updateByPrimaryKeySelective(xnTopqualityPersonalRule);
            message = "修改成功";
        }
        return MyResult.success(message);
    }
}
