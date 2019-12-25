package com.usoft.sschool_manage.service.schoolset.Impl;

import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MD5;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.CfDepartmentMapper;
import com.usoft.sschool_manage.mapper.base.CfUserMapper;
import com.usoft.sschool_manage.mapper.base.HlTeacherMapper;
import com.usoft.sschool_manage.service.schoolset.CfUserService;
import com.usoft.sschool_manage.util.RedisUtil;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/4/24 14:53
 */
@Service
public class CfUserServiceImpl implements CfUserService {

    @Resource
    private CfUserMapper cfUserMapper;

    @Resource
    private HlTeacherMapper hlTeacherMapper;

    @Resource
    private TokenUtil tokenUtil;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private CfDepartmentMapper cfDepartmentMapper;


    /**
     * 默认角色 教师
     */
    private static final int DEFAULTROLE = 1;

    /**
     * 用户类型  教师
     */
    private static final int TEACHER =2;

    @Override
    public MyResult login(String loginName, String password) {
        if(ObjectUtil.isEmpty(loginName))return MyResult.failure("请输入账号");
        if(ObjectUtil.isEmpty(password))return MyResult.failure("请输入密码");
        CfUserExample cfUserExample = new CfUserExample();
        CfUserExample.Criteria criteria = cfUserExample.createCriteria();
        criteria.andLoginnameEqualTo(loginName);
        List<CfUser> cfUserList = cfUserMapper.selectByExample(cfUserExample);
        if(ObjectUtil.isEmpty(cfUserList))return MyResult.failure("当前用户不存在");
        CfUser cfUser = cfUserList.get(0);
        String localPassword = cfUser.getLoginpassword();
        String enterpass = MD5.EncoderByMd5(password).toUpperCase();
        if(!enterpass.equals(localPassword.toUpperCase())){
            return MyResult.failure("密码错误");
        }
        //登录成功后，查询教师信息
        HlTeacherExample hlTeacherExample = new HlTeacherExample();
        HlTeacherExample.Criteria criteria1 = hlTeacherExample.createCriteria();
        criteria1.andTcodeEqualTo(loginName);
        List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(hlTeacherExample);
        if(ObjectUtil.isEmpty(hlTeachers))return MyResult.failure("当前教师信息已不存在");
        if(hlTeachers.get(0).getIsnormal()!=1){
            return MyResult.failure("教师信息已被删除，无法登录");
        }
        HlTeacher hlTeacher = hlTeachers.get(0);
        Integer schoolId = hlTeacher.getSchoolid();
        Integer userId = hlTeacher.getId();
        String token = "";
        try{
            token = tokenUtil.createToken(userId,DEFAULTROLE,schoolId,0,TEACHER);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("创建登录标识错误");
        }
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);

        Map<String,Object> result = new HashMap<>();
        result.put("token",token);
        result.put("code",loginName);
        result.put("name",hlTeacher.getTname());
        result.put("userType",2);
        String img = hlTeacher.getImagesrc();
        try{
            if(!ObjectUtil.isEmpty(img)){
                Map<String,Object> map = JSONObject.parseObject(img, Map.class);
                if(map.containsKey("idImg")){
                    result.put("idImg",map.get("idImg"));
                }else{
                    result.put("idImg","");
                }
            }else{
                result.put("idImg","");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("imageSrc",hlTeacher.getImagesrc());

        result.put("level",hlTeacher.getTlevel());
        result.put("schoolId",hlTeacher.getSchoolid());
        result.put("subject",hlTeacher.getTsubject());
        result.put("teacherId",hlTeacher.getId());
        result.put("schoolName",cfDepartment == null ? "未知":cfDepartment.getDeptname());

        return MyResult.success(result);
    }


    @Override
    public MyResult loginout(){

        Integer userId = SystemParam.getUserId();
        String redisKey = "tea"+userId+"token";
        try{
            Integer i =  redisUtil.remove(redisKey);
            if(i==1){
                return MyResult.success("退出成功");
            }else{
                return MyResult.failure("退出失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("退出失败");
        }
    }
}
