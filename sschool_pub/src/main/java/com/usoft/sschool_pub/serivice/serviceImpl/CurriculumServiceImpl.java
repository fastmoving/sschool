package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.HlCurriculumMapper;
import com.usoft.sschool_pub.mapper.HlEnumitemMapper;
import com.usoft.sschool_pub.mapper.HlStudentinfoMapper;
import com.usoft.sschool_pub.mapper.HlTeacherMapper;
import com.usoft.sschool_pub.serivice.CurriculumService;
import com.usoft.sschool_pub.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wlw
 * @data 2019/4/28 0028-16:28
 */
@Service("CurriculumService")
public class CurriculumServiceImpl implements CurriculumService {
    @Resource
    private HlCurriculumMapper hlCurriculumMapper;
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private HlEnumitemMapper hlEnumitemMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    /**
     * 学生查询课表
     * @param schoolId
     * @param studentId
     * @return
     */
    @Override
    public MyResult stuCurri(Integer schoolId,Integer studentId,Integer week) {
        HlStudentinfoKey key=new HlStudentinfoKey();
        key.setSchoolid(schoolId);
        key.setId(studentId);
        HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(key);
        if (hlStudentinfo.getClassid()!=null){
            HlCurriculumExample example=new HlCurriculumExample();
            HlCurriculumExample.Criteria criteria = example.createCriteria();
            if (week!=null){
                criteria.andWeekEqualTo(week);
            }
            criteria.andSchoolidEqualTo(schoolId);
            criteria.andClassidEqualTo(hlStudentinfo.getClassid());

            //年级？？
            example.setOrderByClause("LessonId");
            List<HlCurriculum> hlCurricula = hlCurriculumMapper.selectByExample(example);
            if (hlCurricula.size()==0){
                return MyResult.failure("没有数据");
            }
            List<Map> list=new ArrayList<>();
            for (HlCurriculum hc:hlCurricula){
                Map map=new HashMap();
                map.put("ClassId",hc.getClassid());
                map.put("SchoolId",hc.getSchoolid());
                map.put("Cycle",hc.getCycle());
                map.put("CurrStart",hc.getCurrstart());
                map.put("CurrEnd",hc.getCurrend());
                HlEnumitemExample example1=new HlEnumitemExample();
                example1.createCriteria().andIdEqualTo(hc.getDiscipline());
                List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(example1);
                map.put("Discipline",hlEnumitems.get(0).getEnumitemname());
                HlTeacherExample example2=new HlTeacherExample();
                example2.createCriteria().andIdEqualTo(hc.getClassteacher());
                List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(example2);
                map.put("ClassTeacher",hlTeachers.get(0).getTname());
                map.put("RoomId",hc.getRoomid());
                map.put("YearId",hc.getYearid());
                map.put("LessonId",hc.getLessonid());
                map.put("week",hc.getWeek());
                list.add(map);
            }
            return MyResult.success(list);
        }
        return MyResult.failure("孩子id丢失");
    }

    /**
     * web端学生查询课表
     * @return
     */
    @Override
    public MyResult webStudentCurri() {
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        HlStudentinfoKey key=new HlStudentinfoKey();
        key.setSchoolid(schoolId);
        key.setId(childId);
        HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(key);
        if (hlStudentinfo.getClassid()==null){
            return MyResult.failure("没找到孩子所属班级");
        }
       Map map1=new HashMap();

        for (int i=0 ;i<7;i++){
            HlCurriculumExample example=new HlCurriculumExample();
            HlCurriculumExample.Criteria criteria = example.createCriteria();
            criteria.andSchoolidEqualTo(schoolId);
            criteria.andClassidEqualTo(hlStudentinfo.getClassid());
            criteria.andWeekEqualTo(i);
            example.setOrderByClause("LessonId");
            List<HlCurriculum> li = hlCurriculumMapper.selectByExample(example);
            List<Map> list=new ArrayList<>();
            for (HlCurriculum hc:li){
                Map map=new HashMap();
                map.put("ClassId",hc.getClassid());
                map.put("SchoolId",hc.getSchoolid());
                map.put("Cycle",hc.getCycle());
                map.put("CurrStart",hc.getCurrstart());
                map.put("CurrEnd",hc.getCurrend());
                HlEnumitemExample example1=new HlEnumitemExample();
                example1.createCriteria().andIdEqualTo(hc.getDiscipline());
                List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(example1);
                map.put("Discipline",hlEnumitems.get(0).getEnumitemname());
                HlTeacherExample example2=new HlTeacherExample();
                example2.createCriteria().andIdEqualTo(hc.getClassteacher());
                List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(example2);
                map.put("ClassTeacher",hlTeachers.get(0).getTname());
                map.put("RoomId",hc.getRoomid());
                map.put("YearId",hc.getYearid());
                map.put("LessonId",hc.getLessonid());
                map.put("week",hc.getWeek());
                list.add(map);
            }
            getLesson(list);
            map1.put(i,list);
        }
        return MyResult.success(map1);
    }

    private void getLesson(List<Map> week){
        Map lesson_1 = new HashMap();
        Map lesson_2 = new HashMap();
        Map lesson_3 = new HashMap();
        Map lesson_4 = new HashMap();
        Map lesson_5 = new HashMap();
        Map lesson_6 = new HashMap();
        Map lesson_7 = new HashMap();
        Map lesson_8 = new HashMap();
        if (week.isEmpty())return ;
        for (Map map:week){
            if (Integer.parseInt(map.get("LessonId").toString())==1) lesson_1 = map;
            else if (Integer.parseInt(map.get("LessonId").toString())==2)lesson_2 = map;
            else if (Integer.parseInt(map.get("LessonId").toString())==3)lesson_3 = map;
            else if (Integer.parseInt(map.get("LessonId").toString())==4)lesson_4 = map;
            else if (Integer.parseInt(map.get("LessonId").toString())==5)lesson_5 = map;
            else if (Integer.parseInt(map.get("LessonId").toString())==6)lesson_6 = map;
            else if (Integer.parseInt(map.get("LessonId").toString())==7)lesson_7 = map;
            else if (Integer.parseInt(map.get("LessonId").toString())==8)lesson_8 = map;
        }
        week.clear();
        week.add(lesson_1);week.add(lesson_2);week.add(lesson_3);week.add(lesson_4);week.add(lesson_5);week.add(lesson_6);
        week.add(lesson_7);week.add(lesson_8);
    }

    /**
     * 教师查看课表
     * @param schoolId
     * @return
     */
    @Override
    public MyResult teacherCurr(Integer schoolId) {
        Integer userId = SystemParam.getUserId();
        HlCurriculumExample example=new HlCurriculumExample();
        example.createCriteria().andSchoolidEqualTo(schoolId).andClassteacherEqualTo(userId);
        example.setOrderByClause("LessonId");
        List<HlCurriculum> hlCurricula = hlCurriculumMapper.selectByExample(example);
        if (hlCurricula.size()==0){
            return MyResult.failure("数据库没有数据");
        }
        List<Map> list=new ArrayList<>();
        for (HlCurriculum hc:hlCurricula){
            Map map=new HashMap();
            map.put("ClassId",hc.getClassid());
            map.put("SchoolId",hc.getSchoolid());
            map.put("Cycle",hc.getCycle());
            map.put("CurrStart",hc.getCurrstart());
            map.put("CurrEnd",hc.getCurrend());
            HlEnumitemExample example1=new HlEnumitemExample();
            example1.createCriteria().andIdEqualTo(hc.getDiscipline());
            List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(example1);
            map.put("Discipline",hlEnumitems.get(0).getEnumitemname());
            HlTeacherExample example2=new HlTeacherExample();
            example2.createCriteria().andIdEqualTo(hc.getClassteacher());
            List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(example2);
            map.put("ClassTeacher",hlTeachers.get(0).getTname());
            map.put("RoomId",hc.getRoomid());
            map.put("YearId",hc.getYearid());
            map.put("LessonId",hc.getLessonid());
            map.put("week",hc.getWeek());
            list.add(map);
        }
        return MyResult.success("找到了老师的课程安排",list);
    }
}
