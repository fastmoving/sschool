package com.usoft.sschool_manage.service.setmeal.Impl;

import com.usoft.smartschool.pojo.XnMeal;
import com.usoft.smartschool.pojo.XnMealExample;
import com.usoft.smartschool.pojo.XnSetmealManage;
import com.usoft.smartschool.pojo.XnSetmealManageExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.enums.MealEnums;
import com.usoft.sschool_manage.enums.TimeTypeEnums;
import com.usoft.sschool_manage.mapper.base.XnMealMapper;
import com.usoft.sschool_manage.mapper.base.XnSetmealManageMapper;
import com.usoft.sschool_manage.service.setmeal.XnSetmealManageService;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author jijh
 * @Date 2019/5/9 15:49
 */
@Service
public class XnSetMealManageServiceImpl implements XnSetmealManageService {

    @Resource
    private XnSetmealManageMapper xnSetmealManageMapper;

    @Resource
    private XnMealMapper xnMealMapper;
    /**
     * 套餐状态正常
     */
    private static final byte NORMAL = 1;

    /**
     * 套餐状态 不正常
     */
    private static final byte UNNORMAL = 0;

    @Override
    public MyResult listXnSetMealManageBase() {
        Integer schoolId = SystemParam.getSchoolId();
        XnSetmealManageExample xnSetmealManageExample = new XnSetmealManageExample();
        XnSetmealManageExample.Criteria criteria = xnSetmealManageExample.createCriteria();
        criteria.andUidEqualTo(schoolId);
        List<XnSetmealManage> xnSetmealManages = xnSetmealManageMapper.selectByExample(xnSetmealManageExample);
        if(ObjectUtil.isEmpty(xnSetmealManages)) return  MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(XnSetmealManage xnSetmealManage : xnSetmealManages){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnSetmealManage.getId());
            map.put("name",xnSetmealManage.getSetmealname());
            map.put("status",xnSetmealManage.getStatus());
            result.add(map);
        }
        return MyResult.success(result);
    }

    @Override
    public MyResult listXnSetmealManage() {
//        XnSetmealManageExample xnSetmealManageExample = new XnSetmealManageExample();
        XnMealExample xnMealExample = new XnMealExample();
        Integer schoolId = SystemParam.getSchoolId();
//        XnSetmealManageExample.Criteria criteria = xnSetmealManageExample.createCriteria();
//        criteria.andUidEqualTo(schoolId);
//        criteria.andStatusEqualTo(NORMAL);
        XnMealExample.Criteria criteria = xnMealExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        criteria.andStatusEqualTo((int)NORMAL);
//        List<XnSetmealManage> xnSetmealManages = xnSetmealManageMapper.selectByExample(xnSetmealManageExample);
        List<XnMeal> xnMeals = xnMealMapper.selectByExample(xnMealExample);
        if(ObjectUtil.isEmpty(xnMeals)){
            return MyResult.failure("暂无数据");
        }

        List<Map<String,Object>> result = new ArrayList<>();
//        for(XnSetmealManage xnSetmealManage : xnSetmealManages){
        List<XnMeal> xnMeales = xnMeals.stream().filter(c->"2".equals(c.getAttr1())).collect(Collectors.toList());
        for(XnMeal xnMeal:xnMeales){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnMeal.getId());
            map.put("name",xnMeal.getMealname());
            map.put("price",xnMeal.getPrice());
//            map.put("time",xnSetmealManage.getBegindate()+"~"+xnSetmealManage.getEnddate());
            map.put("time", TimeTypeEnums.getMessage(xnMeal.getTimetype()));
            map.put("type",MealEnums.getMessage(Integer.valueOf(xnMeal.getAttr1())));
            map.put("bindMan",xnMeal.getBindman());
            map.put("des",xnMeal.getDescription());
            result.add(map);
        }

        return MyResult.success(result);
    }

    @Override
    public MyResult addOrUpdateXnSetmealManage(Integer id,String name, String price, String beginDate,
                                               String endDate, Integer bindMan, String des,Integer timeType,Integer attr1) {
        if(ObjectUtil.isEmpty(name))return MyResult.failure("请输入套餐名称");
        if(ObjectUtil.isEmpty(price))return MyResult.failure("请输入价格");
        if(ObjectUtil.isEmpty(timeType))return MyResult.failure("请选择时间");
        if(ObjectUtil.isEmpty(bindMan))return MyResult.failure("请输入绑定人数");
//        XnSetmealManage xnSetmealManage = new XnSetmealManage();
        Integer schoolId = SystemParam.getSchoolId();
//        xnSetmealManage.setSetmealname(name);
//        xnSetmealManage.setSetmealprice(new BigDecimal(price));
//        xnSetmealManage.setBegindate(beginDate);
//        xnSetmealManage.setEnddate(endDate);
//        xnSetmealManage.setBindman(bindMan);
//        xnSetmealManage.setDescription(des);
        XnMeal xnMeal = new XnMeal();
        xnMeal.setBindman(bindMan);
        xnMeal.setMealname(name);
        xnMeal.setTimetype(timeType);
        xnMeal.setDescription(des);
        xnMeal.setAttr1(attr1.toString());
        xnMeal.setPrice(new BigDecimal(price));
        xnMeal.setNumber((int)NORMAL);
        xnMeal.setStatus((int)NORMAL);
        String message = "";
        try{
            //添加
            if(ObjectUtil.isEmpty(id)){
//                xnSetmealManage.setCreatetime(new Date());
//                xnSetmealManage.setUid(schoolId);
//                xnSetmealManage.setStatus(NORMAL);
//                xnSetmealManageMapper.insert(xnSetmealManage);
                xnMeal.setCreatetime(new Date());
                xnMeal.setSid(schoolId);
                xnMealMapper.insert(xnMeal);
                message = "添加成功";
            }else{//修改
//                XnSetmealManage isExist = xnSetmealManageMapper.selectByPrimaryKey(id);
//                if(ObjectUtil.isEmpty(isExist))return MyResult.failure("未发现当前套餐，无法修改");
//                xnSetmealManage.setId(id);
//                xnSetmealManageMapper.updateByPrimaryKeySelective(xnSetmealManage);
                XnMeal isExist = xnMealMapper.selectByPrimaryKey(id);
                if (ObjectUtil.isEmpty(isExist))return MyResult.failure("未发现当前套餐，无法修改");
                xnMeal.setId(id);
                xnMealMapper.updateByPrimaryKeySelective(xnMeal);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }

        return MyResult.success(message);
    }

    @Override
    public MyResult deleteXnSetmealManage(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
//        XnSetmealManageExample xnSetmealManageExample = new XnSetmealManageExample();
//        XnSetmealManageExample.Criteria criteria  = xnSetmealManageExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
//        criteria.andUidEqualTo(schoolId);
//        criteria.andIdIn(Arrays.asList(ids));
//        List<XnSetmealManage> xnSetmealManages = xnSetmealManageMapper.selectByExample(xnSetmealManageExample);
//        if(ObjectUtil.isEmpty(xnSetmealManages))return MyResult.failure("数据不存在，无法删除");

        XnMealExample xnMealExample = new XnMealExample();
        XnMealExample.Criteria criteria1 = xnMealExample.createCriteria();
        criteria1.andSidEqualTo(schoolId);
        criteria1.andIdIn(Arrays.asList(ids));
        List<XnMeal> xnMeal = xnMealMapper.selectByExample(xnMealExample);
        if (ObjectUtil.isEmpty(xnMeal))return MyResult.failure("数据不存在，无法删除");
        Integer num = 0;
        try{
//            for(XnSetmealManage xnSetmealManage : xnSetmealManages){
//                xnSetmealManage.setStatus(UNNORMAL);
//                num +=xnSetmealManageMapper.updateByPrimaryKeySelective(xnSetmealManage);
//            }
            for (XnMeal xnMeal1 : xnMeal){
                xnMeal1.setStatus((int)UNNORMAL);
                num +=xnMealMapper.updateByPrimaryKeySelective(xnMeal1);
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }



        return MyResult.success("成功删除了"+num+"条数据");
    }

    @Override
    public MyResult selectXnSetmealManage(Integer id) {
        if(ObjectUtil.isEmpty(id))return MyResult.failure("请选择要查看的数据");
//        XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(id);
        XnMeal xnMeal = xnMealMapper.selectByPrimaryKey(id);
//        if(ObjectUtil.isEmpty(xnSetmealManage))return MyResult.failure("无数据");
        if(ObjectUtil.isEmpty(xnMeal))return MyResult.failure("无数据");
        Map<String,Object> map = new HashMap<>();
        map.put("id",xnMeal.getId());
        map.put("price",xnMeal.getPrice());
        map.put("bindMan",xnMeal.getBindman());
//        map.put("time",xnSetmealManage.getBegindate()+"~"+xnSetmealManage.getEnddate());
        map.put("time",TimeTypeEnums.getMessage(xnMeal.getTimetype()));
        map.put("des",xnMeal.getDescription());
        map.put("type", MealEnums.getMessage(Integer.valueOf(xnMeal.getAttr1())));
        map.put("name",xnMeal.getMealname());
        map.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnMeal.getCreatetime()));
        return MyResult.success(map);
    }
}
