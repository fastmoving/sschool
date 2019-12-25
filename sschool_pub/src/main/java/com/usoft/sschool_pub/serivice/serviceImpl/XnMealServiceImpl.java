package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.HlTeacherMapper;
import com.usoft.sschool_pub.mapper.XnMailOrderMapper;
import com.usoft.sschool_pub.mapper.XnMealMapper;
import com.usoft.sschool_pub.mapper.XnStuFamilyinfoMapper;
import com.usoft.sschool_pub.serivice.XnMealService;
import com.usoft.sschool_pub.util.PayUtil.WXUtil;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @author wlw
 * @file    套餐管理
 * @create 2019-09-05 17:37
 */
@Service("XnMealService")
public class XnMealServiceImpl implements XnMealService {
    @Resource
    private XnMealMapper xnMealMapper;
    @Resource
    private XnMailOrderMapper xnMailOrderMapper;
    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;

    /**
     * 查询套餐列表
     * @return
     */
    @Override
    public MyResult mealList(Integer timeType) {

        Integer schoolId = SystemParam.getSchoolId();
        XnMealExample example=new XnMealExample();
        XnMealExample.Criteria criteria = example.createCriteria();
        if(SystemParam.getType()==2){
        criteria.andAttr1EqualTo("2");
        }else {
            criteria.andAttr1EqualTo("1");
        }
        criteria.andSidEqualTo(schoolId);
        criteria.andStatusEqualTo(1);
        criteria.andTimetypeEqualTo(timeType);
        List<XnMeal> xnMeals = xnMealMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnMeals)){
            return MyResult.failure("没有套餐信息");
        }
        return MyResult.success(xnMeals);
    }

    /**
     * 保存套餐订单
     * @param mailId
     * @return
     */
    @Override
    public MyResult saveMealOrder(Integer mailId, Integer timeType, Integer number, BigDecimal price) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=null;
        Integer userType = SystemParam.getType();
        String phone=null;
        if (userType==1){
            userId=SystemParam.getUserId();
            XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(SystemParam.getUserId());
            phone= xnStuFamilyinfo.getPhone();
        }else {
            userId=SystemParam.getUserId();
            HlTeacher ht=new HlTeacher();
            ht.setSchoolid(schoolId);
            ht.setId(userId);
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            phone= hlTeacher.getMobile();
        }
        //套餐信息
        XnMeal xnMeal = xnMealMapper.selectByPrimaryKey(mailId);
        if (ObjectUtil.isEmpty(xnMeal)){
            return MyResult.failure("没找到套餐信息");
        }
        XnMailOrder xmo=new XnMailOrder();
        xmo.setSid(schoolId);
        xmo.setUserid(userId);
        xmo.setUserType(userType);
        xmo.setMailId(mailId);
        xmo.setPayType(1);
        xmo.setTimeType(timeType);
        xmo.setNumber(number);
        String byUUId = WXUtil.getByUUId()+"b";
        xmo.setTradeno(byUUId);
        xmo.setCreatetime(new Date());
        xmo.setOrderStatus(1);
        xmo.setPhone(phone);
        xmo.setPrice(price);
        xmo.setSetmealname(xnMeal.getMealname());


        int i = xnMailOrderMapper.insertSelective(xmo);
        if (i!=1){
            return MyResult.failure("保存失败");
        }
        Map map=new HashMap();
        map.put("tradeNo",byUUId);
        map.put("smid",mailId);
        map.put("product_sys",2);
        map.put("money",price);
        map.put("attach",xnMeal.getMealname());
        return MyResult.success(map);
    }

    /**
     * 查询学生绑定的其他家长
     * @return
     */
    @Override
    public MyResult phoneList() {
        Integer userId=SystemParam.getChildId();
        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(SystemParam.getUserId());
        XnStuFamilyinfoExample example=new XnStuFamilyinfoExample();
        example.createCriteria().andStuidEqualTo(userId).andPhoneNotEqualTo(xnStuFamilyinfo.getPhone());
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuFamilyinfos)){
            return MyResult.failure("该学生还未绑定其他家长，不能添加套餐");
        }
        return MyResult.success(xnStuFamilyinfos);
    }

    /**
     * 查询我的套餐
     * @return
     */
    @Override
    public MyResult myMail() {
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=null;
        Integer userType = SystemParam.getType();
        Integer orderId=null;
        XnMailOrder xnMailOrder=null;
        Integer hasband=null;
        Map map=new HashMap();
        if (userType==1){
            userId=SystemParam.getUserId();
            XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(userId);
            if (ObjectUtil.isEmpty(xnStuFamilyinfo.getOid())){
                return MyResult.failure("您还没有购买过视频套餐");
            }
            orderId=xnStuFamilyinfo.getOid();
            xnMailOrder= xnMailOrderMapper.selectByPrimaryKey(xnStuFamilyinfo.getOid());
            XnStuFamilyinfoExample example=new XnStuFamilyinfoExample();
            example.createCriteria().andStuidEqualTo(SystemParam.getChildId()).andOidEqualTo(orderId);
            List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
            hasband=xnStuFamilyinfos.size();
            map.put("bindList",xnStuFamilyinfos);
        }else {
            userId=SystemParam.getUserId();
            XnMailOrderExample example=new XnMailOrderExample();
            example.createCriteria().andUserTypeEqualTo(2).andUseridEqualTo(userId).andSidEqualTo(schoolId)
            .andOrderStatusEqualTo(2);
            example.setOrderByClause("pay_time desc");
            List<XnMailOrder> xnMailOrders = xnMailOrderMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnMailOrders)){
                return MyResult.failure("您还没有购买过视频套餐");
            }
            xnMailOrder=xnMailOrders.get(0);
            hasband=1;
        }
        XnMeal xnMeal = xnMealMapper.selectByPrimaryKey(xnMailOrder.getMailId());
        map.put("hasBind",xnMeal.getBindman()-hasband);
        map.put("mealName",xnMeal.getMealname());
        map.put("mealId",xnMeal.getId());
        map.put("mealDescription",xnMeal.getDescription());
        map.put("bindMan",xnMeal.getBindman());
        map.put("timeType",xnMailOrder.getTimeType());
        map.put("number",xnMailOrder.getNumber());
        map.put("payTime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnMailOrder.getPayTime()));
        map.put("price",xnMailOrder.getPrice());
        Integer month=null;
        if (xnMailOrder.getTimeType()==1){
            month=xnMailOrder.getNumber()*12;
        }else {
            month=xnMailOrder.getNumber();
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(xnMailOrder.getPayTime());
        calendar.add(Calendar.MONTH,+month);
        map.put("expireTime", TimeUtil.TimeToYYYYMMDDHHMMSS(calendar.getTime()));
        return MyResult.success(map);
    }

    /**
     * 绑定手机号
     * @param phone
     * @return
     */
    @Override
    public MyResult bindPhone(String phone) {
        Integer userId=SystemParam.getChildId();
        XnStuFamilyinfoExample example=new XnStuFamilyinfoExample();
        example.createCriteria().andStuidEqualTo(userId).andPhoneEqualTo(String.valueOf(phone));
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuFamilyinfos)){
            return MyResult.failure("该电话未绑定当前登录学生，请联系老师");
        }
        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(SystemParam.getUserId());
        if (ObjectUtil.isEmpty(xnStuFamilyinfo.getOid())){
            return MyResult.failure("您还没有购买过视频套餐");
        }
        XnMailOrder xnMailOrder = xnMailOrderMapper.selectByPrimaryKey(xnStuFamilyinfo.getOid());
        xnStuFamilyinfos.get(0).setOid(xnStuFamilyinfo.getOid());
        try {
            xnStuFamilyinfos.get(0).setExpiretime(TimeUtil.YYYYMMDDHHMMSSToTime(xnMailOrder.getAttr1()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int i = xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xnStuFamilyinfos.get(0));
        if (i!=1){
            return MyResult.failure("添加失败");
        }
        return MyResult.success(myMail().getData());
    }

}
