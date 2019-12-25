package com.usoft.sschool_manage.service.schoolset.Impl;

import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MD5;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.NumberKit;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.CfDepartmentMapper;
import com.usoft.sschool_manage.mapper.base.CfUserMapper;
import com.usoft.sschool_manage.mapper.base.HlEnumitemMapper;
import com.usoft.sschool_manage.mapper.base.HlTeacherMapper;
import com.usoft.sschool_manage.service.schoolset.HlTeacherService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.personal.TeacherCodeRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/4/29 13:15
 */

@Service
public class HlTeacherServiceImpl implements HlTeacherService {

    @Resource
    private HlTeacherMapper hlTeacherMapper;

    @Resource
    private HlEnumitemMapper hlEnumitemMapper;

    @Resource
    private CfUserMapper cfUserMapper;

    @Resource
    private CfDepartmentMapper cfDepartmentMapper;

    private static Map<Integer,String> role = new HashMap<>();

    static{
        role.put(1,"系统管理员");
        role.put(9,"学校管理员");
        role.put(10,"学校教师");
        role.put(11,"中心校用户");
        role.put(12,"教育局用户");
        role.put(13,"校长");
        role.put(14,"班主任");
    }


    /**
     * 教师状态 1.正常
     */
    private static int ISNORMAL = 1;


    /**
     * 教师状态 2.失效
     */
    private static int ISUNNORMAL = 2;


    @Override
    public MyResult listHlTeacherBase(String name,  Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        HlTeacherExample hlTeacherExample = new HlTeacherExample();
        HlTeacherExample.Criteria criteria = hlTeacherExample.createCriteria();
        criteria.andSchoolidEqualTo(schoolId);
        criteria.andIsnormalEqualTo(ISNORMAL);
        if(!ObjectUtil.isEmpty(name)){
            criteria.andTnameLike("%"+name+"%");
        }

        List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(hlTeacherExample);

        if(ObjectUtil.isEmpty(hlTeachers)){
            return MyResult.failure("暂无数据");
        }
        List<Map<String,Object>> result = new ArrayList<>();
        for(HlTeacher hlTeacher : hlTeachers){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hlTeacher.getId());
            map.put("name",hlTeacher.getTname());
            result.add(map);
        }

        return ResultPage.getPageResult(result,pageNo, pageSize);
    }

    @Override
    public MyResult listSubjectByTeacher(Integer teacherId) {

        if(ObjectUtil.isEmpty(teacherId)) return MyResult.failure("请选择教师");
        Integer schoolId = SystemParam.getSchoolId();
        HlTeacherKey hlTeacherKey = new HlTeacherKey();
        hlTeacherKey.setId(teacherId);
        hlTeacherKey.setSchoolid(schoolId);
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
        if(ObjectUtil.isEmpty(hlTeacher)){
            return MyResult.failure("当前教师信息不存在");
        }
        String subject = hlTeacher.getTsubject();
        if(ObjectUtil.isEmpty(subject)){
            return MyResult.failure("该教师未教授任何科目");
        }
        String[] subjects = subject.split(",");
        List<String> sub = Arrays.asList(subjects);


        return MyResult.success(sub);
    }


    @Override
    public MyResult listHlTeacherBySubject(String name,String subject, Integer pageNo, Integer pageSize){
        Integer schoolId = SystemParam.getSchoolId();
        HlTeacherExample hlTeacherExample = new HlTeacherExample();
        HlTeacherExample.Criteria criteria = hlTeacherExample.createCriteria();
        criteria.andSchoolidEqualTo(schoolId);
        criteria.andIsnormalEqualTo(ISNORMAL);
        if(ObjectUtil.isEmpty(subject))return MyResult.failure("请先选择科目");
        criteria.andTsubjectLike("%"+subject+"%");
        List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(hlTeacherExample);

        if(!ObjectUtil.isEmpty(name)){
            criteria.andTnameLike("%"+name+"%");
            List<HlTeacher> hlTeachers1 = hlTeacherMapper.selectByExample(hlTeacherExample);
            if(!ObjectUtil.isEmpty(hlTeachers1)){
                hlTeachers = hlTeachers1;
            }
        }

        if(ObjectUtil.isEmpty(hlTeachers)){
            return MyResult.failure("暂无数据");
        }
        List<Map<String,Object>> result = new ArrayList<>();
        for(HlTeacher hlTeacher : hlTeachers){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hlTeacher.getId());
            map.put("name",hlTeacher.getTname());
            result.add(map);
        }

        return ResultPage.getPageResult(result,pageNo, pageSize);
    }
    @Override
    public MyResult listHlTeacher(String name, Integer type, String subject, Integer pageNo, Integer pageSize) {
        HlTeacherExample hlTeacherExample = new HlTeacherExample();
        HlTeacherExample.Criteria criteria = hlTeacherExample.createCriteria();
        criteria.andIsnormalEqualTo(ISNORMAL);
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andSchoolidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(name))criteria.andTnameLike("%"+name+"%");
        if(!ObjectUtil.isEmpty(type))criteria.andTtypeEqualTo(type);
        if(!ObjectUtil.isEmpty(subject))criteria.andTsubjectLike("%"+subject+"%");
        List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(hlTeacherExample);
        if(ObjectUtil.isEmpty(hlTeachers))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(HlTeacher hlTeacher: hlTeachers){
            Map<String,Object> map = new HashMap<>();
            map.put("schoolId",hlTeacher.getId());
            map.put("id",hlTeacher.getId());
            map.put("name",hlTeacher.getTname());
            map.put("age",hlTeacher.getBirthday());
            //HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(hlTeacher.getTtype());
            map.put("acount",hlTeacher.getTcode());
            Integer tType = hlTeacher.getTtype();
            map.put("role",tType);
            map.put("roleName",role.get(tType)==null ? "未知角色":role.get(tType));
            map.put("subject",hlTeacher.getTsubject());
            map.put("phone",hlTeacher.getMobile());
            map.put("sex",hlTeacher.getSex());
            map.put("birthday",hlTeacher.getBirthday());
            //map.put("img",hlTeacher.getImagesrc());
            String img = hlTeacher.getImagesrc();
            if(img!=null && img.contains("{")){
                Map<String,Object> imgMap = JSONObject.parseObject(img,Map.class);
                map.put("idImg",imgMap.get("idImg"));
                if(!ObjectUtil.isEmpty(imgMap.get("faceImg"))){
                    map.put("faceImg",imgMap.get("faceImg").toString().split(","));
                }else{
                    map.put("faceImg",null);
                }
            }else{
                map.put("idImg",null);
                map.put("faceImg",null);
            }
            result.add(map);
        }
        return ResultPage.getPageResult(result,pageNo, pageSize);
    }

    @Override
    @Transactional
    public MyResult addOrUpdateTeacher(Integer id, String name, String birthday, Integer sex, Integer type, String subject, String phone, String password, String idImg, String faceImg) {
        Integer schoolId = SystemParam.getSchoolId();
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);
        Integer countyId = cfDepartment.getCountyid();

        HlTeacher hlTeacher = new HlTeacher();
        if(ObjectUtil.isEmpty(name))return MyResult.failure("请输入教师名字");hlTeacher.setTname(name);
        if(ObjectUtil.isEmpty(birthday))return MyResult.failure("请选择出生日期");hlTeacher.setBirthday(birthday);
        if(ObjectUtil.isEmpty(sex))return MyResult.failure("请选择性别"); hlTeacher.setSex(sex);
        if(ObjectUtil.isEmpty(type))return MyResult.failure("请选择角色");hlTeacher.setTtype(type);
        if(ObjectUtil.isEmpty(subject))return MyResult.failure("请选择任教科目");hlTeacher.setTsubject(subject);
        if(ObjectUtil.isEmpty(phone))return MyResult.failure("请输入电话号码");hlTeacher.setMobile(phone);
        if(!NumberKit.isPhone(phone))return MyResult.failure("请输入正确的手机号码");
        Map<String,Object> map = new HashMap<>();
        hlTeacher.setSchoolid(schoolId);
        if(!ObjectUtil.isEmpty(idImg)){
            if(idImg.contains("http")){
                map.put("idImg",idImg.substring(idImg.indexOf("sschoolManageFile")));
            }else{
                map.put("idImg",idImg);
            }
        }
        if(!ObjectUtil.isEmpty(faceImg)){
            String[] faceImgs = faceImg.split(",");
            List<String> faceImgList = new ArrayList<>();
            for(String face : faceImgs){
                if(face.contains("http")){
                    faceImgList.add(face.substring(face.indexOf("sschoolManageFile")));
                }else{
                    faceImgList.add(face);
                }
            }
            map.put("faceImg",String.join(",",faceImgList));
        }
        hlTeacher.setImagesrc(JSONObject.toJSONString(map));

       try{

           if(ObjectUtil.isEmpty(id)){
               if(ObjectUtil.isEmpty(password))return MyResult.failure("请输入密码");
               //创建教师编号
               HlTeacherExample hlTeacherExample = new HlTeacherExample();
               HlTeacherExample.Criteria criteria = hlTeacherExample.createCriteria();
               criteria.andSchoolidNotEqualTo(-1);
               int count = hlTeacherMapper.countByExample(hlTeacherExample);
               String teacherCode = TeacherCodeRule.createTeacherCode(countyId,schoolId,count);
               hlTeacher.setTcode(teacherCode);
               hlTeacher.setIsnormal(ISNORMAL);
               //存入数据库
               hlTeacherMapper.insert(hlTeacher);
               //存入教师表之后再存入到用户登录信息表
               CfUser cfUser = new CfUser();
               cfUser.setPhone(phone);
               HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(sex);
               cfUser.setSex(hlEnumitem.getEnumitemname());
               cfUser.setCreatedate(new Date());
               cfUser.setLoginname(teacherCode);
               cfUser.setRealname(name);
               cfUser.setLoginpassword(MD5.EncoderByMd5(password));
               cfUser.setDeptid(schoolId);
               cfUserMapper.insert(cfUser);
               Map<String,Object> re = new HashMap<>();
               re.put("account",teacherCode);
               return MyResult.success("添加成功",re);
           }else{
               HlTeacherKey hlTeacherKey = new HlTeacherKey();
               hlTeacherKey.setSchoolid(schoolId);
               hlTeacherKey.setId(id);
               HlTeacher hlTeacher1 = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
               if(ObjectUtil.isEmpty(hlTeacher1))return MyResult.failure("未找到当前教师数据，无法修改");
               hlTeacher.setId(id);
               hlTeacherMapper.updateByPrimaryKeySelective(hlTeacher);
               return MyResult.success("修改成功");
           }
       }catch (Exception e){
           e.printStackTrace();
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
           return MyResult.failure("操作失败");
       }

    }

    @Override
    public MyResult deleteTeacher(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的教师的信息");
        Integer schoolId = SystemParam.getSchoolId();

        int num = 0;
        for(Integer id : ids){
            HlTeacher hlTeacher = new HlTeacher();
            hlTeacher.setId(id);
            hlTeacher.setSchoolid(schoolId);
            hlTeacher.setIsnormal(ISUNNORMAL);
            num  += hlTeacherMapper.updateByPrimaryKeySelective(hlTeacher);

        }
        return MyResult.success("成功删除数据"+num+"条");
    }

    @Override
    public MyResult editPassword(Integer id, String password) {
        if(ObjectUtil.isEmpty(id)){
            return MyResult.failure("请选择要修改的数据");
        }
        if(ObjectUtil.isEmpty(password))return MyResult.failure("密码不能为空");
        HlTeacherKey hlTeacherKey = new HlTeacherKey();
        Integer schoolId = SystemParam.getSchoolId();
        hlTeacherKey.setSchoolid(schoolId);
        hlTeacherKey.setId(id);
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
        if(ObjectUtil.isEmpty(hlTeacher))return MyResult.failure("未找到当前教师信息，无法修改密码");
        String teacherCode = hlTeacher.getTcode();
        CfUserExample cfUserExample = new CfUserExample();
        CfUserExample.Criteria criteria = cfUserExample.createCriteria();
        criteria.andLoginnameEqualTo(teacherCode);
        List<CfUser> cfUsers = cfUserMapper.selectByExample(cfUserExample);
        if(ObjectUtil.isEmpty(cfUsers))return MyResult.failure("未找到当前教师登录信息");
        CfUser cfUser = cfUsers.get(0);
        cfUser.setLoginpassword(MD5.EncoderByMd5(password));
        try{
            cfUserMapper.updateByPrimaryKey(cfUser);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("更改密码失败");
        }
        return MyResult.success("修改密码成功");
    }
}
