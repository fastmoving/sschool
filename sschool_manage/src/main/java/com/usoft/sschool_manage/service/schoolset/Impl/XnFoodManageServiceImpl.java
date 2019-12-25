package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnFood;
import com.usoft.smartschool.pojo.XnFoodExample;
import com.usoft.smartschool.pojo.XnFoodKey;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.CfDepartmentMapper;
import com.usoft.sschool_manage.mapper.base.XnFoodMapper;
import com.usoft.sschool_manage.service.schoolset.XnFoodManageService;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/4/22 15:48
 */
@Service
public class XnFoodManageServiceImpl implements XnFoodManageService {

    @Resource
    private XnFoodMapper xnFoodMapper;

    @Resource
    private CfDepartmentMapper cfDepartmentMapper;

    @Override
    public MyResult listXnFood(Integer foodTime, Integer week ) {
        Integer shoolId = SystemParam.getSchoolId();


        List<XnFood> xnFoodList = null;
        try{
            xnFoodList = xnFoodMapper.listFoodBySchool(shoolId,foodTime,week);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("查询失败");
        }
        if(ObjectUtil.isEmpty(xnFoodList)){
            return MyResult.failure("暂无数据");
        }
        List<SortedMap<String,Object>> result = new ArrayList<>();
        for(XnFood xnFood : xnFoodList){
            SortedMap<String,Object> map = new TreeMap<>();

            map.put("id",xnFood.getId());
            map.put("name",xnFood.getFoodname());
            map.put("week",xnFood.getWeek());
            map.put("foodTime",xnFood.getFoodtime());
            map.put("schoolName",cfDepartmentMapper.selectByPrimaryKey(xnFood.getSid()).getDeptname());
            map.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnFood.getCreatetime()));
            result.add(map);
        }
        return MyResult.success(result);
    }

    @Override
    public MyResult insertXnFood(XnFood xnFood) {
        //if(ObjectUtil.isEmpty(xnFood.getSid()))return MyResult.failure("请选择菜谱所属学校");
        Integer schoolId = SystemParam.getSchoolId();
        xnFood.setSid(schoolId);
        if(ObjectUtil.isEmpty(xnFood.getFoodtime())) return MyResult.failure("未知的菜谱时间");
        if(ObjectUtil.isEmpty(xnFood.getWeek())) return MyResult.failure("请选择菜谱属于周几");
        if(ObjectUtil.isEmpty(xnFood.getFoodname())) return MyResult.failure("请输入菜名");
        XnFoodExample xnFoodExample = new XnFoodExample();
        XnFoodExample.Criteria criteria = xnFoodExample.createCriteria();
        criteria.andFoodtimeEqualTo(xnFood.getFoodtime());
        criteria.andWeekEqualTo(xnFood.getWeek());
        List<XnFood> xnFoods = xnFoodMapper.selectByExample(xnFoodExample);
//        if(!ObjectUtil.isEmpty(xnFoods) && xnFood.getId()!= null){
//            return MyResult.failure("当前时间菜谱已存在，请勿重复添加");
//        }
        String message = "";
        try{
            if(ObjectUtil.isEmpty(xnFood.getId())){
                Integer userId = SystemParam.getUserId();
                xnFood.setCreateuser(userId);
                xnFood.setCreatetime(new Date());
                xnFoodMapper.insert(xnFood);
                message = "添加成功";
            }else{
                XnFoodKey xnFoodKey = new XnFoodKey();
                xnFoodKey.setId(xnFood.getId());
                xnFoodKey.setSid(schoolId);
                XnFood xnFoodExist = xnFoodMapper.selectByPrimaryKey(xnFoodKey);
//                //判断是否有重复数据
//                XnFoodExample xnFoodExample1 = new XnFoodExample();
//                XnFoodExample.Criteria criteria1 = xnFoodExample1.createCriteria();
//                criteria1.andWeekEqualTo(xnFood.getWeek());
//                criteria1.andFoodtimeEqualTo(xnFood.getFoodtime());
//                criteria1.andIdNotEqualTo(xnFood.getId());
//                List<XnFood> xnFoods1 = xnFoodMapper.selectByExample(xnFoodExample1);
//                if(!ObjectUtil.isEmpty(xnFoods1)){
//                    return MyResult.failure("该时间段已有其他数据。无法修改");
//                }
                if(ObjectUtil.isEmpty(xnFoodExist))return MyResult.failure("未找到当前菜谱，无法修改");
                xnFoodMapper.updateByPrimaryKeySelective(xnFood);
                message = "修改成功";

            }

        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }

        return MyResult.success(message);
    }

    @Override
    public MyResult deleteXnFood(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的菜谱");


       // xnFoodMapper.deleteByExample();
        XnFoodExample xnFoodExample = new XnFoodExample();
        XnFoodExample.Criteria criteria =xnFoodExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andSidEqualTo(schoolId);
        criteria.andIdIn(Arrays.asList(ids));

        int num  = 0;
        try {
            num = xnFoodMapper.deleteByExample(xnFoodExample);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除"+num+"条数据");
    }

    @Override
    public MyResult updateXnFood(XnFood xnFood) {
        Integer id = xnFood.getId();
        Integer sid = xnFood.getSid();
        if(id ==null || sid == null){
            return MyResult.failure("请选择要修改的数据");
        }
        XnFoodKey xnFoodKey  = new XnFoodKey();
        xnFoodKey.setSid(sid);
        xnFoodKey.setId(id);
        XnFood xnFood1 = xnFoodMapper.selectByPrimaryKey(xnFoodKey);
        if(ObjectUtil.isEmpty(xnFood1))return MyResult.failure("未找到当前数据");
        try{
            xnFoodMapper.updateByPrimaryKeySelective(xnFood);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("更新失败");
        }



        return MyResult.success("更新成功");
    }

    @Override
    public MyResult selectXnFood(Integer foodId) {
        return null;
    }

    @Override
    public MyResult importXnFood(HttpServletRequest request) {
        return null;
    }




}
