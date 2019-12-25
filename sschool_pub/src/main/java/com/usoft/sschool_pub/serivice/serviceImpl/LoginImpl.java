package com.usoft.sschool_pub.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MD5;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.util.PayUtil.WXUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.LoginService;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import com.usoft.sschool_pub.util.TokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author wlw
 * @data 2019/4/22 0022-14:58
 */
@Service("LoginService")
public class LoginImpl implements LoginService {
    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;
    @Resource
    private XnFoodMapper xnFoodMapper;
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private CfDepartmentMapper cfDepartmentMapper;
    @Resource
    private CfUserMapper cfUserMapper;
    @Resource
    private HlSchoolsevipMapper hlSchoolsevipMapper;
    @Resource
    private VoUtil voUtil;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private XnSetmealManageMapper xnSetmealManageMapper;
    @Resource
    private XnSetmealOrderMapper xnSetmealOrderMapper;

    /**
     * 查询学校列表
     * @return
     */
    @Override
    public MyResult schoolList() {
        CfDepartmentExample example=new CfDepartmentExample();
        CfDepartmentExample.Criteria criteria = example.createCriteria();
        return MyResult.success(cfDepartmentMapper.selectByExample(example));
    }

    @Override
    public MyResult getPort(Integer schoolId, String ipPath) {
        HlSchoolsevipExample example=new HlSchoolsevipExample();
        example.createCriteria().andSidEqualTo(schoolId);
        List<HlSchoolsevip> hlSchoolsevips = hlSchoolsevipMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(hlSchoolsevips)){return MyResult.failure("没找到该学校的ip地址");};
        HlSchoolsevip svip=hlSchoolsevips.get(0);
        Map map=new HashMap();
        if (ipPath.equals(svip.getLansevip())){
            map.put("videoPath","rtmp://"+svip.getLansevip()+":"+svip.getLansevport()+"/live/");
        }else if (ipPath.equals(svip.getWansevip())){
            map.put("videoPath","rtmp://"+svip.getWansevip()+":"+svip.getWansevport()+"/live/");
        }else if (ipPath.equals(svip.getWansevipr())){
            map.put("videoPath","rtmp://"+svip.getWansevipr()+":"+svip.getInternetport()+"/live/");
        }else {
            return MyResult.failure("输入的地址不匹配");
        }
        map.put("ip",ipPath);
        return MyResult.success(map);
    }

    /**
     * 查询套餐信息
     * @param schoolId
     * @return
     */
    @Override
    public MyResult searchSetmeal(Integer schoolId) {
        XnSetmealManageExample example=new XnSetmealManageExample();
        example.createCriteria().andUidEqualTo(schoolId).andStatusEqualTo((byte)1);
        List<XnSetmealManage> xnSetmealManages = xnSetmealManageMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnSetmealManages))return MyResult.failure("没找到套餐信息");
        List<Map> list=new ArrayList<>();
        for (XnSetmealManage xsm:xnSetmealManages){
            Map map=new HashMap();
            map.put("id",xsm.getId());
            map.put("setMealName",xsm.getSetmealname());
            map.put("setMealPrice",xsm.getSetmealprice());
            map.put("beginDate", xsm.getBegindate());
            map.put("endDate",xsm.getEnddate());
            map.put("bindMan",xsm.getBindman());
            map.put("description",xsm.getDescription());
            map.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xsm.getCreatetime()));
            map.put("status",xsm.getStatus());
            map.put("UID",xsm.getUid());
            list.add(map);
        }
        return MyResult.success(list);
    }

    /**
     * 保存套餐订单
     * @param schoolId
     * @param childId
     * @param smid
     * @return
     */
    @Override
    public MyResult saveSetmealOrder(Integer schoolId, Integer childId, Integer smid) {
        Integer userId = SystemParam.getUserId();
        HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, childId);
        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(userId);
        if (!studentinfo.getPhone().equals(xnStuFamilyinfo.getPhone())){
            return MyResult.failure("你的账号不是主账号，不能购买");
        }
        //要购买的套餐
        XnSetmealManage xnSetmealManage1 = xnSetmealManageMapper.selectByPrimaryKey(smid);
        Integer bindNum=null;
        if (xnStuFamilyinfo.getOid()!=null){
            //已购买的套餐
            XnSetmealOrder xnSetmealOrder = xnSetmealOrderMapper.selectByPrimaryKey(xnStuFamilyinfo.getOid());
            XnSetmealManage xnSetmealManage2 = xnSetmealManageMapper.selectByPrimaryKey(xnSetmealOrder.getSetid());
            bindNum=xnSetmealManage2.getBindman();
            if (xnSetmealManage2.getBindman()>xnSetmealManage1.getBindman()){
                return MyResult.failure("套餐绑定的人数不能比已购买的套餐绑定人数少");
            }
            XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(smid);
            XnSetmealOrder xso=new XnSetmealOrder();
            xso.setSid(childId);
            xso.setStudentname(studentinfo.getSname());
            String byUUId = WXUtil.getByUUId()+"b";
            xso.setOrdernumber(byUUId);
            xso.setOrderstate((byte)1);
            xso.setOrderprice(xnSetmealManage.getSetmealprice());
            xso.setParentphone(studentinfo.getPhone());
            xso.setSetid(smid);
            xso.setSetmealname(xnSetmealManage.getSetmealname());
            xso.setCreatetime(new Date());
            int i = xnSetmealOrderMapper.insertSelective(xso);
            if (i!=1){
                return MyResult.failure("保存失败");
            }
            Map map=new HashMap();
            map.put("tradeNo",byUUId);
            map.put("smid",smid);
            map.put("product_sys",2);
            map.put("money",xnSetmealManage.getSetmealprice());
            map.put("attach",xnSetmealManage.getSetmealname());
            return MyResult.success(map);
        }
        /*XnSetmealOrderExample example=new XnSetmealOrderExample();
        example.createCriteria().andSidEqualTo(childId).andOrderstateEqualTo((byte)1).andParentphoneEqualTo(xnStuFamilyinfo.getPhone())
                .andSetidEqualTo(smid).andPaytypeNotEqualTo("2");
        List<XnSetmealOrder> xnSetmealOrders = xnSetmealOrderMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnSetmealOrders)){
            Map map=new HashMap();
            map.put("tradeNo",xnSetmealOrders.get(0).getOrdernumber());
            map.put("smid",smid);
            map.put("product_sys",2);
            map.put("money",xnSetmealOrders.get(0).getOrderprice());
            map.put("attach",xnSetmealOrders.get(0).getSetmealname());
            return MyResult.success(map);
        }*/
        XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(smid);
        XnSetmealOrder xso=new XnSetmealOrder();
        xso.setSid(childId);
        xso.setStudentname(studentinfo.getSname());
        String byUUId = WXUtil.getByUUId()+"b";
        xso.setOrdernumber(byUUId);
        xso.setOrderstate((byte)1);
        xso.setOrderprice(xnSetmealManage.getSetmealprice());
        xso.setParentphone(studentinfo.getPhone());
        xso.setSetid(smid);
        xso.setSetmealname(xnSetmealManage.getSetmealname());
        xso.setCreatetime(new Date());
        int i = xnSetmealOrderMapper.insertSelective(xso);
        if (i!=1){
            return MyResult.failure("保存失败");
        }
        Map map=new HashMap();
        map.put("tradeNo",byUUId);
        map.put("smid",smid);
        map.put("product_sys",2);
        map.put("money",xnSetmealManage.getSetmealprice());
        map.put("attach",xnSetmealManage.getSetmealname());
        return MyResult.success(map);
    }

    /**
     * 支付后改变订单状态为已支付
     * @param schoolId
     * @param userId
     * @param byuuid
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyResult changeOrderInfo(Integer schoolId, Integer userId, String byuuid) {
        //改变订单状态
        XnSetmealOrderExample example=new XnSetmealOrderExample();
        example.createCriteria().andOrdernumberEqualTo(byuuid);
        example.setOrderByClause("id desc");
        List<XnSetmealOrder> xnSetmealOrders = xnSetmealOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnSetmealOrders))return MyResult.failure("没找到订单信息");
        xnSetmealOrders.get(0).setBuytime(new Date());
        xnSetmealOrders.get(0).setPaytype("1");
        xnSetmealOrders.get(0).setOrderstate((byte)2);
        int i = xnSetmealOrderMapper.updateByPrimaryKeySelective(xnSetmealOrders.get(0));
        //用户表添加订单id
        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(userId);
        xnStuFamilyinfo.setOid(xnSetmealOrders.get(0).getId());
        int i1 = xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xnStuFamilyinfo);
        if (i!=1|| i1!=1){
            throw new RuntimeException("改变失败");
        }
        XnStuFamilyinfoExample example1=new XnStuFamilyinfoExample();
        example1.createCriteria().andStuidEqualTo(SystemParam.getChildId()).andPhoneNotEqualTo(xnStuFamilyinfo.getPhone());
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example1);
        XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(xnSetmealOrders.get(0).getSetid());
        if (!ObjectUtil.isEmpty(xnStuFamilyinfos)){
            for(XnStuFamilyinfo xs:xnStuFamilyinfos){
                try {
                    xs.setExpiretime(TimeUtil.YYYYMMDDToTime(xnSetmealManage.getEnddate()));
                    int i2 = xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xs);
                    if (i2!=1){
                        throw new RuntimeException("时间转换失败");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return MyResult.success("改变状态成功",xnSetmealOrderMapper.selectByExample(example));
    }

    /**
     * 查询我购买的套餐
     * @return
     */
    @Override
    public MyResult PaySetmail() {
        Integer userId = SystemParam.getUserId();
        Integer childId = SystemParam.getChildId();
        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(userId);
        //根据用户信息查询订单信息
        XnSetmealOrderExample example=new XnSetmealOrderExample();
        example.createCriteria().andSidEqualTo(childId).andOrderstateEqualTo((byte)2);
        example.setOrderByClause("buyTime desc");
        List<XnSetmealOrder> xnSetmealOrders = xnSetmealOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnSetmealOrders))return MyResult.failure("你没有购买套餐");
        XnSetmealOrder xnSetmealOrder = xnSetmealOrders.get(0);

        //根据订单中的套餐id查询套餐
        XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(xnSetmealOrder.getSetid());
        if (ObjectUtil.isEmpty(xnSetmealManage))return MyResult.failure("没找到套餐信息");
        //查询该套餐绑定了多少个手机号
        XnStuFamilyinfoExample example1=new XnStuFamilyinfoExample();
        example1.createCriteria().andStuidEqualTo(childId).andPhoneNotEqualTo(xnStuFamilyinfo.getPhone());
        List<XnStuFamilyinfo> xnStuFamilyinfos1 = xnStuFamilyinfoMapper.selectByExample(example1);
        Map map=new HashMap();
        if (ObjectUtil.isEmpty(xnStuFamilyinfos1)){
            map.put("hasbind",null);
            map.put("size",xnSetmealManage.getBindman());
            map.put("setmeal",xnSetmealManage);
        }else {
            map.put("hasbind",xnStuFamilyinfos1);
            map.put("size",xnSetmealManage.getBindman()-xnStuFamilyinfos1.size());
            map.put("setmeal",xnSetmealManage);
        }
        return MyResult.success(map);
    }

    /**
     * 绑定家长
     * @param schoolId
     * @param childId
     * @param familyRelate
     * @param phone
     * @return
     * @throws ParseException
     */
    @Override
    public MyResult bind(Integer schoolId, Integer childId, String familyRelate, String phone) {
        Integer userId = SystemParam.getUserId();
        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(userId);
        //查询套餐订单
        XnSetmealOrderExample example=new XnSetmealOrderExample();
        example.createCriteria().andSidEqualTo(childId).andOrderstateEqualTo((byte)2);
        example.setOrderByClause("buyTime desc");
        List<XnSetmealOrder> xnSetmealOrders = xnSetmealOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnSetmealOrders))return MyResult.failure("你没有购买套餐");
        XnSetmealOrder xnSetmealOrder = xnSetmealOrders.get(0);

        XnStuFamilyinfoExample example1=new XnStuFamilyinfoExample();
        example1.createCriteria().andPhoneEqualTo(phone).andStuidEqualTo(childId);
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example1);
        if (!ObjectUtil.isEmpty(xnStuFamilyinfos))return MyResult.failure("该账户已绑定了该学生");

        //根据订单id查询订单信息
        /*XnSetmealOrder xnSetmealOrder = xnSetmealOrderMapper.selectByPrimaryKey(xnStuFamilyinfo.getOid());*/
        if (ObjectUtil.isEmpty(xnSetmealOrder))return MyResult.failure("没有找到你的订单信息");
        //根据订单中的套餐id查询套餐
        XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(xnSetmealOrder.getSetid());
        if (ObjectUtil.isEmpty(xnSetmealManage))return MyResult.failure("没找到套餐信息");
        //XnSetmealManage xnSetmealManage = xnSetmealManageMapper.selectByPrimaryKey(smid);
        XnStuFamilyinfo xsf=new XnStuFamilyinfo();
        xsf.setFamilyrelate(familyRelate);
        xsf.setPhone(phone);
        String split = phone.substring(5);
        xsf.setPassword(MD5.EncoderByMd5(split));
        xsf.setStuid(childId);
        try {
            xsf.setExpiretime(TimeUtil.YYYYMMDDToTime(xnSetmealManage.getEnddate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        xsf.setCreatetime(new Date());
        int i = xnStuFamilyinfoMapper.insertSelective(xsf);
        if (i!=1){
            return MyResult.failure("保存失败");
        }
        return MyResult.success("保存成功",xnStuFamilyinfoMapper.selectByExample(example1));
    }

    /**
     * 教师登录
     * @param userName
     * @param pwd
     * @return
     */
    @Override
    public MyResult teacherLogin(String userName, String pwd) {
        if (userName==null || pwd==null){
            return MyResult.failure("请输入账号密码");
        }
        CfUserExample example=new CfUserExample();
        example.createCriteria().andLoginnameEqualTo(userName);
        List<CfUser> cfUsers = cfUserMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(cfUsers))return MyResult.failure("账号错误");
        if (!MD5.EncoderByMd5(pwd).equals(cfUsers.get(0).getLoginpassword())){
            return MyResult.failure("密码错误");
        }
        HlTeacherExample example1=new HlTeacherExample();
        example1.createCriteria().andTcodeEqualTo(userName);
        List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(hlTeachers))return MyResult.failure("教师编号对应的教师信息不存在");
        String token="";
        try {
            token= tokenUtil.createToken(hlTeachers.get(0).getId(),0,cfUsers.get(0).getDeptid(),0,2);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResult.failure("创建登录标识错误");
        }
        Map<String,Object> result = new HashMap<>();
        result.put("token",token);
        result.put("id",hlTeachers.get(0).getId());
        result.put("stuName",hlTeachers.get(0).getTname());
        result.put("userType",2);
        result.put("class","教师没有班级");
        result.put("phone",hlTeachers.get(0).getMobile());
        String str=hlTeachers.get(0).getImagesrc();
        JSON json= JSONObject.parseObject(str);
        Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
        if (map2.get("faceImg")==null){
            result.put("ImageSrc",null);
        }else {
            result.put("ImageSrc",map2.get("faceImg"));
        }

        return MyResult.success("登录成功",result);
    }

    /**
     * 登录
     * @param phone
     * @param pwd
     * @return
     */
    @Override
    public MyResult appLogin(String phone, String pwd) {
        if (ObjectUtil.isEmpty(phone))return MyResult.failure("请输入电话号码");
        if (ObjectUtil.isEmpty(pwd))return MyResult.failure("请输入密码");
        XnStuFamilyinfoExample example=new XnStuFamilyinfoExample();
        example.createCriteria().andPhoneEqualTo(phone).andPasswordEqualTo(MD5.EncoderByMd5(pwd));
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuFamilyinfos))return MyResult.failure("账号或密码错误");
        //查询孩子信息
        List<Map> list=new ArrayList<>();
        for (XnStuFamilyinfo xsf:xnStuFamilyinfos){
            HlStudentinfoExample example1=new HlStudentinfoExample();
            example1.createCriteria().andIdEqualTo(xsf.getStuid());
            List<HlStudentinfo> hlStudentinfos = hlStudentinfoMapper.selectByExample(example1);
            Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte)1,hlStudentinfos.get(0).getId(),hlStudentinfos.get(0).getSchoolid());
            objectObjectMap.put("phone",phone);
            list.add(objectObjectMap);
        }
        return MyResult.success("登录成功",list);
    }

    /**
     * 修改密码
     * @param phone
     * @param pwd
     * @param newpwd
     * @return
     */
    @Override
    public MyResult changePwd(String phone, String pwd, String newpwd) {
        XnStuFamilyinfoExample example=new XnStuFamilyinfoExample();
        example.createCriteria().andPasswordEqualTo(MD5.EncoderByMd5(pwd)).andPhoneEqualTo(phone);
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
        if (xnStuFamilyinfos.size()==0){
            return MyResult.failure("账号或密码错误");
        }
        for (int i=0;i<xnStuFamilyinfos.size();i++){
            XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfos.get(i);
            xnStuFamilyinfo.setPassword(MD5.EncoderByMd5(newpwd));
            int u = xnStuFamilyinfoMapper.updateByPrimaryKeySelective(xnStuFamilyinfo);
        }
        XnStuFamilyinfoExample example1=new XnStuFamilyinfoExample();
        example1.createCriteria().andPhoneEqualTo(phone);
        List<XnStuFamilyinfo> xnStuFamilyinfos1 = xnStuFamilyinfoMapper.selectByExample(example1);
        return MyResult.success(xnStuFamilyinfos1);
    }

    /**
     * 选择几号孩子
     * @param stuId
     * @return
     */
    @Override
    public MyResult checkStu(Integer stuId,String phone) {
        HlStudentinfoExample example=new HlStudentinfoExample();
        example.createCriteria().andIdEqualTo(stuId);
        List<HlStudentinfo> hlStudentinfos = hlStudentinfoMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(hlStudentinfos))return MyResult.failure("没有找到孩子的信息");
        Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte)1,hlStudentinfos.get(0).getId(),hlStudentinfos.get(0).getSchoolid());
        XnStuFamilyinfoExample example1=new XnStuFamilyinfoExample();
        example1.createCriteria().andStuidEqualTo(stuId).andPhoneEqualTo(phone);
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(xnStuFamilyinfos))return MyResult.failure("选择孩子错误，数据库没有数据");
        String token="";
        try {
            token= tokenUtil.createToken(xnStuFamilyinfos.get(0).getId(),0,hlStudentinfos.get(0).getSchoolid(),stuId,1);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResult.failure("创建登录标识错误");
        }
        objectObjectMap.put("phone",phone);
        Map<String,Object> result = new HashMap<>();
        result.put("token",token);
        result.put("userType",1);
        result.put("childerMsg",objectObjectMap);
        return MyResult.success(result);
    }

    /**
     * 查询学生信息
     * @return
     */
    @Override
    public MyResult stuInfo(String phone) {
        XnStuFamilyinfoExample example=new XnStuFamilyinfoExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<XnStuFamilyinfo> xnStuFamilyinfos = xnStuFamilyinfoMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuFamilyinfos))return MyResult.failure("没找到孩子id");
        List<Map> list1=new ArrayList<>();
        for (XnStuFamilyinfo xsf:xnStuFamilyinfos){
            HlStudentinfoExample example2=new HlStudentinfoExample();
            example2.createCriteria().andIdEqualTo(xsf.getStuid());
            List<HlStudentinfo> hlStudentinfos = hlStudentinfoMapper.selectByExample(example2);
            Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte)1,hlStudentinfos.get(0).getId(),hlStudentinfos.get(0).getSchoolid());
            if (hlStudentinfos.get(0).getPhone().equals(phone)){
                objectObjectMap.put("ismain",1);
            }else {
                objectObjectMap.put("ismain",2);
            }
            list1.add(objectObjectMap);
        }
        return MyResult.success(list1);

    }


    /**
     * 菜谱信息
     * @return
     */
    @Override
    public MyResult forFood(Integer schoolId) {
        XnFoodExample example=new XnFoodExample();
        example.createCriteria().andSidEqualTo(schoolId);
        List<XnFood> xnFoods = xnFoodMapper.selectByExample(example);
        if (xnFoods.size()==0){
            return MyResult.failure("数据库没有数据");
        }
        List<Map> list=new ArrayList<>();
        for (XnFood xf:xnFoods){
            Map map=new HashMap();
            if (xf.getWeek().equals(1)){
                map.put("week","星期一");
                map.put("foodTime",xf.getFoodtime());
                map.put("foodName",xf.getFoodname());
            }
            if (xf.getWeek().equals(2)){
                map.put("week","星期二");
                map.put("foodTime",xf.getFoodtime());
                map.put("foodName",xf.getFoodname());
            }
            if (xf.getWeek().equals(3)){
                map.put("week","星期三");
                map.put("foodTime",xf.getFoodtime());
                map.put("foodName",xf.getFoodname());
            }
            if (xf.getWeek().equals(4)){
                map.put("week","星期四");
                map.put("foodTime",xf.getFoodtime());
                map.put("foodName",xf.getFoodname());
            }
            if (xf.getWeek().equals(5)){
                map.put("week","星期五");
                map.put("foodTime",xf.getFoodtime());
                map.put("foodName",xf.getFoodname());
            }
            if (xf.getWeek().equals(6)){
                map.put("week","星期六");
                map.put("foodTime",xf.getFoodtime());
                map.put("foodName",xf.getFoodname());
            }
            if (xf.getWeek().equals(0)){
                map.put("week","星期日");
                map.put("foodTime",xf.getFoodtime());
                map.put("foodName",xf.getFoodname());
            }
            list.add(map);
        }
        return MyResult.success(list);
    }

    /**
     * web菜谱
     * @return
     */
    @Override
    public MyResult webFood() {
        Integer schoolId = SystemParam.getSchoolId();
        List map=new ArrayList();
        for (byte i=1;i<4;i++){
            XnFoodExample example=new XnFoodExample();
            example.createCriteria().andSidEqualTo(schoolId).andFoodtimeEqualTo(i);
            example.setOrderByClause("week");
            List<XnFood> xnFoods = xnFoodMapper.selectByExample(example);
            map.add(xnFoods);
        }
        if (map.size()==0){
            return MyResult.failure("没找到菜谱信息");
        }
        return MyResult.success(map);

    }
}
