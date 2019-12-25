package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.HlTeacherMapper;
import com.usoft.sschool_manage.mapper.base.XnAnnounceMapper;
import com.usoft.sschool_manage.service.schoolset.XnAnnounceService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 公告
 * @Author jijh
 * @Date 2019/5/5 13:47
 */
@Service
public class XnAnnounceServiceImpl implements XnAnnounceService {


    @Resource
    private XnAnnounceMapper xnAnnounceMapper;

    @Resource
    private HlTeacherMapper hlTeacherMapper;


    @Override
    public MyResult listXnAnnounce(String title,Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        XnAnnounceExample xnAnnounceExample = new XnAnnounceExample();
        XnAnnounceExample.Criteria criteria = xnAnnounceExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(title)){
            criteria.andAnnouncetitleLike("%"+title+"%");
        }
        xnAnnounceExample.setOrderByClause("CreateTime desc");
        List<XnAnnounce> xnAnnounceList= xnAnnounceMapper.selectByExample(xnAnnounceExample);
        if(ObjectUtil.isEmpty(xnAnnounceList))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(XnAnnounce xnAnnounce : xnAnnounceList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnAnnounce.getId());
            map.put("title",xnAnnounce.getAnnouncetitle());
            map.put("content",xnAnnounce.getAnnouncecontent());
            map.put("announceImg",xnAnnounce.getAnnounceimg());
            map.put("createTime",TimeUtil.TimeToYYYYMMDD(xnAnnounce.getCreatetime()));
            map.put("createUser",xnAnnounce.getCreateuser());
            result.add(map);
        }


        return ResultPage.getPageResult(result,pageNo,pageSize);
    }

    @Override
    public MyResult  addOrUpdateXnAnnounce(Integer id, String title, String content,String img) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();
        XnAnnounce xnAnnounce = new XnAnnounce();
        if(ObjectUtil.isEmpty(title))return MyResult.failure("请输入公告标题");
        if(ObjectUtil.isEmpty(content))return MyResult.failure("请输入公告内容");
        xnAnnounce.setAnnouncetitle(title);
        xnAnnounce.setAnnouncecontent(content);
        xnAnnounce.setAnnounceimg(img);
        String message = "";
        try{
            if(ObjectUtil.isEmpty(id)){
                xnAnnounce.setCreatetime(new Date());
                xnAnnounce.setSid(schoolId);
                xnAnnounce.setUid(SystemParam.getUserId());
                xnAnnounce.setState((byte)1);
                HlTeacherKey hlTeacherKey = new HlTeacherKey();
                hlTeacherKey.setId(userId);
                hlTeacherKey.setSchoolid(schoolId);
                HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
                xnAnnounce.setCreateuser(hlTeacher.getTname());
                xnAnnounceMapper.insert(xnAnnounce);
                message = "添加成功";
            }else{
                xnAnnounce.setId(id);
                xnAnnounceMapper.updateByPrimaryKeySelective(xnAnnounce);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        return MyResult.success(message);
    }

    @Override
    public MyResult deleteXnAnnounce(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        XnAnnounceExample xnAnnounceExample = new XnAnnounceExample();
        XnAnnounceExample.Criteria criteria = xnAnnounceExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andSidEqualTo(schoolId);
        criteria.andIdIn(Arrays.asList(ids));
        int num = 0;
        try{
            num+=xnAnnounceMapper.deleteByExample(xnAnnounceExample);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除了"+num+"条数据");
    }
}
