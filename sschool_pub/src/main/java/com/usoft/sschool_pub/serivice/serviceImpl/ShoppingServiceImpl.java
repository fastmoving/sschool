package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.util.PayUtil.WXUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.ShoppingService;
import com.usoft.sschool_pub.util.ResultPage;
import com.usoft.sschool_pub.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author wlw
 * @data 2019/4/25 0025-16:54
 */
@Service("ShoppingService")
public class ShoppingServiceImpl implements ShoppingService {
    @Resource
    private XnGoodsManageMapper xnGoodsManageMapper;
    @Resource
    private XnGoodsTypeMapper xnGoodsTypeMapper;
    @Resource
    private XnAddressMapper xnAddressMapper;
    @Resource
    private XnGoodsOrderMapper xnGoodsOrderMapper;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;
    @Resource
    private VoUtil voUtil;

    /**
     * 查询商品分类
     * @return
     */
    @Override
    public MyResult level(Byte goodsType) {
        if (goodsType==null){
            goodsType=1;
        }
        if (goodsType==2){
            if (SystemParam.getType()==1){
                return MyResult.failure("学生没有积分商城分类");
            }
        }
        Integer schoolId = SystemParam.getSchoolId();
        XnGoodsTypeExample example=new XnGoodsTypeExample();
        example.createCriteria().andUidEqualTo(schoolId).andPidEqualTo(0).andIsdisplayEqualTo((byte)1)
        .andTypeEqualTo(goodsType);
        List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnGoodsTypes))return MyResult.failure("没有找到对应的分类信息");
        List<Map> list=new ArrayList<>();
        for (XnGoodsType xgt:xnGoodsTypes){
            Map map=new HashMap();
            map.put("id",xgt.getId());
            map.put("name",xgt.getName());
            map.put("type",xgt.getType());
            map.put("shoolId",xgt.getUid());
            list.add(map);
        }
        return MyResult.success(list);
    }

    /**
     * 二级分类
     * @param level
     * @return
     */
    @Override
    public MyResult childLevel(Integer level) {
        Integer schoolId = SystemParam.getSchoolId();
        XnGoodsTypeExample example=new XnGoodsTypeExample();
        example.createCriteria().andUidEqualTo(schoolId).andPidEqualTo(level).andIsdisplayEqualTo((byte)1);
        List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnGoodsTypes))return MyResult.failure("没有找到对应的分类信息");
        List<Map> list=new ArrayList<>();
        for (XnGoodsType xgt:xnGoodsTypes){
            Map map=new HashMap();
            map.put("id",xgt.getId());
            map.put("name",xgt.getName());
            map.put("type",xgt.getType());
            map.put("shoolId",xgt.getUid());
            list.add(map);
        }
        return MyResult.success(list);
    }

    //显示所有商品
    @Override
    public MyResult all(Integer mallType,String level,String childLevel,Integer pageNo,Integer pageSize) {
        List list=new ArrayList();
        if (mallType==null){
            mallType=1;
        }
        //在线商城商品
        if (mallType==1){
            XnGoodsTypeExample example=new XnGoodsTypeExample();
            XnGoodsTypeExample.Criteria criteria = example.createCriteria();
            criteria.andTypeEqualTo((byte) 1).andIsdisplayEqualTo((byte)1);
            example.setOrderByClause("orders desc");
            if (!"null".equals(level) && level!=null && !"".equals(level)){
                if (!"null".equals(childLevel) && childLevel!=null && !"".equals(childLevel)){
                    criteria.andIdEqualTo(Integer.parseInt(childLevel));
                    List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(example);
                    if(ObjectUtil.isEmpty(xnGoodsTypes))return MyResult.failure("没找到这个分类下的商品");
                    for (XnGoodsType xgt:xnGoodsTypes){
                        XnGoodsManageExample example1=new XnGoodsManageExample();
                        example1.createCriteria().andTypeidEqualTo(xgt.getId()).andStatusEqualTo("1");
                        List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(example1);
                        if (xnGoodsManages.size()!=0){
                            for (XnGoodsManage x:xnGoodsManages){
                                Map<String, Object> stringObjectMap = searchUtil.goodInfo(x);
                                list.add(stringObjectMap);
                            }
                        }
                    }
                }else {
                    criteria.andPidEqualTo(Integer.parseInt(level));
                    List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(example);
                    XnGoodsManageExample example2=new XnGoodsManageExample();
                    example2.createCriteria().andTypeidEqualTo(Integer.parseInt(level)).andStatusEqualTo("1");
                    List<XnGoodsManage> xnGoodsManages2 = xnGoodsManageMapper.selectByExample(example2);
                    if (xnGoodsManages2.size()!=0){
                        for (XnGoodsManage x:xnGoodsManages2){
                            Map<String, Object> stringObjectMap = searchUtil.goodInfo(x);
                            list.add(stringObjectMap);
                        }
                    }
                    for (XnGoodsType xgt:xnGoodsTypes){
                        XnGoodsManageExample example1=new XnGoodsManageExample();
                        example1.createCriteria().andTypeidEqualTo(xgt.getId()).andStatusEqualTo("1");
                        List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(example1);
                        if (xnGoodsManages.size()!=0){
                            for (XnGoodsManage x:xnGoodsManages){
                                Map<String, Object> stringObjectMap = searchUtil.goodInfo(x);
                                list.add(stringObjectMap);
                            }
                        }
                    }
                }
            }else {
                XnGoodsTypeExample example4=new XnGoodsTypeExample();
                XnGoodsTypeExample.Criteria criteria4 = example4.createCriteria();
                criteria4.andTypeEqualTo((byte) 1).andIsdisplayEqualTo((byte)1);
                example4.setOrderByClause("orders desc");
                List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(example4);
                for (XnGoodsType xt:xnGoodsTypes){
                    XnGoodsManageExample example1=new XnGoodsManageExample();
                    example1.createCriteria().andTypeidEqualTo(xt.getId()).andStatusEqualTo("1");
                    List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(example1);
                    if (xnGoodsManages.size()!=0){
                        for (XnGoodsManage x:xnGoodsManages){
                            Map<String, Object> stringObjectMap = searchUtil.goodInfo(x);
                            list.add(stringObjectMap);
                        }
                    }
                }
            }
        }
        if(mallType==2){
            XnGoodsTypeExample example=new XnGoodsTypeExample();
            XnGoodsTypeExample.Criteria criteria = example.createCriteria();
            criteria.andTypeEqualTo((byte)2).andIsdisplayEqualTo((byte)1);
            example.setOrderByClause("orders desc");
            criteria.andTypeEqualTo((byte)2).andIsdisplayEqualTo((byte)1).andPidNotEqualTo(0);
            if (!"null".equals(level) && level!=null && !"".equals(level)){
                if (!"null".equals(childLevel) && childLevel!=null && !"".equals(childLevel)){
                    criteria.andIdEqualTo(Integer.parseInt(childLevel));
                    List<XnGoodsType> xnGoodsTypes1 = xnGoodsTypeMapper.selectByExample(example);
                    if(ObjectUtil.isEmpty(xnGoodsTypes1))return MyResult.failure("没找到这个分类");
                    for (XnGoodsType xgt:xnGoodsTypes1){
                        XnGoodsManageExample example2=new XnGoodsManageExample();
                        example2.createCriteria().andTypeidEqualTo(xgt.getId()).andStatusEqualTo("1");
                        List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(example2);
                        if (xnGoodsManages.size()!=0){
                            for (XnGoodsManage x:xnGoodsManages){
                                Map<String, Object> stringObjectMap = searchUtil.goodInfo(x);
                                list.add(stringObjectMap);
                            }
                        }
                    }
                }else {
                    criteria.andPidEqualTo(Integer.parseInt(level));
                    List<XnGoodsType> xnGoodsTypes1 = xnGoodsTypeMapper.selectByExample(example);
                    //if(ObjectUtil.isEmpty(xnGoodsTypes1))return MyResult.failure("没找到这个分类");
                    XnGoodsManageExample example2=new XnGoodsManageExample();
                    example2.createCriteria().andTypeidEqualTo(Integer.parseInt(level)).andStatusEqualTo("1");
                    List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(example2);
                    if (xnGoodsManages.size()!=0){
                        for (XnGoodsManage x:xnGoodsManages){
                            Map<String, Object> stringObjectMap = searchUtil.goodInfo(x);
                            list.add(stringObjectMap);
                        }
                    }
                    for (XnGoodsType xgt:xnGoodsTypes1){
                        XnGoodsManageExample example3=new XnGoodsManageExample();
                        example3.createCriteria().andTypeidEqualTo(xgt.getId()).andStatusEqualTo("1");
                        List<XnGoodsManage> xnGoodsManages3 = xnGoodsManageMapper.selectByExample(example3);
                        if (xnGoodsManages3.size()!=0){
                            for (XnGoodsManage x:xnGoodsManages3){
                                Map<String, Object> stringObjectMap = searchUtil.goodInfo(x);
                                list.add(stringObjectMap);
                            }
                        }
                    }
                }
            }else {
                XnGoodsTypeExample example4=new XnGoodsTypeExample();
                XnGoodsTypeExample.Criteria criteria4 = example4.createCriteria();
                criteria4.andTypeEqualTo((byte) 2).andIsdisplayEqualTo((byte)1);
                example4.setOrderByClause("orders desc");
                List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(example4);
                for (XnGoodsType xt:xnGoodsTypes){
                    XnGoodsManageExample example1=new XnGoodsManageExample();
                    example1.createCriteria().andTypeidEqualTo(xt.getId()).andStatusEqualTo("1");
                    List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(example1);
                    if (xnGoodsManages.size()!=0){
                        for (XnGoodsManage x:xnGoodsManages){
                            Map<String, Object> stringObjectMap = searchUtil.goodInfo(x);
                            list.add(stringObjectMap);
                        }
                    }
                }
            }
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 选中商品详情
     * @param goodsId
     * @return
     */
    @Override
    public MyResult oneGoodsInfo(Integer goodsId) {
        XnGoodsManage xnGoodsManage = xnGoodsManageMapper.selectByPrimaryKey(goodsId);
        if (ObjectUtil.isEmpty(xnGoodsManage))return MyResult.failure("未找到该商品的详情");
        Map<String, Object> stringObjectMap = searchUtil.goodInfo(xnGoodsManage);
        return MyResult.success(stringObjectMap);
    }

    /**
     * 保存订单信息
     * @param goodsId
     * @param goodsName
     * @param number
     * @param price
     * @param totalPrice
     * @param type
     * @return
     */
    @Override
    public MyResult saveGoodsOrder(Integer goodsId,Integer addressId, String goodsName, Integer number, BigDecimal price, BigDecimal totalPrice, Byte type,Integer type1) {
        XnGoodsOrder order=new XnGoodsOrder();
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=0;
        if (type1==1){
            Integer childId = SystemParam.getChildId();
            HlStudentinfo studentinfo=searchUtil.Studentinfo(schoolId,childId);
            order.setUsername(studentinfo.getSname());
            userId=childId;
            XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(SystemParam.getUserId());
            order.setPhone(xnStuFamilyinfo.getPhone());
        }
        if (type1==2){
            Integer userId1 = SystemParam.getUserId();
            HlTeacherExample ht=new HlTeacherExample();
            ht.createCriteria().andIdEqualTo(userId1).andSchoolidEqualTo(schoolId);
            List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(ht);
            order.setUsername(hlTeachers.get(0).getTname());
            userId=userId1;
            order.setPhone(hlTeachers.get(0).getMobile());
        }
        order.setUid(userId);
        order.setGoodsid(goodsId);
        order.setGoodsname(goodsName);
        order.setNumber(number);
        String byUUId = WXUtil.getByUUId()+"a";
        order.setOrdernumber(byUUId);
        order.setPrice(price);
        order.setTotalprice(totalPrice);
        if (addressId!=null && !"".equals(addressId) && !"null".equals(addressId)){
            order.setAddressid(addressId);
        }
        order.setType(type);
        order.setOrderstate((byte) 1);
        order.setCreatetime(new Date());
        order.setAttr1(String.valueOf(type1));
        order.setAttr2(String.valueOf(schoolId));
        int i = xnGoodsOrderMapper.insertSelective(order);
        if (i==0){
            return MyResult.failure("保存订单信息失败");
        }
        XnGoodsOrderExample example=new XnGoodsOrderExample();
        example.createCriteria().andOrdernumberEqualTo(byUUId);
        List<XnGoodsOrder> xnGoodsOrders = xnGoodsOrderMapper.selectByExample(example);
        return searchByTrandNo(xnGoodsOrders.get(0).getId());
    }

    /**
     * 根据订单号查询订单
     * @param orderId
     * @return
     */
    @Override
    public MyResult searchByTrandNo(Integer orderId) {
        if (ObjectUtil.isEmpty(orderId))return MyResult.failure("请输入订单号");
        XnGoodsOrder order = xnGoodsOrderMapper.selectByPrimaryKey(orderId);
        if (ObjectUtil.isEmpty(order))return MyResult.failure("没找到订单信息");
        Map<String, Object> stringObjectMap = voUtil.orderInfo(order);
        stringObjectMap.put("product_sys",1);
        stringObjectMap.put("attach",stringObjectMap.get("goodsname"));
        stringObjectMap.put("money",stringObjectMap.get("totalprice"));
        return MyResult.success(stringObjectMap);
    }

    /**
     * 订单添加收货地址
     * @param orderId
     * @param addressId
     * @return
     */
    @Override
    public MyResult addAddressId(Integer orderId, Integer addressId) {
        XnGoodsOrder order = xnGoodsOrderMapper.selectByPrimaryKey(orderId);
        if (ObjectUtil.isEmpty(order))return MyResult.failure("没找到订单信息");
        order.setAddressid(addressId);
        XnAddress xnAddress = xnAddressMapper.selectByPrimaryKey(addressId);
        if (ObjectUtil.isEmpty(xnAddress))return MyResult.failure("没找到地址的具体信息");
        order.setPhone(xnAddress.getPhone());
        int i = xnGoodsOrderMapper.updateByPrimaryKeySelective(order);
        if (i==0){
            return MyResult.failure("添加收货地址失败");
        }
        return MyResult.success("添加收货地址成功",xnGoodsOrderMapper.selectByPrimaryKey(orderId));
    }


    /**
     * 查询我的订单
     * @param orderState
     * @param type
     * @return
     */
    @Override
    public MyResult searchMyOrder(Byte orderState, Integer type,Integer pageNo,Integer pageSize) {
        Integer userId=0;
        if (type==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        XnGoodsOrderExample example=new XnGoodsOrderExample();
        XnGoodsOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(userId).andOrderstateNotEqualTo((byte)4).andAttr1EqualTo(String.valueOf(type));
        if (orderState!=null){
            criteria.andOrderstateEqualTo(orderState);
        }
        example.setOrderByClause("ID desc");
        List<XnGoodsOrder> xnGoodsOrders = xnGoodsOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnGoodsOrders))return MyResult.failure("没有找到订单信息");
        List<Map> list=new ArrayList<>();
        for (XnGoodsOrder xgo:xnGoodsOrders){
            Map<String, Object> stringObjectMap = voUtil.orderInfo(xgo);
            stringObjectMap.put("product_sys",1);
            stringObjectMap.put("attach",stringObjectMap.get("goodsname"));
            list.add(stringObjectMap);
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @Override
    public MyResult deleteOrder(String orderId) {
        String[] str=orderId.split(",");
        int i=0;
        for (String s:str){
            int oid=Integer.valueOf(s);
            XnGoodsOrder order = xnGoodsOrderMapper.selectByPrimaryKey(oid);
            if (ObjectUtil.isEmpty(order))return MyResult.failure("未找到该订单");
            order.setOrderstate((byte)4);
            i = xnGoodsOrderMapper.updateByPrimaryKeySelective(order);
        }
        if (i==0){
            return MyResult.failure("删除失败");
        }
        return MyResult.success("删除成功");
    }
}
