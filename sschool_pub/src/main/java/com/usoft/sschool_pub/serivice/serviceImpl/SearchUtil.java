package com.usoft.sschool_pub.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchUtil {
    @Resource
    private  HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private CfDepartmentMapper cfDepartmentMapper;
    @Resource
    private XnGoodsTypeMapper xnGoodsTypeMapper;
    @Resource
    private HlSchclassMapper hlSchclassMapper;
    @Resource
    private HlEnumitemMapper hlEnumitemMapper;
    @Resource
    private XnTeacherIntegralMapper xnTeacherIntegralMapper;
    @Resource
    private HlCurriculumMapper hlCurriculumMapper;
    @Resource
    private HlSchoolsevipMapper hlSchoolsevipMapper;
    @Resource
    private HlCountyMapper hlCountyMapper;
    /**
     * 查询孩子信息
     * @param childId
     * @return
     */
    public HlStudentinfo Studentinfo(Integer schoolId,Integer childId){
        HlStudentinfo hl=new HlStudentinfo();
        hl.setSchoolid(schoolId);hl.setId(childId);
        return  hlStudentinfoMapper.selectByPrimaryKey(hl);
    }

    /**
     * 查询教师信息
     * @param schoolId
     * @param userId
     * @return
     */
    public List<Map> teacherClass(Integer schoolId,Integer userId){
        //Map map=new HashMap();
        //教师信息
        HlTeacher ht=new HlTeacher();
        ht.setId(userId);
        ht.setSchoolid(schoolId);
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
        if (ObjectUtil.isEmpty(hlTeacher))return null;
        //教师的班级
        List<Map<String, Object>> maps = hlCurriculumMapper.teaClassId(schoolId, userId);
        List<Map> list2=new ArrayList<>();
        if (ObjectUtil.isEmpty(maps)){
            Map map1=new HashMap();
            map1.put("classId",null);
            map1.put("className",null);
            map1.put("id",userId);
            map1.put("stuName",hlTeacher.getTname());
            String str=hlTeacher.getImagesrc();
            if (str==null){
                map1.put("ImageSrc",null);
                map1.put("idImg",null);
            }else if(!str.contains("{")){

            }else {
                JSON json= JSONObject.parseObject(str);
                Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
                if (map2.get("idImg")==null){
                    map1.put("ImageSrc",null);
                }else {
                    map1.put("ImageSrc",map2.get("idImg"));
                }
                if (map2.get("faceImg")==null){
                    map1.put("idImg",null);
                }else {
                    String[] s=map2.get("faceImg").toString().split(",");
                    map1.put("idImg",s);
                }
            }

            Integer integer = xnTeacherIntegralMapper.totleScore(schoolId, userId);
            if (integer==null){
                integer=0;
            }
            map1.put("totleScore",integer);
            //老师教的科目
            map1.put("subject",hlTeacher.getTsubject());
            list2.add(map1);
        }else {
            for (Map m:maps){
                Map map1=new HashMap();
                map1.put("classId",m.get("ClassId"));
                HlSchclass hs=new HlSchclass();
                hs.setId(Integer.valueOf(m.get("ClassId").toString()));
                hs.setSchoolid(schoolId);
                HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
                if (ObjectUtil.isEmpty(hlSchclass)){
                    map1.put("className",null);
                }else {
                    map1.put("className",hlSchclass.getClassname());
                }
                map1.put("id",userId);
                map1.put("stuName",hlTeacher.getTname());
                String str=hlTeacher.getImagesrc();
                if (str==null){
                    map1.put("ImageSrc",null);
                    map1.put("idImg",null);
                }else {
                    JSON json= JSONObject.parseObject(str);
                    Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
                    if (map2.get("idImg")==null){
                        map1.put("ImageSrc",null);
                    }else {
                        map1.put("ImageSrc",map2.get("idImg"));
                    }
                    if (map2.get("faceImg")==null){
                        map1.put("idImg",null);
                    }else {
                        String[] s=map2.get("faceImg").toString().split(",");
                        map1.put("idImg",s);
                    }
                }
                Integer integer = xnTeacherIntegralMapper.totleScore(schoolId, userId);
                if (integer==null){
                    integer=0;
                }
                map1.put("totleScore",integer);
                //老师教的科目
                map1.put("subject",hlTeacher.getTsubject());
                list2.add(map1);
            }
        }
        return list2;
    }

    /**
     * 根据userId查询学生信息
     * @param schoolId
     * @param userId
     * @return
     */
    public List<HlStudentinfo> stuInfoByUserId(Integer schoolId,Integer userId){
        List<HlStudentinfo> list=new ArrayList<>();
        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(userId);
        XnStuFamilyinfoExample example=new XnStuFamilyinfoExample();
        example.createCriteria().andPhoneEqualTo(xnStuFamilyinfo.getPhone());
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
        for (XnStuFamilyinfo xsf:xnStuFamilyinfos){
            HlStudentinfo studentinfo = Studentinfo(schoolId, xsf.getStuid());
            list.add(studentinfo);
        }
        return list;
    }

    /**
     * 查询学校的视频播放地址
     * @param schoolId
     * @return
     */
    public Map<String,Object> videoPath(Integer schoolId,TerminalInfo ti){
        //String servPath1 = "rtmp://" + hlSchoolsevips.getLansevip() + ":1935/live/" + ti.getTerminalMac() + "_hd_1";
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);
        Map map=new HashMap();
        map.put("id",ti.getId());
        map.put("servPath1",ti.getTerminalMac());
        map.put("schoolId",schoolId);
        map.put("DeptCode",cfDepartment.getDeptcode());
        map.put("schoolName",cfDepartment.getDeptname());
        map.put("CountyID",cfDepartment.getCountyid());
        HlCounty hlCounty = hlCountyMapper.selectByPrimaryKey(cfDepartment.getCountyid());
        map.put("CountyName",hlCounty.getName());
        map.put("classId",ti.getTerminalClassroomId());
        HlSchclass hs=new HlSchclass();
        hs.setId(ti.getTerminalClassroomId());hs.setSchoolid(schoolId);
        HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
        if (ObjectUtil.isEmpty(hlSchclass)){
            map.put("className",null);
        }else {
            map.put("className",hlSchclass.getClassname());
        }
        return map;
    }

    /**
     * 封装商品信息
     * @param xgm
     * @return
     */
    public Map<String,Object> goodInfo(XnGoodsManage xgm){
        Map<String,Object> map=new HashMap<>();
        map.put("id",xgm.getId());
        map.put("goodsName",xgm.getGoodsname());
        map.put("typeId",xgm.getTypeid());
        map.put("status",xgm.getStatus());

        map.put("number",xgm.getNumber());
        map.put("thumbnail",xgm.getThumbnail());
        if (xgm.getDesimg()!=null){
            String[] str=xgm.getDesimg().split(",");
            map.put("desImg",str);
        }else {
            map.put("desImg",xgm.getDesimg());
        }
        map.put("isDeliver",xgm.getIsdeliver());
        map.put("description",xgm.getDescription());
        map.put("createTime",xgm.getCreatetime());
        map.put("UID",xgm.getUid());
        XnGoodsType xnGoodsType = xnGoodsTypeMapper.selectByPrimaryKey(xgm.getTypeid());
        map.put("mailType",xnGoodsType.getType());
        if (xnGoodsType.getType()==1){
            map.put("price",xgm.getPrice());
        }else {
            map.put("price",xgm.getIntegral());
        }
        return map;
    }

    /**
     * 订单统计插入的信息
     * @param userId
     * @param userType
     * @return
     */
    public Map<String,String> setOrderCount(Integer userId,Integer userType){
        Map<String,String> map=new HashMap<>();
        Integer schoolId=0;
        if (userType==1){
            HlStudentinfoExample stu=new HlStudentinfoExample();
            stu.createCriteria().andIdEqualTo(userId);
            List<HlStudentinfo> list = hlStudentinfoMapper.selectByExample(stu);
            if (ObjectUtil.isEmpty(list))return null;
            schoolId=list.get(0).getSchoolid();
            HlSchclass hs=new HlSchclass();
            hs.setSchoolid(schoolId);
            hs.setId(list.get(0).getClassid());
            HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
            if (ObjectUtil.isEmpty(hlSchclass)){
                map.put("classId",list.get(0).getClassid().toString());
            }else {
                map.put("classId",hlSchclass.getClassname());
            }
            HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(list.get(0).getGradeid());
            if (ObjectUtil.isEmpty(hlEnumitem)){
                map.put("gradeId",list.get(0).getGradeid().toString());
            }else {
                map.put("gradeId",hlEnumitem.getEnumitemname());
            }
            map.put("phone",list.get(0).getPhone());
            map.put("userId",list.get(0).getSname());

        }else {
            HlTeacherExample example=new HlTeacherExample();
            example.createCriteria().andIdEqualTo(userId);
            List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(hlTeachers))return null;
            schoolId=hlTeachers.get(0).getSchoolid();
            map.put("classId","0");
            map.put("gradeId","0");
            map.put("phone",hlTeachers.get(0).getMobile());
            map.put("userId",hlTeachers.get(0).getTname());
        }
        map.put("userType",userType.toString());
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);
        map.put("schoolId",cfDepartment.getDeptname());
        HlCounty hlCounty = hlCountyMapper.selectByPrimaryKey(cfDepartment.getCountyid());
        map.put("townsId",hlCounty.getName());
        if (hlCounty.getParentid()==null){
            map.put("countyId",null);
        }else {
            HlCounty hlCounty1 = hlCountyMapper.selectByPrimaryKey(hlCounty.getParentid());
            if (!ObjectUtil.isEmpty(hlCounty1)){
                map.put("countyId",hlCounty1.getName());
            }
        }
        return map;
    }
}
