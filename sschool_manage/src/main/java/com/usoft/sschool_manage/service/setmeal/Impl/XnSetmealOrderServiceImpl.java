package com.usoft.sschool_manage.service.setmeal.Impl;

import com.usoft.smartschool.pojo.XnMailOrder;
import com.usoft.smartschool.pojo.XnSetmealOrder;
import com.usoft.smartschool.pojo.XnStuFamilyinfo;
import com.usoft.smartschool.pojo.XnStuFamilyinfoExample;
import com.usoft.smartschool.util.MD5;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.enums.TimeTypeEnums;
import com.usoft.sschool_manage.mapper.base.XnMailOrderMapper;
import com.usoft.sschool_manage.mapper.base.XnSetmealOrderMapper;
import com.usoft.sschool_manage.mapper.base.XnStuFamilyinfoMapper;
import com.usoft.sschool_manage.service.setmeal.XnSetmealOrderService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/5/13 16:02
 */

@Service
public class XnSetmealOrderServiceImpl implements XnSetmealOrderService {

    @Resource
    private XnSetmealOrderMapper xnSetmealOrderMapper;

    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;

    @Resource
    private XnMailOrderMapper xnMailOrderMapper;

    @Override
    public MyResult listSetmealOrder(String studentName, String phone, String orderTimeBegin, String orderTimeEnd,
                                     String priceBegin, String priceEnd, Integer setMealId, String ordernumber,
                                     Integer orderState, Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
//        List<Map<String,Object>> result = xnSetmealOrderMapper.listXnSetmealOrder(studentName,phone,orderTimeBegin,
//                orderTimeEnd,priceBegin,priceEnd,orderState,setMealId,ordernumber, schoolId);
        List<Map<String,Object>> result = xnMailOrderMapper.listXnMailOrder(studentName,phone,orderTimeBegin,
                orderTimeEnd,priceBegin,priceEnd,setMealId,ordernumber,orderState, schoolId);
        if(ObjectUtil.isEmpty(result))return MyResult.failure("暂无数据");

        return ResultPage.getPageResult(result, pageNo, pageSize);
    }

    @Override
    public MyResult selectSetmealOrder(Integer orderId) {
        if(ObjectUtil.isEmpty(orderId))return MyResult.failure("请选择要查看的数据");
//        XnSetmealOrder xnSetmealOrder = xnSetmealOrderMapper.selectByPrimaryKey(orderId);
//        if(ObjectUtil.isEmpty(xnSetmealOrder))return MyResult.failure("未发现当前数据");

        XnMailOrder xnMailOrder = xnMailOrderMapper.selectByPrimaryKey(orderId);
        if(ObjectUtil.isEmpty(xnMailOrder))return MyResult.failure("未发现当前数据");

//        Map<String,Object> map = xnSetmealOrderMapper.selectXnSetmealOrder(orderId);
//        if(ObjectUtil.isEmpty(map)) return MyResult.failure("无相关数据");

        Map<String,Object> map = xnMailOrderMapper.selectXnMailOrder(orderId);
        if(ObjectUtil.isEmpty(map)) return MyResult.failure("无相关数据");

        map.put("time", TimeTypeEnums.getMessage(Integer.valueOf(map.get("time_type").toString())));

        return MyResult.success(map);
    }


    @Override
    public MyResult listBindingDetail(Integer orderId) {
        if(ObjectUtil.isEmpty(orderId))return MyResult.failure("请选择要查看的绑定详情");
//        XnSetmealOrder xnSetmealOrder = xnSetmealOrderMapper.selectByPrimaryKey(orderId);
//        if(ObjectUtil.isEmpty(xnSetmealOrder))return MyResult.failure("未发现当前数据");
        XnMailOrder xnMailOrder = xnMailOrderMapper.selectByPrimaryKey(orderId);
        if(ObjectUtil.isEmpty(xnMailOrder))return MyResult.failure("未发现当前数据");

        XnStuFamilyinfoExample xnStuFamilyinfoExample = new XnStuFamilyinfoExample();
        XnStuFamilyinfoExample.Criteria criteria = xnStuFamilyinfoExample.createCriteria();
        criteria.andOidEqualTo(orderId);
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(xnStuFamilyinfoExample);
        if(ObjectUtil.isEmpty(xnStuFamilyinfos)) return MyResult.failure("暂无数据");

        List<Map<String,Object>> result = new ArrayList<>();
        for(XnStuFamilyinfo xnStuFamilyinfo : xnStuFamilyinfos){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnStuFamilyinfo.getId());
            map.put("phone",xnStuFamilyinfo.getPhone());
            map.put("relation",xnStuFamilyinfo.getFamilyrelate());
            result.add(map);
        }

            return MyResult.success(result);
    }

}
