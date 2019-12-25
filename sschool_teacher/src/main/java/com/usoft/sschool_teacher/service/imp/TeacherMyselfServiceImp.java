package com.usoft.sschool_teacher.service.imp;

import com.usoft.smartschool.pojo.*;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.enums.*;
import com.usoft.sschool_teacher.exception.CustomException;
import com.usoft.sschool_teacher.exception.MyException;
import com.usoft.sschool_teacher.mapper.*;
import com.usoft.sschool_teacher.service.ITeacherMyselfService;
import com.usoft.sschool_teacher.util.ConstantsDate;
import com.usoft.sschool_teacher.util.HttpContenUtil;
import net.sf.json.JSONObject;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 陈秋
 * @Date: 2019/5/16 11:43
 * @Version 1.0
 */
@Service
public class TeacherMyselfServiceImp implements ITeacherMyselfService {

    @Resource
    private HlTeacherMapper teacherMapper;

    @Resource
    private XnAddressMapper addressMapper;

    @Resource
    private XnAbsentManageMapper absentManageMapper;

    @Resource
    private XnKindnessSchoolMapper kindnessSchoolMapper;

    @Resource
    private XnGoodsOrderMapper orderMapper;

    @Resource
    private HlCurriculumMapper curriculumMapper;

    @Resource
    private XnFoodMapper foodMapper;

    @Resource
    private XnVideoOrderMapper videoOrderMapper;

    @Resource
    private XnResourceRelationMapper resourceRelationMapper;

    @Resource
    private XnSetmealOrderMapper setmealOrderMapper;

    @Resource
    private XnTeacherIntegralMapper teacherIntegralMapper;

    @Resource
    private XnMailOrderMapper mailOrderMapper;
    /**
     * 教师个人信息
     * @param teacherId
     * @return
     */
    @Override
    public List<Map> getMyselfIfo(int teacherId) {
        List<Map> teacherIfo = teacherMapper.getTeacherIfo(teacherId);
        if (teacherIfo.size()==0){
            return null;
        }
        Integer integral = teacherIntegralMapper.getTeacherIntegral(teacherId);
        List<Map> listData = new ArrayList<>();
        for (Map map:teacherIfo) {
            if (map == null){
                return null;
            }
            if ( map.get("Sex")!=null && !"".equals(map.get("Sex"))){
                map.put("sex", CommonEnums.getMessage(Integer.parseInt(map.get("Sex").toString())));
            }
            if (map.get("Ttype")!=null && !"".equals(map.get("Ttype"))){
                map.put("role",CommonEnums.getMessage(Integer.parseInt(map.get("Ttype").toString())));
            }
            if (map.containsKey("ImageSrc")&&map.get("ImageSrc").toString().contains("idImg")) {
                Map data = JSONObject.fromObject(map.get("ImageSrc"));
                if (!data.containsKey("faceImg")) {
                    map.put("faceImg", "");
                } else {
                    map.put("faceImg",data.get("idImg"));
                }
                if (!data.containsKey("idImg")) {
                    map.put("idImg", "");
                } else {
                    map.put("idImg", data.get("faceImg"));
                }
            }else {
                map.put("faceImg", "");
                map.put("idImg", "");
            }
            map.put("sum_integral",integral==null?0:integral);
            listData.add(map);
        }
        return listData;
    }

    /**
     * 修改个人照片
     * @param teacherId
     * @param faceImg
     * @param idImg
     * @return
     */
    @Override
    @Transactional
    public int updateMyself(int teacherId,String faceImg,String idImg,String code){
        HlTeacher teacher = new HlTeacher();
        teacher.setId(teacherId);
        teacher.setSchoolid(SystemParam.getSchoolId());
        List<Map> teacherIfo = teacherMapper.getTeacherIfo(teacherId);
        Map map = new HashMap();
        if(teacherIfo.get(0).containsKey("ImageSrc") && teacherIfo.get(0).get("ImageSrc").toString().contains("{")){
            map = JSONObject.fromObject(teacherIfo.get(0).get("ImageSrc"));
        }else{
            map = null;
        }
        if(map == null){
            map.put("faceImg",faceImg);
            map.put("idImg",idImg);
            //teacher.setImagesrc(com.alibaba.fastjson.JSONObject.toJSONString(map));
        }else{
            String idImgLocal = (String)map.get("idImg");
            String faceImgLocal = (String)map.get("faceImg");
            //Map<String,Object> m = new HashMap<>();
            if(faceImg!=null && !"".equals(faceImg)){
                map.put("faceImg",faceImg);
            }
            if(idImg!=null && !"".equals(idImg)){
                map.put("idImg",idImg);
            }

        }
        teacher.setImagesrc(com.alibaba.fastjson.JSONObject.toJSONString(map));
//        if (code!=null && Integer.parseInt(code)==ConstantsDate.HEAR_PHOTO){
//            if(map == null){
//                map.put("faceImg",faceImg);
//                map.put("idImg","");
//                teacher.setImagesrc(com.alibaba.fastjson.JSONObject.toJSONString(map));
//            }else{
//                String idImgLocal = (String)map.get("idImg");
//                Map<String,Object> m = new HashMap<>();
//                m.put("faceImg",faceImg);
//                m.put("idImg",idImgLocal);
//                teacher.setImagesrc(com.alibaba.fastjson.JSONObject.toJSONString(m));
//            }
//        } else if (code!=null && Integer.parseInt(code)==ConstantsDate.HEAR_PHOTOS){
//            if(idImg != null && !"".equals(idImg)) {
//                String[] imgs = idImg.split(",");
//                if(imgs.length>3)return -3;
//            }
//            if (!map.isEmpty())
//                teacher.setImagesrc(JSONObject.fromObject("{\"faceImg\":\"" + map.get("faceImg") + "\",\"idImg\":\"" + idImg+"\"}").toString());
//            else teacher.setImagesrc(JSONObject.fromObject("{\"faceImg\":\"" + "" + "\",\"idImg\":\"" + idImg+"\"}").toString());
//        } else if (code!=null && Integer.parseInt(code)==ConstantsDate.HEAR_APP){
//            teacher.setImagesrc(JSONObject.fromObject("{\"faceImg\":\"" + idImg + "\",\"idImg\":\"" + faceImg+"\"}").toString());
//        }
        int i = teacherMapper.updateByPrimaryKeySelective(teacher);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -1;
        }
        return i;
    }

    /**
     * 添加订单地址
     * @param teacherId 教师id
     * @param linkMan 联系人名字
     * @param phone 电话
     * @param isDefault 是否默认 1，是；2，否
     * @param province 省
     * @param city 市
     * @param dist 区
     * @param address 详细地址
     * @return
     */
    @Override
    @Transactional
    public int insertAddress(int teacherId, String linkMan, String phone, String isDefault,
                             String province, String city, String dist, String address,String cityCode) {
        XnAddress adrs = new XnAddress();
        if (isDefault==null || "".equals(isDefault) || "2".equals(isDefault)){
            adrs.setIsdefault(ConstantsDate.ADDRESS_NOIS);
        }else{
            List<XnAddress> isdefultAddress = addressMapper.getIsDefault(teacherId);
            int i = 0;
            if (isdefultAddress.size()!=0){
                for (XnAddress address1:isdefultAddress){
                    address1.setIsdefault(ConstantsDate.ADDRESS_NOIS);
                    i = addressMapper.updateByPrimaryKeySelective(address1);
                    break;
                }
                /*isdefultAddress.get(0).setIsdefault(ConstantsDate.ADDRESS_NOIS);
                int i = addressMapper.updateByPrimaryKeySelective(isdefultAddress.get(0));*/
                try{
                    CustomException.customeIf(i);
                }catch (MyException e){
                    return -2;
                }
            }
            adrs.setIsdefault(ConstantsDate.ADDRESS_IS);
        }
        adrs.setType(ConstantsDate.TEACHER);
        adrs.setUserid(teacherId);
        adrs.setAddress(address);
        adrs.setCreatetime(new Date());
        adrs.setUsername(linkMan);
        adrs.setPhone(phone);
        adrs.setProvince(province);
        adrs.setCity(city);
        adrs.setDist(dist);
        adrs.setAttr1(cityCode);
        int j = addressMapper.insertSelective(adrs);
        try{
            CustomException.customeIf(j);
        }catch (MyException e){
            return -2;
        }
        return j;
    }

    /**
     * 管理收货地址
     * @param teacherId 教师ID
     * @return
     */
    @Override
    public List<Map> getAddress(String addressId,int teacherId,int page,int start) {
        Map<String,Object> key = new HashMap<>();
        key.put("teacherId",teacherId);
        key.put("addressId","".equals(addressId) || addressId == null?null:Integer.parseInt(addressId.trim()));
        key.put("page",page);
        key.put("start",(start-1)*page);
        List<XnAddress> addresses = addressMapper.getAddresses(key);
        if (addresses.size()==0){
            return null;
        }
        List<Map> data = new ArrayList<>();
        for(XnAddress address : addresses){
            Map mapData = new HashMap();
            mapData.put("isDefault",CommonEnums.getMessage(address.getIsdefault()));
            mapData.put("address",address.getAddress());
            mapData.put("cityCode",address.getAttr1());
            mapData.put("city",address.getCity());
            mapData.put("dist",address.getDist());
            mapData.put("phone",address.getPhone());
            mapData.put("province",address.getProvince());
            mapData.put("linkMan",address.getUsername());
            mapData.put("id",address.getId());
            mapData.put("userId",address.getUserid());
            data.add(mapData);
        }
        return data;
    }

    @Override
    public int getAddressesCount(int teacherId) {
        Map<String,Object> key = new HashMap<>();
        key.put("teacherId",teacherId);
        return addressMapper.getAddressesCount(key);
    }

    /**
     * 删除地址
     * @param addressIds 地址ID
     * @return
     */
    @Override
    @Transactional
    public int deleteAddress(String[] addressIds) {
        int i = addressMapper.removeAddress(addressIds);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -1;
        }
        return i;
    }

    /**
     * 编辑地址
     * @param addressId 地址ID
     * @param teacherId 教师id
     * @param linkMan 联系人名字
     * @param phone 电话
     * @param isDefault 是否默认 1，是；2，否
     * @param province 省
     * @param city 市
     * @param dist 区
     * @param address 详细地址
     * @return
     */
    @Override
    @Transactional
    public int updateAddress(int addressId, int teacherId, String linkMan,
                             String phone, String isDefault, String province, String city,
                             String dist, String address, String cityCode) {
        XnAddress upAddress = new XnAddress();
        upAddress.setId(addressId);
        upAddress.setType(ConstantsDate.TEACHER);
        upAddress.setUserid(teacherId);
        upAddress.setAddress(address);
        upAddress.setUsername(linkMan);
        if ("1".equals(isDefault)){
            List<XnAddress> isdefultAddress = addressMapper.getIsDefault(teacherId);
            if (isdefultAddress.size()!=0){
                isdefultAddress.get(0).setIsdefault(ConstantsDate.ADDRESS_NOIS);
                int i = addressMapper.updateByPrimaryKeySelective(isdefultAddress.get(0));
                try {
                    CustomException.customeIf(i);
                }catch (Exception e){
                    return -2;
                }
            }
            upAddress.setIsdefault(ConstantsDate.ADDRESS_IS);
        }else if ("2".equals(isDefault)){
            upAddress.setIsdefault(ConstantsDate.ADDRESS_NOIS);
        }
        upAddress.setPhone(phone);
        upAddress.setProvince(province);
        upAddress.setCity(city);
        upAddress.setDist(dist);
        upAddress.setAttr1(cityCode);
        int i = addressMapper.updateByPrimaryKeySelective(upAddress);
        try{
            CustomException.customeIf(i);
        }catch (MyException e){
            return -1;
        }
        return i;
    }

    /**
     * 查询默认地址
     * @return
     */
    public XnAddress getDefaultAddress(){
        if (addressMapper.getIsDefault(SystemParam.getUserId()).size()==0)return null;
        return addressMapper.getIsDefault(SystemParam.getUserId()).get(0);
    }

    /**
     * 查找领导
     * @return
     */
    @Override
    public Map getSchoolManager(){
        List<HlTeacher> school_manager= teacherMapper.getSchoolManager(SystemParam.getSchoolId());
        if (school_manager.isEmpty())return null;
        Map data = new HashMap();
        Map res = JSONObject.fromObject(school_manager.get(0).getImagesrc());
        if (res.isEmpty())data.put("faceImg","");
        else data.put("faceImg",res.get("idImg"));
        data.put("name",school_manager.get(0).getTname());
        return data;
    }

    /**
     * 验证当前身份
     * @return
     */
    @Override
    public List getMyselfManager(){
        Map key = new HashMap();
        key.put("teacherId",SystemParam.getUserId());
        key.put("schoolId",SystemParam.getSchoolId());
        return teacherMapper.getMyselfManager(key);
    }

    /**
     * 添加教师请假
     * @param teacherId 教师ID
     * @param type 请假类型
     * @param begin 开始时间
     * @param end 结束时间
     * @param times 请假时间
     * @param thing 请假类型
     * @return
     */
    @Override
    public int insertAbsent(int teacherId,int type,String begin,String end,String times,
                            String thing){
        XnAbsentManage absentManage = new XnAbsentManage();
        try{
            absentManage.setBegindate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(begin));
            absentManage.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end));
        }catch (Exception e){
            return -1;
        }
        absentManage.setStatus(ConstantsDate.ABSENT_WAIT);
        absentManage.setAttr1(thing);
        absentManage.setAttr2(times);
        absentManage.setType(ConstantsDate.ABSENT_TEACHER);
        HlTeacherKey key = new HlTeacherKey();
        key.setId(teacherId);
        HlTeacher teacher = teacherMapper.selectByPrimaryKey(key);
        absentManage.setUserid(teacherId);
        absentManage.setSchoolid(teacher.getSchoolid());
        absentManage.setUsername(teacher.getTname());
        absentManage.setAbsenttype(type);
        int i = absentManageMapper.insertSelective(absentManage);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -2;
        }
        return i;
    }

    /**
     * 我的订单（购物车）
     * @return
     */
    @Override
    public List<Map> getMyOrder(Integer start,Integer page){
        Integer teacherId = SystemParam.getUserId();
        Integer schoolId = SystemParam.getSchoolId();
        Map key = new HashMap();
        key.put("teacherId",teacherId);
        key.put("schoolId",schoolId);
        key.put("start",(start-1)*page);
        key.put("page",page);
        /*key.put("teacherId",18);
        key.put("schoolId",null);*/
        List<Map> data = orderMapper.getMyOrder(key);
        if (data.size()==0){
            return null;
        }
        List dataMap = new ArrayList();
        for (Map map:data){
            map.put("create_time",map.get("createTime").toString().substring(0,16));
            if (map.containsKey("buyTime") && map.get("buyTime")!=null && !"".equals(map.get("buyTime"))){
                map.put("buy_time",map.get("buyTime").toString().substring(0,16));
            }else{
                map.put("buy_time","");
            }
            map.put("status", OrderEnums.getMessage(Integer.parseInt(map.get("orderState").toString())));
            map.put("phone",map.containsKey("phone")?map.get("phone"):"");
            dataMap.add(map);
        }
        return dataMap;
    }

    @Override
    public Integer getMyOrderCount(){
        Map key = new HashMap();
        key.put("teacherId",SystemParam.getUserId());
        key.put("schoolId",SystemParam.getSchoolId());
        return orderMapper.getMyOrderCount(key);
    }

    /**
     * 订单详情
     * @return
     */
    @Override
    public Map getOrderIfo(int orderId){
        int thId = SystemParam.getUserId();
        Map key = new HashMap();
        key.put("teacherId",thId);
        key.put("id",orderId);
        String address = new String();
        List<Map> data = orderMapper.getOrderIfo(key);
        if (data.size()==0){
            return null;
        }
        for (Map map : data){
            if (!map.containsKey("link_phone") || map.get("link_phone")==null || "".equals(map.get("link_phone"))){
                map.put("link_phone",map.get("phone"));
            }
            if (!map.containsKey("link_user_name") || map.get("link_user_name")==null || "".equals(map.get("link_user_name"))){
                map.put("link_user_name",map.get("userName"));
            }
            map.put("create_time",map.get("createTime").toString().substring(0,16));
            map.put("status", OrderEnums.getMessage(Integer.parseInt(map.get("orderState").toString())));
            map.put("buy_type",map.containsKey("buy_type") && map.get("buy_type")!=null?BuyEnum.getMessage(Integer.parseInt(map.get("buy_type").toString())):"");
            if (map.containsKey("buyTime") && map.get("buyTime")!=null && !"".equals(map.get("buyTime"))){
                map.put("buy_time",map.get("buyTime").toString().substring(0,16));
            }else{
                map.put("buy_time","");
            }
            if (!map.containsKey("province"))address = address+" ";
            else address = address + map.get("province");
            if (!map.containsKey("city"))address = address+" ";
            else address = address + map.get("city");
            if (!map.containsKey("dist"))address = address+" ";
            else address = address + map.get("dist");
            if (!map.containsKey("address"))address = address+" ";
            else address = address + map.get("address");
            map.put("address",address);
            return map;
        }
        return  null;
    }
    /**
     * 我的考勤
     * @param teacherId 教师ID
     * @param times 月份
     * @return
     */
    @Override
    public Map getMyTimeBook(int teacherId,String times){
        //String url = "http://api.goseek.cn/Tools/holiday?date=";
        //封装查询条件
        //年月日
        String month = new String();//请求月
        String year = new String();
        String month_ = new String();
        String times_ = "";
        Integer time_num = 0;
        Map data = new HashMap();
        Map key = new HashMap();
        if(times!=null && !"".equals(times)) {
            if (times.compareTo(new SimpleDateFormat("yyyy-MM").format(new Date())) > 0) {
                data.put("times", "");
                data.put("time_num", "");
                data.put("time_rest", "");
                data.put("_num", "");
                return data;
            }
        }
        if (times==null || "".equals(times)){
            key.put("times",new SimpleDateFormat("yyyy-MM").format(new Date()));
            month = new SimpleDateFormat("yyyyMM").format(new Date());
            month_ = new SimpleDateFormat("MM").format(new Date());
        }else {
            key.put("times",times);
            month = times.replace("-","");
            month_ = times.substring(5,7);
        }
        key.put("teacherId",teacherId);
        key.put("status",2);
        List<XnAbsentManage> list = absentManageMapper.getMyTimeBook(key);
        /*if (list.size()==0){
            return null;
        }*/
        //休息天数

        Set<String> data_set = new HashSet<>();
        Integer maxDate = 0;
        if (list.size()!=0) {
            year = new SimpleDateFormat("yyyy").format(list.get(0).getBegindate());
        }else{
            year = new SimpleDateFormat("yyyy").format(new Date());
        }
            Map data_map = JSONObject.fromObject(HttpContenUtil.getURLGetContent("http://www.easybots.cn/api/holiday.php?m=" + month));
            Map set_map = JSONObject.fromObject(data_map.get(month));
            data_set = set_map.keySet();
            //当月天数
            Calendar a = Calendar.getInstance();
            a.set(Calendar.YEAR, Integer.parseInt(year));
            a.set(Calendar.MONTH, Integer.parseInt(month_) - 1);
            a.set(Calendar.DATE, 1);//把日期设置为当月第一天
            a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
            maxDate = a.get(Calendar.DATE);
        Set<Date> set_time = new HashSet<>();
        for (XnAbsentManage xnAbsentManage:list){
            List<Date> date = findDates(xnAbsentManage.getBegindate(),xnAbsentManage.getEnddate());
            /*if (date.size()==0){
                return null;
            }*/
            //time_num += date.size();
            for (Date dataList:date) {
                String if_time = new String();
                if(times == null && "".equals(times)) if_time = new SimpleDateFormat("yyyy-MM").format(new Date());
                else if_time = times;
                if (if_time.equals(new SimpleDateFormat("yyyy-MM").format(dataList))) set_time.add(dataList);
                //times_ = times_ + new SimpleDateFormat("yyyy-MM-dd").format(dataList) + ",";
                //time_num++;
            }
        }
        //请假时间or天数
        for (Date date_:set_time) {
            String time = new SimpleDateFormat("dd").format(date_);
            times_ = times_ + time + ",";
            time_num++;
                /*int f = HttpContenUtil.getURLGetContent(url+time).getInt("data");
                if(f == ConstantsDate.WEEK_DAY || f == ConstantsDate.WEEK_DAY_1) continue;*/
            for (String s : data_set) {
                if (s.equals(time)) {
                    time_num--;
                    times_ = times_.substring(0, times_.length() - 3);
                    break;
                }
            }
        }
        data.put("times",times_.length()>0?times_.substring(ConstantsDate.begin_1,times_.length()-1):"");
        data.put("time_num",time_num);
        data.put("times_1",set_time);
        data.put("time_rest",data_set.size());
        //data.put("set_zhi",data_set);
        data.put("num",maxDate-time_num-data_set.size());
        return data;
    }

    // JAVA获取某段时间内的所有日期
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 教师请假信息
     * @param page 每页显示条数
     * @param start 当前页
     * @return
     */
    @Override
    public List getTeacherAbsent(Integer page,Integer start,String status){
        Map key = new HashMap();
        key.put("page",page);
        key.put("start",(start-1)*page);
        key.put("schoolId",SystemParam.getSchoolId());
        if (status != null && !"".equals(status))
            key.put("status",Integer.parseInt(status.trim()));
        List<XnAbsentManage> absentManages = absentManageMapper.getTeacherAbsent(key);
        return getXnAbsentManager(absentManages);
    }

    public List<Map> getXnAbsentManager(List<XnAbsentManage> xnAbsentManage){
        List<Map> res_data = Lists.newArrayList();
        xnAbsentManage.stream()
                .forEach(absentManage->{
                    Map data = new HashMap();
                    data.put("id",absentManage.getId());
                    data.put("begin",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(absentManage.getBegindate()));
                    data.put("end",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(absentManage.getEnddate()));
                    data.put("thing",absentManage.getAttr1());
                    data.put("name",absentManage.getUsername());
                    data.put("status", AbsentEnums.getMessage(absentManage.getStatus()));
                    data.put("times",absentManage.getAttr2());
                    data.put("type",CommonEnums.getMessage(absentManage.getAbsenttype()));
                    data.put("class_name","教师");
                    res_data.add(data);
                });
        return res_data;
    }

    @Override
    public Integer getTeacherAbsentCount(){
        return absentManageMapper.getTeacherAbsentCount(SystemParam.getSchoolId());
    }

    /**
     * 我的爱心
     * @param teacherId 教师ID
     * @return
     */
    @Override
    public List getMyKindness(int teacherId,String page,String start,int goodsStatus){
        Map key = new HashMap();
        if (page==null || "".equals(page) || start==null || "".equals(start)){
            key.put("page",10);
            key.put("start",1);
        }else {
            key.put("page",Integer.parseInt(page.trim()));
            key.put("start",(Integer.parseInt(start.trim())-1)*Integer.parseInt(page.trim()));
        }
        key.put("teacherId",teacherId);
        key.put("goodsStatus",goodsStatus);
        List<Map> listData = kindnessSchoolMapper.getMyKindness(key);
        if (listData.size()==0){
            return null;
        }
        List resData = new ArrayList();
        for (Map kindess:listData){
            kindess.put("create_time",kindess.get("createTime").toString().substring(0,16));
            kindess.put("car_id","教师");
            resData.add(kindess);
        }
        return resData;
    }

    /**
     * 变更我的爱心状态
     * @param kindnessId
     * @return
     */
    @Override
    @Transactional
    public int updateKindness(int kindnessId){
        XnKindnessSchool key = new XnKindnessSchool();
        key.setId(kindnessId);
        key.setGoodsstatus(ConstantsDate.KINDNESS_POST);
        int i = kindnessSchoolMapper.updateByPrimaryKeySelective(key);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -1;
        }
        return i;
    }

    /**
     * 我的课程
     * @return
     */
    @Override
    public List getCurriculum() throws Exception{
        int teacherId = SystemParam.getUserId();
        Map key = new HashMap();
        key.put("teacherId",teacherId);
        List<Map> data =curriculumMapper.getClassCurriculum(key);
        if (data.size()==0){
            return null;
        }
        List resData = new ArrayList();
      /*  for (Map map:data){
            map.put("week", WeekEnums.getMessage(Integer.parseInt(map.get("week").toString())));
        }*/
        return data;
    }
    @Override
    public List getWebCurriculum(Integer classId) throws Exception{
        int teacherId = SystemParam.getUserId();
        Map key = new HashMap();
        key.put("teacherId",teacherId);
        key.put("classId",classId);
        List<Map> data =curriculumMapper.getClassCurriculum(key);
        if (data.size()==0){
            return null;
        }
        List week1 = new ArrayList();
        List week2 = new ArrayList();
        List week3 = new ArrayList();
        List week4 = new ArrayList();
        List week5 = new ArrayList();
        List week6 = new ArrayList();
        List week0 = new ArrayList();
        List res = Lists.newArrayList();
        for (Map map:data){
            int data_rum = Integer.parseInt(map.get("week").toString());
            if (data_rum ==0){
                map.put("week", WeekEnums.getMessage(data_rum));
                week0.add(map);
            }
            else if (data_rum ==1){
                map.put("week", WeekEnums.getMessage(data_rum));
                week1.add(map);
            }
            else if (data_rum ==2){
                map.put("week", WeekEnums.getMessage(data_rum));
                week2.add(map);
            }
            else if (data_rum ==3){
                map.put("week", WeekEnums.getMessage(data_rum));
                week3.add(map);
            }
            else if (data_rum ==4){
                map.put("week", WeekEnums.getMessage(data_rum));
                week4.add(map);
            }
            else if (data_rum ==5){
                map.put("week", WeekEnums.getMessage(data_rum));
                week5.add(map);
            }
            else if (data_rum ==6){
                map.put("week", WeekEnums.getMessage(data_rum));
                week6.add(map);
            }
        }
        getLesson(week0);getLesson(week1);getLesson(week2);getLesson(week3);
        getLesson(week4);getLesson(week5);getLesson(week6);
        res.add(week1);res.add(week2);res.add(week3);res.add(week4);
        res.add(week5);res.add(week6);res.add(week0);
        return res;
    }

    public void getLesson(List<Map> week){
        Map lesson_1 = new HashMap();
        Map lesson_2 = new HashMap();
        Map lesson_3 = new HashMap();
        Map lesson_4 = new HashMap();
        Map lesson_5 = new HashMap();
        Map lesson_6 = new HashMap();
        Map lesson_7 = new HashMap();
        Map lesson_8 = new HashMap();
//        Map lesson_9 = new HashMap();
//        Map lesson_10 = new HashMap();
//        Map lesson_11 = new HashMap();
//        Map lesson_12 = new HashMap();
//        Map lesson_13 = new HashMap();
//        Map lesson_14 = new HashMap();
        if (week.isEmpty())return ;
        for (Map map:week){
            if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_1) lesson_1 = map;
            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_2)lesson_2 = map;
            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_3)lesson_3 = map;
            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_4)lesson_4 = map;
            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_5)lesson_5 = map;
            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_6)lesson_6 = map;
            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_7)lesson_7 = map;
            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_8)lesson_8 = map;
//            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_9)lesson_9 = map;
//            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_10)lesson_10 = map;
//            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_11)lesson_11 = map;
//            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_12)lesson_12 = map;
//            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_13)lesson_13 = map;
//            else if (Integer.parseInt(map.get("Lesson").toString())==ConstantsDate.LESSON_14)lesson_14 = map;
        }
        week.clear();
        week.add(lesson_1);week.add(lesson_2);week.add(lesson_3);week.add(lesson_4);week.add(lesson_5);week.add(lesson_6);
        week.add(lesson_7);week.add(lesson_8);
//        week.add(lesson_9);week.add(lesson_10);week.add(lesson_11);week.add(lesson_12);
//        week.add(lesson_13);week.add(lesson_14);
    }

    /**
     * 我的菜单
     * @return
     */
    @Override
    public List getMenu(){
        Integer schoolId = SystemParam.getSchoolId();
        List<XnFood> data = foodMapper.getMenus(schoolId);
        if (data.size()==0){
            return null;
        }
        List resData = new ArrayList();
        List<XnFood> food_morning = Lists.newArrayList();
        List<XnFood> food_afternoon = Lists.newArrayList();
        List<XnFood> food_evening = Lists.newArrayList();
        for (XnFood food:data){
            if (food.getFoodtime() == ConstantsDate.MORNING)
                food_morning.add(food);
            else if (food.getFoodtime() == ConstantsDate.AFTERNOON)
                food_afternoon.add(food);
            else if (food.getFoodtime() == ConstantsDate.EVENING)
                food_evening.add(food);
        }
        Map food_m = getTime(food_morning);
        Map food_a = getTime(food_afternoon);
        Map food_e = getTime(food_evening);
        Map data_map = new HashMap();
        data_map.put("morning",food_m);
        data_map.put("afternoon",food_a);
        data_map.put("evening",food_e);
        resData.add(data_map);
        return resData;
    }
    @Override
    public List<Map> getAppMenu(){
        Integer schoolId = SystemParam.getSchoolId();
        List<XnFood> data = foodMapper.getMenus(schoolId);
        if (data.size()==0){
            return null;
        }
        List res_data = new ArrayList();
        data.stream()
                .forEach(c->{
                    Map map = new HashMap();
                    map.put("food_time", MenuEnum.getMessage(c.getFoodtime()));
                    map.put("week_1",WeekEnums.getMessage(c.getWeek()));
                    map.put("create_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getCreatetime()));
                    map.put("week",c.getWeek());
                    map.put("ID",c.getId());
                    map.put("foodName",c.getFoodname());
                    map.put("foodTime",c.getFoodtime());
                    res_data.add(map);
                });
        return res_data;
    }

    public Map getTime(List<XnFood> food){
        if (food.isEmpty()){
            return null;
        }
        String sunday = "";//星期天
        String monday = "";//星期一
        String tuesday = "";//星期二
        String wednesday = "";//星期三
        String thursday = "";//星期四
        String friday = "";//星期五
        String saturday = "";//星期六
        for (XnFood food_:food){
            if (food_.getWeek()==ConstantsDate.SUNDAY)
                sunday = sunday + food_.getFoodname() + ",";
            else if (food_.getWeek()==ConstantsDate.MONDAY)
                monday = monday + food_.getFoodname()+",";
            else if (food_.getWeek()==ConstantsDate.TUESDAY)
                tuesday = tuesday + food_.getFoodname() + ",";
            else if (food_.getWeek()==ConstantsDate.WEDNESDAY)
                wednesday = wednesday + food_.getFoodname() +",";
            else if (food_.getWeek()==ConstantsDate.THURSDAY)
                thursday = thursday + food_.getFoodname() + ",";
            else if (food_.getWeek()==ConstantsDate.FRIDAY)
                friday = friday + food_.getFoodname() + ",";
            else if (food_.getWeek()==ConstantsDate.SATURDAY)
                saturday = saturday +food_.getFoodname() + ",";
        }
        Map data = new HashMap();
        data.put("monday",monday.substring(0,monday.length()>0?(monday.length()-1):0));
        data.put("tuesday",tuesday.substring(0,tuesday.length()>0?(tuesday.length()-1):0));
        data.put("wednesday",wednesday.substring(0,wednesday.length()>0?(wednesday.length()-1):0));
        data.put("thursday",thursday.substring(0,thursday.length()>0?thursday.length()-1:0));
        data.put("friday",friday.substring(0,friday.length()>0?friday.length()-1:0));
        data.put("saturday",saturday.substring(0,saturday.length()>0?saturday.length()-1:0));
        data.put("sunday",sunday.substring(0,sunday.length()>0?sunday.length()-1:0));
        return data;
    }

    /**
     * 已购课程
     * @return
     */
    @Override
    public List getVideo(int page,int start){
        Map key = new HashMap();
        key.put("page",page);
        key.put("start",(start-1)*page);
        key.put("teacherId",SystemParam.getUserId());
        List resData = videoOrderMapper.getTeacherVideo(key);
        if (resData.size()==0){
            return null;
        }
        List data = new ArrayList();
        for (int i=0;i<resData.size();i++){
            Map map = (Map) resData.get(i);
            map.put("time",map.containsKey("buyTime")?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get("buyTime")):"");
            map.put("type",map.containsKey("buyType")?OrderTypeEnum.getMessage(Integer.parseInt(map.get("buyType").toString())):"");
            data.add(map);
        }
        return data;
    }

    @Override
    public int getTeacherVideoCount(){
        return videoOrderMapper.getTeacherVideoCount(SystemParam.getUserId());
    }

    /**
     * 班主任 管理班级
     */
    @Override
    public List<Map> getClassManage(){
        int teacherId = SystemParam.getUserId();
        return  resourceRelationMapper.getClassManage(teacherId);
    }

    @Override
    public List getClassManage(Integer classId){
        Map key = new HashMap();
        key.put("teacherId",SystemParam.getUserId());
        key.put("classId",classId);
        return resourceRelationMapper.getClassManage2(key);
    }

    /**
     * 订阅套餐
     * @param classId 班级ID
     * @return
     */
    @Override
    public List<Map> getOrderClassIfo(Integer classId,Integer start,Integer page){
        Map key = getKey(classId);
        if (key==null){
            return null;
        }
        key.put("start",start);
        key.put("page",page);
        List data = new ArrayList();
        List<Map> resData = mailOrderMapper.listXnSetmealOrder(key);
        if (resData.size()==0){
            return null;
        }
        for (Map map:resData){
            if (map!=null && map.get("orderState")!=null && !"".equals(map.get("orderState"))) {
                map.put("order_state", OrderClassStateEnums.getMessage(Integer.parseInt(map.get("orderState").toString())));
            }
            data.add(map);
        }
        return data;
    }

    @Override
    public Integer listXnSetmealOrderCount(Integer classId){
        Map key = getKey(classId);
        return mailOrderMapper.listXnSetmealOrderCount(key);
    }

    public Map getKey(Integer classId){
        Map key = new HashMap();
        int teacherId = SystemParam.getUserId();
        List<Map> class_id = resourceRelationMapper.getClassManage(teacherId);
        if (class_id.size()==0){
            return null;
        }
        if (classId == null ){
            key.put("classId",classId);
            key.put("schoolId",SystemParam.getSchoolId());
            key.put("condition",1);
            List keyList = new ArrayList();
            for (Map map:class_id){
                keyList.add(Integer.parseInt(map.get("ID").toString()));
            }
            key.put("list",keyList);
        }else {
            key.put("classId",classId);
            key.put("schoolId",SystemParam.getSchoolId());
        }
        return key;
    }
}
