package com.usoft.sschool_manage.service.order.Impl;

import com.usoft.smartschool.pojo.XnAddress;
import com.usoft.smartschool.pojo.XnGoodsOrder;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnAddressMapper;
import com.usoft.sschool_manage.mapper.base.XnGoodsOrderMapper;
import com.usoft.sschool_manage.service.order.XnGoodsOrderService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/5/20 9:41
 */

@Service
public class XnGoodsOrderServiceImpl implements XnGoodsOrderService {

    @Resource
    private XnGoodsOrderMapper xnGoodsOrderMapper;
    @Resource
    private XnAddressMapper xnAddressMapper;



    @Override
    public MyResult listGoodsOrder(Integer type, String userName, Integer orderState, String buyTimeBegin, String buyTimeEnd,
                                   String priceBegin, String priceEnd, String phone, String orderNumber,Integer pageNo, Integer pageSize) {
        if(ObjectUtil.isEmpty(type)){
            return MyResult.failure("请选择要查看的订单类型");
        }
        String schoolId = String.valueOf(SystemParam.getSchoolId());
        List<Map<String,Object>> result = xnGoodsOrderMapper.listGoodsOrder(type, userName, orderState, buyTimeBegin, buyTimeEnd, priceBegin, priceEnd, phone, orderNumber,schoolId);
        if(ObjectUtil.isEmpty(result))return MyResult.failure("暂无数据");

        return ResultPage.getPageResult(result,pageNo,pageSize);
    }

    @Override
    public MyResult selectGoodsOrder(Integer id) {
        if(ObjectUtil.isEmpty(id))return MyResult.failure("请选择要查看的数据");
        XnGoodsOrder xnGoodsOrder = xnGoodsOrderMapper.selectByPrimaryKey(id);
        if(ObjectUtil.isEmpty(xnGoodsOrder)) return MyResult.failure("未找到当前订单信息");
        Map<String,Object> result = new HashMap<>();//封装返回信息
        //订单信息
        result.put("id",xnGoodsOrder.getId());
        result.put("orderNumber",xnGoodsOrder.getOrdernumber());
        result.put("orderState",xnGoodsOrder.getOrderstate());
        result.put("number",xnGoodsOrder.getNumber());
        result.put("orderType",xnGoodsOrder.getType() ==1?"现金":"积分");
        result.put("totalPrice",xnGoodsOrder.getTotalprice());
        result.put("phone",xnGoodsOrder.getPhone()==null ?"无":xnGoodsOrder.getPhone());
        result.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnGoodsOrder.getCreatetime()));
        //商品信息
        result.put("goodsId",xnGoodsOrder.getGoodsid());
        result.put("goodName",xnGoodsOrder.getGoodsname());
        result.put("price",xnGoodsOrder.getPrice());
        //用户信息
        String userType = xnGoodsOrder.getAttr1();
        Integer userT = Integer.valueOf(userType);
        result.put("userType",userT == 1?"学生/家长":"教师");
        result.put("userId",xnGoodsOrder.getUid());
        result.put("username",xnGoodsOrder.getUsername());
        if (xnGoodsOrder.getAddressid()==null || "".equals(xnGoodsOrder.getAddressid()) || "null".equals(xnGoodsOrder.getAddressid())){
            result.put("address","无");
            result.put("buyName",xnGoodsOrder.getUsername());
        }else {
            XnAddress xnAddress = xnAddressMapper.selectByPrimaryKey(xnGoodsOrder.getAddressid());
            if (ObjectUtil.isEmpty(xnAddress)){
                result.put("address","没找到地址");
                result.put("buyName",xnGoodsOrder.getUsername());
            }else {
                result.put("address",xnAddress.getProvince()+xnAddress.getCity()+xnAddress.getDist()+xnAddress.getAddress());
                result.put("buyName",xnAddress.getUsername());
                result.put("phone",xnAddress.getPhone());
            }
        }


        return MyResult.success(result);
    }
}
