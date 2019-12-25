package com.usoft.sschool_manage.service.setmeal.Impl;

import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MD5;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.NumberKit;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.enums.CommonEnums;
import com.usoft.sschool_manage.enums.PayTimeEnums;
import com.usoft.sschool_manage.mapper.base.*;
import com.usoft.sschool_manage.service.setmeal.XnSetmealBindService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import com.usoft.sschool_manage.util.personal.OrderNumberUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/5/16 9:57
 */
@Service
public class XnSetmealBindServiceImpl implements XnSetmealBindService {


    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;

    @Resource
    private HlSchclassMapper hlSchclassMapper;

    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;

    @Resource
    private XnSetmealOrderMapper xnSetmealOrderMapper;

    @Resource
    private XnSetmealManageMapper xnSetmealManageMapper;

    @Resource
    private XnMailOrderMapper xnMailOrderMapper;

    @Resource
    private XnMealMapper xnMealMapper;

    /**
     * 已支付
     */
    private static final byte BUYED =2 ;

    /**
     * 购买方式 线上
     */
    private static final String ONLINE = "1";

    /**
     * 购买方式 线下
     */
    private static final int UNLINE = 2;


    @Override
    public MyResult listXnSeatmealBinding(Integer classId, Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        if(ObjectUtil.isEmpty(classId)){
            return MyResult.failure("请选择查看的班级");
        }

        //查看班级是否存在
        HlSchclassKey hlSchclassKey = new HlSchclassKey();
        hlSchclassKey.setSchoolid(schoolId);
        hlSchclassKey.setId(classId);
        HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hlSchclassKey);
        if(ObjectUtil.isEmpty(hlSchclass)){
            return MyResult.failure("当前班级不存在");
        }


        HlStudentinfoExample hlStudentinfoExample = new HlStudentinfoExample();
        HlStudentinfoExample.Criteria criteria = hlStudentinfoExample.createCriteria();
        criteria.andSchoolidEqualTo(schoolId);
        criteria.andClassidEqualTo(classId);
        List<HlStudentinfo> hlStudentinfos = hlStudentinfoMapper.selectByExample(hlStudentinfoExample);
        if(ObjectUtil.isEmpty(hlStudentinfos)){
            return MyResult.failure("该班级暂无学生信息");
        }

        //获取当前时间信息
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //返回数据
        List<Map<String,Object>> result = new ArrayList<>();

        //遍历学生
        for(HlStudentinfo hlStudentinfo : hlStudentinfos){
            Map<String,Object> map = new HashMap<>();//封装数据
            map.put("studentId",hlStudentinfo.getId());
            map.put("studentName",hlStudentinfo.getSname());
            map.put("price","无");
            map.put("setmealId",null);
            map.put("setmeal","无");
            map.put("defaultphone",hlStudentinfo.getPhone());
            map.put("binding",new String[]{});

            //查询关联账号
            XnStuFamilyinfoExample example = new XnStuFamilyinfoExample();
            XnStuFamilyinfoExample.Criteria criteria2 = example.createCriteria();
            criteria2.andStuidEqualTo(hlStudentinfo.getId());
            List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
            if(xnStuFamilyinfos.get(0).getOid()!=null){
                XnMailOrder xnMailOrder = xnMailOrderMapper.selectByPrimaryKey(xnStuFamilyinfos.get(0).getOid());
                map.put("price",xnMailOrder.getPrice());
                map.put("setmealId",xnMailOrder.getMailId());
                map.put("setmeal",xnMailOrder.getSetmealname());
            }
            List<Map<String,Object>> binding = new ArrayList<>();
            for (XnStuFamilyinfo stuFamilyinfo:xnStuFamilyinfos){
                if(stuFamilyinfo.getOid()!=null){
                    Map<String,Object> bind = new HashMap<>();
                    bind.put("id",stuFamilyinfo.getId());
                    bind.put("phone",stuFamilyinfo.getPhone());
                    bind.put("relation",stuFamilyinfo.getFamilyrelate());
                    binding.add(bind);
                    map.put("binding",binding);
                }
            }

//            XnSetmealOrderExample xnSetmealOrderExample = new XnSetmealOrderExample();
//            XnSetmealOrderExample.Criteria criteria1 = xnSetmealOrderExample.createCriteria();
//            criteria1.andSidEqualTo(hlStudentinfo.getId());
//            criteria1.andOrderstateEqualTo(BUYED);
//            xnSetmealOrderExample.setOrderByClause(" createTime desc ");
//            List<XnSetmealOrder> xnSetmealOrders = xnSetmealOrderMapper.selectByExample(xnSetmealOrderExample);
//
//            if(!ObjectUtil.isEmpty(xnSetmealOrders)){ //如果买了
//                XnSetmealOrder xnSetmealOrder = xnSetmealOrders.get(0);
//                Integer setId = xnSetmealOrder.getSetid();
//                //查询当前套餐过期时间
//                XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(setId);
//                try {
//                    if(ObjectUtil.isEmpty(xnSetmealManage)){
//                        continue;
//                    }else{
//                        String endDate = xnSetmealManage.getEnddate();
//                        if( sdf.parse(endDate).getTime()>= date.getTime()){//如果没有过期，那么就查询绑定信息
//                            map.put("price",xnSetmealOrder.getOrderprice());
//                            map.put("setmealId",xnSetmealManage.getId());
//                            map.put("setmeal",xnSetmealManage.getSetmealname());
//                            XnStuFamilyinfoExample xnStuFamilyinfoExample = new XnStuFamilyinfoExample();
//                            XnStuFamilyinfoExample.Criteria criteria2 = xnStuFamilyinfoExample.createCriteria();
//                            criteria2.andStuidEqualTo(xnSetmealOrder.getSid());
//                            criteria2.andExpiretimeGreaterThan(date);
//                            List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(xnStuFamilyinfoExample);
//
//                            List<Map<String,Object>> binding = new ArrayList<>();
//                            if(!ObjectUtil.isEmpty(xnStuFamilyinfos)){
//                                for(XnStuFamilyinfo xnStuFamilyinfo : xnStuFamilyinfos){
//                                    Map<String,Object> bind = new HashMap<>();
//                                    bind.put("id",xnStuFamilyinfo.getId());
//                                    bind.put("phone",xnStuFamilyinfo.getPhone());
//                                    bind.put("relation",xnStuFamilyinfo.getFamilyrelate());
//                                    if(date.getTime()<xnStuFamilyinfo.getExpiretime().getTime()){
//                                        binding.add(bind);
//                                    }
//                                }
//                                map.put("binding",binding);
//                            }
//                        }
//                    }
//
//                }catch (ParseException e){
//                    e.printStackTrace();
//                    return MyResult.failure("时间格式有误");
//                }
//
//            }
            result.add(map);
        }
        return ResultPage.getPageResult(result,pageNo,pageSize);
    }

    @Override
    @Transactional
    public MyResult bindingPerson(Integer setId, Integer stuId, List<Map<String,Object>> p) {
        if(ObjectUtil.isEmpty(setId)){return MyResult.failure("请选择套餐");}
        if(ObjectUtil.isEmpty(stuId)){return MyResult.failure("请选择学生");}
        //查看学生id的合法性
        Integer schoolId = SystemParam.getSchoolId();
        HlStudentinfoKey hlStudentinfoKey = new HlStudentinfoKey();
        hlStudentinfoKey.setId(stuId);
        hlStudentinfoKey.setSchoolid(schoolId);
        HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(hlStudentinfoKey);
        if(ObjectUtil.isEmpty(hlStudentinfo) || !schoolId.equals(hlStudentinfo.getSchoolid())){
            return MyResult.failure("当前学生信息不存在");
        }

        //查看套餐id的合法性
        XnMeal xnMeal = xnMealMapper.selectByPrimaryKey(setId);
        if(ObjectUtil.isEmpty(xnMeal)){
            return MyResult.failure("当前套餐信息不存在");
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try{
//            if(sdf.parse(xnSetmealManage.getEnddate()).getTime()<System.currentTimeMillis()){
//                return MyResult.failure("该套餐已经过期，无法购买");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return MyResult.failure("套餐时间不合法");
//        }

        //查询当前套餐可绑定人数
        int binds = xnMeal.getBindman();
        if(p!=null && p.size()>binds){
            return MyResult.failure("该套餐最多绑定"+(binds)+"个用户");
        }

        //查询学生绑定主账号
        XnStuFamilyinfoExample example =  new XnStuFamilyinfoExample();
        XnStuFamilyinfoExample.Criteria criteria = example.createCriteria();
        criteria.andStuidEqualTo(hlStudentinfo.getId());
        criteria.andPhoneEqualTo(hlStudentinfo.getPhone());
        List<XnStuFamilyinfo> stuFamilyinfo = xnStuFamilyinfoMapper.selectByExample(example);
        if(ObjectUtil.isEmpty(stuFamilyinfo)){
            return MyResult.failure("当前学生没有主账号");
        }

        try{
            //插入订单表
            XnMailOrder xnMailOrder = new XnMailOrder();
            xnMailOrder.setUserid(stuFamilyinfo.get(0).getId());
            xnMailOrder.setUserType(CommonEnums.USER_TYPE_STUDENT);
            xnMailOrder.setSid(schoolId);
            xnMailOrder.setMailId(xnMeal.getId());
            xnMailOrder.setPayType(UNLINE);
            xnMailOrder.setTimeType(xnMeal.getTimetype());
            xnMailOrder.setNumber(CommonEnums.PAY_NUMBER);
            xnMailOrder.setTradeno(OrderNumberUtil.getSetMealOrderNumber(setId,stuId));
            xnMailOrder.setPayTime(new Date());
            xnMailOrder.setCreatetime(new Date());
            xnMailOrder.setOrderStatus((int)BUYED);
            xnMailOrder.setPhone(stuFamilyinfo.get(0).getPhone());
            xnMailOrder.setPrice(xnMeal.getPrice());
            xnMailOrder.setSetmealname(xnMeal.getMealname());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, PayTimeEnums.getTimeType(xnMeal.getTimetype()));
            xnMailOrder.setAttr1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
            xnMailOrderMapper.insert(xnMailOrder);

            //添加套餐关系表
            for(Map<String,Object> m : p){
//                String phone = (String) m.get("phone");
                Integer userid = Integer.valueOf(m.get("userid").toString());
                XnStuFamilyinfo stuFamilyinfo1 = new XnStuFamilyinfo();
                stuFamilyinfo1.setOid(xnMailOrder.getId());
                stuFamilyinfo1.setId(userid);
                xnStuFamilyinfoMapper.updateByPrimaryKeySelective(stuFamilyinfo1);
//                xnMailOrder.setUserid(userid);
//                xnMailOrder.setPhone(phone);
//                if(!NumberKit.isPhone(phone)){
//                    return MyResult.failure("请输入正确的是手机号码");
//                }
            }


        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MyResult.failure("套餐时间格式有误");
        }

        return MyResult.success("绑定成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyResult editPerson(String bindPer) {
        List<Map<String,Object>> person = JSONObject.parseObject(bindPer,List.class);
        try{
            for(Map<String,Object> m : person){
                //Map<String,Object> map = m;
                Integer id = (Integer)m.get("id");
                Integer stuId = (Integer)m.get("STUID");
                if(ObjectUtil.isEmpty(id)){
                    return MyResult.failure("id必传");
                }
                String phone = (String) m.get("phone");
                if(phone == null || !NumberKit.isPhone(phone)){
                    return MyResult.failure("号码格式不正确");
                }
                XnStuFamilyinfoExample example=new XnStuFamilyinfoExample();
                example.createCriteria().andStuidEqualTo(stuId).andExpiretimeGreaterThan(new Date());
                List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
                for (XnStuFamilyinfo xsf:xnStuFamilyinfos){
                    if (phone.equals(xsf.getPhone())){
                        return MyResult.failure(xsf.getPhone()+"该电话已绑定，不能再次绑定");
                    }
                }
                String relation = (String) m.get("relation");
                if(ObjectUtil.isEmpty(relation)){
                    return MyResult.failure("关系必填");
                }
                XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(id);
                if(ObjectUtil.isEmpty(xnStuFamilyinfo)){
                    return MyResult.failure("未找到当前数据");
                }
                xnStuFamilyinfo.setPhone(phone);
                xnStuFamilyinfo.setFamilyrelate(relation);
                xnStuFamilyinfo.setPassword(MD5.EncoderByMd5(phone.substring(5)));
                xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xnStuFamilyinfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MyResult.failure("数据出现错误");
        }
        return MyResult.success("修改成功");
    }


    @Override
    public MyResult selectPerson(Integer stuId){
        XnStuFamilyinfoExample example = new XnStuFamilyinfoExample();
        XnStuFamilyinfoExample.Criteria criteria = example.createCriteria();
        criteria.andStuidEqualTo(stuId);
        List<XnStuFamilyinfo> familyinfos = xnStuFamilyinfoMapper.selectByExample(example);
        return MyResult.success(familyinfos);
    }
}
