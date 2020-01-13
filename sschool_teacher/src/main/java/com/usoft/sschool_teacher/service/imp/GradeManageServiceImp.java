package com.usoft.sschool_teacher.service.imp;

import com.usoft.smartschool.pojo.XnScore;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.mapper.XnResourceRelationMapper;
import com.usoft.sschool_teacher.mapper.XnScoreMapper;
import com.usoft.sschool_teacher.service.IGradeManageService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 陈秋
 * @Date: 2019/5/20 17:16
 * @Version 1.0
 */
@Service
public class GradeManageServiceImp implements IGradeManageService {
    @Resource
    private XnScoreMapper scoreMapper;

    @Resource
    private XnResourceRelationMapper resourceMapper;

    /**
     * 成绩管理查询
     * @param classId 班级ID
     * @param teacherId 教师ID
     * @param term  学期
     * @param testName  考试类型
     * @param subject 科目
     * @return
     */
    @Override
    public Map getGrade(int classId, int teacherId,String term,
                        String testName,String subject,int start,int page) {
        Map data = new HashMap();
        Map key1 = new HashMap();
        //默认考试类型和时间
        if (testName==null || "".equals(testName)){
            List testNameData = scoreMapper.getExamType(classId);
            if (testNameData.size()==0)return null;
            Map testNameMap= JSONObject.fromObject(testNameData.get(testNameData.size()-1));
            testName = testNameMap.get("TestName").toString();
        }
        if (term==null || "".equals(term)){
            List termDate = scoreMapper.getSemester(classId);
            if (termDate.size()==0)return null;
            Map termMap = JSONObject.fromObject(termDate.get(termDate.size()-1));
            term = termMap.get("Term").toString();
        }
        int a = 0;//>100
        int b = 0;//=100
        int c = 0;//100> >=90
        int d = 0;//90>  >=80
        int e = 0;//80>  >=60
        int f = 0;//<60
        int i = 0;
        if (subject != null && !"".equals(subject)){
            key1.put("subject",subject);
        }else{
            //默认科目
            Map<String,Object> key_subject = new HashMap<>();
            key_subject.put("teacherId", SystemParam.getUserId());
            key_subject.put("classId",classId);
            List<Map> list_subject = scoreMapper.getSubject(key_subject);
            key1.put("subject",list_subject.size()<1?null:list_subject.get(0).get("EnumItemName"));
        }
        key1.put("term",term);
        key1.put("classId",classId);
        key1.put("testName",testName);
        key1.put("start",(start-1)*page);
        key1.put("teacherId",teacherId);
        key1.put("page",page);
        List<XnScore> scores = scoreMapper.getScoreSubject(key1);
        if (scores.size()==0){
            return null;
        }
        //班级详细数据
        data.put("xn_score",scores);
        Double[] scoreNum = new Double[scores.size()];
        for (XnScore score:scores){
            scoreNum[i] = Double.parseDouble(score.getScore());
            i++;
        }
        //班级成绩区间段
        Map score_section = new HashMap();
        for (Double scoreDouble:scoreNum){
            if (scoreDouble>100){
                a++;
            }else if (scoreDouble==100){
                b++;
            }else if (scoreDouble<100 && scoreDouble>=90){
                c++;
            }else if (scoreDouble<90 && scoreDouble>=80){
                d++;
            }else if (scoreDouble<80 && scoreDouble>=60){
                e++;
            }else if (scoreDouble<60){
                f++;
            }
        }
        score_section.put("a",a);
        score_section.put("b",b);
        score_section.put("c",c);
        score_section.put("d",d);
        score_section.put("e",e);
        score_section.put("f",f);
        List sectionList = new ArrayList();
        //年级单科平均分or总分数
        Map key2 = new HashMap();
        if (subject != null && !"".equals(subject)){
            key2.put("subject",subject);
        }else {
            key2.put("subject",scores.get(0).getTsubject());
        }
        key2.put("classId",classId);
        key2.put("term",term);
        key2.put("testName",testName);
        List<Map> class_subject = scoreMapper.getClassScore(key2);
        if (class_subject.size()==0){
            return null;
        }
        for (Map map:class_subject){
            if (classId == Integer.parseInt(map.get("ID").toString())){
                score_section.put("resNum",map.get("resnum"));
                score_section.put("number",map.get("number"));
                sectionList.add(map.get("resnum"));
                sectionList.add(map.get("number"));
                sectionList.add(a);
                sectionList.add(b);
                sectionList.add(c);
                sectionList.add(d);
                sectionList.add(e);
                sectionList.add(f);
            }
        }
        data.put("score_section",sectionList);
        data.put("class_subject",class_subject);
        //年级各科平均分数
        List<Map> class_score = scoreMapper.getClassScore(key1);
        if (class_score.size()==0){
            return null;
        }
        Set<String> class_shu = new HashSet();
        Map<String,List<Map>> TSubject = class_score.stream().collect(Collectors.groupingBy(m->m.get("TSubject").toString()));
//        for (Map map:class_score){
//            class_shu.add(map.get("TSubject").toString());
//        }
        if(!ObjectUtil.isEmpty(TSubject)){
            class_shu = TSubject.keySet();
        }
        //按班级分组
        Set<String> class_name = new HashSet();
//        for (Map map:class_score){
//            class_name.add(map.get("ClassName").toString());
//        }
        //按科目分班级
        Map mapData = new HashMap();
        List<Map> subject_data_list = new ArrayList<>();
        for(String classShu:class_shu){
            List<Map> tSubject = TSubject.get(classShu);
            Map<String,List<Map>> classData = tSubject.stream().collect(Collectors.groupingBy(m->m.get("TSubject").toString()));
            class_name = classData.keySet();
            //根据班级获取成绩数据
            List<String> class_score_list = new ArrayList<>();
            for (String className:class_name){
                List<Map> classScoreTsubject = classData.get(className);
                class_score_list.add(classScoreTsubject.get(0).get("avges").toString());
            }
            Map subject_data = new HashMap();
            subject_data.put("subject",classShu);
            subject_data.put("data",class_score_list);
            subject_data_list.add(subject_data);
        }
//        Map mapData = new HashMap();
//        List<Map> subject_data_list = new ArrayList<>();
//        for (String string:class_shu){
//            List<String> class_score_list = new ArrayList<>();
//            for (Map map:class_score){
//                if (string.equals(map.get("TSubject").toString())){
//                    class_score_list.add(map.get("avges").toString());
//                }
//            }
//            Map subject_data = new HashMap();
//            subject_data.put("subject",string);
//            subject_data.put("data",class_score_list);
//            subject_data_list.add(subject_data);
//        }
        mapData.put("subject_data",subject_data_list);
        mapData.put("class_name",class_name);
        data.put("class_score",mapData);
        return data;
    }

    /**
     * 获取到学期
     * @param classId 班级ID
     * @return
     */
    @Override
    public List getSemester(int classId) {
        return scoreMapper.getSemester(classId);
    }

    /**
     * 获取到考试类型
     * @param classId 班级ID
     * @return
     */
    @Override
    public List getExamType(int classId) {
        return scoreMapper.getExamType(classId);
    }

    /**
     * 成绩管理查询
     * @param classId 班级ID
     * @param teacherId 教师ID
     * @param term  学期
     * @param testName  考试类型
     * @param subject 科目
     * @return
     */
    @Override
    public Integer getCont(int classId, int teacherId,String term,
                           String testName,String subject,int start,int page){
        Map key = new HashMap();
        if (subject != null && !"".equals(subject)){
            key.put("subject",subject);
        }else{
            key.put("subject",null);
        }
        key.put("classId",classId);
        key.put("term",term);
        key.put("testName",testName);
        key.put("teacherId",teacherId);
        key.put("start",(start-1)*page);
        key.put("page",page);
        int i= scoreMapper.getScoreSubjectCount(key);
        return i;
    }
}
