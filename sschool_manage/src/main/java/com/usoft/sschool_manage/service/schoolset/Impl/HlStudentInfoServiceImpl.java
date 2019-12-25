package com.usoft.sschool_manage.service.schoolset.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MD5;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.NumberKit;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.exception.CustomException;
import com.usoft.sschool_manage.exception.MyException;
import com.usoft.sschool_manage.mapper.base.*;
import com.usoft.sschool_manage.service.schoolset.HlStudentInfoService;
import com.usoft.sschool_manage.util.MatcherUtil;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author jijh
 * @Date 2019/4/26 15:33
 */
@Service
public class HlStudentInfoServiceImpl  implements HlStudentInfoService {


    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;

    @Resource
    private CfDepartmentMapper cfDepartmentMapper;

    @Resource
    private HlEnumitemMapper hlEnumitemMapper;

    @Resource
    private HlSchclassMapper hlSchclassMapper;

    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;




    @Override
    public MyResult listStuInfoBase(Integer cid, String studentName, Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        List<Map<String,Object>> base = hlStudentinfoMapper.findByStudentName(cid, studentName,schoolId);
        if(ObjectUtil.isEmpty(base)){
            base = hlStudentinfoMapper.findByStudentName(cid,null,schoolId);
        }
        if(ObjectUtil.isEmpty(base)){
            return MyResult.failure("暂无数据");
        }
        if(pageNo == null) pageNo = 1;
        if(pageSize == null) pageSize = 20;


        return ResultPage.getPageResult(base,pageNo,pageSize);
    }

    @Override
    public MyResult selectStudentInfo(Integer sid) {

        if(ObjectUtil.isEmpty(sid))return MyResult.failure("请选择要查看的学生");
        Integer schoolId  = SystemParam.getSchoolId();
        HlStudentinfoKey hlStudentinfoKey = new HlStudentinfoKey();
        hlStudentinfoKey.setSchoolid(schoolId);
        hlStudentinfoKey.setId(sid);
        HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(hlStudentinfoKey);
        if(ObjectUtil.isEmpty(hlStudentinfo))return MyResult.failure("暂无数据");
        Map<String,Object> result = new HashMap<>();
        result.put("name",hlStudentinfo.getSname());
        result.put("id",hlStudentinfo.getId());
        result.put("phone",hlStudentinfo.getPhone());
        HlSchclassKey hlSchclassKey = new HlSchclassKey();
        hlSchclassKey.setSchoolid(schoolId);
        hlSchclassKey.setId(hlStudentinfo.getClassid());
        HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hlSchclassKey);
        result.put("className",hlSchclass !=null ? hlSchclass.getClassname() : "未知");
        HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(hlSchclass.getGradetype());
        result.put("grade",hlEnumitem!=null ? hlEnumitem.getEnumitemname() : "未知");
        return MyResult.success(result);
    }


    @Override
    public MyResult listHlStudentInfo(String studentName, Integer gradeType, Integer classId
            , String parentPhone, Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        //获取学校名字
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);
        String schoolName = cfDepartment.getDeptname();
        HlStudentinfoExample hlStudentinfoExample = new HlStudentinfoExample();
        HlStudentinfoExample.Criteria criteria = hlStudentinfoExample.createCriteria();
        criteria.andSchoolidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(studentName))criteria.andSnameLike("%"+studentName+"%");
        if(!ObjectUtil.isEmpty(gradeType))criteria.andGradeidEqualTo(gradeType);
        if(!ObjectUtil.isEmpty(classId))criteria.andClassidEqualTo(classId);
        if(!ObjectUtil.isEmpty(parentPhone))criteria.andPhoneLike("%"+parentPhone+"%");
        List<HlStudentinfo> hlStudentinfos = hlStudentinfoMapper.selectByExample(hlStudentinfoExample);
        List<Map<String,Object>> result = new ArrayList<>();
        for(HlStudentinfo hlStudentInfo :hlStudentinfos){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hlStudentInfo.getId());
            map.put("name",hlStudentInfo.getSname());
            map.put("schoolName",schoolName);
            map.put("schoolId",schoolId);
            map.put("gradeId",hlStudentInfo.getGradeid());
            //年级名称
            HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(hlStudentInfo.getGradeid());
            map.put("gradeName",hlEnumitem.getEnumitemname());
            map.put("classId",hlStudentInfo.getClassid());
            //班级名称
            HlSchclassKey hlSchclassKey = new HlSchclassKey();
            hlSchclassKey.setId(hlStudentInfo.getClassid());
            hlSchclassKey.setSchoolid(schoolId);
            HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hlSchclassKey);
            map.put("className",hlSchclass.getClassname());
            map.put("birthday",hlStudentInfo.getBirthday());
            Integer sexId = hlStudentInfo.getSexid();
            map.put("sex",sexId);
            map.put("setName",sexId == 73 ? "男":"女");
            String phone = hlStudentInfo.getPhone();
            map.put("phone",phone);
            //查询关系表
           if(ObjectUtil.isEmpty(phone)){
               map.put("role","无");
           }else{
               XnStuFamilyinfoExample xnStuFamilyinfoExample = new XnStuFamilyinfoExample();
               XnStuFamilyinfoExample.Criteria criteria1 = xnStuFamilyinfoExample.createCriteria();
//               criteria1.andPhoneEqualTo(phone);
               criteria1.andStuidEqualTo(hlStudentInfo.getId());
               List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(xnStuFamilyinfoExample);
               if(ObjectUtil.isEmpty(xnStuFamilyinfos)){
                   map.put("role","未知");
               }else{
                   List<Map<String,Object>> roles = new ArrayList<>();
                   for (XnStuFamilyinfo xnStuFamilyinfo:xnStuFamilyinfos) {
                       Map<String,Object> role_map = new HashMap<>();
                       role_map.put("role",xnStuFamilyinfo.getFamilyrelate());
                       role_map.put("phone",xnStuFamilyinfo.getPhone());
                       roles.add(role_map);
                   }
//                   XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfos.get(0);
//                   map.put("role",xnStuFamilyinfo.getFamilyrelate());
                   map.put("role",roles);
               }
           }
            //map.put("img",hlStudentInfo.getSphoto());
            Map<String,Object> imgMap = JSONObject.parseObject(hlStudentInfo.getSphoto(),Map.class);
            map.put("idImg",imgMap.get("idImg"));
            if(!ObjectUtil.isEmpty(imgMap.get("faceImg"))){
                map.put("faceImg",imgMap.get("faceImg").toString().split(","));
            }else{
                map.put("faceImg",null);
            }
            result.add(map);
        }
        return ResultPage.getPageResult(result,pageNo,pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyResult addOrUpdateHlStudent(Integer id, String name, Integer schoolId,
                                         Integer gradeId, Integer classId, String birthday,
                                         Integer sex,String parentRole, String parentPhone,
                                         String idImg, String faceImg,String patriarch) {

        //Integer schoolId = SystemParam.getSchoolId();
        HlStudentinfo hlStudentinfo = new HlStudentinfo();
        if(ObjectUtil.isEmpty(name))return MyResult.failure("请输入学生姓名");
        if(ObjectUtil.isEmpty(schoolId))return MyResult.failure("请选择学校");
        if(ObjectUtil.isEmpty(gradeId))return MyResult.failure("请选择年级");
        if(ObjectUtil.isEmpty(classId))return MyResult.failure("请选择班级");
        if(ObjectUtil.isEmpty(birthday))return MyResult.failure("请输入出生日期");
        if(!MatcherUtil.isYYYY_MM_DD(birthday))return MyResult.failure("请输入正确的生日：YYYY-MM-DD");
        if(ObjectUtil.isEmpty(sex))return MyResult.failure("请选择性别");
//        if(ObjectUtil.isEmpty(parentPhone))return MyResult.failure("输入家长号码");
//        if(!NumberKit.isPhone(parentPhone))return MyResult.failure("请输入正确的手机号码");
//        if (ObjectUtil.isEmpty(patriarch))return MyResult.failure("请输入家长号码！");
//        List<Map> patriarchs = (List<Map>) JSON.parse(patriarch);
//        System.out.println(patriarchs);

        //学生信息
        hlStudentinfo.setClassid(classId);
        hlStudentinfo.setBirthday(birthday);
        hlStudentinfo.setGradeid(gradeId);
        hlStudentinfo.setSname(name);
        hlStudentinfo.setSexid(sex);
        hlStudentinfo.setSchoolid(schoolId);
        //hlStudentinfo.setPhone(parentPhone);
        String message  = "";
        Map<String,Object> map = new HashMap<>();
        //风采照和证件照反了，所以这里需要调整下
        if(!ObjectUtil.isEmpty(idImg)){
            String[] idImgs = idImg.split(",");
            List<String> idImgList = new ArrayList<>();
            for(String idIm : idImgs){
                if(idIm.contains("http")){
                    idImgList.add(idIm.substring(idIm.indexOf("sschoolManageFile")));
                    //map.put("faceImg",idImg.substring(idImg.indexOf("sschoolManageFile")));
                }else{
                    idImgList.add(idIm);
                }
            }
            map.put("faceImg",String.join(",",idImgList));
        }
        if(!ObjectUtil.isEmpty(faceImg)){
            if(faceImg.contains("http")){
                map.put("idImg",faceImg.substring(faceImg.indexOf("sschoolManageFile")));
            }else{
                map.put("idImg",faceImg);
            }
        }
        hlStudentinfo.setSphoto(JSONObject.toJSONString(map));
        try{
            if(ObjectUtil.isEmpty(id)){
                if (ObjectUtil.isEmpty(patriarch))return MyResult.failure("请输入家长号码！");
                List<Map> patriarchs = (List<Map>) JSON.parse(patriarch);
                hlStudentinfo.setPhone(patriarchs.get(0).containsKey("parentPhone")?patriarchs.get(0).get("parentPhone").toString():null);
                int i = hlStudentinfoMapper.insert(hlStudentinfo);
                try {
                    CustomException.customeIf(i);
                }catch (MyException e){
                    e.printStackTrace();
                    return MyResult.failure("系统故障");
                }

                Integer stuid = hlStudentinfo.getId();
                XnStuFamilyinfo xnStuFamilyinfo = new XnStuFamilyinfo();

                //添加账号
                if("".equals(parentPhone) || parentPhone == null){//web上传学生信息
                    if (patriarchs.size()>0){
                        for(Map student_data:patriarchs){
                            xnStuFamilyinfo.setStuid(stuid);
                            xnStuFamilyinfo.setCreatetime(new Date());
                            xnStuFamilyinfo.setPhone(student_data.get("parentPhone").toString());
                            xnStuFamilyinfo.setFamilyrelate(student_data.get("parentRole").toString());
                            xnStuFamilyinfo.setExpiretime(TimeUtil.YYYYMMDDHHMMSSToTime("2099-12-31 23:59:59"));
                            xnStuFamilyinfo.setPassword(MD5.EncoderByMd5(student_data.get("parentPhone").toString().substring(5)));//默认密码，电话号码后六位
                            xnStuFamilyinfoMapper.insert(xnStuFamilyinfo);
                        }
                        message = "添加成功";
                    }
                }else {
                    xnStuFamilyinfo.setStuid(stuid);
                    xnStuFamilyinfo.setCreatetime(new Date());
                    xnStuFamilyinfo.setPhone(parentPhone);
                    xnStuFamilyinfo.setFamilyrelate(parentRole);
                    xnStuFamilyinfo.setExpiretime(TimeUtil.YYYYMMDDHHMMSSToTime("2099-12-31 23:59:59"));
                    xnStuFamilyinfo.setPassword(MD5.EncoderByMd5(parentPhone.substring(5)));//默认密码，电话号码后六位
                    xnStuFamilyinfoMapper.insert(xnStuFamilyinfo);
                    message = "添加成功";
                }

            }else{
                HlStudentinfoKey hlStudentinfoKey = new HlStudentinfoKey();
                hlStudentinfoKey.setId(id);
                hlStudentinfoKey.setSchoolid(schoolId);
                HlStudentinfo hlStudentinfo1 = hlStudentinfoMapper.selectByPrimaryKey(hlStudentinfoKey);
                if(ObjectUtil.isEmpty(hlStudentinfo1))return MyResult.failure("未找到当前学生信息，无法修改");
                hlStudentinfo.setId(id);
                //关联修改表
                XnStuFamilyinfoExample xnStuFamilyinfoExample = new XnStuFamilyinfoExample();

                XnStuFamilyinfo xnStuFamilyinfo = new XnStuFamilyinfo();
                //添加学生关系
                if (!ObjectUtil.isEmpty(patriarch)){
                    List<Map> patriarchs = (List<Map>) JSON.parse(patriarch);
                    if (patriarchs.size()>0){
                        for(Map student_data:patriarchs){
                            if (student_data.containsKey("parentPhone") && student_data.get("parentPhone")!=null && !"".equals(student_data.get("parentPhone"))) {
                                xnStuFamilyinfo.setPhone(student_data.get("parentPhone").toString());
                                xnStuFamilyinfo.setStuid(id);
                                xnStuFamilyinfo.setCreatetime(new Date());
                                xnStuFamilyinfo.setFamilyrelate(student_data.get("parentRole").toString());
                                xnStuFamilyinfo.setExpiretime(TimeUtil.YYYYMMDDHHMMSSToTime("2099-12-31 23:59:59"));
                                xnStuFamilyinfo.setPassword(MD5.EncoderByMd5(student_data.get("parentPhone").toString().substring(5)));//默认密码，电话号码后六位
                                xnStuFamilyinfoMapper.insert(xnStuFamilyinfo);
                            }
                        }
                    }
                }

                XnStuFamilyinfoExample.Criteria criteria = xnStuFamilyinfoExample.createCriteria();
                hlStudentinfoMapper.updateByPrimaryKeySelective(hlStudentinfo);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MyResult.failure("操作失败");
        }

        return MyResult.success(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyResult addExcelHlStudent(Integer id,String name, Integer schoolId, Integer gradeId, Integer classId, String birthday,
                                 Integer sex,String parentRole, String parentPhone, String idImg, String faceImg){
        //Integer schoolId = SystemParam.getSchoolId();
        HlStudentinfo hlStudentinfo = new HlStudentinfo();
        if(ObjectUtil.isEmpty(name))return MyResult.failure("请输入学生姓名");
        if(ObjectUtil.isEmpty(schoolId))return MyResult.failure("请选择学校");
        if(ObjectUtil.isEmpty(gradeId))return MyResult.failure("请选择年级");
        if(ObjectUtil.isEmpty(classId))return MyResult.failure("请选择班级");
        if(ObjectUtil.isEmpty(birthday))return MyResult.failure("请输入出生日期");
        if(!MatcherUtil.isYYYY_MM_DD(birthday))return MyResult.failure("请输入正确的生日：YYYY-MM-DD");
        if(ObjectUtil.isEmpty(sex))return MyResult.failure("请选择性别");
        if(ObjectUtil.isEmpty(parentPhone))return MyResult.failure("输入家长号码");
        if(!NumberKit.isPhone(parentPhone))return MyResult.failure("请输入正确的手机号码");
        hlStudentinfo.setClassid(classId);
        hlStudentinfo.setPhone(parentPhone);
        hlStudentinfo.setBirthday(birthday);
        hlStudentinfo.setGradeid(gradeId);
        hlStudentinfo.setSname(name);
        hlStudentinfo.setSexid(sex);
        hlStudentinfo.setSchoolid(schoolId);
        //hlStudentinfo.setPhone(parentPhone);
        String message  = "";
        Map<String,Object> map = new HashMap<>();
        //风采照和证件照反了，所以这里需要调整下
        if(!ObjectUtil.isEmpty(idImg)){
            String[] idImgs = idImg.split(",");
            List<String> idImgList = new ArrayList<>();
            for(String idIm : idImgs){
                if(idIm.contains("http")){
                    idImgList.add(idIm.substring(idIm.indexOf("sschoolManageFile")));
                    //map.put("faceImg",idImg.substring(idImg.indexOf("sschoolManageFile")));
                }else{
                    idImgList.add(idIm);
                }
            }
            map.put("faceImg",String.join(",",idImgList));
        }
        if(!ObjectUtil.isEmpty(faceImg)){
            if(faceImg.contains("http")){
                map.put("idImg",faceImg.substring(faceImg.indexOf("sschoolManageFile")));
            }else{
                map.put("idImg",faceImg);
            }
        }
        hlStudentinfo.setSphoto(JSONObject.toJSONString(map));
        try{
            if(ObjectUtil.isEmpty(id)){
                hlStudentinfoMapper.insert(hlStudentinfo);

                Integer stuid = hlStudentinfo.getId();
                XnStuFamilyinfo xnStuFamilyinfo = new XnStuFamilyinfo();
                xnStuFamilyinfo.setStuid(stuid);
                xnStuFamilyinfo.setCreatetime(new Date());
                xnStuFamilyinfo.setPhone(parentPhone);
                xnStuFamilyinfo.setFamilyrelate(parentRole);
                xnStuFamilyinfo.setExpiretime(TimeUtil.YYYYMMDDHHMMSSToTime("2099-12-31 23:59:59"));
                xnStuFamilyinfo.setPassword(MD5.EncoderByMd5(parentPhone.substring(5)));//默认密码，电话号码后六位
                xnStuFamilyinfoMapper.insert(xnStuFamilyinfo);
                message = "添加成功";

            }else{
                HlStudentinfoKey hlStudentinfoKey = new HlStudentinfoKey();
                hlStudentinfoKey.setId(id);
                hlStudentinfoKey.setSchoolid(schoolId);
                HlStudentinfo hlStudentinfo1 = hlStudentinfoMapper.selectByPrimaryKey(hlStudentinfoKey);
                if(ObjectUtil.isEmpty(hlStudentinfo1))return MyResult.failure("未找到当前学生信息，无法修改");
                hlStudentinfo.setId(id);
                //关联修改表
                XnStuFamilyinfoExample xnStuFamilyinfoExample = new XnStuFamilyinfoExample();

                XnStuFamilyinfoExample.Criteria criteria = xnStuFamilyinfoExample.createCriteria();
                hlStudentinfoMapper.updateByPrimaryKeySelective(hlStudentinfo);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MyResult.failure("操作失败");
        }

        return MyResult.success(message);
    }

    @Override
    public MyResult delelteStudent(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        HlStudentinfoExample hlStudentinfoExample = new HlStudentinfoExample();
        HlStudentinfoExample.Criteria criteria = hlStudentinfoExample.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        int num = 0;
        try{
            num = hlStudentinfoMapper.deleteByExample(hlStudentinfoExample);
        }catch (Exception e ){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return  MyResult.failure("成功删除"+num+"条数据");
    }

    @Override
    public MyResult familyRelation(Integer stuId) {
        XnStuFamilyinfoExample xnStuFamilyinfoExample = new XnStuFamilyinfoExample();
        XnStuFamilyinfoExample.Criteria criteria = xnStuFamilyinfoExample.createCriteria();
        criteria.andStuidEqualTo(stuId);
        criteria.andExpiretimeGreaterThan(new Date());
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(xnStuFamilyinfoExample);
        if(ObjectUtil.isEmpty(xnStuFamilyinfos))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();

        for(XnStuFamilyinfo xnStuFamilyinfo : xnStuFamilyinfos){
            Map<String,Object> map = new HashMap<>();
            map.put("relation",xnStuFamilyinfo.getFamilyrelate());
            map.put("id",xnStuFamilyinfo.getId());
            map.put("phone",xnStuFamilyinfo.getPhone());
            result.add(map);
        }

        return MyResult.success(result);
    }

    public static void  main(String[] args){
        String  good = "smartschoolFile/201906/ssfasf.jpg";
        System.out.println(good.substring(good.indexOf("sschoolManage")));
       // ConcurrentA
    }

}
