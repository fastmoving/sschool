package com.usoft.sschool_manage.service.live.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.*;
import com.usoft.sschool_manage.service.live.XnCheckNumService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("XnCheckNumService")
public class XnCheckNumServiceImpl implements XnCheckNumService {
    @Resource
    private XnCheckNumMapper xnCheckNumMapper;
    @Resource
    private CfDepartmentMapper cfDepartmentMapper;
    @Resource
    private HlSchclassMapper hlSchclassMapper;
    @Resource
    private XnVideoOrderMapper xnVideoOrderMapper;
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private XnTopqualityPersonalRuleMapper xnTopqualityPersonalRuleMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;
    @Resource
    private XnFineclassOrderMapper xnFineclassOrderMapper;
    //直播点播视频缴费列表
    @Override
    public MyResult list(Integer classId, String stuName, String phone,Integer pageNo,Integer pageSize) {
        XnVideoOrderExample example=new XnVideoOrderExample();
        XnVideoOrderExample.Criteria criteria = example.createCriteria();
        if (classId!=null && !"".equals(classId) && !"null".equals(classId)){
            criteria.andCidEqualTo(classId);
        }
        if (stuName!=null && !"".equals(stuName) && !"null".equals(stuName)){
            HlStudentinfoExample example1=new HlStudentinfoExample();
            example1.createCriteria().andSchoolidEqualTo(SystemParam.getSchoolId()).andSnameEqualTo(stuName);
            List<HlStudentinfo> list = hlStudentinfoMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(list)){return MyResult.failure("没有找到该学生");};
            List l=new ArrayList();
            for (HlStudentinfo hl:list){
                l.add(hl.getId());
            }
            criteria.andUidIn(l);
        }
        if (phone!=null && !"".equals(phone) && !"null".equals(phone)){
            HlStudentinfoExample example1=new HlStudentinfoExample();
            example1.createCriteria().andSchoolidEqualTo(SystemParam.getSchoolId()).andPhoneEqualTo(phone);
            List<HlStudentinfo> list = hlStudentinfoMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(list)){return MyResult.failure("没有找到电话号码对应的学生");};
            List l=new ArrayList();
            for (HlStudentinfo hl:list){
                l.add(hl.getId());
            }
            criteria.andUidIn(l);
        }
        criteria.andAttr1EqualTo("2");
        criteria.andTargetsubjectEqualTo("2");
        example.setOrderByClause("buyTime desc");
        List<XnVideoOrder> xnVideoOrders = xnVideoOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnVideoOrders)){
            return MyResult.failure("没有记录");
        }
        List list=new ArrayList();
        for (XnVideoOrder xvo:xnVideoOrders){
            Map map=new HashMap();
            Map<Object, Object> objectObjectMap = userPhoto(Byte.valueOf(xvo.getUsertype().toString()), xvo.getUid(), xvo.getSid());
            map.put("stuName",objectObjectMap.get("stuName"));
            map.put("class",objectObjectMap.get("class"));
            map.put("phone",objectObjectMap.get("phone"));
            map.put("payType",xvo.getBuytype());
            if (xvo.getBuyusertype()==1){
                map.put("payUser",objectObjectMap.get("stuName"));
            }else {
                HlTeacher ht=new HlTeacher();
                ht.setId(xvo.getBuyuserid());
                ht.setSchoolid(xvo.getSid());
                HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
                map.put("payUser",ObjectUtil.isEmpty(hlTeacher)? null:hlTeacher.getTname());
            }
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(xvo.getTargetsid());
            map.put("school",ObjectUtil.isEmpty(cfDepartment)?null:cfDepartment.getDeptname());

            map.put("teacher","--");
            map.put("subject","--");
            list.add(map);
        }
        if (pageSize==null){
            pageSize=10;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    @Override
    public MyResult fineList(Integer classId, String stuName, String phone, Integer pageNo, Integer pageSize) {
        XnFineclassOrderExample example=new XnFineclassOrderExample();
        XnFineclassOrderExample.Criteria criteria = example.createCriteria();
        if (classId!=null && !"".equals(classId) && !"null".equals(classId)){
            criteria.andCidEqualTo(classId);
        }
        if (stuName!=null && !"".equals(stuName) && !"null".equals(stuName)){
            HlStudentinfoExample example1=new HlStudentinfoExample();
            example1.createCriteria().andSchoolidEqualTo(SystemParam.getSchoolId()).andSnameEqualTo(stuName);
            List<HlStudentinfo> list = hlStudentinfoMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(list)){return MyResult.failure("没有找到该学生");};
            List l=new ArrayList();
            for (HlStudentinfo hl:list){
                l.add(hl.getId());
            }
            criteria.andUseridIn(l);
        }
        if (phone!=null && !"".equals(phone) && !"null".equals(phone)){
            HlStudentinfoExample example1=new HlStudentinfoExample();
            example1.createCriteria().andSchoolidEqualTo(SystemParam.getSchoolId()).andPhoneEqualTo(phone);
            List<HlStudentinfo> list = hlStudentinfoMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(list)){return MyResult.failure("没有找到电话号码对应的学生");};
            List l=new ArrayList();
            for (HlStudentinfo hl:list){
                l.add(hl.getId());
            }
            criteria.andUseridIn(l);
        }
        criteria.andAttr1EqualTo("2");
        example.setOrderByClause("buyTime desc");
        List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnFineclassOrders)){
            return MyResult.failure("没有记录");
        }
        List list=new ArrayList();
        for (XnFineclassOrder xvo:xnFineclassOrders) {
            Map map = new HashMap();
            Map<Object, Object> objectObjectMap = userPhoto(Byte.valueOf(xvo.getUsertype().toString()), xvo.getUserid(), xvo.getSid());
            map.put("stuName", objectObjectMap.get("stuName"));
            map.put("class", objectObjectMap.get("class"));
            map.put("phone", objectObjectMap.get("phone"));
            map.put("payType", xvo.getBuytype());
            if (xvo.getBuytype() == 1) {
                map.put("payUser", objectObjectMap.get("stuName"));
            } else {
                HlTeacher ht = new HlTeacher();
                ht.setId(xvo.getOrdernumber());
                ht.setSchoolid(xvo.getSid());
                HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
                map.put("payUser", ObjectUtil.isEmpty(hlTeacher) ? null : hlTeacher.getTname());
            }
            XnTopqualityPersonalRule xtpr = xnTopqualityPersonalRuleMapper.selectByPrimaryKey(xvo.getFid());
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(xtpr.getSid());
            map.put("school", ObjectUtil.isEmpty(cfDepartment) ? null : cfDepartment.getDeptname());
            HlTeacher ht=new HlTeacher();
            ht.setId(xtpr.getTid());
            ht.setSchoolid(xtpr.getSid());
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            map.put("teacher",ObjectUtil.isEmpty(hlTeacher)?null:hlTeacher.getTname());
            map.put("subject",xtpr.getSubject());
            list.add(map);
        }
        if (pageSize==null){
            pageSize=10;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }


    //根据用户类型查询用户的头像，班级
    public Map<Object,Object> userPhoto(Byte userType, Integer userId, Integer schoolId){
        Map map=new HashMap();
        if (userType==1){
            HlStudentinfo hl=new HlStudentinfo();
            hl.setSchoolid(schoolId);hl.setId(userId);
            HlStudentinfo studentinfo = hlStudentinfoMapper.selectByPrimaryKey(hl);
            map.put("id",studentinfo.getId());
            map.put("stuName",studentinfo.getSname());
            HlSchclassExample example12=new HlSchclassExample();
            example12.createCriteria().andSchoolidEqualTo(studentinfo.getSchoolid())
                    .andIdEqualTo(studentinfo.getClassid());
            List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(example12);
            if (ObjectUtil.isEmpty(hlSchclasses)){
                map.put("classId",null);
                map.put("class",null);
            }else {
                map.put("classId",studentinfo.getClassid());
                map.put("class",hlSchclasses.get(0).getClassname());
            }
            if (studentinfo.getSphoto()==null){
                map.put("ImageSrc",null);
                map.put("idImg",null);
            }else {
                String str=studentinfo.getSphoto();
                JSON json= JSONObject.parseObject(str);
                Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
                if (map2.get("idImg")==null || "null".equals(map2.get("idImg")) || "".equals(map2.get("idImg"))){
                    map.put("idImg",null);
                    map.put("ImageSrc",map2.get("faceImg"));
                }else {
                    if(map2.containsKey("faceImg")){
                        String[] s=map2.get("faceImg").toString().split(",");
                        map.put("ImageSrc",map2.get("faceImg"));
                        map.put("idImg",s);
                    }
                }
            }
            map.put("phone",studentinfo.getPhone());
        }
        if (userType==2){
            HlTeacher ht=new HlTeacher();
            ht.setId(userId);
            ht.setSchoolid(schoolId);
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            map.put("id",hlTeacher.getId());
            map.put("stuName",hlTeacher.getTname());
            map.put("class",hlTeacher.getTname()+"老师");
            if (hlTeacher.getImagesrc()==null){
                map.put("ImageSrc",null);
                map.put("idImg",null);
            }else {
                String str=hlTeacher.getImagesrc();
                JSON json= JSONObject.parseObject(str);
                Map<String,Object> map2=JSONObject.toJavaObject(json,Map.class);
                if (map2.get("faceImg")==null || "null".equals(map2.get("faceImg")) || "".equals(map2.get("faceImg"))){
                    map.put("idImg",null);
                    map.put("ImageSrc",map2.get("idImg"));
                }else {
                    String[] s=map2.get("faceImg").toString().split(",");
                    map.put("ImageSrc",map2.get("idImg"));
                    map.put("idImg",s);
                }
            }
            map.put("phone",hlTeacher.getMobile());
        }
        return map;
    }
    @Override
    public MyResult totalNum() {
        Integer schoolId = SystemParam.getSchoolId();
        Map<String, Object> stringObjectMap = xnCheckNumMapper.totalNum(schoolId, 1);
        Map<String, Object> stringObjectMap1 = xnCheckNumMapper.totalNum(schoolId, 2);
        Map map=new HashMap();
        if (ObjectUtil.isEmpty(stringObjectMap)){
            map.put("video",0);
        }else {
            map.put("video",stringObjectMap.get("total"));
        }
        if (ObjectUtil.isEmpty(stringObjectMap1)){
            map.put("live",0);
        }else {
            map.put("live",stringObjectMap1.get("total"));
        }
        return MyResult.success(map);
    }

    @Override
    public MyResult liveRank(String time,Integer videoType) {
        if (time==null || "null".equals(time) || "".equals(time)){
            Date date=new Date();
            time= TimeUtil.TimeToYYYYMMDDHHMMSS(date).substring(0,10);
        }
        String str="%"+time+"%";
        Integer schoolId = SystemParam.getSchoolId();
        List<Map<String, Object>> maps = xnCheckNumMapper.liveRank(str,schoolId, videoType);
        if (ObjectUtil.isEmpty(maps))return MyResult.failure("没有今日的数据");
        List<Map> list=new ArrayList<>();
        for (Map m:maps){
            Map map=new HashMap();
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey((Integer) m.get("sid"));
            if (ObjectUtil.isEmpty(cfDepartment)){
                map.put("school",m.get("sid"));
            }else {
                map.put("school",cfDepartment.getDeptname());
            }
            HlSchclass hs=new HlSchclass();
            hs.setSchoolid((Integer) m.get("sid"));
            hs.setId((Integer)m.get("cid"));
            HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
            if (ObjectUtil.isEmpty(hlSchclass)){
                map.put("class",m.get("cid"));
            }else {
                map.put("class",hlSchclass.getClassname());
            }
            map.put("checkNum",m.get("num"));
            list.add(map);
        }
        return MyResult.success(list);
    }

    @Override
    public MyResult RankByWeek(String start, String end, Integer videoType) {
        Integer schoolId = SystemParam.getSchoolId();
        List<Map<String, Object>> maps = xnCheckNumMapper.RankByWeek(start,end,videoType,schoolId);
        if (ObjectUtil.isEmpty(maps))return MyResult.failure("暂无数据");
        List<Map> list=new ArrayList<>();
        for (Map m:maps){
            Map map=new HashMap();
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey((Integer) m.get("sid"));
            if (ObjectUtil.isEmpty(cfDepartment)){
                map.put("school",m.get("sid"));
            }else {
                map.put("school",cfDepartment.getDeptname());
            }
            HlSchclass hs=new HlSchclass();
            hs.setSchoolid((Integer) m.get("sid"));
            hs.setId((Integer)m.get("cid"));
            HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
            if (ObjectUtil.isEmpty(hlSchclass)){
                map.put("class",m.get("cid"));
            }else {
                map.put("class",hlSchclass.getClassname());
            }
            map.put("checkNum",m.get("num"));
            list.add(map);
        }
        return MyResult.success(list);
    }

    @Override
    public MyResult EmpVideo(Integer stuId,Integer cid, Integer videoType, String list,Integer payType) {
        if (list==null){
            return MyResult.failure("请选择视频信息");
        }
        String[] str=list.split(",");
        List<Integer> list1=new ArrayList<>();
        for (String li:str){
            list1.add(Integer.valueOf(li));
        }
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();
        int insert=0;
        if (videoType==1){
            XnVideoOrderExample example=new XnVideoOrderExample();
            example.createCriteria().andSidEqualTo(schoolId).andCidEqualTo(cid).andUidEqualTo(stuId)
                    .andBuytypeEqualTo(payType).andUsertypeEqualTo(1);
            List<XnVideoOrder> xnVideoOrders = xnVideoOrderMapper.selectByExample(example);
            if (!ObjectUtil.isEmpty(xnVideoOrders)){
                return MyResult.failure("该学生已有授权或线下购买过");
            }
            XnVideoOrder xnVideoOrder=new XnVideoOrder();
            xnVideoOrder.setSid(schoolId);
            xnVideoOrder.setCid(cid);
            xnVideoOrder.setBuytime(new Date());
            xnVideoOrder.setUsertype(1);
            xnVideoOrder.setUid(stuId);
            HlStudentinfo hl=new HlStudentinfo();
            hl.setSchoolid(schoolId);hl.setId(stuId);
            HlStudentinfo studentinfo = hlStudentinfoMapper.selectByPrimaryKey(hl);
            if (!ObjectUtil.isEmpty(studentinfo)){
                xnVideoOrder.setPhone(ObjectUtil.isEmpty(studentinfo.getPhone())? null:studentinfo.getPhone());
            }
            for (Integer li:list1){
                xnVideoOrder.setBuytype(payType);
                xnVideoOrder.setBuyusertype(2);
                xnVideoOrder.setBuyuserid(userId);
                xnVideoOrder.setTargetsid(schoolId);
                xnVideoOrder.setTargetsubject("2");
                xnVideoOrder.setTargettid(Integer.valueOf(li));
                xnVideoOrder.setCreatetime(new Date());
                xnVideoOrder.setAttr1("2");
                xnVideoOrder.setAttr2("0");
                Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.YEAR,1);
                xnVideoOrder.setAttr3(TimeUtil.TimeToYYYYMMDDHHMMSS(calendar.getTime()));
                insert = xnVideoOrderMapper.insert(xnVideoOrder)+insert;
            }

        }else {
            XnFineclassOrderExample example=new XnFineclassOrderExample();
            example.createCriteria().andSidEqualTo(schoolId).andFidIn(list1).andUseridEqualTo(stuId)
                    .andUsertypeEqualTo((byte)1).andBuytypeEqualTo(payType);
            List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example);
            if (!ObjectUtil.isEmpty(xnFineclassOrders)){
                return MyResult.failure("该学生已有授权或线下购买过");
            }
            for (Integer li:list1){
                XnFineclassOrder xfo=new XnFineclassOrder();
                xfo.setSid(schoolId);
                xfo.setCid(2);
                xfo.setOrdernumber(userId);
                xfo.setFid(Integer.valueOf(li));
                xfo.setUsertype((byte)1);
                xfo.setUserid(stuId);
                xfo.setBuytime(new Date());
                xfo.setBuytype(payType);
                xfo.setCreatetime(new Date());
                xfo.setAttr1("2");
                insert = xnFineclassOrderMapper.insert(xfo)+insert;
            }
        }
        if (insert!=str.length){
            return MyResult.failure("授权或线下购买失败");
        }
        return MyResult.success("授权或线下购买成功");
    }

    /**
     * 根据学校班级查询学生
     * @param sid
     * @param cid
     * @return
     */
    @Override
    public MyResult allStu(Integer sid, Integer cid) {
        HlStudentinfoExample example=new HlStudentinfoExample();
        example.createCriteria().andSchoolidEqualTo(sid).andClassidEqualTo(cid);
        List<HlStudentinfo> list = hlStudentinfoMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(list)){
            return MyResult.failure("没找到学生信息");
        }
        return MyResult.success(list);
    }

    @Override
    public MyResult fineClassList(Integer pageNo,Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        XnTopqualityPersonalRuleExample example=new XnTopqualityPersonalRuleExample();
        example.createCriteria().andSidEqualTo(schoolId);
        List<XnTopqualityPersonalRule> xnTopqualityPersonalRules = xnTopqualityPersonalRuleMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnTopqualityPersonalRules)){
            return MyResult.failure("没找到信息");
        }
        List list=new ArrayList();
        for (XnTopqualityPersonalRule xtp:xnTopqualityPersonalRules){
            Map map=new HashMap();
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(xtp.getSid());
            map.put("school",ObjectUtil.isEmpty(cfDepartment)?"无":cfDepartment.getDeptname());
            map.put("subject",xtp.getSubject());
            HlTeacher ht=new HlTeacher();
            ht.setId(xtp.getTid());
            ht.setSchoolid(xtp.getSid());
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            map.put("teacher",ObjectUtil.isEmpty(hlTeacher)?"无":hlTeacher.getTname());
            map.put("videoSchoolId",xtp.getSid());
            map.put("videoType",2);
            map.put("videoId",xtp.getId());
            list.add(map);
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }
}
