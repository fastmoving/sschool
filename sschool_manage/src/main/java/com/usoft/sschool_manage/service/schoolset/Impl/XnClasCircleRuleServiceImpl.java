package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.HlTeacherMapper;
import com.usoft.sschool_manage.mapper.base.XnClascircleRuleMapper;
import com.usoft.sschool_manage.service.schoolset.XnClasCircleRuleService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/5/7 16:00
 */
@Service
public class XnClasCircleRuleServiceImpl implements XnClasCircleRuleService {

    @Resource
    private XnClascircleRuleMapper xnClascircleRuleMapper;

    @Resource
    private HlTeacherMapper hlTeacherMapper;

    /**
     * 班级圈审核规则  关闭
     */
    private final static byte CLOSE =0;
    /**
     * 班级圈审核规则 开启
     */
    private final static byte OPEN = 1;

    /**
     * 班级圈不需要审核
     */
    private final static byte UNCHECK = 0;

    /**
     * 班级圈需要审核
     */
    private final static byte CHECK = 1;


    @Override
    public MyResult selectXnClasCircleRule() {
        XnClascircleRuleExample xnClascircleRuleExample = new XnClascircleRuleExample();
        XnClascircleRuleExample.Criteria criteria = xnClascircleRuleExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andLuidEqualTo(schoolId);
        List<XnClascircleRule> xnClascircleRules = xnClascircleRuleMapper.selectByExample(xnClascircleRuleExample);
        if(ObjectUtil.isEmpty(xnClascircleRules))return MyResult.failure("暂无数据");
        XnClascircleRule xnClascircleRule = xnClascircleRules.get(0);
        Map<String,Object> map = new HashMap<>();
        map.put("isOpen",xnClascircleRule.getIsopen());
        map.put("checkType",xnClascircleRule.getChecktype());
        return MyResult.success(map);
    }

    @Override
    public MyResult editXnClasCircleRule(Integer isOpen, Integer checkType) {

        if(ObjectUtil.isEmpty(isOpen))return MyResult.failure("请选择是否开启");
        if(ObjectUtil.isEmpty(checkType))return MyResult.failure("请选择是否需要审核");
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId= SystemParam.getUserId();
        XnClascircleRuleExample xnClascircleRuleExample = new XnClascircleRuleExample();
        XnClascircleRuleExample.Criteria criteria = xnClascircleRuleExample.createCriteria();
        criteria.andLuidEqualTo(schoolId);
        List<XnClascircleRule> xnClascircleRules = xnClascircleRuleMapper.selectByExample(xnClascircleRuleExample);
        try{
            if(ObjectUtil.isEmpty(xnClascircleRules)){
                XnClascircleRule xnClascircleRule = new XnClascircleRule();
                int io = isOpen;
                xnClascircleRule.setIsopen((byte)io);
                int ct = checkType;
                xnClascircleRule.setChecktype((byte)ct);
                xnClascircleRule.setLuid(schoolId);
                xnClascircleRule.setLastmodifytime(new Date());
                xnClascircleRule.setLastmodifyuser(getTeacherName(schoolId,userId));
                xnClascircleRuleMapper.insert(xnClascircleRule);
            }else{
                XnClascircleRule xnClascircleRule = xnClascircleRules.get(0);
                int io = isOpen;
                xnClascircleRule.setIsopen((byte)io);
                int ct = checkType;
                xnClascircleRule.setChecktype((byte)ct);
                xnClascircleRuleMapper.updateByPrimaryKeySelective(xnClascircleRule);
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        return MyResult.success("操作成功");
    }


    /**
     * 获取教师名字
     */

    private String getTeacherName(Integer schoolId, Integer tId){
        HlTeacherKey hlTeacherKey = new HlTeacherKey();
        hlTeacherKey.setSchoolid(schoolId);
        hlTeacherKey.setId(tId);
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
        if(ObjectUtil.isEmpty(hlTeacher))return "未知";
        return hlTeacher.getTname();
    }
}
