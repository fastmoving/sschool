package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.util.PayUtil.WXUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.SchProService;
import com.usoft.sschool_pub.util.ResultPage;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author wlw
 * @data 2019/4/24 0024-15:18
 */
@Service(value = "SchProService")
public class SchProServiceImpl implements SchProService {
    @Resource
    private XnCarouselMapper xnCarouselMapper;
    @Resource
    private CfDepartmentMapper cfDepartmentMapper;
    @Resource
    private XnAnnounceMapper xnAnnounceMapper;
    @Resource
    private XnTeaApperanceMapper xnTeaApperanceMapper;
    @Resource
    private XnIntrestClassMapper xnIntrestClassMapper;
    @Resource
    private XnIntrestChatMapper xnIntrestChatMapper;
    @Resource
    private XnIntrestEntryMapper xnIntrestEntryMapper;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private VoUtil voUtil;
    @Resource
    private XnSchoolIntroduceMapper xnSchoolIntroduceMapper;
    @Resource
    private HlEnumitemMapper hlEnumitemMapper;

    /**
     * 轮播图
     * @param types 位置 1.校园首页 2.学生端首页 3.教师端首页
     * @param position  	方向 1.app 2.web
     * @return
     */
    @Override
    public MyResult carousel(Integer schoolId,Byte types,Byte position){
        XnCarouselExample example=new XnCarouselExample();
        example.createCriteria().andTypeEqualTo(position).andPositionEqualTo(types).andUidEqualTo(schoolId);
        List<XnCarousel> xnCarousels = xnCarouselMapper.selectByExample(example);
        if (xnCarousels.size()==0){
            return MyResult.failure("没找到轮播图的信息");
        }
        return MyResult.success(xnCarousels);
    }
    /**
     * 学校简介
     * @param schoolId
     * @return
     */
    @Override
    public MyResult schoolInfo(Integer schoolId) {
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);
        if (ObjectUtil.isEmpty(cfDepartment))return MyResult.failure("数据库没有该学校信息");
        //简介和图片
        XnSchoolIntroduceExample example=new XnSchoolIntroduceExample();
        example.createCriteria().andSchoolidEqualTo(schoolId);
        List<XnSchoolIntroduce> xnSchoolIntroduces = xnSchoolIntroduceMapper.selectByExample(example);

        //乡镇
        HlEnumitemExample example1=new HlEnumitemExample();
        example1.createCriteria().andIdEqualTo(cfDepartment.getCountyid());
        List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(example1);
        List<Map> list=new ArrayList<>();
        Map map=new HashMap();
        map.put("deptid",cfDepartment.getDeptid());
        map.put("deptcode",cfDepartment.getDeptcode());
        map.put("deptname",cfDepartment.getDeptname());
        map.put("depttell",cfDepartment.getDepttell());
        map.put("deptaddress",cfDepartment.getDeptaddress());
        map.put("president", cfDepartment.getPresident());
        map.put("countyid",cfDepartment.getCountyid());
        if (ObjectUtil.isEmpty(hlEnumitems)){
            map.put("countyName",null);
        }else {
            map.put("countyName",hlEnumitems.get(0).getEnumitemname());
        }
        if (!ObjectUtil.isEmpty(xnSchoolIntroduces)){
            map.put("img",xnSchoolIntroduces.get(0).getImg());
            map.put("recordstatus",xnSchoolIntroduces.get(0).getDescription());
        }else {
            map.put("img",null);
            map.put("recordstatus","没有找到简介");
        }
        list.add(map);
        return MyResult.success(list);
    }

    /**
     * 学校公告
     * @param schoolId
     * @return
     */
    @Override
    public MyResult announce(Integer schoolId,Integer pageNo,Integer pageSize) {
        XnAnnounceExample example=new XnAnnounceExample();
        example.createCriteria().andSidEqualTo(schoolId).andStateEqualTo((byte) 1);
        example.setOrderByClause("CreateTime desc");
        List<XnAnnounce> xnAnnounces = xnAnnounceMapper.selectByExample(example);
        if (xnAnnounces.size()==0){
            return MyResult.failure("数据库没有该学校已发送的公告");
        }
        List<Map> list=new ArrayList<>();
        for (XnAnnounce xa:xnAnnounces){
            Map map=new HashMap();
            map.put("announcetitle",xa.getAnnouncetitle());
            map.put("announcecontent",xa.getAnnouncecontent());
            map.put("announceImg",xa.getAnnounceimg());
            map.put("createtime", TimeUtil.TimeToYYYYMMDDHHMMSS(xa.getCreatetime()));
            map.put("sid",xa.getId());
            map.put("createuser",xa.getCreateuser());
            map.put("id",xa.getId());
            list.add(map);
        }
        return ResultPage.getPageResult(list, pageNo, pageSize);
    }

    /**
     * 公告详情
     * @param aid
     * @return
     */
    @Override
    public MyResult announceInfo(Integer aid) {
        if (ObjectUtil.isEmpty(aid))return MyResult.failure("请选择公告");
        XnAnnounce xnAnnounce = xnAnnounceMapper.selectByPrimaryKey(aid);
        if (ObjectUtil.isEmpty(aid))return MyResult.failure("没找到该公告");
        Map map=new HashMap();
        map.put("announcetitle",xnAnnounce.getAnnouncetitle());
        map.put("announcecontent",xnAnnounce.getAnnouncecontent());
        map.put("announceImg",xnAnnounce.getAnnounceimg());
        map.put("createtime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnAnnounce.getCreatetime()));
        map.put("sid",xnAnnounce.getId());
        map.put("createuser",xnAnnounce.getCreateuser());
        map.put("id",xnAnnounce.getId());
        return MyResult.success(map);
    }

    /**
     * 教师风采
     * @param schoolId
     * @return
     */
    @Override
    public MyResult teacherApperance(Integer schoolId,Integer pageNo,Integer pageSize) {
        XnTeaApperanceExample example=new XnTeaApperanceExample();
        example.createCriteria().andSidEqualTo(schoolId);
        List<XnTeaApperance> xnTeaApperances = xnTeaApperanceMapper.selectByExample(example);
        if (xnTeaApperances.size()==0){
            return MyResult.failure("数据库没有该学校的教师风采信息");
        }
        return ResultPage.getPageResult(xnTeaApperances,pageNo,pageSize);
    }

    /**
     * 兴趣班列表
     * @param schoolId
     * @return
     */
    @Override
    public MyResult intClass(Integer schoolId,Integer pageNo,Integer pageSize) {
        XnIntrestClassExample example=new XnIntrestClassExample();
        example.createCriteria().andSidEqualTo(schoolId);
        example.setOrderByClause("id desc");
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnIntrestClasses))return MyResult.failure("数据库没有该学校的兴趣班信息");
        List<Map> list=new ArrayList<>();
        for (XnIntrestClass xic:xnIntrestClasses){
            Map map=new HashMap();
            map.put("classname",xic.getClassname());
            map.put("teaname",xic.getTeaname());
            map.put("begindate",TimeUtil.TimeToYYYYMMDDHHMMSS(xic.getBegindate()));
            map.put("classdes",xic.getClassdes());
            map.put("signtype",xic.getSigntype());
            map.put("tid",xic.getTid());
            map.put("id",xic.getId());
            map.put("classnum",xic.getClassnum());
            map.put("img",xic.getClassimg());
            map.put("price",xic.getAttr1());
            XnIntrestEntryExample entryExample=new XnIntrestEntryExample();
            entryExample.createCriteria().andSidEqualTo(schoolId).andIntrestidEqualTo(xic.getId())
                    .andAttr1EqualTo("2");
            List<XnIntrestEntry> xnIntrestEntries = xnIntrestEntryMapper.selectByExample(entryExample);
            if (ObjectUtil.isEmpty(xnIntrestClasses)){
                map.put("enrollment",0);
            }else {
                map.put("enrollment",xnIntrestEntries.size());
            }
            list.add(map);
        }

        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 兴趣班详情
     * @param intrestClassId
     * @return
     */
    @Override
    public MyResult intrestClassInfo(Integer intrestClassId,Integer schoolId) {
       XnIntrestClassExample example=new XnIntrestClassExample();
        example.createCriteria().andSidEqualTo(schoolId).andIdEqualTo(intrestClassId);
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnIntrestClasses))return MyResult.failure("没找到该兴趣班信息");
        //已报名人数
        XnIntrestEntryExample entryExample=new XnIntrestEntryExample();
        entryExample.createCriteria().andSidEqualTo(schoolId).andIntrestidEqualTo(intrestClassId).andAttr1EqualTo("2");
        List<XnIntrestEntry> xnIntrestEntries = xnIntrestEntryMapper.selectByExample(entryExample);

        List<Map> list=new ArrayList<>();
        Map map=new HashMap();
        map.put("classname",xnIntrestClasses.get(0).getClassname());
        map.put("teaname",xnIntrestClasses.get(0).getTeaname());
        map.put("begindate",TimeUtil.TimeToYYYYMMDDHHMMSS(xnIntrestClasses.get(0).getBegindate()));
        map.put("classdes",xnIntrestClasses.get(0).getClassdes());
        map.put("signtype",xnIntrestClasses.get(0).getSigntype());
        map.put("tid",xnIntrestClasses.get(0).getTid());
        Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte) 2, xnIntrestClasses.get(0).getTid(), xnIntrestClasses.get(0).getSid());
        map.put("teaPic",objectObjectMap.get("ImageSrc"));
        map.put("id",xnIntrestClasses.get(0).getId());
        map.put("classnum",xnIntrestClasses.get(0).getClassnum());
        if (ObjectUtil.isEmpty(xnIntrestClasses)){
            map.put("enrollment",0);
        }else {
            map.put("enrollment",xnIntrestEntries.size());
        }
        map.put("img",xnIntrestClasses.get(0).getClassimg());
        map.put("price",xnIntrestClasses.get(0).getAttr1());
        map.put("teaPic",objectObjectMap.get("ImageSrc"));
        list.add(map);
        return MyResult.success(list);
    }

    /**
     * 查询兴趣班聊天记录
     * @param IntrestId
     * @return
     */
    @Override
    public MyResult communicate(Integer IntrestId) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=SystemParam.getChildId();
        XnIntrestClassExample example=new XnIntrestClassExample();
        example.createCriteria().andIdEqualTo(IntrestId);
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnIntrestClasses))return MyResult.failure("没有找到该兴趣班的信息");
        List<XnIntrestChat> list1 = xnIntrestChatMapper.selectCommunicate(xnIntrestClasses.get(0).getSid(),IntrestId,xnIntrestClasses.get(0).getTid(),userId);
        List<Map> list=new ArrayList<>();
        for (XnIntrestChat ma:list1) {
            Map map = new HashMap();
            map.put("IntrestId", ma.getIntrestid());
            map.put("userId", ma.getUserid());
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(ma.getUsertype(), ma.getUserid(), ma.getSid());
            map.put("stuName", objectObjectMap.get("stuName"));
            map.put("ImageSrc", objectObjectMap.get("ImageSrc"));
            map.put("class", objectObjectMap.get("class"));
            map.put("userType", ma.getUsertype());
            map.put("content", ma.getContent());
            map.put("createTime", TimeUtil.TimeToYYYYMMDDHHMMSS(ma.getCreatetime()));
            map.put("attr1", ma.getAttr1());
            map.put("attr2", ma.getAttr2());
            list.add(map);
            if (!SystemParam.getType().equals(ma.getUsertype())&& !userId.equals(ma.getUserid())) {
                ma.setAttr2("1");
                int i=xnIntrestChatMapper.updateByPrimaryKeySelective(ma);
            }
        }
        return MyResult.success(list);
    }

    /**
     * 写入聊天记录
     * @param IntrestId
     * @param content
     * @param schoolId
     * @param childId
     * @return
     */
    @Override
    public MyResult addcommunicate(Integer IntrestId, String content, Integer schoolId, Integer childId) {
        XnIntrestClassExample example=new XnIntrestClassExample();
        example.createCriteria().andIdEqualTo(IntrestId);
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnIntrestClasses))return MyResult.failure("没有找到该兴趣班的信息");
        Integer tid = xnIntrestClasses.get(0).getTid();
        //写入消息
        XnIntrestChat xic=new XnIntrestChat();
        xic.setSid(schoolId);
        xic.setIntrestid(IntrestId);
        xic.setUserid(childId);
        xic.setUsertype((byte)1);
        xic.setContent(content);
        xic.setCreatetime(new Date());
        xic.setAttr1(tid.toString());
        xic.setAttr2("2");
        xic.setAttr3("2");
        int i = xnIntrestChatMapper.insertSelective(xic);
        if (i==0){
            return MyResult.failure("发送失败");
        }
        return MyResult.success("发送成功");
    }

    /**
     * 教师查询自己的兴趣班信息
     * @return
     */
    @Override
    public MyResult teaInterstId() {
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=SystemParam.getUserId();
        XnIntrestClassExample example=new XnIntrestClassExample();
        example.createCriteria().andSidEqualTo(schoolId).andTidEqualTo(userId);
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnIntrestClasses))return MyResult.failure("您没有兴趣班");
        return MyResult.success(xnIntrestClasses);
    }

    /**
     * 教师回复家长的聊天
     * @param recevieId
     * @return
     */
    @Override
    public MyResult teaReply(Integer IntrestId,Integer recevieId,String content) {
        //写入消息
        XnIntrestChat xic=new XnIntrestChat();
        xic.setSid(SystemParam.getSchoolId());
        xic.setIntrestid(IntrestId);
        xic.setUserid(SystemParam.getUserId());
        xic.setUsertype((byte)2);
        xic.setContent(content);
        xic.setCreatetime(new Date());
        xic.setAttr1(recevieId.toString());
        xic.setAttr2("2");
        xic.setAttr3("1");
        int i = xnIntrestChatMapper.insertSelective(xic);
        if (i==0){
            return MyResult.failure("发送失败");
        }
        return MyResult.success("发送成功");
    }

    /**
     * 教师查看兴趣班沟通列表
     * @return
     */
    @Override
    public MyResult teaMsgList() {
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();

        List<Map<String, Object>> stringObjectMap = xnIntrestChatMapper.teaMsgList(schoolId,userId.toString());
        if (ObjectUtil.isEmpty(stringObjectMap))return MyResult.failure("没有聊天记录");
        List list=new ArrayList();
        for (Map m:stringObjectMap){
            XnIntrestChatExample example=new XnIntrestChatExample();
            example.createCriteria().andSidEqualTo(schoolId).andUsertypeEqualTo((byte)1)
                    .andIntrestidEqualTo((int)m.get("IntrestId")).andUseridEqualTo((Integer) m.get("userId"));
            example.setOrderByClause("createTime desc");
            List<XnIntrestChat> list1 = xnIntrestChatMapper.selectByExample(example);
            Map map=new HashMap();
            map.put("IntrestId",list1.get(0).getIntrestid());
            map.put("userId",list1.get(0).getUserid());
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(list1.get(0).getUsertype(), list1.get(0).getUserid(), list1.get(0).getSid());
            map.put("stuName",objectObjectMap.get("stuName"));
            map.put("ImageSrc",objectObjectMap.get("ImageSrc"));
            map.put("class",objectObjectMap.get("class"));
            map.put("userType",list1.get(0).getUsertype());
            map.put("content",list1.get(0).getContent());
            map.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(list1.get(0).getCreatetime()));
            map.put("attr1",list1.get(0).getAttr1());
            map.put("attr2",list1.get(0).getAttr2());
            list.add(map);
        }
        return MyResult.success(list);
    }

    /**
     * 教师查看和单个家长的聊天记录
     * @param IntrestId
     * @param userId
     * @return
     */
    @Override
    public MyResult teaSearchChat(Integer IntrestId, Integer userId) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId1=SystemParam.getUserId();
        List<XnIntrestChat> list1 = xnIntrestChatMapper.teaSearchChat(schoolId,IntrestId,userId1,userId);
        List<Map> list=new ArrayList<>();
        for (XnIntrestChat ma:list1) {
            Map map = new HashMap();
            map.put("IntrestId", ma.getIntrestid());
            map.put("userId", ma.getUserid());
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(ma.getUsertype(), ma.getUserid(), ma.getSid());
            map.put("stuName", objectObjectMap.get("stuName"));
            map.put("ImageSrc", objectObjectMap.get("ImageSrc"));
            map.put("class", objectObjectMap.get("class"));
            map.put("userType", ma.getUsertype());
            map.put("content", ma.getContent());
            map.put("createTime", TimeUtil.TimeToYYYYMMDDHHMMSS(ma.getCreatetime()));
            map.put("attr1", ma.getAttr1());
            map.put("attr2", ma.getAttr2());
            list.add(map);
            if (!SystemParam.getType().equals(ma.getUsertype())&& !userId1.equals(ma.getUserid())) {
                ma.setAttr2("1");
                int i=xnIntrestChatMapper.updateByPrimaryKeySelective(ma);
            }
        }
        return MyResult.success(list);
    }

    /**
     * 报名信息写入到数据库
     * @param schoolId
     * @param childId
     * @param entryType
     * @param icid
     * @return
     */
    @Override
    public MyResult enterClass(Integer schoolId, Integer childId, Integer entryType, Integer icid) {
        Map map=new HashMap();
        XnIntrestClassExample example=new XnIntrestClassExample();
        example.createCriteria().andIdEqualTo(icid);
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(example);
        XnIntrestEntryExample entryExample=new XnIntrestEntryExample();
        entryExample.createCriteria().andSidEqualTo(schoolId).andIntrestidEqualTo(icid).andStuidEqualTo(childId);
        List<XnIntrestEntry> xnIntrestEntries1 = xnIntrestEntryMapper.selectByExample(entryExample);
        if (!ObjectUtil.isEmpty(xnIntrestEntries1)){
            if ("2".equals(xnIntrestEntries1.get(0).getAttr1())){
                return MyResult.failure("你已报名成功，不能再报名");
            }else if ("1".equals(xnIntrestEntries1.get(0).getAttr1())){
                map.put("tradeNo",xnIntrestEntries1.get(0).getAttr2());
                map.put("isPay",2);
                map.put("orderId",xnIntrestEntries1.get(0).getId());
                map.put("product_sys",4);
                map.put("attach",xnIntrestClasses.get(0).getClassname());
                return MyResult.success("已报名，但未支付",map);
            }
        }
        XnIntrestEntry xie=new XnIntrestEntry();
        xie.setSid(schoolId);
        xie.setIntrestid(icid);
        xie.setStuid(childId);
        xie.setEntrytype(entryType);
        xie.setCreatetime(new Date());
        String byUUId = WXUtil.getByUUId()+"d";
        if (entryType==2){
            xie.setAttr1("2");
        }else {
            xie.setAttr1("1");
            xie.setAttr2(byUUId);
        }
        xie.setAttr3(xnIntrestClasses.get(0).getAttr1());
        int i = xnIntrestEntryMapper.insertSelective(xie);
        if (i!=1){
            return MyResult.failure("保存失败");
        }
        XnIntrestEntryExample example1=new XnIntrestEntryExample();
        example1.createCriteria().andSidEqualTo(schoolId).andIntrestidEqualTo(icid).andStuidEqualTo(childId);
        List<XnIntrestEntry> xnIntrestEntries = xnIntrestEntryMapper.selectByExample(example1);
        map.put("orderId",xnIntrestEntries.get(0).getId());
        map.put("tradeNo",byUUId);
        map.put("isPay",1);
        map.put("product_sys",4);
        map.put("attach",xnIntrestClasses.get(0).getClassname());
        return MyResult.success(map);
    }
    /**
     * 兴趣班根据订单id查询订单详情
     * @param orderId 订单id
     */
    @Override
    public MyResult searchOrder(Integer orderId) {
        if (ObjectUtil.isEmpty(orderId))return MyResult.failure("参数错误");
        XnIntrestEntry xnIntrestEntry = xnIntrestEntryMapper.selectByPrimaryKey(orderId);
        if (ObjectUtil.isEmpty(xnIntrestEntry))return MyResult.failure("没找到订单信息");
        XnIntrestClassExample example=new XnIntrestClassExample();
        example.createCriteria().andIdEqualTo(xnIntrestEntry.getIntrestid());
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnIntrestClasses))return MyResult.failure("没有找到兴趣班信息");
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",xnIntrestEntry.getId());
        map.put("orderStatus",xnIntrestEntry.getAttr1());
        map.put("ClassName",xnIntrestClasses.get(0).getClassname());
        map.put("TeaName",xnIntrestClasses.get(0).getTeaname());
        map.put("BeginDate",TimeUtil.TimeToYYYYMMDDHHMMSS(xnIntrestClasses.get(0).getBegindate()));
        if (xnIntrestClasses.get(0).getClassimg()==null){
            map.put("classImg",null);
        }else {
            map.put("classImg",xnIntrestClasses.get(0).getClassimg());
        }
        map.put("tradeNo",xnIntrestEntry.getAttr2());
        map.put("price",xnIntrestClasses.get(0).getAttr1());
        map.put("product_sys",4);
        map.put("attach",xnIntrestClasses.get(0).getClassname());
        return MyResult.success(map);
    }

    /**
     * 支付后改变订单状态
     * @param ieid
     * @return
     */
    @Override
    public MyResult changeOrder(Integer ieid) {
        XnIntrestEntry xnIntrestEntry = xnIntrestEntryMapper.selectByPrimaryKey(ieid);
        if (ObjectUtil.isEmpty(xnIntrestEntry))return MyResult.failure("没找到该订单信息");
        if (!xnIntrestEntry.getStuid().equals(SystemParam.getChildId()))return MyResult.failure("不是你的订单,支付失败");
        if (xnIntrestEntry.getEntrytype()==2)return MyResult.failure("该订单为线下支付！");
        xnIntrestEntry.setAttr1("2");
        int i = xnIntrestEntryMapper.updateByPrimaryKeySelective(xnIntrestEntry);
        if (i!=1){
            return MyResult.failure("保存失败");
        }
        XnIntrestClassExample example=new XnIntrestClassExample();
        example.createCriteria().andIdEqualTo(xnIntrestEntry.getIntrestid());
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(example);
        Map map=new HashMap();
        map.put("id",xnIntrestEntry.getId());
        map.put("sid",xnIntrestEntry.getSid());
        map.put("intrestid",xnIntrestEntry.getIntrestid());
        map.put("stuid",xnIntrestEntry.getStuid());
        map.put("entrytype",xnIntrestEntry.getEntrytype());
        map.put("createtime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnIntrestEntry.getCreatetime()));
        map.put("attr1",xnIntrestEntry.getAttr1());
        map.put("attr2",xnIntrestEntry.getAttr2());
        map.put("attr3",xnIntrestEntry.getAttr3());
        map.put("price",xnIntrestClasses.get(0).getAttr1());
        map.put("img",xnIntrestClasses.get(0).getClassimg());
        return MyResult.success(map);
    }

}
