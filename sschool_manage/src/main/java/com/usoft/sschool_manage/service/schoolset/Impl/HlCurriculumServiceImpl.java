package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.*;
import com.usoft.sschool_manage.service.schoolset.HlCurriculumService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/4/24 13:15
 */
@Service
public class HlCurriculumServiceImpl implements HlCurriculumService {

    @Resource
    private HlCurriculumMapper hlCurriculumMapper;//课程表

    @Resource
    private HlTeacherMapper hlTeacherMapper;//教师

    @Resource
    private HlCurriculumsetMapper hlCurriculumsetMapper;//课时

    @Resource
    private HlEnumitemMapper hlEnumitemMapper;//枚举类

    @Resource
    private HlSchclassMapper hlSchclassMapper;//班级信息

    @Resource
    private HlSchoolyearMapper hlSchoolyearMapper;//学年

    /**
     * 当前学年
     */
    private static final int CURRENT_YEAR = 192;

    @Override
    public MyResult listCurriculum(Integer classId, Integer week, Integer lesson, Integer subjectId, String teacher, Integer pageNo, Integer pageSize) {
        if(pageNo == null) pageNo = 1;
        if(pageSize == null) pageSize = 20;
        HlCurriculumExample hlCurriculumExample = new HlCurriculumExample();
        HlCurriculumExample.Criteria criteria = hlCurriculumExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andSchoolidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(week)){
            criteria.andWeekEqualTo(week);
        }
        if(!ObjectUtil.isEmpty(lesson)){
            criteria.andLessonidEqualTo(lesson);
        }
        if(!ObjectUtil.isEmpty(subjectId)){
            criteria.andDisciplineEqualTo(subjectId);
        }
        if(!ObjectUtil.isEmpty(classId)){
            criteria.andClassidEqualTo(classId);
        }
        if(!ObjectUtil.isEmpty(teacher)){
            HlTeacherExample hlTeacherExample = new HlTeacherExample();
            HlTeacherExample.Criteria criteria1 = hlTeacherExample.createCriteria();
            criteria1.andTnameLike("%"+teacher+"%");
            criteria1.andSchoolidEqualTo(schoolId);
            List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(hlTeacherExample);
            if(!ObjectUtil.isEmpty(hlTeachers)){
                List<Integer> teacherId = new ArrayList<>();
                for(HlTeacher hlTeacher : hlTeachers){
                    teacherId.add(hlTeacher.getId());
                }
                criteria.andClassteacherIn(teacherId);
            }else{
                return MyResult.failure("无数据");
            }
        }
        List<HlCurriculum> hlCurriculumList = hlCurriculumMapper.selectByExample(hlCurriculumExample);
        if(ObjectUtil.isEmpty(hlCurriculumList)){
           return MyResult.failure("暂无数据");
        }

        List<Map<String,Object>> result = new ArrayList<>();
        for(HlCurriculum hlCurriculum : hlCurriculumList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hlCurriculum.getId());
            map.put("week",hlCurriculum.getWeek());
            map.put("lesson",hlCurriculum.getLessonid());
            //教师信息
            Integer teacherId = hlCurriculum.getClassteacher();
            HlTeacherKey hlTeacherKey = new HlTeacherKey();
            hlTeacherKey.setId(teacherId);
            hlTeacherKey.setSchoolid(schoolId);
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
            map.put("teacherId",hlCurriculum.getClassteacher());
            map.put("teacherName",hlTeacher!=null ?hlTeacher.getTname():"未知");
            //科目信息
            Integer subId = hlCurriculum.getDiscipline();
            HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(subId);
            map.put("discipline",hlEnumitem.getId());
            map.put("disciplineName",hlEnumitem.getEnumitemname());
            //班级信息
            Integer classid = hlCurriculum.getClassid();
            HlSchclassKey hlSchclassKey = new HlSchclassKey();
            hlSchclassKey.setId(classid);
            hlSchclassKey.setSchoolid(schoolId);
            HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hlSchclassKey);
            map.put("classId",classid);
            map.put("className",hlSchclass != null ?hlSchclass.getClassname(): "未知");
            result.add(map);
        }

        return ResultPage.getPageResult(result,pageNo,pageSize);
    }

    @Override
    public MyResult addCurriculum(Integer id, Integer classId, Integer cycle, Integer discipline, Integer classTeacher, Integer lessonId, Integer week) {
        Integer schoolId = SystemParam.getSchoolId();
        HlCurriculum hlCurriculum = new HlCurriculum();
        if(ObjectUtil.isEmpty(classId))return MyResult.failure("请选择班级");
        //if(ObjectUtil.isEmpty(cycle))return MyResult.failure("请选择节次");
        if(ObjectUtil.isEmpty(discipline))return MyResult.failure("请选择科目");
        if(ObjectUtil.isEmpty(classTeacher))return MyResult.failure("请选择班主任");
        if(ObjectUtil.isEmpty(lessonId))return MyResult.failure("请选择节次");
        if(ObjectUtil.isEmpty(week))return MyResult.failure("请选择周几");
        if(week<0 || week>6){
            return MyResult.failure("未知的时间");
        }

        HlCurriculumExample hlCurriculumExample = new HlCurriculumExample();
        HlCurriculumExample.Criteria criterias = hlCurriculumExample.createCriteria();
        criterias.andClassidEqualTo(classId);
        criterias.andLessonidEqualTo(lessonId);
        criterias.andWeekEqualTo(week);
        criterias.andSchoolidEqualTo(schoolId);
        List<HlCurriculum>  hlCurriculumList = hlCurriculumMapper.selectByExample(hlCurriculumExample);
        if(ObjectUtil.isEmpty(id) && !ObjectUtil.isEmpty(hlCurriculumList)){
            return MyResult.failure("您已添加了该节次，请勿重复添加");
        }



        hlCurriculum.setSchoolid(schoolId);
        hlCurriculum.setClassid(classId);
        hlCurriculum.setClassteacher(classTeacher);
        hlCurriculum.setDiscipline(discipline);
        hlCurriculum.setWeek(week);
        hlCurriculum.setCycle(cycle);
        hlCurriculum.setLessonid(lessonId);

        //查询出当前学年的信息
        HlSchoolyearExample hlSchoolyearExample = new HlSchoolyearExample();
        HlSchoolyearExample.Criteria criteria = hlSchoolyearExample.createCriteria();
        criteria.andIsnowyearEqualTo(CURRENT_YEAR);
        List<HlSchoolyear> hlSchoolyears = hlSchoolyearMapper.selectByExample(hlSchoolyearExample);
        if(!ObjectUtil.isEmpty(hlSchoolyears)){
            HlSchoolyear hlSchoolyear = hlSchoolyears.get(0);
            hlCurriculum.setYearid(hlSchoolyear.getId());
        }


        HlCurriculumsetKey hlCurriculumsetKey = new HlCurriculumsetKey();
        hlCurriculumsetKey.setId(lessonId);
        hlCurriculumsetKey.setSchoolid(schoolId);
        HlCurriculumset hlCurriculumset = hlCurriculumsetMapper.selectByPrimaryKey(hlCurriculumsetKey);
        String message = "";
        try{
            if(ObjectUtil.isEmpty(hlCurriculumset))return MyResult.failure("未知的节次");

            hlCurriculum.setCurrstart(hlCurriculumset.getCurrstart());
            hlCurriculum.setCurrend(hlCurriculumset.getCurrend());
            if(ObjectUtil.isEmpty(id)){
                hlCurriculumMapper.insert(hlCurriculum);
                message = "添加成功";
            }else{
                HlCurriculumKey hlCurriculumKey  = new HlCurriculumKey();
                hlCurriculumKey.setId(id);
                hlCurriculumKey.setSchoolid(schoolId);
                HlCurriculum hlCurriculum1 = hlCurriculumMapper.selectByPrimaryKey(hlCurriculumKey);
                if(ObjectUtil.isEmpty(hlCurriculum1))return MyResult.failure("未找到当前课程数据，无法修改");
                hlCurriculum.setId(id);
                hlCurriculumMapper.updateByPrimaryKeySelective(hlCurriculum);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        return MyResult.success(message);
    }

    @Override
    public MyResult deleteCurriculum(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        HlCurriculumExample hlCurriculumExample = new HlCurriculumExample();
        HlCurriculumExample.Criteria criteria = hlCurriculumExample.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        int deletes =  0 ;
        try{
            deletes =  hlCurriculumMapper.deleteByExample(hlCurriculumExample);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除"+deletes+"条数据");
    }

    @Override
    public MyResult importCurriculum(HttpServletRequest request) {

        return null;
    }
}
