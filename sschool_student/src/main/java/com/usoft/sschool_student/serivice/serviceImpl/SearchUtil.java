package com.usoft.sschool_student.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_student.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchUtil {
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private CfUserMapper cfUserMapper;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;
    @Resource
    private HlSchclassMapper hlSchclassMapper;
    @Resource
    private HlEnumitemMapper hlEnumitemMapper;
    @Resource
    private XnTeacherIntegralMapper xnTeacherIntegralMapper;
    @Resource
    private HlCurriculumMapper hlCurriculumMapper;
    /**
     * 查询孩子信息
     * @param childId
     * @return
     */
    public HlStudentinfo Studentinfo(Integer schoolId,Integer childId){
        HlStudentinfoExample hl=new HlStudentinfoExample();
        hl.createCriteria().andSchoolidEqualTo(schoolId).andIdEqualTo(childId);
        return  hlStudentinfoMapper.selectByExample(hl).get(0);
    }

    /**
     * 查询教师信息
     * @param schoolId
     * @param userId
     * @return
     */
    public List<Map> teacherClass(Integer schoolId,Integer userId){
        List<Map> list=new ArrayList<>();
        Map map=new HashMap();
        //教师信息
        HlTeacherExample ht=new HlTeacherExample();
        ht.createCriteria().andSchoolidEqualTo(schoolId).andIdEqualTo(userId);
        List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(ht);
        if (ObjectUtil.isEmpty(hlTeachers))return null;
        //教师的班级
        List<Map<String, Object>> maps = hlCurriculumMapper.teaClassId(schoolId, hlTeachers.get(0).getId());
        if (ObjectUtil.isEmpty(maps)){
            map.put("className",null);
            map.put("classId",null);
        }else {
            for (Map m:maps){
                map.put("classId",m.get("ClassId"));
                HlSchclass hs=new HlSchclass();
                hs.setId(Integer.valueOf(m.get("ClassId").toString()));
                hs.setSchoolid(schoolId);
                HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
                if (ObjectUtil.isEmpty(hlSchclass)){
                    map.put("className",null);
                }else {
                    map.put("className",hlSchclass.getClassname());
                }
            }
        }
        map.put("id",hlTeachers.get(0).getId());
        map.put("stuName",hlTeachers.get(0).getTname());
        String str=hlTeachers.get(0).getImagesrc();
        JSON json= JSONObject.parseObject(str);
        Map<String,Object> map1=JSONObject.toJavaObject(json,Map.class);
        if (map1.get("faceImg")==null){
            map.put("ImageSrc",null);
        }else {
            map.put("ImageSrc",map1.get("faceImg"));
        }
        if (map1.get("idImg")==null){
            map.put("idImg",null);
        }else {
            String[] s=map1.get("idImg").toString().split(",");
            List list1=new ArrayList();
            for (int i=0;i<s.length;i++){
                list1.add(s[i]);
                System.out.println("拆分后的是："+s[i]);
            }
            map.put("idImg",list1);
        }
        map.put("ImageSrc",hlTeachers.get(0).getImagesrc());
        Integer integer = xnTeacherIntegralMapper.totleScore(schoolId, userId);
        if (integer==null){
            integer=0;
        }
        map.put("totleScore",integer);
        //老师教的科目
        map.put("subject",hlTeachers.get(0).getTsubject());
        list.add(map);
        return list;
    }
}
