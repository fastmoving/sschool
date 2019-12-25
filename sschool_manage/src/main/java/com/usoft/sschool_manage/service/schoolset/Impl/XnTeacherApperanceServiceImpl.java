package com.usoft.sschool_manage.service.schoolset.Impl;

import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.HlTeacherMapper;
import com.usoft.sschool_manage.mapper.base.XnTeaApperanceMapper;
import com.usoft.sschool_manage.service.schoolset.XnTeacherApperanceService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/5/6 14:19
 */

@Service
public class XnTeacherApperanceServiceImpl implements XnTeacherApperanceService {

    @Resource
    private XnTeaApperanceMapper xnTeaApperanceMapper;


    @Resource
    private HlTeacherMapper hlTeacherMapper;

    @Override
    public MyResult getTeacherMessage(Integer tid) {
        if (ObjectUtil.isEmpty(tid))return MyResult.failure();
        Integer schoolId = SystemParam.getSchoolId();
        HlTeacherKey hlTeacherKey = new HlTeacherKey();
        hlTeacherKey.setId(tid);
        hlTeacherKey.setSchoolid(schoolId);
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
        if(ObjectUtil.isEmpty(hlTeacher))return MyResult.failure("教师信息不存在");
        Map<String,Object> map = new HashMap<>();
        map.put("id",tid);
        map.put("teacherName",hlTeacher.getTname());
        map.put("subject",hlTeacher.getTsubject());
        Map<String,Object> imgMap = JSONObject.parseObject(hlTeacher.getImagesrc(),Map.class);
        map.put("img",imgMap.get("faceImg"));
        return MyResult.success(map);
    }

    @Override
    public MyResult listXnTeacherApperance(String teacherName, Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        XnTeaApperanceExample xnTeaApperanceExample = new XnTeaApperanceExample();
        XnTeaApperanceExample.Criteria criteria = xnTeaApperanceExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(teacherName)){
            criteria.andTeanameLike("%"+teacherName+"%");
        }
        List<XnTeaApperance> xnTeaApperances = xnTeaApperanceMapper.selectByExample(xnTeaApperanceExample);
        if(ObjectUtil.isEmpty(xnTeaApperances))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(XnTeaApperance xnTeaApperance : xnTeaApperances){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnTeaApperance.getId());
            map.put("teacherId",xnTeaApperance.getTid());
            map.put("teacherName",xnTeaApperance.getTeaname());
            map.put("subject",xnTeaApperance.getSubject());
            map.put("img",xnTeaApperance.getImg());
            map.put("description",xnTeaApperance.getDescription());
            result.add(map);
        }

        return ResultPage.getPageResult(result,pageNo, pageSize);
    }

    @Override
    public MyResult addOrUpdateXnTeaxherApperance(Integer id, Integer tid, String subject, String img, String description) {
        Integer schoolId = SystemParam.getSchoolId();
        XnTeaApperance xnTeaApperance = new XnTeaApperance();
        if(ObjectUtil.isEmpty(tid))return MyResult.failure("请选择教师");
        if(ObjectUtil.isEmpty(description))return MyResult.failure("请输入风采详情");
        if(ObjectUtil.isEmpty(img))return MyResult.failure("请选择风采照");
        if(ObjectUtil.isEmpty(subject))return MyResult.failure("请选择科目");
        HlTeacherKey hlTeacherKey = new HlTeacherKey();
        hlTeacherKey.setId(tid);
        hlTeacherKey.setSchoolid(schoolId);
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
        if(ObjectUtil.isEmpty(hlTeacher))return MyResult.failure("教师信息不存在");
        xnTeaApperance.setTid(tid);
        xnTeaApperance.setSid(schoolId);
        xnTeaApperance.setTeaname(hlTeacher.getTname());
        xnTeaApperance.setDescription(description);
        xnTeaApperance.setImg(img);
        xnTeaApperance.setSubject(subject);
        String message  ="";
        try{
            if(ObjectUtil.isEmpty(id)){
                xnTeaApperance.setCreatetime(new Date());
                Integer userId = SystemParam.getUserId();
                HlTeacherKey hlTeacherKey2 = new HlTeacherKey();
                hlTeacherKey2.setSchoolid(schoolId);
                hlTeacherKey2.setId(userId);
                HlTeacher hlTeacher1 = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey2);
                xnTeaApperance.setCreateuser(hlTeacher1.getTname());
                xnTeaApperance.setUid(userId);
                xnTeaApperanceMapper.insert(xnTeaApperance);
                message ="添加成功";

            }else{
                xnTeaApperance.setId(id);
                XnTeaApperanceKey xnTeaApperanceKey = new XnTeaApperanceKey();
                xnTeaApperanceKey.setId(id);
                xnTeaApperanceKey.setSid(schoolId);
                XnTeaApperance xnTeaApperance1 = xnTeaApperanceMapper.selectByPrimaryKey(xnTeaApperanceKey);
                if(ObjectUtil.isEmpty(xnTeaApperance1))return MyResult.failure("当前教师风采不存在，无法修改");
                xnTeaApperanceMapper.updateByPrimaryKeySelective(xnTeaApperance);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        return MyResult.success(message);
    }

    @Override
    public MyResult deleteXnTeacherApperance(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        XnTeaApperanceExample xnTeaApperanceExample = new XnTeaApperanceExample();
        XnTeaApperanceExample.Criteria criteria = xnTeaApperanceExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andSidEqualTo(schoolId);
        criteria.andIdIn(Arrays.asList(ids));
        int num = 0;
        try {
            num = xnTeaApperanceMapper.deleteByExample(xnTeaApperanceExample);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除了"+num+"条数据");
    }
}
