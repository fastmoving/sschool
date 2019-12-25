package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnGoodsManage;
import com.usoft.smartschool.pojo.XnGoodsManageExample;
import com.usoft.smartschool.pojo.XnGoodsType;
import com.usoft.smartschool.pojo.XnGoodsTypeExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnGoodsManageMapper;
import com.usoft.sschool_manage.mapper.base.XnGoodsTypeMapper;
import com.usoft.sschool_manage.service.schoolset.XnGoodsTypeService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/4/26 16:50
 */

@Service
public class XnGoodsTypeServiceImpl implements XnGoodsTypeService {

    @Resource
    private XnGoodsTypeMapper xnGoodsTypeMapper;

    @Resource
    private XnGoodsManageMapper xnGoodsManageMapper;//商品


    @Override
    public MyResult listXnGoodsType() {
        Integer schoolId = SystemParam.getSchoolId();

        //先查出所有一级分类

        XnGoodsTypeExample xnGoodsTypeExample = new XnGoodsTypeExample();
        XnGoodsTypeExample.Criteria criteria = xnGoodsTypeExample.createCriteria();
        criteria.andUidEqualTo(schoolId);
        criteria.andPidEqualTo(0);
        xnGoodsTypeExample.setOrderByClause(" type,orders asc");
        List<XnGoodsType>  xnGoodsTypeFirst = xnGoodsTypeMapper.selectByExample(xnGoodsTypeExample);
        if(ObjectUtil.isEmpty(xnGoodsTypeFirst))return MyResult.failure("暂无数据");

        List<Map<String,Object>> result = new ArrayList<>();
        for(XnGoodsType xnGoodsType : xnGoodsTypeFirst){
            XnGoodsTypeExample xnGoodsTypeExample1 = new XnGoodsTypeExample();
            XnGoodsTypeExample.Criteria criteria1 = xnGoodsTypeExample1.createCriteria();
            criteria1.andPidEqualTo(xnGoodsType.getId());
            xnGoodsTypeExample1.setOrderByClause("type,orders asc");
            List<XnGoodsType> xnGoodsTypeSecond = xnGoodsTypeMapper.selectByExample(xnGoodsTypeExample1);
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnGoodsType.getId());
            map.put("name",xnGoodsType.getName());
            map.put("type",xnGoodsType.getType());
            map.put("pid",xnGoodsType.getPid());
            map.put("isdisplay",xnGoodsType.getIsdisplay());
            map.put("orders",xnGoodsType.getOrders());
            map.put("uId",xnGoodsType.getUid());
            map.put("level",xnGoodsType.getLevel());
            map.put("createTime",xnGoodsType.getCreatetime());
            map.put("son",xnGoodsTypeSecond!=null ? xnGoodsTypeSecond : new String[]{});
            result.add(map);
        }
        return MyResult.success(result);
    }

    @Override
    public MyResult listXnGoodsTypeBase() {
        Integer schoolId = SystemParam.getSchoolId();


        List<Map<String,Object>> first = new ArrayList<>();
        Map<String,Object> online = new HashMap<>();
        online.put("value",-1);
        online.put("label","在线商城");
        Map<String,Object> integral = new HashMap<>();
        integral.put("value",-2);
        integral.put("label","积分商城");
        for(int i=1; i<3; i++){
            XnGoodsTypeExample xnGoodsTypeExample = new XnGoodsTypeExample();
            XnGoodsTypeExample.Criteria criteria = xnGoodsTypeExample.createCriteria();
            criteria.andUidEqualTo(schoolId);
            criteria.andTypeEqualTo((byte)i);
            criteria.andPidEqualTo(0);
            List<XnGoodsType> integralSecond = xnGoodsTypeMapper.selectByExample(xnGoodsTypeExample);
            if(!ObjectUtil.isEmpty(integralSecond)){
                List<Map<String,Object>> second = new ArrayList<>();
                for(XnGoodsType xnGoodsType : integralSecond){
                    Map<String,Object> integralSe = new HashMap<>();
                    integralSe.put("value",xnGoodsType.getId());
                    integralSe.put("label",xnGoodsType.getName());
                    criteria.andPidEqualTo(xnGoodsType.getId());
                    XnGoodsTypeExample xnGoodsTypeExample1 = new XnGoodsTypeExample();
                    XnGoodsTypeExample.Criteria criteria1 = xnGoodsTypeExample1.createCriteria();
                    criteria1.andUidEqualTo(schoolId);
                    criteria1.andPidEqualTo(xnGoodsType.getId());
                    List<XnGoodsType> IntegralThird = xnGoodsTypeMapper.selectByExample(xnGoodsTypeExample1);
                    if(!ObjectUtil.isEmpty(IntegralThird)){
                        List<Map<String,Object>> third = new ArrayList<>();
                        for(XnGoodsType xnGoodsTypeT : IntegralThird){
                            Map<String,Object> integralTh = new HashMap<>();
                            integralTh.put("value",xnGoodsTypeT.getId());
                            integralTh.put("label",xnGoodsTypeT.getName());
                            third.add(integralTh);
                        }
                        integralSe.put("children", third);
                    }
                    second.add(integralSe);
                }
                if(i == 1){
                    online.put("children",second);
                }else{
                    integral.put("children",second);
                }

            }

        }
        first.add(online);
        first.add(integral);



        return MyResult.success(first);
    }

    @Override
    public MyResult addOrUpdataXnGoodsType(Integer id, String name, Integer type, Integer pid, Integer isDisplay, Integer orders) {
        XnGoodsType xnGoodsType = new XnGoodsType();
        Integer schoolId = SystemParam.getSchoolId();
        if(ObjectUtil.isEmpty(name))return MyResult.failure("分类名称？");
        if(ObjectUtil.isEmpty(type))return MyResult.failure("商品类型？");
        if(ObjectUtil.isEmpty(pid))return MyResult.failure("父级id？");
        if(ObjectUtil.isEmpty(isDisplay))return MyResult.failure("是否展示？");
        if(ObjectUtil.isEmpty(orders))return MyResult.failure("排序？");
        xnGoodsType.setName(name);
        xnGoodsType.setUid(schoolId);
        int ty = type;
        xnGoodsType.setType((byte)ty);
        xnGoodsType.setPid(pid);
        int isdis = isDisplay;
        xnGoodsType.setIsdisplay((byte)isdis);
        xnGoodsType.setOrders(orders);
        String message = "";
        try{

            if(ObjectUtil.isEmpty(id)){
                if(pid == 0){
                    xnGoodsType.setLevel((byte)1);
                }else{
                    xnGoodsType.setLevel((byte)2);
                    XnGoodsType xnGoodsType1 = xnGoodsTypeMapper.selectByPrimaryKey(pid);
                    if(ObjectUtil.isEmpty(xnGoodsType1))return MyResult.failure("未找到当前一级分类，无法添加二级分类");
                }
                xnGoodsType.setCreatetime(new Date());
                xnGoodsTypeMapper.insert(xnGoodsType);
                message = "添加成功";
            }else{
                xnGoodsType.setId(id);
                XnGoodsType xnGoodsType2 = xnGoodsTypeMapper.selectByPrimaryKey(id);
                if(ObjectUtil.isEmpty(xnGoodsType2))return MyResult.failure("未找到当前分类。无法修改");
                if(xnGoodsType2.getPid() ==0){
                    XnGoodsTypeExample xnGoodsTypeExample = new XnGoodsTypeExample();
                    XnGoodsTypeExample.Criteria criteria = xnGoodsTypeExample.createCriteria();
                    criteria.andPidEqualTo(xnGoodsType2.getId());
                    List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(xnGoodsTypeExample);
                    if(!ObjectUtil.isEmpty(xnGoodsTypes)){
                        for(XnGoodsType xn : xnGoodsTypes){
                            xn.setType((byte)ty);
                            xnGoodsTypeMapper.updateByPrimaryKey(xn);
                        }
                    }
                }
                //不允许修改层级
                xnGoodsType.setPid(null);
                xnGoodsType.setLevel(null);
                xnGoodsTypeMapper.updateByPrimaryKeySelective(xnGoodsType);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }

        return MyResult.success(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyResult changeIsDisPlay(Integer id, Integer isDisplay) {
        if(ObjectUtil.isEmpty(id))return MyResult.failure("请选择要修改的数据");
        XnGoodsType xnGoodsType = xnGoodsTypeMapper.selectByPrimaryKey(id);
        if(ObjectUtil.isEmpty(xnGoodsType))return MyResult.failure("所修改的数据不存在");
        int display = isDisplay;
        xnGoodsType.setIsdisplay((byte)display);
        xnGoodsTypeMapper.updateByPrimaryKey(xnGoodsType);
        Integer pid = xnGoodsType.getPid();
        if(pid == 0){
            XnGoodsTypeExample xnGoodsTypeExample = new XnGoodsTypeExample();
            XnGoodsTypeExample.Criteria criteria = xnGoodsTypeExample.createCriteria();
            criteria.andPidEqualTo(id);
            List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(xnGoodsTypeExample);
            if(!ObjectUtil.isEmpty(xnGoodsTypes)){
                for(XnGoodsType xnGoodsType1 : xnGoodsTypes){
                    xnGoodsType1.setIsdisplay((byte)display);
                    xnGoodsTypeMapper.updateByPrimaryKey(xnGoodsType1);
                }
            }
        }else{
            if(isDisplay == 1){
                XnGoodsType xnGoodsTypeP = xnGoodsTypeMapper.selectByPrimaryKey(pid);
                xnGoodsTypeP.setIsdisplay((byte)display);
                xnGoodsTypeMapper.updateByPrimaryKey(xnGoodsTypeP);
            }
        }
        return MyResult.success("修改成功");
    }
    @Override
    @Transactional
    public MyResult deleteXnGoodsType(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        int num = 0;
        try{

            for(int id : ids){
                XnGoodsType xnGoodsType = xnGoodsTypeMapper.selectByPrimaryKey(id);
                if(!ObjectUtil.isEmpty(xnGoodsType)){
                    Integer pid = xnGoodsType.getPid();
                    if(pid == 0){//如果删除了层级1. 对应下面层级2的数据都要删除
                        XnGoodsTypeExample xnGoodsTypeExample = new XnGoodsTypeExample();
                        XnGoodsTypeExample.Criteria criteria = xnGoodsTypeExample.createCriteria();
                        criteria.andPidEqualTo(id);
                        num += xnGoodsTypeMapper.deleteByPrimaryKey(id);
                        num += xnGoodsTypeMapper.deleteByExample(xnGoodsTypeExample);
                        //删完类型后，还要删除对应的商品
                        XnGoodsManageExample xnGoodsManageExample = new XnGoodsManageExample();
                        XnGoodsManageExample.Criteria criteria1 = xnGoodsManageExample.createCriteria();
                        criteria1.andTypeidEqualTo(id);
                        List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(xnGoodsManageExample);
                        if(!ObjectUtil.isEmpty(xnGoodsManages)){
                            for(XnGoodsManage xnGoodsManage : xnGoodsManages){
                                xnGoodsManageMapper.deleteByPrimaryKey(xnGoodsManage.getId());
                            }
                        }

                    }else{
                        num += xnGoodsTypeMapper.deleteByPrimaryKey(id);
                        //删完类型后，还要删除对应的商品
                        XnGoodsManageExample xnGoodsManageExample = new XnGoodsManageExample();
                        XnGoodsManageExample.Criteria criteria1 = xnGoodsManageExample.createCriteria();
                        criteria1.andTypeidEqualTo(id);
                        List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(xnGoodsManageExample);
                        if(!ObjectUtil.isEmpty(xnGoodsManages)){
                            for(XnGoodsManage xnGoodsManage : xnGoodsManages){
                                xnGoodsManageMapper.deleteByPrimaryKey(xnGoodsManage.getId());
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除"+num+"条数据");
    }
}
