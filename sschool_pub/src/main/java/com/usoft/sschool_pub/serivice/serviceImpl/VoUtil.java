package com.usoft.sschool_pub.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.util.TimeUtil;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//封装信息
@Service
public class VoUtil {
    @Resource
    private HlEnumitemMapper hlEnumitemMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private HlSchclassMapper hlSchclassMapper;
    @Resource
    private XnClassCircleMapper xnClassCircleMapper;
    @Resource
    private XnCircleLikeMapper xnCircleLikeMapper;
    @Resource
    private HlCountyMapper hlCountyMapper;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private XnCommentTableMapper xnCommentTableMapper;
    @Resource
    private XnKindnessSchoolMapper xnKindnessSchoolMapper;
    @Resource
    private XnAddressMapper xnAddressMapper;
    @Resource
    private CfDepartmentMapper cfDepartmentMapper;
    @Resource
    private XnGoodsManageMapper xnGoodsManageMapper;
    //封装返回的学生信息(太慢，建议不要用)
    public Map<Object,Object> stuInfo(HlStudentinfo hlStudentinfo){
        Map<Object,Object> map=new HashMap();
        map.put("id",hlStudentinfo.getId());
        map.put("SCode",hlStudentinfo.getScode());
        map.put("SName",hlStudentinfo.getSname());
        if (hlStudentinfo.getSexid()==null){
            map.put("SexId",null);
        }else {
            HlEnumitemExample example=new HlEnumitemExample();
            HlEnumitemExample.Criteria criteria = example.createCriteria();
            criteria.andEnumidEqualTo(17).andIdEqualTo(hlStudentinfo.getSexid());
            List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(hlEnumitems)){
                map.put("SexId",hlStudentinfo.getSexid());
            }else {
                map.put("SexId",hlEnumitems.get(0).getEnumitemname());
            }
        }
        map.put("Birthday",hlStudentinfo.getBirthday());
        map.put("BirthPlace",hlStudentinfo.getBirthplace());
        if (hlStudentinfo.getSphoto()==null){
            map.put("ImageSrc",null);
            map.put("idImg",null);
        }else {
            String str=hlStudentinfo.getSphoto();
            JSON json= JSONObject.parseObject(str);
            Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
            if (map2.get("idImg")==null || "null".equals(map2.get("idImg")) || "".equals(map2.get("idImg"))){
                map.put("idImg",null);
                map.put("ImageSrc",map2.get("faceImg"));
            }else {
                String[] s=map2.get("faceImg").toString().split(",");
                map.put("ImageSrc",map2.get("faceImg"));
                map.put("idImg",s);
            }
        }
        map.put("NativePlace", hlStudentinfo.getNativeplace());
        map.put("NativePlaceID", hlStudentinfo.getNativeplaceid());
        map.put("SNationID", hlStudentinfo.getSnationid());
        if (hlStudentinfo.getNationalityid()==null){
            map.put("NationalityID",null);
        }else {
            HlEnumitemExample example1=new HlEnumitemExample();
            example1.createCriteria().andEnumidEqualTo(18).andIdEqualTo(hlStudentinfo.getNationalityid());
            List<HlEnumitem> hlEnumitems1 = hlEnumitemMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(hlEnumitems1)){
                map.put("NationalityID",hlStudentinfo.getNationalityid());
            }else {
                map.put("NationalityID", hlEnumitems1.get(0).getEnumitemname());
            }
        }
        map.put("IDNumber", hlStudentinfo.getIdnumber());
        if (hlStudentinfo.getPoliticalid()==null){
            map.put("PoliticalID",null);
        }else {
            HlEnumitemExample example2=new HlEnumitemExample();
            example2.createCriteria().andEnumidEqualTo(19).andIdEqualTo(hlStudentinfo.getPoliticalid());
            List<HlEnumitem> hlEnumitems2 = hlEnumitemMapper.selectByExample(example2);
            if (ObjectUtil.isEmpty(hlEnumitems2)){
                map.put("PoliticalID",hlStudentinfo.getPoliticalid());
            }else {
                map.put("PoliticalID",hlEnumitems2.get(0).getEnumitemname());
            }
        }
        if (hlStudentinfo.getHealthid()==null){
            map.put("HealthID",null);
        }else {
            HlEnumitemExample example3=new HlEnumitemExample();
            example3.createCriteria().andEnumidEqualTo(3).andIdEqualTo(hlStudentinfo.getHealthid());
            List<HlEnumitem> hlEnumitems3 = hlEnumitemMapper.selectByExample(example3);
            if (ObjectUtil.isEmpty(hlEnumitems3)){
                map.put("HealthID",hlStudentinfo.getHealthid());
            }else {
                map.put("HealthID",hlEnumitems3.get(0).getEnumitemname());
            }
        }

        map.put("LeagueDate", hlStudentinfo.getLeaguedate());
        map.put("HouseHoldAddress", hlStudentinfo.getHouseholdaddress());
        if (hlStudentinfo.getHouseholdtypeid()==null){
            map.put("HouseHoldTypeID",null);
        }else {
            HlEnumitemExample example4=new HlEnumitemExample();
            example4.createCriteria().andEnumidEqualTo(21).andIdEqualTo(hlStudentinfo.getHouseholdtypeid());
            List<HlEnumitem> hlEnumitems4 = hlEnumitemMapper.selectByExample(example4);
            if (ObjectUtil.isEmpty(hlEnumitems4)){
                map.put("HouseHoldTypeID",hlStudentinfo.getHouseholdtypeid());
            }else {
                map.put("HouseHoldTypeID",hlEnumitems4.get(0).getEnumitemname());
            }
        }
        if (hlStudentinfo.getGradeid()==null){
            map.put("GradeID",null);
        }else {
            HlEnumitemExample example5=new HlEnumitemExample();
            example5.createCriteria().andEnumidEqualTo(12).andIdEqualTo(hlStudentinfo.getGradeid());
            List<HlEnumitem> hlEnumitems5 = hlEnumitemMapper.selectByExample(example5);
            if (ObjectUtil.isEmpty(hlEnumitems5)){
                map.put("GradeID",hlStudentinfo.getGradeid());
            }else {
                map.put("GradeID",hlEnumitems5.get(0).getEnumitemname());
            }
        }

        map.put("EnterTime",hlStudentinfo.getEntertime());
        map.put("GraduationTime",hlStudentinfo.getGraduationtime());
        if (hlStudentinfo.getEnterwayid()==null){
            map.put("EnterWayID",null);
        }else {
            HlEnumitemExample example6=new HlEnumitemExample();
            example6.createCriteria().andEnumidEqualTo(22).andIdEqualTo(hlStudentinfo.getEnterwayid());
            List<HlEnumitem> hlEnumitems6 = hlEnumitemMapper.selectByExample(example6);
            if (ObjectUtil.isEmpty(hlEnumitems6)){
                map.put("EnterWayID",hlStudentinfo.getEnterwayid());
            }else {
                map.put("EnterWayID",hlEnumitems6.get(0).getEnumitemname());
            }
        }

        /*HlEnumitemExample example7=new HlEnumitemExample();
        example7.createCriteria().andEnumidEqualTo(23).andEnumitemcodeEqualTo("00"+String.valueOf(hlStudentinfo.getStudyingwayid()));
        List<HlEnumitem> hlEnumitems7 = hlEnumitemMapper.selectByExample(example7);
        if (ObjectUtil.isEmpty(hlEnumitems7)){
            map.put("StudyingWayID",hlStudentinfo.getStudyingwayid());
        }else {

        }*/
        map.put("StudyingWayID",hlStudentinfo.getStudyingwayid());
        if (hlStudentinfo.getStudentsourceid()==null){
            map.put("StudentSourceID",null);
        }else {
            HlEnumitemExample example8=new HlEnumitemExample();
            example8.createCriteria().andEnumidEqualTo(24).andIdEqualTo(hlStudentinfo.getStudentsourceid());
            List<HlEnumitem> hlEnumitems8 = hlEnumitemMapper.selectByExample(example8);
            if (ObjectUtil.isEmpty(hlEnumitems8)){
                map.put("StudentSourceID",hlStudentinfo.getStudentsourceid());
            }else {
                map.put("StudentSourceID",hlEnumitems8.get(0).getEnumitemname());
            }
        }


        map.put("PresentAddressID",hlStudentinfo.getPresentaddressid());
        map.put("PresentAddress",hlStudentinfo.getPresentaddress());
        map.put("MailAddress",hlStudentinfo.getMailaddress());
        map.put("PostalCode",hlStudentinfo.getPostalcode());
        map.put("Semail",hlStudentinfo.getSemail());
        if (hlStudentinfo.getIsonlyid()==null){
            map.put("IsOnlyID",null);
        }else {
            HlEnumitemExample example9=new HlEnumitemExample();
            example9.createCriteria().andEnumidEqualTo(25).andIdEqualTo(hlStudentinfo.getIsonlyid());
            List<HlEnumitem> hlEnumitems9 = hlEnumitemMapper.selectByExample(example9);
            if (ObjectUtil.isEmpty(hlEnumitems9)){
                map.put("IsOnlyID",hlStudentinfo.getIsonlyid());
            }else {
                map.put("IsOnlyID",hlEnumitems9.get(0).getEnumitemname());
            }
        }
        if (hlStudentinfo.getIspreschoolid()==null){
            map.put("IsPreSchoolID",null);
        }else {
            HlEnumitemExample example10=new HlEnumitemExample();
            example10.createCriteria().andEnumidEqualTo(26).andIdEqualTo(hlStudentinfo.getIspreschoolid());
            List<HlEnumitem> hlEnumitems10 = hlEnumitemMapper.selectByExample(example10);
            if (ObjectUtil.isEmpty(hlEnumitems10)){
                map.put("IsPreSchoolID",hlStudentinfo.getIspreschoolid());
            }else {
                map.put("IsPreSchoolID",hlEnumitems10.get(0).getEnumitemname());
            }
        }
        if (hlStudentinfo.getStudentstatusid()==null){
            map.put("StudentStatusID",null);
        }else {
            HlEnumitemExample example11=new HlEnumitemExample();
            example11.createCriteria().andEnumidEqualTo(28).andIdEqualTo(hlStudentinfo.getStudentstatusid());
            List<HlEnumitem> hlEnumitems11 = hlEnumitemMapper.selectByExample(example11);
            if (ObjectUtil.isEmpty(hlEnumitems11)){
                map.put("StudentStatusID",hlStudentinfo.getStudentstatusid());
            }else {
                map.put("StudentStatusID",hlEnumitems11.get(0).getEnumitemname());
            }
        }


        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(hlStudentinfo.getSchoolid());
        if (ObjectUtil.isEmpty(cfDepartment)){
            map.put("SchoolID","没有找到该学校的信息");
        }else {
            map.put("SchoolID",cfDepartment.getDeptname());
        }
        if (hlStudentinfo.getClassid()==null){
            map.put("ClassID",null);
        }else {
            HlSchclassExample example12=new HlSchclassExample();
            example12.createCriteria().andIdEqualTo(hlStudentinfo.getClassid());
            List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(example12);
            if (ObjectUtil.isEmpty(hlSchclasses)){
                map.put("ClassID",hlStudentinfo.getClassid());
            }else {
                map.put("ClassID",hlSchclasses.get(0).getClassname());
            }
        }


        map.put("IsLived",hlStudentinfo.getIslived());
        if (hlStudentinfo.getAuditstatusid()==null){
            map.put("AuditStatusID",null);
        }else {
            HlEnumitemExample example13=new HlEnumitemExample();
            example13.createCriteria().andEnumidEqualTo(40).andIdEqualTo(hlStudentinfo.getAuditstatusid());
            List<HlEnumitem> hlEnumitems13 = hlEnumitemMapper.selectByExample(example13);
            if (ObjectUtil.isEmpty(hlEnumitems13)){
                map.put("AuditStatusID",hlStudentinfo.getAuditstatusid());
            }else {
                map.put("AuditStatusID",hlEnumitems13.get(0).getEnumitemname());
            }
        }
        if (hlStudentinfo.getGxtime()==null){
            map.put("GXtime", null);
        }else {
            map.put("GXtime", TimeUtil.TimeToYYYYMMDDHHMMSS(hlStudentinfo.getGxtime()));
        }
        map.put("phone",hlStudentinfo.getPhone());
        return map;
    }
    //封装返回的教师信息(太慢，建议不要用)
    public Map<Object,Object> teaInfo(Integer schoolId,Integer userId){
        Map<Object,Object> map=new HashMap();
        HlTeacher ht=new HlTeacher();
        ht.setSchoolid(schoolId);ht.setId(userId);
        HlTeacher hlTeacher=hlTeacherMapper.selectByPrimaryKey(ht);
        map.put("id",hlTeacher.getId());
        map.put("Tname",hlTeacher.getTname());
        map.put("TsName",hlTeacher.getTsname());
        map.put("TCode",hlTeacher.getTcode());
        if (hlTeacher.getSex()==null){
            map.put("Sex",hlTeacher.getSex());
        }else {
            HlEnumitemExample example=new HlEnumitemExample();
            example.createCriteria().andEnumidEqualTo(17).andIdEqualTo(hlTeacher.getSex());
            List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(hlEnumitems)){
                map.put("Sex",hlTeacher.getSex());
            }else {
                map.put("Sex",hlEnumitems.get(0).getEnumitemname());
            }
        }

        map.put("IdCard",hlTeacher.getIdcard());
        if (hlTeacher.getNation()==null){
            map.put("Nation",hlTeacher.getSex());
        }else {
            HlEnumitemExample example1=new HlEnumitemExample();
            example1.createCriteria().andEnumidEqualTo(2).andIdEqualTo(hlTeacher.getNation());
            List<HlEnumitem> hlEnumitems1 = hlEnumitemMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(hlEnumitems1)){
                map.put("Nation",hlTeacher.getNation());
            }else {
                map.put("Nation",hlEnumitems1.get(0).getEnumitemname());
            }
        }

        map.put("Hometown",hlTeacher.getHometown());
        map.put("Birthday", hlTeacher.getBirthday());
        if (hlTeacher.getHealthstatusint()==null){
            map.put("HealthStatusint",hlTeacher.getSex());
        }else {
            HlEnumitemExample example2=new HlEnumitemExample();
            example2.createCriteria().andEnumidEqualTo(3).andIdEqualTo(hlTeacher.getHealthstatusint());
            List<HlEnumitem> hlEnumitems2 = hlEnumitemMapper.selectByExample(example2);
            if (ObjectUtil.isEmpty(hlEnumitems2)){
                map.put("HealthStatusint",hlTeacher.getHealthstatusint());
            }else {
                map.put("HealthStatusint",hlEnumitems2.get(0).getEnumitemname());
            }
        }
        if (hlTeacher.getTtype()==null){
            map.put("Ttype",hlTeacher.getSex());
        }else {
            HlEnumitemExample example3=new HlEnumitemExample();
            example3.createCriteria().andEnumidEqualTo(4).andIdEqualTo(hlTeacher.getTtype());
            List<HlEnumitem> hlEnumitems3 = hlEnumitemMapper.selectByExample(example3);
            if (ObjectUtil.isEmpty(hlEnumitems3)){
                map.put("Ttype",hlTeacher.getTtype());
            }else {
                map.put("Ttype",hlEnumitems3.get(0).getEnumitemname());
            }
        }
        if (hlTeacher.getEmpforms()==null){
            map.put("EmpForms",hlTeacher.getSex());
        }else {
            HlEnumitemExample example4=new HlEnumitemExample();
            example4.createCriteria().andEnumidEqualTo(5).andIdEqualTo(hlTeacher.getEmpforms());
            List<HlEnumitem> hlEnumitems4 = hlEnumitemMapper.selectByExample(example4);
            if (ObjectUtil.isEmpty(hlEnumitems4)){
                map.put("EmpForms",hlTeacher.getEmpforms());
            }else {
                map.put("EmpForms",hlEnumitems4.get(0).getEnumitemname());
            }
        }
        if (hlTeacher.getPoliticalaff()==null){
            map.put("PoliticalAff",hlTeacher.getSex());
        }else {
            HlEnumitemExample example5=new HlEnumitemExample();
            example5.createCriteria().andEnumidEqualTo(6).andIdEqualTo(hlTeacher.getPoliticalaff());
            List<HlEnumitem> hlEnumitems5 = hlEnumitemMapper.selectByExample(example5);
            if (ObjectUtil.isEmpty(hlEnumitems5)){
                map.put("PoliticalAff",hlTeacher.getPoliticalaff());
            }else {
                map.put("PoliticalAff",hlEnumitems5.get(0).getEnumitemname());
            }
        }


        map.put("PartyTime",hlTeacher.getPartytime());
        map.put("Address",hlTeacher.getAddress());
        map.put("HomePhone",hlTeacher.getHomephone());
        map.put("Mobile",hlTeacher.getMobile());
        map.put("Email",hlTeacher.getEmail());
        map.put("MaxEducation",hlTeacher.getMaxeducation());
        map.put("MaxSpecialty",hlTeacher.getMaxspecialty());
        map.put("MaxDegree",hlTeacher.getMaxdegree());
        if (hlTeacher.getMandarinle()==null){
            map.put("MandarinLe",hlTeacher.getSex());
        }else {
            HlEnumitemExample example6=new HlEnumitemExample();
            example6.createCriteria().andEnumidEqualTo(7).andIdEqualTo(hlTeacher.getMandarinle());
            List<HlEnumitem> hlEnumitems6 = hlEnumitemMapper.selectByExample(example6);
            if (ObjectUtil.isEmpty(hlEnumitems6)){
                map.put("MandarinLe",hlTeacher.getMandarinle());
            }else {
                map.put("MandarinLe",hlEnumitems6.get(0).getEnumitemname());
            }
        }
        if (hlTeacher.getEnglishpro()==null){
            map.put("EnglishPro",hlTeacher.getSex());
        }else {
            HlEnumitemExample example7=new HlEnumitemExample();
            example7.createCriteria().andEnumidEqualTo(8).andIdEqualTo(hlTeacher.getEnglishpro());
            List<HlEnumitem> hlEnumitems7 = hlEnumitemMapper.selectByExample(example7);
            if (ObjectUtil.isEmpty(hlEnumitems7)){
                map.put("EnglishPro",hlTeacher.getEnglishpro());
            }else {
                map.put("EnglishPro",hlEnumitems7.get(0).getEnumitemname());
            }
        }

        map.put("WorkTime", TimeUtil.TimeToYYYYMMDDHHMMSS(hlTeacher.getWorktime()));

        map.put("TSubject",hlTeacher.getTsubject());
        if (hlTeacher.getTpoststatue()==null){
            map.put("TPostStatue",hlTeacher.getSex());
        }else {
            HlEnumitemExample example9=new HlEnumitemExample();
            example9.createCriteria().andEnumidEqualTo(10).andIdEqualTo(Integer.valueOf(hlTeacher.getTpoststatue()));
            List<HlEnumitem> hlEnumitems9 = hlEnumitemMapper.selectByExample(example9);
            if (ObjectUtil.isEmpty(hlEnumitems9)){
                map.put("TPostStatue",hlTeacher.getTpoststatue());
            }else {
                map.put("TPostStatue",hlEnumitems9.get(0).getEnumitemname());
            }
        }
        if (hlTeacher.getTlevel()==null){
            map.put("TLevel",hlTeacher.getSex());
        }else {
            HlEnumitemExample example10=new HlEnumitemExample();
            example10.createCriteria().andEnumidEqualTo(16).andIdEqualTo(hlTeacher.getTlevel());
            List<HlEnumitem> hlEnumitems10 = hlEnumitemMapper.selectByExample(example10);
            if (ObjectUtil.isEmpty(hlEnumitems10)){
                map.put("TLevel",hlTeacher.getTlevel());
            }else {
                map.put("TLevel",hlEnumitems10.get(0).getEnumitemname());
            }
        }
        if (hlTeacher.getImagesrc()==null){
            map.put("ImageSrc",null);
            map.put("idImg",null);
        }else {
            String str=hlTeacher.getImagesrc();
            JSON json= JSONObject.parseObject(str);
            Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
            if (map2.get("faceImg")==null || "null".equals(map2.get("faceImg")) || "".equals(map2.get("faceImg"))){
                map.put("idImg",null);
                map.put("ImageSrc",map2.get("idImg"));
            }else {
                String[] s=map2.get("faceImg").toString().split(",");
                map.put("ImageSrc",map2.get("idImg"));
                map.put("idImg",s);
            }
        }
        //map.put("ImageSrc",hlTeacher.getImagesrc());
        if (hlTeacher.getSchoolid()==null){
            map.put("SchoolID",hlTeacher.getSex());
        }else {
            HlEnumitemExample example11=new HlEnumitemExample();
            example11.createCriteria().andEnumidEqualTo(1).andIdEqualTo(hlTeacher.getSchoolid());
            List<HlEnumitem> hlEnumitems11 = hlEnumitemMapper.selectByExample(example11);
            if (ObjectUtil.isEmpty(hlEnumitems11)){
                map.put("SchoolID",hlTeacher.getSchoolid());
            }else {
                map.put("SchoolID",hlEnumitems11.get(0).getEnumitemname());
            }
        }

        map.put("CreateUser",hlTeacher.getCreateuser());
        map.put("CreateTime",TimeUtil.TimeToYYYYMMDDHHMMSS(hlTeacher.getCreatetime()));
        if (hlTeacher.getIsauditing()==null){
            map.put("IsAuditing",hlTeacher.getSex());
        }else {
            HlEnumitemExample example12=new HlEnumitemExample();
            example12.createCriteria().andEnumidEqualTo(40).andIdEqualTo(hlTeacher.getIsauditing());
            List<HlEnumitem> hlEnumitems12 = hlEnumitemMapper.selectByExample(example12);
            if (ObjectUtil.isEmpty(hlEnumitems12)){
                map.put("IsAuditing",hlTeacher.getIsauditing());
            }else {
                map.put("IsAuditing",hlEnumitems12.get(0).getEnumitemname());
            }
        }
        map.put("IsNormal", hlTeacher.getIsnormal());
        map.put("GXtime",TimeUtil.TimeToYYYYMMDDHHMMSS(hlTeacher.getGxtime()));
        return map;
    }
    //根据用户类型查询用户的头像，班级
    public Map<Object,Object>  userPhoto(Byte userType,Integer userId,Integer schoolId){
        Map map=new HashMap();
        if (userType==1){
            HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId,userId);
            map.put("id",studentinfo.getId());
            map.put("stuName",studentinfo.getSname());
            HlSchclassExample example12=new HlSchclassExample();
            example12.createCriteria().andSchoolidEqualTo(studentinfo.getSchoolid())
                    .andIdEqualTo(studentinfo.getClassid());
            List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(example12);
            if (ObjectUtil.isEmpty(hlSchclasses)){
                map.put("classId",null);
                map.put("class",null);
            }else {
                map.put("classId",studentinfo.getClassid());
                map.put("class",hlSchclasses.get(0).getClassname());

            }
            map.put("schoolId",schoolId);
            if (studentinfo.getSphoto()==null){
                map.put("ImageSrc",null);
                map.put("idImg",null);
            }else {
                String str=studentinfo.getSphoto();
                JSON json= JSONObject.parseObject(str);
                Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
                if (map2.get("idImg")==null || "null".equals(map2.get("idImg")) || "".equals(map2.get("idImg"))){
                    map.put("idImg",null);
                    map.put("ImageSrc",map2.get("faceImg"));
                }else {
                    String[] s=map2.get("idImg").toString().split(",");
                    map.put("ImageSrc",map2.get("faceImg"));
                    map.put("idImg",s);
                }
            }
            map.put("phone",studentinfo.getPhone());
        }
        if (userType==2){
            HlTeacher ht=new HlTeacher();
            ht.setId(userId);
            ht.setSchoolid(schoolId);
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            map.put("id",hlTeacher.getId());
            map.put("stuName",hlTeacher.getTname());
            map.put("class",hlTeacher.getTname()+"老师");
            if (hlTeacher.getImagesrc()==null){
                map.put("ImageSrc",null);
                map.put("idImg",null);
            }else {
                String str=hlTeacher.getImagesrc();
                JSON json= JSONObject.parseObject(str);
                Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
                if (map2.get("idImg")==null || "null".equals(map2.get("idImg")) || "".equals(map2.get("idImg"))){
                    map.put("idImg",null);
                    map.put("ImageSrc",map2.get("faceImg"));
                }else {
                    String[] s=map2.get("idImg").toString().split(",");
                    map.put("ImageSrc",map2.get("faceImg"));
                    map.put("idImg",s);
                }
            }
            map.put("phone",hlTeacher.getMobile());
        }
        return map;
    }

    /**
     * 查询封装留言信息
     * @param schoolId
     * @param korC =1班级圈，=2爱心
     * @param typeId    班级圈id（爱心id）
     * @return
     */
    public Map<String,Object> searchComment(Integer schoolId,Integer korC,Integer typeId){
        Byte b=Byte.valueOf(korC.toString());
        //封装评论信息
        XnCommentTableExample example=new XnCommentTableExample();
        example.createCriteria().andSidEqualTo(schoolId).andTypeEqualTo(b).andStatusEqualTo((byte)1)
                .andTypeidEqualTo(typeId).andParentidEqualTo(0);
        example.setOrderByClause("id desc");
        List<XnCommentTable> xnCommentTables = xnCommentTableMapper.selectByExample(example);
        Integer CommentTableNum=0;
        if (!ObjectUtil.isEmpty(xnCommentTables)){
            CommentTableNum=xnCommentTables.size();
        }
        List list=new ArrayList();
        for (XnCommentTable xct:xnCommentTables){
            Map<Object, Object> objectMap =userPhoto(xct.getUsertype(), xct.getUserid(), xct.getSid());
            Map map1=new HashMap();
            map1.put("stuName",xct.getUsername());
            map1.put("class",objectMap.get("class"));
            map1.put("ImageSrc",objectMap.get("ImageSrc"));
            map1.put("parentId",xct.getParentid());
            map1.put("content",xct.getContent());
            map1.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xct.getCreatetime()));
            map1.put("commentId",xct.getId());
           /* if (xct.getParentid()==0){
                map1.put("childComment",null);
            }else {*/
                //二级评论
                XnCommentTableExample example2=new XnCommentTableExample();
                example2.createCriteria().andSidEqualTo(schoolId).andTypeEqualTo(b).andStatusEqualTo((byte)1)
                        .andTypeidEqualTo(typeId).andParentidEqualTo(xct.getParentid());
                example2.setOrderByClause("id desc");
                List<XnCommentTable> xnCommentTables1 = xnCommentTableMapper.selectByExample(example2);
                List<Map> list1=new ArrayList();
                if (ObjectUtil.isEmpty(xnCommentTables1)){
                    list1.add(null);
                }else {
                    for (XnCommentTable xc:xnCommentTables1){
                        Map<Object, Object> objectMap1 =userPhoto(xc.getUsertype(), xc.getUserid(), xc.getSid());
                        XnCommentTable xnCommentTable=xnCommentTableMapper.selectByPrimaryKey(xc.getParentid());
                        Map<Object, Object> objectObjectMap = userPhoto(xnCommentTable.getUsertype(), xnCommentTable.getUserid(), xnCommentTable.getSid());
                        Map map2=new HashMap();
                        map2.put("PstuName",xnCommentTable.getUsername());
                        map2.put("Pclass",objectObjectMap.get("class"));
                        map2.put("PImageSrc",objectObjectMap.get("ImageSrc"));
                        map2.put("PparentId",xnCommentTable.getParentid());
                        map2.put("Pcontent",xnCommentTable.getContent());
                        map2.put("PcreateTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnCommentTable.getCreatetime()));
                        map2.put("PcommentId",xnCommentTable.getId());


                        map2.put("CstuName",xc.getUsername());
                        map2.put("Cclass",objectMap1.get("class"));
                        map2.put("CImageSrc",objectMap1.get("ImageSrc"));
                        map2.put("CparentId",xc.getParentid());
                        map2.put("Ccontent",xc.getContent());
                        map2.put("CcreateTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xc.getCreatetime()));
                        map2.put("CcommentId",xc.getId());
                        list1.add(map2);
                    }
                }
            /*}*/
            list.add(map1);
        }
        Map<String,Object> map=new HashMap();
        //封装爱心信息
        if (b==2){
            //查询爱心信息
            XnKindnessSchool xnKindnessSchool = xnKindnessSchoolMapper.selectByPrimaryKey(typeId);
            Map<Object, Object> objectObjectMap = userPhoto(xnKindnessSchool.getUsertype(), xnKindnessSchool.getUserid(), xnKindnessSchool.getSchoolid());
            map.put("stuName",objectObjectMap.get("stuName"));
            map.put("class",objectObjectMap.get("class"));
            map.put("ImageSrc",objectObjectMap.get("ImageSrc"));
            map.put("goodsstatus",xnKindnessSchool.getGoodsstatus());
            map.put("createtime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnKindnessSchool.getCreatetime()));
            if (xnKindnessSchool.getImg()==null){
                map.put("img",null);
            }else {
                String[] str=xnKindnessSchool.getImg().split(",");
                map.put("img",str);
            }
            map.put("description",xnKindnessSchool.getDescription());
            HlEnumitemExample example1=new HlEnumitemExample();
            example1.createCriteria().andEnumidEqualTo(101).andIdEqualTo(xnKindnessSchool.getGoodstype());
            List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(example1);
            map.put("goodstype",hlEnumitems.get(0).getEnumitemname());
            map.put("phone",xnKindnessSchool.getPhone());
            map.put("kindnum",xnKindnessSchool.getKindnum());
            map.put("commentNum",CommentTableNum);
            map.put("id",xnKindnessSchool.getId());
            map.put("userType",xnKindnessSchool.getUsertype());
            map.put("comment",list);
            //map.put("childComment",list1);
        }
        if (b==1){
            //班级圈信息
            XnClassCircle xnClassCircle = xnClassCircleMapper.selectByPrimaryKey(typeId);

            Map<Object, Object> Map = userPhoto(xnClassCircle.getUsertype(), xnClassCircle.getUserid(), xnClassCircle.getSchoolid());
            map.put("id",typeId);
            map.put("stuName",Map.get("stuName"));
            map.put("class",Map.get("class"));
            map.put("ImageSrc",Map.get("ImageSrc"));
            //点赞
            XnCircleLikeExample example3=new XnCircleLikeExample();
            example3.createCriteria().andSidEqualTo(schoolId).andCidEqualTo(xnClassCircle.getClassid())
                    .andCircleidEqualTo(typeId).andAttr2EqualTo("1");
            List<XnCircleLike> xnCircleLikes = xnCircleLikeMapper.selectByExample(example3);
            map.put("likeNum",xnCircleLikes.size());
            map.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnClassCircle.getCreatetime()));
            if (xnClassCircle.getFileurl()==null){
                map.put("fileUrl",null);
            }else {
                String[] str=xnClassCircle.getFileurl().split(",");
                map.put("fileUrl",str);
            }
            map.put("description",xnClassCircle.getDescription());
            map.put("commentNum",CommentTableNum);
            map.put("comment",list);
            //map.put("childComment",list1);
        }
        return map;
    }

    /**
     * 封装班级圈信息
     * @param schoolId
     * @param xcc
     * @return
     */
    public Map<String,Object> classCircleInfo(Integer schoolId,XnClassCircle xcc){
        Byte b=Byte.valueOf("1");
        //封装评论信息
        XnCommentTableExample example=new XnCommentTableExample();
        example.createCriteria().andSidEqualTo(schoolId).andTypeEqualTo(b).andStatusEqualTo((byte)1)
                .andTypeidEqualTo(xcc.getId());
        List<XnCommentTable> xnCommentTables = xnCommentTableMapper.selectByExample(example);
        Integer CommentTableNum=0;
        if (!ObjectUtil.isEmpty(xnCommentTables)){
            CommentTableNum=xnCommentTables.size();
        }

        Map map=new HashMap();
        //班级圈信息
        //XnClassCircle xnClassCircle = xnClassCircleMapper.selectByPrimaryKey(ccId);

        Map<Object, Object> Map = userPhoto(xcc.getUsertype(), xcc.getUserid(), xcc.getSchoolid());
        map.put("id",xcc.getId());
        map.put("stuName",Map.get("stuName"));
        map.put("class",Map.get("class"));
        map.put("ImageSrc",Map.get("ImageSrc"));
        //点赞
        XnCircleLikeExample example2=new XnCircleLikeExample();
        example2.createCriteria().andSidEqualTo(schoolId).andCidEqualTo(xcc.getClassid())
                .andCircleidEqualTo(xcc.getId()).andAttr2EqualTo("1");
        List<XnCircleLike> xnCircleLikes = xnCircleLikeMapper.selectByExample(example2);
        map.put("likeNum",xnCircleLikes.size());
        map.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xcc.getCreatetime()));
        if (xcc.getFileurl()==null || "".equals(xcc.getFileurl()) || "null".equals(xcc.getFileurl())){
            map.put("fileUrl",null);
        }else {
            String[] str=xcc.getFileurl().split(",");
            map.put("fileUrl",str);
        }
        map.put("description",xcc.getDescription());
        map.put("commentNum",CommentTableNum);
        map.put("isCheck",xcc.getStatus());
        return map;
    }
    /**
     * 封装校园爱心信息
     * @param schoolId
     * @param xks
     * @return
     */
    public  Map<String,Object> kindnessInfo(Integer schoolId,XnKindnessSchool xks){
        Byte b=Byte.valueOf("2");
        //封装评论信息
        XnCommentTableExample example=new XnCommentTableExample();
        example.createCriteria().andSidEqualTo(schoolId).andTypeEqualTo(b).andStatusEqualTo((byte)1)
                .andTypeidEqualTo(xks.getId());
        List<XnCommentTable> xnCommentTables = xnCommentTableMapper.selectByExample(example);
        Integer CommentTableNum=0;
        if (!ObjectUtil.isEmpty(xnCommentTables)){
            CommentTableNum=xnCommentTables.size();
        }
        Map map=new HashMap();
        //查询爱心信息
        Map<Object, Object> objectObjectMap = userPhoto(xks.getUsertype(), xks.getUserid(), xks.getSchoolid());
        map.put("stuName",objectObjectMap.get("stuName"));
        map.put("class",objectObjectMap.get("class"));
        map.put("ImageSrc",objectObjectMap.get("ImageSrc"));
        map.put("goodsstatus",xks.getGoodsstatus());
        map.put("createtime", TimeUtil.TimeToYYYYMMDDHHMMSS(xks.getCreatetime()));
        if (xks.getImg()==null || "".equals(xks.getImg()) || "null".equals(xks.getImg())){
            map.put("img",null);
        }else {
            String[] str=xks.getImg().split(",");
            map.put("img",str);
        }
        map.put("description",xks.getDescription());
        HlEnumitemExample example1=new HlEnumitemExample();
        example1.createCriteria().andEnumidEqualTo(101).andIdEqualTo(xks.getGoodstype());
        List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(example1);
        String goodsType = "";
        if(hlEnumitems!=null && hlEnumitems.size()>0){
            goodsType = hlEnumitems.get(0).getEnumitemname();
        }else{
            goodsType = "其他";
        }
        map.put("goodstype",goodsType);
        map.put("phone",xks.getPhone());
        map.put("kindnum",xks.getKindnum());
        map.put("commentNum",CommentTableNum);
        map.put("id",xks.getId());
        map.put("userType",xks.getUsertype());
        return map;
    }

    /**
     * 封装评论信息
     * @param schoolId
     * @param korC
     * @param typeId
     * @return
     */
    public  List<Map<String,Object>> commentInfo(Integer schoolId,Integer korC,Integer typeId){
        Byte b=Byte.valueOf(korC.toString());
        //封装评论信息
        XnCommentTableExample example=new XnCommentTableExample();
        example.createCriteria().andSidEqualTo(schoolId).andTypeEqualTo(b).andStatusEqualTo((byte)1)
                .andTypeidEqualTo(typeId).andParentidEqualTo(0);
        example.setOrderByClause("id desc");
        List<XnCommentTable> xnCommentTables = xnCommentTableMapper.selectByExample(example);

        XnCommentTableExample example1=new XnCommentTableExample();
        example1.createCriteria().andSidEqualTo(schoolId).andTypeEqualTo(b).andStatusEqualTo((byte)1)
                .andTypeidEqualTo(typeId).andParentidNotEqualTo(0);
        example1.setOrderByClause("id desc");
        List<XnCommentTable> xnCommentTables1 = xnCommentTableMapper.selectByExample(example1);

        List list=new ArrayList();
        for (XnCommentTable xct:xnCommentTables){
            Map<Object, Object> objectMap =userPhoto(xct.getUsertype(), xct.getUserid(), xct.getSid());
            Map map1=new HashMap();
            map1.put("stuName",xct.getUsername());
            map1.put("class",objectMap.get("class"));
            map1.put("ImageSrc",objectMap.get("ImageSrc"));
            map1.put("parentId",xct.getParentid());
            map1.put("content",xct.getContent());
            map1.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xct.getCreatetime()));
            map1.put("commentId",xct.getId());
            List<XnCommentTable> list3=new ArrayList<>();
            getteat(xct, xnCommentTables1, list3);
            List<Map> list1=new ArrayList<>();
            for (XnCommentTable xc:list3){
                Map<Object, Object> objectMap1 =userPhoto(xc.getUsertype(), xc.getUserid(), xc.getSid());
                XnCommentTable xnCommentTable=xnCommentTableMapper.selectByPrimaryKey(xc.getParentid());
                Map<Object, Object> objectObjectMap = userPhoto(xnCommentTable.getUsertype(), xnCommentTable.getUserid(), xnCommentTable.getSid());
                Map map2=new HashMap();
                map2.put("PstuName",xnCommentTable.getUsername());
                map2.put("Pclass",objectObjectMap.get("class"));
                map2.put("PImageSrc",objectObjectMap.get("ImageSrc"));
                map2.put("PparentId",xnCommentTable.getParentid());
                map2.put("Pcontent",xnCommentTable.getContent());
                map2.put("PcreateTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnCommentTable.getCreatetime()));
                map2.put("PcommentId",xnCommentTable.getId());
                map2.put("CstuName",xc.getUsername());
                map2.put("Cclass",objectMap1.get("class"));
                map2.put("CImageSrc",objectMap1.get("ImageSrc"));
                map2.put("CparentId",xc.getParentid());
                map2.put("Ccontent",xc.getContent());
                map2.put("CcreateTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xc.getCreatetime()));
                map2.put("CcommentId",xc.getId());
                list1.add(map2);
            }
            map1.put("childComment",list1);
            list.add(map1);
        }

        return list;
    }

    public void getteat(XnCommentTable xct,List<XnCommentTable> list2,List<XnCommentTable> list3){
        List<XnCommentTable> rest= Lists.newArrayList();
        List<XnCommentTable> body= Lists.newArrayList();
        if (xct!=null && !ObjectUtil.isEmpty(xct)){
            for (XnCommentTable xc:list2){
                if (xct.getId().equals(xc.getParentid())){
                    rest.add(xc);
                    list3.add(xc);
                }else {
                    body.add(xc);
                }
            }
        }
        if(!ObjectUtil.isEmpty(body)){
            for (XnCommentTable xco:rest){
                getteat(xco, body, list3);
            }

        }
        return;
    }
    /**
     * 订单详情
     * @param xnGoodsOrder
     * @return
     */
    public Map<String,Object> orderInfo(XnGoodsOrder xnGoodsOrder){
        Map map=new HashMap();
        map.put("id",xnGoodsOrder.getId());
        map.put("username",xnGoodsOrder.getUsername());
        map.put("uid",xnGoodsOrder.getUid());
        map.put("goodsid",xnGoodsOrder.getGoodsid());
        map.put("goodsname",xnGoodsOrder.getGoodsname());
        if (xnGoodsOrder.getAddressid()==null){
            map.put("addressid",null);
            map.put("phone",null);
            map.put("name",null);
        }else {
            XnAddress xnAddress = xnAddressMapper.selectByPrimaryKey(xnGoodsOrder.getAddressid());
            String add=null;
            String phone=null;
            String name=null;
            if (!ObjectUtil.isEmpty(xnAddress)){
                add=xnAddress.getProvince()+xnAddress.getCity()+xnAddress.getDist()+xnAddress.getAddress();
                phone=xnAddress.getPhone();
                name=xnAddress.getUsername();
            }
            map.put("addressid",add);
            map.put("phone",phone);
            map.put("name",name);
        }
        map.put("number",xnGoodsOrder.getNumber());
        map.put("tradeNo",xnGoodsOrder.getOrdernumber());
        if(xnGoodsOrder.getBuytime()==null){
            map.put("buytime",null);
        }else {
            map.put("buytime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnGoodsOrder.getBuytime()));
        }
        String orderStatus=null;
        if (xnGoodsOrder.getOrderstate()==1){orderStatus="待支付";};
        if (xnGoodsOrder.getOrderstate()==2){orderStatus="已支付";};
        if (xnGoodsOrder.getOrderstate()==3){orderStatus="已取消";};
        if (xnGoodsOrder.getOrderstate()==4){orderStatus="已删除";};
        if (xnGoodsOrder.getOrderstate()==9){orderStatus="已成功";};
        map.put("orderstate",orderStatus);
        map.put("price",xnGoodsOrder.getPrice());
        map.put("totalprice",xnGoodsOrder.getTotalprice());
        map.put("type",xnGoodsOrder.getType());
        if (xnGoodsOrder.getBuytime()==null){
            map.put("buyTime","未支付");
        }else {
            map.put("buyTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnGoodsOrder.getBuytime()));
        }
        map.put("createtime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnGoodsOrder.getCreatetime()));
        if (xnGoodsOrder.getAttr3()==null){
            map.put("payWay",null);
        }else {
            map.put("payWay",xnGoodsOrder.getAttr3());
        }
        XnGoodsManage xnGoodsManage = xnGoodsManageMapper.selectByPrimaryKey(xnGoodsOrder.getGoodsid());
        if (!ObjectUtil.isEmpty(xnGoodsManage)){
            if (xnGoodsManage.getThumbnail()==null){
                map.put("thumbnail",null);
            }else {
                map.put("thumbnail",xnGoodsManage.getThumbnail());
            }
            if (xnGoodsManage.getDesimg()==null){
                map.put("desImg",null);
            }else {
                String[] str=xnGoodsManage.getDesimg().split(",");
                map.put("desImg",str);
            }
        }else {
            map.put("thumbnail",null);
            map.put("desImg",null);
        }
        return map;
    }

    /**
     * 封装点播视频信息
     * @param hv
     * @return
     */
    public Map prgInfo(HlVideoinfonew hv){
        Map map=new HashMap();
        map.put("id",hv.getId());
        map.put("TName",hv.getTname());
        map.put("flr_name",hv.getFlrName());
        map.put("className",hv.getClarmName());
        map.put("Cycle",hv.getCycle());
        map.put("classId",hv.getPrgClassroomId());
        map.put("DisciplineNmae",hv.getDisciplinenmae());
        map.put("build_name",hv.getBuildName());
        map.put("YearName",hv.getYearname());
        map.put("VideoName",hv.getVideoname());
        map.put("Lesson",hv.getLesson());
        map.put("week",hv.getWeek());
        map.put("SchoolId",hv.getSchoolid());
        map.put("prg_datetime",hv.getPrgDatetime());
        map.put("file_path",hv.getFilePath());
        map.put("file_absolute_path",hv.getFileAbsolutePath());
        map.put("file_thumbnails_path",hv.getFileThumbnailsPath());
        map.put("file_thumbnail_absolute_path",hv.getFileThumbnailAbsolutePath());
        return map;
    }

    /**
     * 封装精品课堂信息
     * @param xtpr
     * @return
     */
    public Map<String,Object> pointFocusInfo(XnTopqualityPersonalRule xtpr){
        Map<String,Object> map=new HashMap();
        map.put("id",xtpr.getId());
        map.put("sid",xtpr.getSid());
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(xtpr.getSid());
        if (ObjectUtil.isEmpty(cfDepartment)){
            map.put("schoolName",null);
        }else {
            map.put("schoolName",cfDepartment.getDeptname());
        }
        map.put("subject",xtpr.getSubject());
        map.put("tid",xtpr.getTid());
        HlTeacher ht=new HlTeacher();
        ht.setSchoolid(xtpr.getSid());
        ht.setId(xtpr.getTid());
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
        if (ObjectUtil.isEmpty(hlTeacher)){
            map.put("teaName",null);
        }else {
            map.put("teaName",hlTeacher.getTname());
        }
        map.put("tPrice",xtpr.getTprice());
        map.put("sPrice",xtpr.getSprice());
        map.put("uid",xtpr.getUid());
        map.put("path",xtpr.getAttr1());
        map.put("img",xtpr.getAttr3());
        map.put("className","精品课堂");
        return map;
    }

}
