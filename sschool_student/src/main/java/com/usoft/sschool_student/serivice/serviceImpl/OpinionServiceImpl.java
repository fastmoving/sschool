package com.usoft.sschool_student.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_student.mapper.XnAbsentManageMapper;
import com.usoft.sschool_student.mapper.XnResourceRelationMapper;
import com.usoft.sschool_student.serivice.OpinionService;
import com.usoft.sschool_student.util.SystemParam;
import com.usoft.sschool_student.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("OpinionService")
public class OpinionServiceImpl implements OpinionService {
    @Resource
    private XnAbsentManageMapper xnAbsentManageMapper;
    @Resource
    private VoUtil voUtil;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;

    /**
     * 查询班主任信息
     *
     * @param schoolId
     * @param childId
     * @return
     */
    @Override
    public MyResult searchTeacher(Integer schoolId, Integer childId) {
        HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, childId);
        XnResourceRelationExample example1 = new XnResourceRelationExample();
        example1.createCriteria().andResourceatableEqualTo("hl_teacher").andResourcebtableEqualTo("hl_schclass")
                .andResourcebidEqualTo(studentinfo.getClassid());
        List<XnResourceRelation> xnResourceRelations = xnResourceRelationMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(xnResourceRelations)) return MyResult.failure("没找到班主任信息");
        Integer teaId = xnResourceRelations.get(0).getResourceaid();
        Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte) 2, teaId, schoolId);
        objectObjectMap.put("subject", "班主任");
        objectObjectMap.put("class", studentinfo.getClassid());
        return MyResult.success(objectObjectMap);
    }

    /**
     * 我要请假
     *
     * @param schoolId
     * @param childId
     * @param absentType
     * @param beginDate
     * @param endDate
     * @param attr2
     * @param attr1
     * @return
     */
    @Override
    public MyResult leave(Integer schoolId, Integer childId, Integer absentType, String beginDate, String endDate, String attr2, String attr1) {
        if (absentType == null || "".equals(absentType) || "null".equals(absentType)) {
            return MyResult.failure("请选择请假类型");
        }
        HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, childId);
        XnAbsentManage xam = new XnAbsentManage();
        xam.setType((byte) 1);
        xam.setClassid(studentinfo.getClassid());
        xam.setSchoolid(schoolId);
        xam.setUserid(childId);
        xam.setUsername(studentinfo.getSname());
        xam.setAbsenttype(absentType);
        try {
            xam.setBegindate(TimeUtil.YYYYMMDDToTime(beginDate));
            xam.setEnddate(TimeUtil.YYYYMMDDToTime(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        xam.setStatus((byte) 1);
        xam.setAttr2(attr2);
        xam.setAttr1(attr1);
        int i = xnAbsentManageMapper.insertSelective(xam);
        if (i != 1) {
            return MyResult.failure("发送失败");
        }
        return MyResult.success("发送成功，待审核");
    }

    /**
     * 查看我的请假信息
     *
     * @param schoolId
     * @param childId
     * @return
     */
    @Override
    public MyResult searchMyLeave(Integer schoolId, Integer childId) {
        HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, childId);
        XnAbsentManageExample example = new XnAbsentManageExample();
        example.createCriteria().andTypeEqualTo((byte) 1).andClassidEqualTo(studentinfo.getClassid())
                .andSchoolidEqualTo(schoolId).andUseridEqualTo(childId).andStatusEqualTo((byte) 2);
        example.setOrderByClause("id desc");
        List<XnAbsentManage> xnAbsentManages = xnAbsentManageMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnAbsentManages)) return MyResult.failure("没找到请假信息");
        List<Map> list = new ArrayList<>();
        for (XnAbsentManage xam : xnAbsentManages) {
            Map map = new HashMap();
            map.put("userName", xam.getUsername());
            map.put("uid", xam.getUserid());
            map.put("id", xam.getId());
            String absentType = null;
            if (xam.getAbsenttype() == 233) {
                absentType = "病假";
            }
            ;
            if (xam.getAbsenttype() == 234) {
                absentType = "事假";
            }
            ;
            if (xam.getAbsenttype() == 235) {
                absentType = "婚假";
            }
            ;
            if (xam.getAbsenttype() == 236) {
                absentType = "产假";
            }
            ;
            if (xam.getAbsenttype() == 238) {
                absentType = "其他";
            }
            ;
            map.put("absentType", absentType);
            map.put("beginDate", TimeUtil.TimeToYYYYMMDD(xam.getBegindate()));
            map.put("endDate", TimeUtil.TimeToYYYYMMDD(xam.getEnddate()));
            map.put("status", xam.getStatus());
            map.put("attr1", xam.getAttr1());
            map.put("atrr2", xam.getAttr2());
            list.add(map);
        }
        return MyResult.success(list);
    }

    /**
     * 根据月份查询休息日
     * @param httpArg
     * @return
     */
    public static  String getHolidayJson(String httpArg){
        String httpUrl="http://www.easybots.cn/api/holiday.php";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?m=" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结果是："+result);
        //result:{"201908":{"03":"1","04":"2","10":"1","11":"2","17":"2","18":"1","24":"2","25":"1","31":"2"}}
        return result;
    }

    /**
     * 我的考勤
     *
     * @param times   月份
     * @return
     */
    @Override
    public MyResult getMyTimeBook(String times) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();

        String month = null;
        String year = null;
        String month_ = null;
        if (times!=null && !"".equals(times) && !"null".equals(times)) {
            String[] ss=times.split("-");
            month=ss[0]+ss[1];
            year = ss[0];
            month_ = ss[1];
        } else {
            month = new SimpleDateFormat("yyyyMM").format(new Date());
            year = new SimpleDateFormat("yyyy").format(new Date());
            month_ = new SimpleDateFormat("MM").format(new Date());
            times=year+"-"+month_;
        }

        //休息天数
        Set<String> data_set = new HashSet<>();
        Map data_map = JSON.parseObject(getHolidayJson(month));
        Map set_map = (Map) data_map.get(month);
        data_set = set_map.keySet();

        //当月天数
        int maxDate = 0;
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, Integer.parseInt(year));
        a.set(Calendar.MONTH, Integer.parseInt(month_)-1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);
        maxDate = a.get(Calendar.DAY_OF_MONTH);
        //请假的日期
        String times_ = "";
        Integer time_num = 0;
        Map data = new HashMap();
        String timess = null;
        XnAbsentManage key = new XnAbsentManage();
        if (times == null || "".equals(times)) {
            timess="%"+new SimpleDateFormat("yyyy-MM").format(new Date())+"%";
        } else {
            timess="%"+times+"%";
        }
        key.setUserid(childId);
        key.setStatus((byte) 2);
        key.setType((byte) 1);
        key.setSchoolid(schoolId);
        List<XnAbsentManage> list = xnAbsentManageMapper.getMyAbsent(key, timess);
        if (list.size()==0){
            data.put("times", "");
            data.put("time_num", 0);
            data.put("time_rest", data_set.size());
            data.put("_num", maxDate - time_num - data_set.size());
        }else {
            Set<Date> set_time = new HashSet<>();
            for (XnAbsentManage xnAbsentManage : list) {
                int begindata =Integer.parseInt(new SimpleDateFormat("yyyyMM").format(xnAbsentManage.getBegindate()));
                int enddata = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(xnAbsentManage.getEnddate()));
                Date b=xnAbsentManage.getBegindate();
                Date end=xnAbsentManage.getEnddate();
                if (begindata<Integer.parseInt(month)){
                    try {
                        b=TimeUtil.YYYYMMDDToTime(times+"-01");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (enddata>Integer.parseInt(month)){
                    try {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(TimeUtil.YYYYMMDDToTime(times+"-01"));
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);// 设定当前时间为每月一号
                        // 当前日历的天数上-1变成最大值 , 此方法不会改变指定字段之外的字段
                        calendar1.roll(Calendar.DAY_OF_MONTH, -1);
                        end=calendar1.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                List<Date> date = findDates(b, end);
                if (date.size()==0){
                    return null;
                }
                //time_num += date.size();
                for (Date dataList : date) {
                    set_time.add(dataList);
                    //times_ = times_ + new SimpleDateFormat("yyyy-MM-dd").format(dataList) + ",";
                    //time_num++;
                }
            }
            //请假时间or天数
            for (Date date_ : set_time) {
                String time = new SimpleDateFormat("dd").format(date_);
                times_ = times_ + time + ",";
                time_num++;
                for (String s : data_set) {
                    if (s.equals(time)) {
                        time_num--;
                        System.out.println("times_是："+times_);
                        times_ = times_.substring(0, times_.length() - 3);
                        break;
                    }
                }
            }
            List list1=new ArrayList();
            String[] strings=times_.split(",");
            for (int i=0 ;i<strings.length;i++){
                list1.add(month+strings[i]);
            }
            data.put("times", list1);
            data.put("dataset",data_set);
            data.put("time_num", time_num);
            data.put("time_rest", data_set.size());
            data.put("_num", maxDate - time_num - data_set.size());
        }
        return MyResult.success(data);
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
}