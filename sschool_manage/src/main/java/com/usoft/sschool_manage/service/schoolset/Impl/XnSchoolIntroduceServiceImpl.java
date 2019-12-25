package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnSchoolIntroduce;
import com.usoft.smartschool.pojo.XnSchoolIntroduceExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnSchoolIntroduceMapper;
import com.usoft.sschool_manage.service.schoolset.XnSchoolIntroduceService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/5/7 11:27
 */
@Service
public class XnSchoolIntroduceServiceImpl implements XnSchoolIntroduceService {

    @Resource
    private XnSchoolIntroduceMapper xnSchoolIntroduceMapper;

    @Override
    public MyResult selectXnSchoolIntroduce() {
        Integer schoolId  = SystemParam.getSchoolId();
        Map<String,Object> map = new HashMap<>();
        XnSchoolIntroduceExample xnSchoolIntroduceExample = new XnSchoolIntroduceExample();
        XnSchoolIntroduceExample.Criteria criteria = xnSchoolIntroduceExample.createCriteria();
        criteria.andSchoolidEqualTo(schoolId);
        List<XnSchoolIntroduce> xnSchoolIntroduces = xnSchoolIntroduceMapper.selectByExample(xnSchoolIntroduceExample);
        if(ObjectUtil.isEmpty(xnSchoolIntroduces)){
            return MyResult.failure("暂无数据");
        }
        XnSchoolIntroduce xnSchoolIntroduce = xnSchoolIntroduces.get(0);
        map.put("id",xnSchoolIntroduce.getId());
        map.put("img",xnSchoolIntroduce.getImg());
        map.put("des",xnSchoolIntroduce.getDescription());


        return MyResult.success(map);
    }

    @Override
    public MyResult editXnSchoolIntroduce(String img, String des) {
        Integer schoolId  = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();
        if(ObjectUtil.isEmpty(img))return MyResult.failure("请选择图片");
        if(ObjectUtil.isEmpty(des))return MyResult.failure("请选择内容");
        Map<String,Object> map = new HashMap<>();
        XnSchoolIntroduceExample xnSchoolIntroduceExample = new XnSchoolIntroduceExample();
        XnSchoolIntroduceExample.Criteria criteria = xnSchoolIntroduceExample.createCriteria();
        criteria.andSchoolidEqualTo(schoolId);
        List<XnSchoolIntroduce> xnSchoolIntroduces = xnSchoolIntroduceMapper.selectByExample(xnSchoolIntroduceExample);
        try{
            if(ObjectUtil.isEmpty(xnSchoolIntroduces)){
                XnSchoolIntroduce xnSchoolIntroduce = new XnSchoolIntroduce();
                xnSchoolIntroduce.setSchoolid(schoolId);
                xnSchoolIntroduce.setUserid(userId);
                xnSchoolIntroduce.setDescription(des);
                xnSchoolIntroduce.setImg(img);
                xnSchoolIntroduce.setCreatetime(new Date());
                xnSchoolIntroduceMapper.insert(xnSchoolIntroduce);
            }else{
                XnSchoolIntroduce xnSchoolIntroduce = xnSchoolIntroduces.get(0);
                xnSchoolIntroduce.setImg(img);
                xnSchoolIntroduce.setDescription(des);
                xnSchoolIntroduceMapper.updateByPrimaryKeySelective(xnSchoolIntroduce);
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        return MyResult.success("操作成功");
    }
}
