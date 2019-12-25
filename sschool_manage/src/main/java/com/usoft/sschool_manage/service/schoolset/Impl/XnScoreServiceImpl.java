package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.CfDepartmentMapper;
import com.usoft.sschool_manage.mapper.base.HlEnumitemMapper;
import com.usoft.sschool_manage.mapper.base.HlSchclassMapper;
import com.usoft.sschool_manage.mapper.base.XnScoreMapper;
import com.usoft.sschool_manage.service.schoolset.XnScoreService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/4/26 14:19
 */

@Service
public class XnScoreServiceImpl extends QueryAndInsertImp implements XnScoreService {


    @Resource
    private XnScoreMapper xnScoreMapper;//成绩管理

    @Resource
    private HlEnumitemMapper hlEnumitemMapper;//枚举类  -- 年级


    @Resource
    private HlSchclassMapper hlSchclassMapper;//班级管理

    @Resource
    private CfDepartmentMapper cfDepartmentMapper;//学校

    //上传班级成绩
    private final Integer SCORE = 3;

    //是否开启
    private final byte YES = 1;

    @Override
    public MyResult listXnScore(String studentName, String term, Integer schoolId, Integer gradeId, Integer classId, String testName, String subject
            , String score, Integer wrongNumber,Integer pageNo, Integer pageSize) {
        XnScoreExample xnScoreExample = new XnScoreExample();
        XnScoreExample.Criteria criteria = xnScoreExample.createCriteria();
        if(!ObjectUtil.isEmpty(studentName)) criteria.andStudentnameLike("%"+studentName+"%");
        if(!ObjectUtil.isEmpty(term))criteria.andTermLike("%"+term+"%");
        if(!ObjectUtil.isEmpty(schoolId))criteria.andSidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(classId))criteria.andCidEqualTo(classId);
        if(!ObjectUtil.isEmpty(testName))criteria.andTestnameLike("%"+testName+"%");
        if(!ObjectUtil.isEmpty(subject))criteria.andTsubjectLike("%"+subject+"%");
        if(!ObjectUtil.isEmpty(score))criteria.andScoreEqualTo(score);
        if(!ObjectUtil.isEmpty(wrongNumber))criteria.andWrongnumEqualTo(wrongNumber);
        if(!ObjectUtil.isEmpty(gradeId)){
            HlSchclassExample hlSchclassExample = new HlSchclassExample();
            HlSchclassExample.Criteria criteria1 = hlSchclassExample.createCriteria();
            criteria1.andGradetypeEqualTo(gradeId);
            List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(hlSchclassExample);
            if(ObjectUtil.isEmpty(hlSchclasses))return MyResult.failure("暂无数据");
            List<Integer> classIds = new ArrayList<>();
            for(HlSchclass hlSchclass : hlSchclasses){
                classIds.add(hlSchclass.getId());
            }
            criteria.andCidIn(classIds);
        }
        List<XnScore> xnScores = xnScoreMapper.selectByExample(xnScoreExample);
        if(ObjectUtil.isEmpty(xnScores))return MyResult.failure("暂无数据");

        List<Map<String,Object>> result = new ArrayList<>();
        for(XnScore xnScore : xnScores){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnScore.getId());
            map.put("schoolId",xnScore.getSid());
            map.put("term",xnScore.getTerm());
            map.put("sid",xnScore.getSid());
            map.put("name",xnScore.getStudentname());
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(xnScore.getSid());
            map.put("schoolName",cfDepartment.getDeptname());
            HlSchclassKey hlSchclassKey = new HlSchclassKey();
            hlSchclassKey.setSchoolid(xnScore.getSid());
            hlSchclassKey.setId(xnScore.getCid());
            HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hlSchclassKey);//班级
            HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(hlSchclass.getGradetype());//年级
            map.put("classId",xnScore.getCid());
            map.put("className",hlSchclass.getClassname());//班级名称
            map.put("gradeName",hlEnumitem.getEnumitemname());//年级名称
            map.put("testName",xnScore.getTestname());
            map.put("subject",xnScore.getTsubject());
            map.put("score",xnScore.getScore());
            map.put("wrongNumber",xnScore.getWrongnum());
            result.add(map);
            //map.put("grade",hlEnumitemMapper.selectByPrimaryKey(xnScore.get))
        }
        if(pageNo==null)pageNo = 1;
        if(pageSize == null)pageSize = 20;


        return ResultPage.getPageResult(result,pageNo, pageSize);
    }

    @Override
    @Transactional
    public MyResult addOrUpdateXnScore(Integer id, Integer studentId, String studentName, Integer schoolId, Integer classId, String term, String testName, String subject, String score, Integer wrongNumber) {
        //添加成绩
        XnScore xnScore = new XnScore();
        schoolId = SystemParam.getSchoolId();
        if(ObjectUtil.isEmpty(studentId)) return MyResult.failure("请传入学生id");
        if(ObjectUtil.isEmpty(studentName)) return MyResult.failure("学生姓名？");
        //if(ObjectUtil.isEmpty(schoolId)) return MyResult.failure("学校?");
        if(ObjectUtil.isEmpty(classId))return MyResult.failure("班级？");
        if(ObjectUtil.isEmpty(term)) return MyResult.failure("学期?");
        if(ObjectUtil.isEmpty(testName)) return MyResult.failure("考试名称");
        if(ObjectUtil.isEmpty(subject)) return MyResult.failure("考试科目？");
        if(ObjectUtil.isEmpty(score)) return MyResult.failure("分数？");
        try{
            Double scoreInteger = Double.valueOf(score);
            if(scoreInteger<0 || scoreInteger>300) return MyResult.failure("分数");
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("未知的分数");
        }
        if(ObjectUtil.isEmpty(wrongNumber))return MyResult.failure("错题数量");
        if(wrongNumber<0)return MyResult.failure("错题数量不能小于零");
        xnScore.setCid(classId);

        HlSchclassKey hlSchclassKey = new HlSchclassKey();
        hlSchclassKey.setSchoolid(schoolId);
        hlSchclassKey.setId(classId);
        HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hlSchclassKey);//班级
        if(ObjectUtil.isEmpty(hlSchclass)) return MyResult.failure("未找到当前班级信息");

        xnScore.setClassname(hlSchclass.getClassname());
        xnScore.setSid(schoolId);
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);
        if(ObjectUtil.isEmpty(cfDepartment))return MyResult.failure("未找到当前学校信息");
        xnScore.setSchoolname(cfDepartment.getDeptname());
        xnScore.setScore(score);
        xnScore.setStudentname(studentName);
        xnScore.setTerm(term);
        xnScore.setStuid(studentId);
        xnScore.setTsubject(subject);
        xnScore.setTestname(testName);
        xnScore.setWrongnum(wrongNumber);
        String message = "";
        try{
            if(ObjectUtil.isEmpty(id)){
                xnScoreMapper.insert(xnScore);
                message = "添加成功";
            }else{
                xnScore.setId(id);
                XnScore xnScore1 = xnScoreMapper.selectByPrimaryKey(id);
                if(ObjectUtil.isEmpty(xnScore1))return MyResult.failure("未找到当前成绩信息，无法修改");
                xnScoreMapper.updateByPrimaryKeySelective(xnScore);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        //添加积分
        if (id == null){
            XnIntegralRule rule = queryIntegralRule(SCORE);
            if(rule.getIsopen() == YES){
                insertIntegral(SCORE,rule.getIntegralnumer());
            }
        }
        return MyResult.success("添加成功");
    }

    @Override
    public MyResult deleteXnScore(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        int  i = 0;
        try{
            XnScoreExample xnScoreExample = new XnScoreExample();
            XnScoreExample.Criteria criteria = xnScoreExample.createCriteria();
            criteria.andIdIn(Arrays.asList(ids));
            i = xnScoreMapper.deleteByExample(xnScoreExample);
        }catch (Exception e ){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除"+i+"条数据");
    }
}
