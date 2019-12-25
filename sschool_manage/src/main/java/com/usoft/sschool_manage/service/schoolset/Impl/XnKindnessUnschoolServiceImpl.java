package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnKindnessUnschool;
import com.usoft.smartschool.pojo.XnKindnessUnschoolExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnKindnessUnschoolMapper;
import com.usoft.sschool_manage.service.schoolset.XnKindnessUnschoolService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/5/7 13:14
 */
@Service
public class XnKindnessUnschoolServiceImpl implements XnKindnessUnschoolService {


    @Resource
    private XnKindnessUnschoolMapper xnKindnessUnschoolMapper;

    /**
     * 爱心状态 正常
     */
    private final static byte NORMAL = 1;

    /**
     * 爱心状态  删除
     */
    private final static byte DELETE = 2;


    @Override
    public MyResult listXnKindnessUnschool(Integer type, Integer pageNo, Integer pageSize) {
        Integer schoolId  = SystemParam.getSchoolId();
        XnKindnessUnschoolExample xnKindnessUnschoolExample = new XnKindnessUnschoolExample();
        XnKindnessUnschoolExample.Criteria criteria = xnKindnessUnschoolExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        criteria.andStatusEqualTo(NORMAL);
        if(!ObjectUtil.isEmpty(type)){
            int t = type;
            criteria.andTypeEqualTo((byte)t);
        }
        List<XnKindnessUnschool> xnKindnessUnschools = xnKindnessUnschoolMapper.selectByExample(xnKindnessUnschoolExample);
        if(ObjectUtil.isEmpty(xnKindnessUnschools))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(XnKindnessUnschool xnKindnessUnschool : xnKindnessUnschools){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnKindnessUnschool.getId());
            map.put("type",xnKindnessUnschool.getType());
            map.put("name",xnKindnessUnschool.getName());
            map.put("des",xnKindnessUnschool.getDes());
            map.put("img",xnKindnessUnschool.getImg());
            map.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnKindnessUnschool.getCreatetime()));
            result.add(map);
        }
        return ResultPage.getPageResult(result,pageNo, pageSize);
    }

    @Override
    public MyResult addOrUpdateXnKindnessUnschool(Integer id, Integer type, String name, String img, String description) {
        if(ObjectUtil.isEmpty(name))return MyResult.failure("请传入名称");
        if(ObjectUtil.isEmpty(type))return MyResult.failure("请选择爱心类型");
        if(ObjectUtil.isEmpty(img))return MyResult.failure("请传入图片");
        if(ObjectUtil.isEmpty(description))return MyResult.failure("请输入内容");
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();
        XnKindnessUnschool xnKindnessUnschool = new XnKindnessUnschool();
        xnKindnessUnschool.setImg(img);
        xnKindnessUnschool.setName(name);
        xnKindnessUnschool.setDes(description);
        int t = type;
        xnKindnessUnschool.setType((byte)t);
        String message = "";
        try {
            if(ObjectUtil.isEmpty(id)){
                xnKindnessUnschool.setSid(schoolId);
                xnKindnessUnschool.setCreatetime(new Date());
                xnKindnessUnschool.setStatus(NORMAL);
                xnKindnessUnschool.setUid(userId);
                xnKindnessUnschoolMapper.insert(xnKindnessUnschool);
                message = "添加成功";
            }else{
                XnKindnessUnschool xnKindnessUnschool1 = xnKindnessUnschoolMapper.selectByPrimaryKey(id);
                if(ObjectUtil.isEmpty(xnKindnessUnschool1))return MyResult.failure("当前校园爱心不存在，无法修改");
                xnKindnessUnschool.setId(id);
                xnKindnessUnschoolMapper.updateByPrimaryKeySelective(xnKindnessUnschool);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        return MyResult.success(message);
    }

    @Override
    public MyResult deleteXnKindnessUnschool(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        XnKindnessUnschoolExample xnKindnessUnschoolExample = new XnKindnessUnschoolExample();
        XnKindnessUnschoolExample.Criteria criteria = xnKindnessUnschoolExample.createCriteria();
        int num = 0;
       try{
           for(Integer id : ids){
               XnKindnessUnschool xnKindnessUnschool = new XnKindnessUnschool();
               xnKindnessUnschool.setId(id);
               xnKindnessUnschool.setStatus(DELETE);
               num += xnKindnessUnschoolMapper.updateByPrimaryKeySelective(xnKindnessUnschool);
           }
       }catch (Exception e){
           e.printStackTrace();
           return MyResult.failure("删除失败");
       }
        return MyResult.success("成功删除"+num+"条数据");
    }
}
