package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.util.PayUtil.WXUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.TermianlService;
import com.usoft.sschool_pub.util.ResultPage;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wlw
 * @data 2019/4/25 0025-17:34
 */
@Service("TermianlService")
public class TerminalServiceImpl implements TermianlService {
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private TerminalInfoMapper terminalInfoMapper;
    @Resource
    private XnMailOrderMapper xnMailOrderMapper;
    @Resource
    private PrgFileMapper prgFileMapper;
    @Resource
    private HlVideoinfonewMapper hlVideoinfonewMapper;
    @Resource
    private XnAttentionMapper xnAttentionMapper;
    @Resource
    private HlCurriculumMapper hlCurriculumMapper;
    @Resource
    private HlCurriculumsetMapper hlCurriculumsetMapper;
    @Resource
    private CfDepartmentMapper cfDepartmentMapper;
    @Resource
    private HlSchclassMapper hlSchclassMapper;
    @Resource
    private HlEnumitemMapper hlEnumitemMapper;
    @Resource
    private XnFineclassOrderMapper xnFineclassOrderMapper;
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private XnVideoRuleMapper xnVideoRuleMapper;
    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;
    @Resource
    private XnVideoOrderMapper xnVideoOrderMapper;
    @Resource
    private XnVideoRulePayMapper xnVideoRulePayMapper;
    @Resource
    private XnVideoCheckMapper xnVideoCheckMapper;
    @Resource
    private XnTopqualityPersonalRuleMapper xnTopqualityPersonalRuleMapper;
    @Resource
    private HlCountyMapper hlCountyMapper;
    @Resource
    private XnTopqualityClassRuleMapper xnTopqualityClassRuleMapper;
    @Resource
    private XnCheckNumMapper xnCheckNumMapper;
    @Resource
    private HlSchoolsevipMapper hlSchoolsevipMapper;
    @Resource
    private XnMealMapper xnMealMapper;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private VoUtil voUtil;

    /**
     * 查询所有乡镇和学校
     * @param schoolId
     * @return
     */
    @Override
    public MyResult conditions(Integer schoolId) {
        if (schoolId==null){
            schoolId=1;
        }
        //查询所有乡镇
        Map map=new HashMap();
        HlCountyExample example=new HlCountyExample();
        example.createCriteria().andParentidIsNotNull();
        List<HlCounty> hlCounties = hlCountyMapper.selectByExample(example);
        List lis=new ArrayList();
        for (HlCounty hc:hlCounties){
            Map ma=new HashMap();
            ma.put("id",hc.getId());
            ma.put("enumitemname",hc.getName());
            lis.add(ma);
        }
        map.put("county",lis);
        //查询所有学校
        CfDepartmentExample example1=new CfDepartmentExample();
        example1.createCriteria();
        List<CfDepartment> cfDepartments = cfDepartmentMapper.selectByExample(example1);
        List<Map> list=new ArrayList<>();
        for (CfDepartment cd:cfDepartments){
            Map map1=new HashMap();
            map1.put("schoolId",cd.getDeptid());
            map1.put("schoolName",cd.getDeptname());
            map1.put("countyId",cd.getCountyid());
            list.add(map1);
        }
        map.put("school",list);
        //查询所有科目
        HlEnumitemExample example2=new HlEnumitemExample();
        example2.createCriteria().andEnumidEqualTo(13);
        List<HlEnumitem> hlEnumitems2 = hlEnumitemMapper.selectByExample(example2);
        map.put("subject",hlEnumitems2);
        //查询所有作息时间
        HlCurriculumsetExample example3=new HlCurriculumsetExample();
        example3.createCriteria().andSchoolidEqualTo(schoolId);
        List<HlCurriculumset> hlCurriculumsets = hlCurriculumsetMapper.selectByExample(example3);
        map.put("curriculumset",hlCurriculumsets);
        return MyResult.success(map);
    }

    /**
     * 根据学校id查询所有的班级
     * @param schoolId
     * @return
     */
    @Override
    public MyResult searchClass(Integer schoolId) {
        if (schoolId==null){
            return MyResult.failure("请输入学校");
        }
        //查询所有班级
        HlSchclassExample example2=new HlSchclassExample();
        example2.createCriteria().andSchoolidEqualTo(schoolId);
        List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(example2);
        List<Map> list2=new ArrayList<>();
        for (HlSchclass hs:hlSchclasses){
            Map map2=new HashMap();
            map2.put("classId",hs.getId());
            map2.put("classSchoolId",hs.getSchoolid());
            map2.put("className",hs.getClassname());
            map2.put("granderId",hs.getGradetype());
            list2.add(map2);
        }
        return MyResult.success(list2);
    }

    /**
     * 查询班级所有老师信息
     * @param schoolId
     * @param classId
     * @return
     */
    @Override
    public MyResult allTeacherInfo(Integer schoolId, Integer classId) {
        //查询所有老师
        HlTeacherExample example=new HlTeacherExample();
        HlTeacherExample.Criteria criteria = example.createCriteria();
        if (schoolId==null){
            schoolId=SystemParam.getSchoolId();
        }
        List<Map> list=new ArrayList<>();
        if (classId==null){
            criteria.andSchoolidEqualTo(schoolId);
            List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(hlTeachers))return MyResult.failure("没找到该学校的老师信息");
            for (HlTeacher ht:hlTeachers){
                Map map=new HashMap();
                map.put("id",ht.getId());
                map.put("Tname",ht.getTname());
                list.add(map);
            }
        }else {
            List<Map<String, Object>> maps = hlCurriculumMapper.classTeacherId(schoolId, classId);
            if (ObjectUtil.isEmpty(maps))return MyResult.failure("没找到该班级的老师信息");
            for (Map m:maps){
                Map map=new HashMap();
                map.put("id",m.get("ClassTeacher"));
                HlTeacher h=new HlTeacher();
                h.setSchoolid(schoolId);
                h.setId((Integer) m.get("ClassTeacher"));
                HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(h);
                map.put("Tname",hlTeacher.getTname());
                list.add(map);
            }
        }
        return MyResult.success(list);

    }

    /**
     * 添加关注
     * @param videoId
     * @param states    0点播视频，1直播，2名师讲堂，3代表取消关注
     * @return
     */
    @Override
    public MyResult addAttention(Integer videoId,Integer states) {
        Integer childId=0;
        Integer teacherId=0;
        Integer type = SystemParam.getType();
        XnAttentionExample example=new XnAttentionExample();
        XnAttentionExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(states);
        criteria.andCountEqualTo(videoId.toString());
        if (type==1){
            childId = SystemParam.getChildId();
            criteria.andStuIdEqualTo(childId);
        }else {
            teacherId = SystemParam.getUserId();
            criteria.andStuFamilyIdEqualTo(Long.valueOf(teacherId));
        }
        List<XnAttention> xnAttentions = xnAttentionMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnAttentions))return MyResult.failure("您已关注了该视频，不能重复关注");
        XnAttention attention=new XnAttention();
        if (type==1){
            attention.setStuId(childId);
        }else {
            attention.setStuFamilyId((long)SystemParam.getUserId());
        }
        attention.setState(states);
        attention.setCreateTime(new Date());
        attention.setModelfileTime(new Date());
        attention.setCount(videoId.toString());
        int i = xnAttentionMapper.insertSelective(attention);
        if (i==0){
            return MyResult.failure("保存关注视频信息失败");
        }
        return MyResult.success("保存关注视频信息成功");
    }

    /**
     * 取消关注
     * @param videoId
     * @param states
     * @return
     */
    @Override
    public MyResult deleteAttention(Integer videoId, Integer states) {
        Integer userType = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=null;
        XnAttentionExample example=new XnAttentionExample();
        XnAttentionExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(states);
        criteria.andCountEqualTo(videoId.toString());
        if (userType==1){
            userId=SystemParam.getChildId();
            criteria.andStuIdEqualTo(userId);
        }else {
            userId=SystemParam.getUserId();
            criteria.andStuFamilyIdEqualTo(Long.valueOf(userId));
        }
        List<XnAttention> xnAttentions = xnAttentionMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnAttentions))return MyResult.failure("您没有关注该视频，不能取消关注");
        xnAttentions.get(0).setState(3);
        int i = xnAttentionMapper.updateByPrimaryKeySelective(xnAttentions.get(0));
        if (i!=1){
            return MyResult.failure("取消失败");
        }
        return MyResult.success("取消成功");
    }

    /**
     * 我的关注列表
     * @param states 0点播视频，1直播，2名师讲堂，3代表取消关注
     * @return
     */
    @Override
    public MyResult myAttention(Integer states,Integer pageNo,Integer pageSize) {
        Integer childId=0;
        Integer type = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        //查询关注的视频列表
        XnAttentionExample example=new XnAttentionExample();
        XnAttentionExample.Criteria criteria = example.createCriteria();
        if (type==1){
            childId = SystemParam.getChildId();
            criteria.andStuIdEqualTo(childId);
        }else {
            childId = SystemParam.getUserId();
            criteria.andStuFamilyIdEqualTo(Long.valueOf(childId));
        }
        if (states==9){
            criteria.andStateNotEqualTo(3);
        }else {
            criteria.andStateEqualTo(states);
        }
        List<Map> list=new ArrayList<>();
        List<XnAttention> xnAttentions = xnAttentionMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnAttentions))return MyResult.failure("没有找到你关注的视频");
        for (XnAttention xa:xnAttentions){
            //点播视频信息
            if (xa.getState()==0){
                HlVideoinfonewExample example1=new HlVideoinfonewExample();
                example1.createCriteria().andIdEqualTo(Integer.valueOf(xa.getCount()));
                List<HlVideoinfonew> hlVideoinfonews = hlVideoinfonewMapper.selectByExample(example1);
                if (!ObjectUtil.isEmpty(hlVideoinfonews)){
                    Map<String, Object> stringObjectMap = new HashMap<>();
                    stringObjectMap.put("id",hlVideoinfonews.get(0).getId());
                    stringObjectMap.put("img",hlVideoinfonews.get(0).getFileThumbnailsPath());
                    stringObjectMap.put("schoolName",hlVideoinfonews.get(0).getFlrName());
                    stringObjectMap.put("className",hlVideoinfonews.get(0).getClarmName());
                    stringObjectMap.put("schoolId",hlVideoinfonews.get(0).getSchoolid());
                    stringObjectMap.put("classId",hlVideoinfonews.get(0).getPrgClassroomId());
                    stringObjectMap.put("DisciplineNmae",hlVideoinfonews.get(0).getDisciplinenmae());
                    stringObjectMap.put("TName",hlVideoinfonews.get(0).getTname());
                    stringObjectMap.put("YearName",hlVideoinfonews.get(0).getYearname());
                    stringObjectMap.put("Lesson",hlVideoinfonews.get(0).getLesson());
                    stringObjectMap.put("videoState",0);
                    stringObjectMap.put("prg_datetime",hlVideoinfonews.get(0).getPrgDatetime());
                    list.add(stringObjectMap);
                }

            }

            //直播视频信息
            if (xa.getState()==1){
                TerminalInfoExample example3=new TerminalInfoExample();
                example3.createCriteria().andIdEqualTo(Integer.valueOf(xa.getCount()));
                example3.setOrderByClause("id desc");
                List<TerminalInfo> terminalInfos = terminalInfoMapper.selectByExample(example3);
                if (!ObjectUtil.isEmpty(terminalInfos)){
                    for (TerminalInfo ti:terminalInfos){
//                        Map<String, Object> stringObjectMap = new HashMap<>();
                        Map<String, Object> stringObjectMap = searchUtil.videoPath(schoolId, ti);
                        stringObjectMap.put("id",ti.getId());
                        stringObjectMap.put("img",null);
                        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(ti.getSid());
                        if (ObjectUtil.isEmpty(cfDepartment)){
                            stringObjectMap.put("schoolName",null);
                        }else {
                            stringObjectMap.put("schoolName",cfDepartment.getDeptname());
                        }
//                        HlSchclass hs=new HlSchclass();
//                        hs.setId(ti.getId());hs.setSchoolid(ti.getSid());
//                        HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
//                        if (ObjectUtil.isEmpty(hlSchclass)){
//                            stringObjectMap.put("className",null);
//                        }else {
//                            stringObjectMap.put("className",hlSchclass.getClassname());
//                        }
                        stringObjectMap.put("schoolId",ti.getSid());
                        stringObjectMap.put("classId",ti.getId());
                        stringObjectMap.put("videoState",1);
                        list.add(stringObjectMap);
                    }
                }

            }
            //名师讲堂
            if (xa.getState()==2){
                XnTopqualityPersonalRule xtpr = xnTopqualityPersonalRuleMapper.selectByPrimaryKey(Integer.valueOf(xa.getCount()));
                if (!ObjectUtil.isEmpty(xtpr)){
                    Map<String, Object> stringObjectMap= voUtil.pointFocusInfo(xtpr);

                    XnTopqualityClassRuleExample example2=new XnTopqualityClassRuleExample();
                    example2.createCriteria().andSidEqualTo(xtpr.getSid());
                    List<XnTopqualityClassRule> xnTopqualityClassRules = xnTopqualityClassRuleMapper.selectByExample(example2);
                    if (ObjectUtil.isEmpty(xnTopqualityClassRules)){
                        stringObjectMap.put("isPay",1);
                    }else {
                        if (xnTopqualityClassRules.get(0).getIspay()==1){
                            XnFineclassOrderExample example1=new XnFineclassOrderExample();
                            example1.createCriteria().andSidEqualTo(xtpr.getSid()).andUsertypeEqualTo(Byte.valueOf(type.toString()))
                                    .andUseridEqualTo(childId).andAttr1EqualTo("2").andFidEqualTo(xtpr.getId());
                            List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example1);

                            if (!ObjectUtil.isEmpty(xnFineclassOrders)){
                                stringObjectMap.put("isPay",1);
                                stringObjectMap.put("path",xtpr.getAttr1());
                            }else {
                                stringObjectMap.put("isPay",2);
                                stringObjectMap.put("path",null);
                            }
                        }else {
                            stringObjectMap.put("isPay",1);
                        }
                    }
                    stringObjectMap.put("videoState",2);
                    list.add(stringObjectMap);
                }
            }
        }
        if (list.size()==0){
            return MyResult.failure("没有找到视频信息");
        }
        if (pageSize==null){
            pageSize=6;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 登录账户学校的所有直播视频
     * @param schoolId
     * @return
     */
    @Override
    public MyResult searchVideo(Integer schoolId,Integer pageNo,Integer pageSize) {
        if (pageSize==null){
            pageSize=6;
        }
        List<Map> list=new ArrayList<>();
        //我的孩子的直播
        if (SystemParam.getType()==1&&schoolId.equals(SystemParam.getSchoolId())) {
            HlStudentinfo stu = new HlStudentinfo();
            stu.setId(SystemParam.getChildId());
            stu.setSchoolid(schoolId);
            HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(stu);
            HlStudentinfoExample example = new HlStudentinfoExample();
            example.createCriteria().andPhoneEqualTo(hlStudentinfo.getPhone());
            List<HlStudentinfo> list1 = hlStudentinfoMapper.selectByExample(example);

            TerminalInfoExample example3 = new TerminalInfoExample();
            example3.createCriteria().andSidEqualTo(schoolId);
            example3.setOrderByClause("id desc");
            List<TerminalInfo> terminalInfos = terminalInfoMapper.selectByExample(example3);
            if (!ObjectUtil.isEmpty(terminalInfos)) {
                for (TerminalInfo ti : terminalInfos) {
                    Map<String, Object> stringObjectMap = searchUtil.videoPath(schoolId, ti);
                    stringObjectMap.put("servPath1",null);
                    list.add(stringObjectMap);
                }
            }
        }else {
            //查询其他的直播
            TerminalInfoExample example=new TerminalInfoExample();
            example.createCriteria().andSidEqualTo(schoolId);
            example.setOrderByClause("id desc");
            List<TerminalInfo> terminalInfos = terminalInfoMapper.selectByExample(example);
            if (!ObjectUtil.isEmpty(terminalInfos)) {
                for (TerminalInfo ti : terminalInfos) {
                    Map<String, Object> map = searchUtil.videoPath(schoolId, ti);
                    map.put("servPath1",null);
                    list.add(map);
                }
            }
        }
        return ResultPage.getPageResult(list, pageNo, pageSize);
    }

    /**
     * 根据筛选查询直播视频地址
     * @param classId
     * @param schoolId
     * @param conutyId
     * @return
     */
    @Override
    public MyResult classVideo(String classId,String schoolId,String conutyId,Integer pageNo,Integer pageSize) {
        if (pageSize==null){
            pageSize=6;
        }

        //没有乡镇id就查询登录账户的学校直播视频
        if (conutyId==null || "".equals(conutyId)){
            //没有学校id
            if (schoolId==null || "".equals(schoolId)){
                CfDepartmentExample example=new CfDepartmentExample();
                //example.createCriteria().andDeptidEqualTo(1);
                List<CfDepartment> cfDepartments = cfDepartmentMapper.selectByExample(example);
                if (ObjectUtil.isEmpty(cfDepartments))return MyResult.failure("没有找到该乡镇的学校");
                return searchVideo(cfDepartments.get(0).getDeptid(),pageNo,pageSize);
            }else {
                //没有班级id
                if (classId==null || "".equals(classId)){
                    return searchVideo(Integer.valueOf(schoolId),pageNo,pageSize);
                }else {
                    /*TerminalInfo tif=new TerminalInfo();
                    tif.setId(Integer.valueOf(classId));tif.setSid(Integer.valueOf(schoolId));
                    TerminalInfo terminalInfo = terminalInfoMapper.selectByPrimaryKey(tif);*/
                    TerminalInfoExample example=new TerminalInfoExample();
                    example.createCriteria().andTerminalClassroomIdEqualTo(Integer.valueOf(classId)).andSidEqualTo(Integer.valueOf(schoolId));
                    List<TerminalInfo> list1 = terminalInfoMapper.selectByExample(example);
                    if (ObjectUtil.isEmpty(list1))return MyResult.failure("数据库没有数据");
                    Map<String, Object> stringObjectMap = searchUtil.videoPath(Integer.valueOf(schoolId), list1.get(0));
                    stringObjectMap.put("servPath1",null);
                    List list=new ArrayList();
                    list.add(stringObjectMap);
                    return MyResult.success(list);
                }
            }
        }else {
            //没有学校id
            if (schoolId==null || "".equals(schoolId)){
                CfDepartmentExample example=new CfDepartmentExample();
                example.createCriteria().andCountyidEqualTo(Integer.valueOf(conutyId));
                List<CfDepartment> cfDepartments = cfDepartmentMapper.selectByExample(example);
                if (ObjectUtil.isEmpty(cfDepartments))return MyResult.failure("没有找到该乡镇的学校");
                return searchVideo(cfDepartments.get(0).getDeptid(),pageNo,pageSize);
            }else {
                //没有班级id
                if (classId==null || "".equals(classId)){
                    return searchVideo(Integer.valueOf(schoolId),pageNo,pageSize);
                }else {
                    /*TerminalInfo tif=new TerminalInfo();
                    tif.setId(Integer.valueOf(classId));tif.setSid(Integer.valueOf(schoolId));
                    TerminalInfo terminalInfo = terminalInfoMapper.selectByPrimaryKey(tif);
                    if (ObjectUtil.isEmpty(terminalInfo))return MyResult.failure("数据库没有数据");

                    Map<String, Object> stringObjectMap = searchUtil.videoPath(Integer.valueOf(schoolId), terminalInfo);*/
                    TerminalInfoExample example=new TerminalInfoExample();
                    example.createCriteria().andTerminalClassroomIdEqualTo(Integer.valueOf(classId)).andSidEqualTo(Integer.valueOf(schoolId));
                    List<TerminalInfo> list1 = terminalInfoMapper.selectByExample(example);
                    if (ObjectUtil.isEmpty(list1))return MyResult.failure("数据库没有数据");
                    Map<String, Object> stringObjectMap = searchUtil.videoPath(Integer.valueOf(schoolId), list1.get(0));
                    stringObjectMap.put("servPath1",null);
                    List list=new ArrayList();
                    list.add(stringObjectMap);
                    return MyResult.success(list);
                }

            }
        }
    }


    /**
     * 试用
     * @param videoClassId
     * @return
     */
    @Override
    public MyResult addTestDays(Integer videoClassId,Integer videoSchoolId) {
        Integer type = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        Integer uid =0;
        if (type==1){
            uid = SystemParam.getChildId();
        }else {
            uid=SystemParam.getUserId();
        }

        //查询规则信息
        XnVideoRuleExample example=new XnVideoRuleExample();
        example.createCriteria().andAttr1EqualTo("1").andSidEqualTo(videoSchoolId).andUsertypeEqualTo(Byte.valueOf(type.toString()))
        .andPayruleEqualTo((byte)1);
        List<XnVideoRule> xnVideoRules = xnVideoRuleMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnVideoRules))return MyResult.failure("该视频没有试用");
        //查询是否已经有试用
        XnVideoOrderExample example1=new XnVideoOrderExample();
        example1.createCriteria().andUidEqualTo(uid).andSidEqualTo(schoolId).andTargetsubjectEqualTo("1")
                .andUsertypeEqualTo(type).andTargettidEqualTo(videoClassId);
        List<XnVideoOrder> xnVideoOrders = xnVideoOrderMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(xnVideoOrders)){
            XnVideoOrder xv=new XnVideoOrder();
            xv.setSid(schoolId);
            xv.setUsertype(type);
            xv.setUid(uid);
            xv.setTargetsid(videoSchoolId);
            xv.setTargetsubject("1");
            xv.setTargettid(videoClassId);
            xv.setAttr1("2");
            //过期时间
            /*Date d=new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String format = df.format(new Date(d.getTime() +xnVideoRules.get(0).getTestdays() * 24 * 60 * 60 * 1000L));*/
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.DATE,xnVideoRules.get(0).getTestdays());
            Date time = calendar.getTime();
            xv.setAttr3(TimeUtil.TimeToYYYYMMDDHHMMSS(time));
            int i = xnVideoOrderMapper.insertSelective(xv);
            if (i!=1){
                return MyResult.failure("保存试用信息失败");
            }else {
                return MyResult.success("试用成功",xnVideoRules.get(0).getTestdays());
            }
        }else {
            return MyResult.failure("已有试用期，不能试用了");
        }
    }

    /**
     * 用户申请看视频权限
     * @param videoClassId
     * @param videoSchoolId
     * @return
     */
    @Override
    public MyResult videoCheck(Integer videoClassId,Integer videoSchoolId) {
        //用户信息
        Integer type = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=null;
        if (type==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        };
        XnVideoCheckExample example=new XnVideoCheckExample();
        example.createCriteria().andTypeidEqualTo(videoClassId).andUidEqualTo(userId).andSidEqualTo(schoolId)
                .andUsertypeEqualTo(type).andAttr1EqualTo(videoSchoolId.toString());
        List<XnVideoCheck> xnVideoChecks = xnVideoCheckMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnVideoChecks)){
            if (xnVideoChecks.get(0).getCheckstatus()==1){
                return MyResult.failure("你的申请待审核中，不能再发送");
            }else if (xnVideoChecks.get(0).getCheckstatus()==2) {
                return MyResult.failure("已通过了审核，不能再申请了");
            }else if (xnVideoChecks.get(0).getCheckstatus()==3) {
                return MyResult.failure("审核未通过，请联系老师");
            }
        }
        XnVideoCheck xvc=new XnVideoCheck();
        xvc.setSid(schoolId);
        xvc.setUsertype(type);
        xvc.setUid(userId);
        xvc.setTypeid(videoClassId);
        xvc.setAttr1(videoSchoolId.toString());
        xvc.setCheckstatus(1);
        xvc.setCreatetime(new Date());
        int i = xnVideoCheckMapper.insertSelective(xvc);
        if (i!=1){
            return MyResult.failure("申请失败，请重试");
        }
        return MyResult.success("申请成功，等待审核");
    }

    /**
     * 查询视频费用
     * @param type
     * @param videoId
     * @param videoClassId
     * @param videoSchoolId
     * @return
     */
    @Override
    public MyResult videoPrice(Integer type, Integer videoId, Integer videoClassId, Integer videoSchoolId) {
        List list=new ArrayList();
        if (type==1){
            /*XnVideoRuleExample example=new XnVideoRuleExample();
            example.createCriteria().andAttr1EqualTo("1").andSidEqualTo(videoSchoolId).andUsertypeEqualTo(Byte.valueOf(SystemParam.getType().toString()));
            List<XnVideoRule> xnVideoRules = xnVideoRuleMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnVideoRules))return MyResult.failure("没找到视频规则信息");
            XnVideoRulePayExample example1=new XnVideoRulePayExample();
            example1.createCriteria().andRuleidEqualTo(xnVideoRules.get(0).getId());
            List<XnVideoRulePay> xnVideoRulePays = xnVideoRulePayMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(xnVideoRules))return MyResult.failure("没找到视频收费信息");*/
            XnMealExample example=new XnMealExample();
            example.createCriteria().andAttr1EqualTo(String.valueOf(SystemParam.getType()));
            List<XnMeal> xnMeals = xnMealMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnMeals)){
                return MyResult.failure("没找到视频收费信息");
            }
            for (XnMeal xm:xnMeals){
                Map map=new HashMap();
                map.put("meilId",xm.getId());
                map.put("videoId",videoId);
                map.put("videoClassId",videoClassId);
                map.put("videoSchoolId",videoSchoolId);
                map.put("timeType",xm.getTimetype());
                map.put("price",xm.getPrice());
                CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(videoSchoolId);
                if (ObjectUtil.isEmpty(cfDepartment)){
                    map.put("targetSid",null);
                }else {
                    map.put("targetSid",cfDepartment.getDeptname());
                }
                HlSchclassExample example2=new HlSchclassExample();
                example2.createCriteria().andSchoolidEqualTo(videoSchoolId).andIdEqualTo(videoClassId);
                List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(example2);
                if (ObjectUtil.isEmpty(hlSchclasses)){
                    map.put("classname",null);
                }else {
                    map.put("classname",hlSchclasses.get(0).getClassname());
                }
                map.put("ClassImg",null);
                map.put("subject","班级视频权限");
                list.add(map);
            }
            /*for (XnVideoRulePay xvrp:xnVideoRulePays){
                Map map=new HashMap();
                map.put("videoId",videoId);
                map.put("videoClassId",videoClassId);
                map.put("videoSchoolId",videoSchoolId);
                map.put("timeType",xvrp.getTimetype());
                map.put("price",xvrp.getPrice());
                CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(videoSchoolId);
                if (ObjectUtil.isEmpty(cfDepartment)){
                    map.put("targetSid",null);
                }else {
                    map.put("targetSid",cfDepartment.getDeptname());
                }
                HlSchclassExample example2=new HlSchclassExample();
                example2.createCriteria().andSchoolidEqualTo(videoSchoolId).andIdEqualTo(videoClassId);
                List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(example2);
                if (ObjectUtil.isEmpty(hlSchclasses)){
                    map.put("classname",null);
                }else {
                    map.put("classname",hlSchclasses.get(0).getClassname());
                }
                map.put("ClassImg",null);
                map.put("subject","班级视频权限");
                list.add(map);
            }
            */
        }else {
            XnTopqualityPersonalRule xnTopqualityPersonalRule = xnTopqualityPersonalRuleMapper.selectByPrimaryKey(videoId);
            Map map=new HashMap();
            map.put("videoId",videoId);
            map.put("videoClassId",videoClassId);
            map.put("videoSchoolId",videoSchoolId);
            map.put("timeType",null);
            if (SystemParam.getType()==1){
                map.put("price",xnTopqualityPersonalRule.getSprice());
            }else {
                map.put("price",xnTopqualityPersonalRule.getTprice());
            }
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(videoSchoolId);
            if (ObjectUtil.isEmpty(cfDepartment)){
                map.put("targetSid",null);
            }else {
                map.put("targetSid",cfDepartment.getDeptname());
            }
            HlTeacher ht=new HlTeacher();
            ht.setId(xnTopqualityPersonalRule.getTid());
            ht.setSchoolid(xnTopqualityPersonalRule.getSid());
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            if (ObjectUtil.isEmpty(hlTeacher)){
                map.put("teaName",null);
            }else {
                map.put("teaName",hlTeacher.getTname());
            }
            map.put("classname","名师讲堂");
            map.put("ClassImg",xnTopqualityPersonalRule.getAttr3());
            map.put("subject",xnTopqualityPersonalRule.getSubject());
            list.add(map);
        }
        return MyResult.success(list);
    }

    /**
     * 保存精品课堂订单
     * @param videoId
     * @return
     */
    @Override
    public MyResult savePointFocusOrder(Integer videoId,String price) {
        Integer userType = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=null;
        String byUUId = WXUtil.getByUUId()+"e";
        int i=0;
        int cid=0;
        if (userType==1){
            userId=SystemParam.getChildId();
            HlStudentinfo hlStudentinfo=new HlStudentinfo();
            hlStudentinfo.setId(userId);hlStudentinfo.setSchoolid(schoolId);
            HlStudentinfo hlStudentinfo1 = hlStudentinfoMapper.selectByPrimaryKey(hlStudentinfo);
            cid=hlStudentinfo1.getClassid();
        }else {
            userId=SystemParam.getUserId();
        }
        XnFineclassOrderExample example=new XnFineclassOrderExample();
        example.createCriteria().andSidEqualTo(schoolId).andFidEqualTo(videoId).andUsertypeEqualTo(Byte.parseByte(userType.toString()))
                .andUseridEqualTo(userId).andAttr1EqualTo("2");
        List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnFineclassOrders))return MyResult.success(xnFineclassOrders.get(0).getAttr3());
        XnFineclassOrder xfo=new XnFineclassOrder();
        xfo.setSid(schoolId);
        xfo.setCid(cid);
        xfo.setFid(videoId);
        xfo.setUsertype(Byte.parseByte(userType.toString()));
        xfo.setUserid(userId);
        xfo.setOrdernumber(userId);
        xfo.setBuytype(1);
        xfo.setCreatetime(new Date());
        xfo.setAttr1("1");
        xfo.setAttr2(price);
        xfo.setAttr3(byUUId);
        i=xnFineclassOrderMapper.insertSelective(xfo);
        if (i!=1){
            return MyResult.failure("保存订单失败");
        }else {
            return fineClassOrder(byUUId);
        }
    }

    /**
     * 保存视频订单
     * @param type 1、年 2、月
     * @param videoClassId
     * @param videoSchoolId
     * @return
     */
    @Override
    public MyResult saveVideoOrder(Integer type,Integer num,Integer videoId, Integer videoClassId, Integer videoSchoolId,String price) {
        Integer userType = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=null;
        String byUUId = WXUtil.getByUUId()+"c";
        String phone=null;
        Integer cid=0;
        int i=0;
        if (userType==1){
            userId=SystemParam.getChildId();
            HlStudentinfo hlStudentinfo=new HlStudentinfo();
            hlStudentinfo.setId(userId);hlStudentinfo.setSchoolid(schoolId);
            HlStudentinfo hlStudentinfo1 = hlStudentinfoMapper.selectByPrimaryKey(hlStudentinfo);
            phone=hlStudentinfo1.getPhone();
            cid=hlStudentinfo1.getClassid();
        }else {
            userId=SystemParam.getUserId();
            HlTeacher hlTeacher=new HlTeacher();
            hlTeacher.setSchoolid(schoolId);hlTeacher.setId(userId);
            HlTeacher hlTeacher1 = hlTeacherMapper.selectByPrimaryKey(hlTeacher);
            phone=hlTeacher1.getMobile();
        }
        XnVideoOrderExample example=new XnVideoOrderExample();
        example.createCriteria().andSidEqualTo(schoolId).andUsertypeEqualTo(userType).andUidEqualTo(userId)
                .andTargetsidEqualTo(videoSchoolId).andTargetsubjectEqualTo("2").andBuytypeNotEqualTo(3).andAttr1EqualTo("1");
        List<XnVideoOrder> xnVideoOrders = xnVideoOrderMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnVideoOrders))return searchByOrder(xnVideoOrders.get(0).getOrdernumber());
        //查询收费规则
        XnVideoRuleExample example1=new XnVideoRuleExample();
        example1.createCriteria().andAttr1EqualTo("1").andSidEqualTo(videoSchoolId).andUsertypeEqualTo(Byte.valueOf(userType.toString()));
        List<XnVideoRule> xnVideoRules = xnVideoRuleMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(xnVideoRules))return MyResult.failure("没找到视频规则信息");

        XnVideoOrder xo=new XnVideoOrder();
        xo.setSid(schoolId);
        xo.setCid(cid);
        xo.setOrdernumber(byUUId);
        xo.setUsertype(userType);
        xo.setPhone(phone);
        xo.setUid(userId);
        xo.setBuytype(1);
        xo.setTargetsid(videoSchoolId);
        xo.setBuyusertype(1);
        xo.setBuyuserid(SystemParam.getUserId());
        xo.setTargetsubject("2");
        xo.setTargettid(videoClassId);
        xo.setCreatetime(new Date());
        xo.setAttr1("1");
        xo.setAttr2(price);
        if (type==1){
            xo.setAttr3(String.valueOf(12*num));
        }else {
            xo.setAttr3(String.valueOf(num));
        }
        i = xnVideoOrderMapper.insertSelective(xo);

        if (i!=1){
            return MyResult.failure("保存订单失败");
        }else {
            return searchByOrder(byUUId);
        }
    }

    /**
     * 查询所有的点播视频信息
     * @param schoolId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult allPrg(Integer schoolId,Integer pageNo,Integer pageSize) {
        HlVideoinfonewExample example=new HlVideoinfonewExample();
        example.createCriteria().andSchoolidEqualTo(schoolId).andPrgStatusEqualTo(2);
        example.setOrderByClause("id desc");
        List<HlVideoinfonew> hlVideoinfonews = hlVideoinfonewMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(hlVideoinfonews))return MyResult.failure("没有找到点播视频信息");
        List<Map> list=new ArrayList<>();
        for (HlVideoinfonew hv:hlVideoinfonews){
            Map<String, Object> stringObjectMap = voUtil.prgInfo(hv);
            stringObjectMap.put("file_path",null);
            stringObjectMap.put("file_absolute_path",null);
            list.add(stringObjectMap);

        }
        if (pageSize==null){
            pageSize=6;
        }
        return ResultPage.getPageResult(list, pageNo, pageSize);
    }

    /**
     * 筛选点播视频
     * @param classId
     * @param schoolId
     * @param conutyId
     * @param teaId
     * @param subId
     * @param timeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult screenPrg(String classId, String schoolId, String conutyId,String teaId,String subId,String timeId, String prgDatetime, Integer pageNo, Integer pageSize) {
        if (pageSize==null){
            pageSize=6;
        }
        HlVideoinfonewExample example = new HlVideoinfonewExample();
        HlVideoinfonewExample.Criteria criteria = example.createCriteria();
        if (conutyId!=null && !"null".equals(conutyId)){
            criteria.andBuildNameEqualTo(conutyId);
        }
        if(schoolId==null || "null".equals(schoolId)){
            schoolId= String.valueOf(SystemParam.getSchoolId());
        }
        if (classId!=null&& !"null".equals(classId)){
            criteria.andPrgClassroomIdEqualTo(Integer.valueOf(classId));
        }
        if (teaId!=null&& !"null".equals(teaId)){
            criteria.andClassteacherEqualTo(teaId);
        }
        if (subId!=null&& !"null".equals(subId)){
            criteria.andDisciplinenmaeEqualTo(subId);
        }
        if (timeId!=null&& !"null".equals(timeId)){
            criteria.andLessonEqualTo(Integer.valueOf(timeId));
        }
//        prgDatetime
        if (prgDatetime!=null&& !"null".equals(prgDatetime)){
            criteria.andPrgDatetimeEqualTo(prgDatetime);
        }
        criteria.andSchoolidEqualTo(Integer.valueOf(schoolId));
        criteria.andPrgStatusEqualTo(2);
        example.setOrderByClause("newID desc");
        List<HlVideoinfonew> hlVideoinfonews = hlVideoinfonewMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(hlVideoinfonews)) return MyResult.failure("没有找到点播视频信息");
        List<Map> list = new ArrayList<>();
        for (HlVideoinfonew hv:hlVideoinfonews){
            Map<String, Object> stringObjectMap = voUtil.prgInfo(hv);
            stringObjectMap.put("file_path",null);
            stringObjectMap.put("file_absolute_path",null);
            list.add(stringObjectMap);
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }


    /**
     * 点播、直播权限判断
     * @param videoClassId
     * @param videoSchoolId
     * @return
     */
    @Override
    public MyResult isRule(Integer videoId,Integer videoType,Integer videoSchoolId,Integer videoClassId){
        Integer type = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=null;
        if (type==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        Map<String, Object> stringObjectMap=null;
        if (videoType==1){
            HlVideoinfonewExample example=new HlVideoinfonewExample();
            example.createCriteria().andIdEqualTo(videoId).andSchoolidEqualTo(videoSchoolId).andPrgClassroomIdEqualTo(videoClassId);
            List<HlVideoinfonew> hlVideoinfonews = hlVideoinfonewMapper.selectByExample(example);

            stringObjectMap = voUtil.prgInfo(hlVideoinfonews.get(0));
        }else {
            TerminalInfo t=new TerminalInfo();
            t.setSid(videoSchoolId);
            t.setId(videoId);
            TerminalInfo terminalInfo = terminalInfoMapper.selectByPrimaryKey(t);
            stringObjectMap=searchUtil.videoPath(videoSchoolId,terminalInfo);
        }


        Map map=new HashMap();
        //查询规则信息
        XnVideoRuleExample example=new XnVideoRuleExample();
        example.createCriteria().andAttr1EqualTo("1").andSidEqualTo(videoSchoolId).andUsertypeEqualTo(Byte.valueOf(type.toString()));
        List<XnVideoRule> xnVideoRules = xnVideoRuleMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnVideoRules)){
            switch (xnVideoRules.get(0).getPayrule()) {
                //免费
                case 3:
                    if (xnVideoRules.get(0).getOwnapply()==0){
                        stringObjectMap.put("isRule",1);
                        addCheckNum(videoId, videoType, videoSchoolId, videoClassId);
                        return  MyResult.success(stringObjectMap);
                    }
                    XnVideoCheckExample example1=new XnVideoCheckExample();
                    example1.createCriteria().andSidEqualTo(schoolId).andUsertypeEqualTo(type).andUidEqualTo(userId)
                            .andTypeidEqualTo(videoClassId);
                    List<XnVideoCheck> xnVideoChecks = xnVideoCheckMapper.selectByExample(example1);
                    //跳转申请页面
                    if (ObjectUtil.isEmpty(xnVideoChecks)){
                        map.put("isRule",2);
                        return  MyResult.success(map);
                    }else {
                        //正常播放
                        if (xnVideoChecks.get(0).getCheckstatus()==2){
                            stringObjectMap.put("isRule",1);
                            addCheckNum(videoId, videoType, videoSchoolId, videoClassId);
                            return  MyResult.success(stringObjectMap);
                        }
                        //申请审核中
                        else if (xnVideoChecks.get(0).getCheckstatus()==1){
                            map.put("isRule",3);
                            return  MyResult.success(map);
                        }
                        //审核不通过
                        else if (xnVideoChecks.get(0).getCheckstatus()==3){
                            map.put("isRule",4);
                            return  MyResult.success(map);
                        }
                    }
                    break;
                //收费
                case 2:
                    XnMailOrder xnMailOrder =null;
                    if (type==2){
                        XnMailOrderExample example2=new XnMailOrderExample();
                        example2.createCriteria().andSidEqualTo(schoolId).andUserTypeEqualTo(type).andUseridEqualTo(userId)
                                .andOrderStatusEqualTo(2);
                        example2.setOrderByClause("pay_time desc");
                        List<XnMailOrder> xnMailOrders = xnMailOrderMapper.selectByExample(example2);
                        if (ObjectUtil.isEmpty(xnMailOrders)){
                            map.put("isRule",5);
                            return  MyResult.success(map);
                        }
                        xnMailOrder=xnMailOrders.get(0);
                    }else {
                        XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(SystemParam.getUserId());
                        if (ObjectUtil.isEmpty(xnStuFamilyinfo.getOid())){
                            map.put("isRule",5);
                            return  MyResult.success(map);
                        }
                         xnMailOrder = xnMailOrderMapper.selectByPrimaryKey(xnStuFamilyinfo.getOid());
                    }

                    try {
                        if (TimeUtil.YYYYMMDDHHMMSSToTime(xnMailOrder.getAttr1()).before(new Date())){
                            map.put("isRule",6);
                            return  MyResult.success(map);
                        }else {
                            stringObjectMap.put("isRule",1);
                            addCheckNum(videoId, videoType, videoSchoolId, videoClassId);
                            return  MyResult.success(stringObjectMap);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                    //收费加试用
                case 1:
                    //查看试用
                    XnVideoOrderExample example3=new XnVideoOrderExample();
                    example3.createCriteria().andUidEqualTo(userId).andSidEqualTo(schoolId)
                            .andUsertypeEqualTo(type).andTargetsidEqualTo(videoSchoolId).andTargetsubjectEqualTo("1");
                    List<XnVideoOrder> xnVideoOrders1 = xnVideoOrderMapper.selectByExample(example3);
                    if (ObjectUtil.isEmpty(xnVideoOrders1)){
                        //查看是否购买套餐
                        XnMailOrder xnMailOrder2 =null;
                        if (type==2){
                            XnMailOrderExample example2=new XnMailOrderExample();
                            example2.createCriteria().andSidEqualTo(schoolId).andUserTypeEqualTo(type).andUseridEqualTo(userId)
                                    .andOrderStatusEqualTo(2);
                            example2.setOrderByClause("pay_time desc");
                            List<XnMailOrder> xnMailOrders = xnMailOrderMapper.selectByExample(example2);
                            if (ObjectUtil.isEmpty(xnMailOrders)){
                                //弹窗试用或购买
                                map.put("isRule",7);
                                return  MyResult.success(map);
                            }
                            xnMailOrder2=xnMailOrders.get(0);
                        }else {
                            XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(SystemParam.getUserId());
                            if (ObjectUtil.isEmpty(xnStuFamilyinfo.getOid())){
                                //弹窗试用或购买
                                map.put("isRule",7);
                                return  MyResult.success(map);
                            }
                            xnMailOrder2 = xnMailOrderMapper.selectByPrimaryKey(xnStuFamilyinfo.getOid());
                        }
                        try {
                            if (TimeUtil.YYYYMMDDHHMMSSToTime(xnMailOrder2.getAttr1()).before(new Date())){
                                //弹窗试用或购买
                                map.put("isRule",7);
                                return  MyResult.success(map);
                            }else {
                                stringObjectMap.put("isRule",1);
                                addCheckNum(videoId, videoType, videoSchoolId, videoClassId);
                                return  MyResult.success(stringObjectMap);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            if (TimeUtil.YYYYMMDDHHMMSSToTime(xnVideoOrders1.get(0).getAttr1()).before(new Date())){
                                XnMailOrder xnMailOrder2 =null;
                                if (type==2){
                                    XnMailOrderExample example2=new XnMailOrderExample();
                                    example2.createCriteria().andSidEqualTo(schoolId).andUserTypeEqualTo(type).andUseridEqualTo(userId)
                                            .andOrderStatusEqualTo(2);
                                    example2.setOrderByClause("pay_time desc");
                                    List<XnMailOrder> xnMailOrders = xnMailOrderMapper.selectByExample(example2);
                                    if (ObjectUtil.isEmpty(xnMailOrders)){
                                        map.put("isRule",5);
                                        return  MyResult.success(map);
                                    }
                                    xnMailOrder2=xnMailOrders.get(0);
                                }else {
                                    XnStuFamilyinfo xnStuFamilyinfo = xnStuFamilyinfoMapper.selectByPrimaryKey(SystemParam.getUserId());
                                    if (ObjectUtil.isEmpty(xnStuFamilyinfo.getOid())) {
                                        map.put("isRule",5);
                                        return  MyResult.success(map);
                                    }
                                    xnMailOrder2 = xnMailOrderMapper.selectByPrimaryKey(xnStuFamilyinfo.getOid());
                                }
                                if (TimeUtil.YYYYMMDDHHMMSSToTime(xnMailOrder2.getAttr1()).before(new Date())){
                                    map.put("isRule",5);
                                    return  MyResult.success(map);
                                }else {
                                    stringObjectMap.put("isRule",1);
                                    addCheckNum(videoId, videoType, videoSchoolId, videoClassId);
                                    return  MyResult.success(stringObjectMap);
                                }
                            }else {
                                stringObjectMap.put("isRule",1);
                                addCheckNum(videoId, videoType, videoSchoolId, videoClassId);
                                return  MyResult.success(stringObjectMap);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        stringObjectMap.put("isRule",1);
        addCheckNum(videoId, videoType, videoSchoolId, videoClassId);
        return  MyResult.success(stringObjectMap);
    }
    /**
     * 名师讲堂筛选条件
     * @return
     */
    @Override
    public MyResult topqualityLevel(Integer schoolId,String subject1) {
        List<Map<String, Object>> sid = xnTopqualityPersonalRuleMapper.sid();
        List<Map<String, Object>> subject = new ArrayList<>();
        List<Map<String, Object>> tid = new ArrayList<>();
        if (schoolId==0){
            subject=xnTopqualityPersonalRuleMapper.subject();
            if (subject1==null || "".equals(subject1) || "null".equals(subject1)){
                tid=xnTopqualityPersonalRuleMapper.tid();
            }else {
               tid=xnTopqualityPersonalRuleMapper.subtid(subject1);
            }
        }else {
            if (subject1==null || "".equals(subject1) || "null".equals(subject1)){
                tid=xnTopqualityPersonalRuleMapper.schooltid(schoolId);
            }else {
                tid=xnTopqualityPersonalRuleMapper.schoolSubTid(schoolId,subject1);
            }
            subject=xnTopqualityPersonalRuleMapper.schoolsubject(schoolId);
        }
        List list=new ArrayList();
        if (!ObjectUtil.isEmpty(sid)){
            for (Map m1:sid){
                CfDepartment sid1 = cfDepartmentMapper.selectByPrimaryKey((int) m1.get("sid"));
                if (!ObjectUtil.isEmpty(sid1)){
                    Map m=new HashMap();
                    m.put("schoolId",m1.get("sid"));
                    m.put("schoolName",sid1.getDeptname());
                    list.add(m);
                }
            }
        }
        List list1=new ArrayList();
        if (!ObjectUtil.isEmpty(tid)){
            for (Map map:tid){
                HlTeacher ht=new HlTeacher();
                ht.setSchoolid((int)map.get("sid"));
                ht.setId((int)map.get("tid"));
                HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
                if (!ObjectUtil.isEmpty(hlTeacher)){
                    Map m=new HashMap();
                    m.put("teaId",map.get("tid"));
                    m.put("teaName",hlTeacher.getTname());
                    list1.add(m);
                }
            }
        }
        Map map=new HashMap();
        map.put("schoolInfo",list);
        map.put("teaInfo",list1);
        map.put("subInfo",subject);
        return MyResult.success(map);
    }

    /**
     * 名师讲堂
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult pointFocus(Integer schoolId1,String subject,Integer teaId,Integer pageNo,Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        Integer userId=null;
        if (type==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        List<Map> list=new ArrayList<>();
        XnFineclassOrderExample example1=new XnFineclassOrderExample();
        example1.createCriteria().andSidEqualTo(schoolId).andUsertypeEqualTo(Byte.valueOf(type.toString()))
        .andUseridEqualTo(userId).andAttr1EqualTo("2");
        List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example1);
        List l=new ArrayList();
        if (!ObjectUtil.isEmpty(xnFineclassOrders)){
            for (XnFineclassOrder xfo:xnFineclassOrders){
                XnTopqualityPersonalRule xnTopqualityPersonalRule = xnTopqualityPersonalRuleMapper.selectByPrimaryKey(xfo.getFid());
                Map<String, Object> stringObjectMap = voUtil.pointFocusInfo(xnTopqualityPersonalRule);
                stringObjectMap.put("isPay",1);
                list.add(stringObjectMap);
                l.add(xfo.getFid());
            }
        }

        XnTopqualityPersonalRuleExample example=new XnTopqualityPersonalRuleExample();
        XnTopqualityPersonalRuleExample.Criteria criteria = example.createCriteria();
        if (schoolId1!=0){
            criteria.andSidEqualTo(schoolId1);
        }
        if (subject!=null && !"".equals(subject) && !"null".equals(subject)){
            criteria.andSubjectEqualTo(subject);
        }
        if (teaId!=0){
            criteria.andTidEqualTo(teaId);
        }
        if (l.size()!=0){
            criteria.andIdNotIn(l);
        }
        example.setOrderByClause("createTime desc");
        List<XnTopqualityPersonalRule> result = xnTopqualityPersonalRuleMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(result)){
            for (XnTopqualityPersonalRule xt:result){
                Map<String, Object> stringObjectMap = voUtil.pointFocusInfo(xt);
                XnTopqualityClassRuleExample example2=new XnTopqualityClassRuleExample();
                example2.createCriteria().andSidEqualTo(xt.getSid());
                List<XnTopqualityClassRule> xnTopqualityClassRules = xnTopqualityClassRuleMapper.selectByExample(example2);
                if (ObjectUtil.isEmpty(xnTopqualityClassRules)){
                    stringObjectMap.put("isPay",1);
                }else {
                    if (xnTopqualityClassRules.get(0).getIspay()==1){
                        stringObjectMap.put("isPay",2);
                        stringObjectMap.put("path",null);
                    }else {
                        stringObjectMap.put("isPay",1);
                    }
                }
                list.add(stringObjectMap);
            }
        }
        if (pageSize==null){
            pageSize=6;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 直播、点播订单查询
     * @param byuuid
     * @return
     */
    @Override
    public MyResult searchByOrder(String byuuid) {
        Integer type = SystemParam.getType();
        Integer userId=null;
        Integer schoolId=SystemParam.getSchoolId();
        if(type==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        XnVideoOrderExample example=new XnVideoOrderExample();
        example.createCriteria().andSidEqualTo(schoolId).andOrdernumberEqualTo(byuuid)
                .andUsertypeEqualTo(type).andUidEqualTo(userId);
        List<XnVideoOrder> xnVideoOrders = xnVideoOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnVideoOrders))return MyResult.failure("没找到该订单信息");
        Map<String,Object> map=new HashMap<>();
        map.put("id",xnVideoOrders.get(0).getId());
        map.put("orderStatus",xnVideoOrders.get(0).getAttr1());
        map.put("price",xnVideoOrders.get(0).getAttr2());
        map.put("expireTime",xnVideoOrders.get(0).getAttr3());
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(xnVideoOrders.get(0).getTargetsid());
        if (ObjectUtil.isEmpty(cfDepartment)){
            map.put("targetSid",null);
        }else {
            map.put("targetSid",cfDepartment.getDeptname());
        }
        HlSchclassExample example1=new HlSchclassExample();
        example1.createCriteria().andSchoolidEqualTo(xnVideoOrders.get(0).getSid()).andIdEqualTo(xnVideoOrders.get(0).getTargettid());
        List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(hlSchclasses)){
            map.put("targetTid",null);
        }else {
            map.put("targetTid",hlSchclasses.get(0).getClassname());
        }
        map.put("tradeNo",xnVideoOrders.get(0).getOrdernumber());
        map.put("product_sys",3);
        map.put("attach",cfDepartment.getDeptname()+"视频权限");
        return MyResult.success(map);
    }

    /**
     * 名师讲堂订单查询
     * @param byuuid
     * @return
     */
    @Override
    public MyResult fineClassOrder(String byuuid) {
        Integer type = SystemParam.getType();
        Integer userId=null;
        Integer schoolId=SystemParam.getSchoolId();
        Map<String, Object> stringObjectMap=new HashMap<>();
        if(type==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        XnFineclassOrderExample example=new XnFineclassOrderExample();
        example.createCriteria().andAttr3EqualTo(byuuid).andUsertypeEqualTo(Byte.valueOf(type.toString()))
        .andUseridEqualTo(userId).andSidEqualTo(schoolId);
        List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnFineclassOrders))return MyResult.failure("没找到订单信息");
        XnTopqualityPersonalRule xtr = xnTopqualityPersonalRuleMapper.selectByPrimaryKey(xnFineclassOrders.get(0).getFid());
        if (ObjectUtil.isEmpty(xtr))return MyResult.failure("没找到该视频信息");
        if (type==1){
            stringObjectMap.put("price",xtr.getSprice());
        }else {
            stringObjectMap.put("price",xtr.getTprice());
        }
        stringObjectMap.put("orderStatus",xnFineclassOrders.get(0).getAttr1());
        stringObjectMap.put("id",xnFineclassOrders.get(0).getId());
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(xtr.getSid());
        if (ObjectUtil.isEmpty(cfDepartment)){
            stringObjectMap.put("targetSid",null);
        }else {
            stringObjectMap.put("targetSid",cfDepartment.getDeptname());
        }
        HlTeacher ht=new HlTeacher();
        ht.setId(xtr.getTid());
        ht.setSchoolid(xtr.getSid());
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
        if (ObjectUtil.isEmpty(hlTeacher)){
            stringObjectMap.put("teaName",null);
        }else {
            stringObjectMap.put("teaName",hlTeacher.getTname());
        }
        stringObjectMap.put("ClassImg",xtr.getAttr3());
        stringObjectMap.put("subject",xtr.getSubject());
        stringObjectMap.put("tradeNo",xnFineclassOrders.get(0).getAttr3());
        stringObjectMap.put("product_sys",5);
        stringObjectMap.put("attach",cfDepartment.getDeptname()+"-"+xtr.getSubject());
        return MyResult.success(stringObjectMap);
    }

    /**
     * 改变订单状态
     * @param orderType
     * @param orderId
     * @return
     */
    @Override
    public MyResult changeOrderStatus(Integer orderType, String orderId) {
        int i=0;
        if (orderType==1){
            XnVideoOrderExample example=new XnVideoOrderExample();
            example.createCriteria().andOrdernumberEqualTo(orderId);
            List<XnVideoOrder> xnVideoOrders = xnVideoOrderMapper.selectByExample(example);

            if (ObjectUtil.isEmpty(xnVideoOrders))return MyResult.failure("没找到该订单信息");
            XnVideoOrder xnVideoOrder =xnVideoOrders.get(0);
            xnVideoOrder.setBuytime(new Date());
            xnVideoOrder.setAttr1("2");
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MONTH,Integer.valueOf(xnVideoOrder.getAttr3()));
            Date time = calendar.getTime();
            xnVideoOrder.setAttr3(TimeUtil.TimeToYYYYMMDDHHMMSS(time));
            i=xnVideoOrderMapper.updateByPrimaryKeySelective(xnVideoOrder);
        }else {
            XnFineclassOrderExample example=new XnFineclassOrderExample();
            example.createCriteria().andAttr3EqualTo(orderId);
            List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnFineclassOrders))return MyResult.failure("没找到该订单信息");
            XnFineclassOrder xnFineclassOrder =xnFineclassOrders.get(0);
            xnFineclassOrder.setBuytime(new Date());
            xnFineclassOrder.setAttr1("2");
            i=xnFineclassOrderMapper.updateByPrimaryKeySelective(xnFineclassOrder);
        }
        if (i!=1){
            return MyResult.failure("改变订单状态失败");
        }
        return MyResult.success("改变订单状态成功");
    }

    /**
     * 已购课程
     * @return
     */
    @Override
    public MyResult myVideo(Integer pageNo,Integer pageSize) {
        Integer type = SystemParam.getType();
        Integer userId=null;
        Integer schoolId=SystemParam.getSchoolId();
        if(type==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        XnFineclassOrderExample example1=new XnFineclassOrderExample();
        example1.createCriteria().andSidEqualTo(schoolId).andUsertypeEqualTo(Byte.valueOf(type.toString()))
                .andUseridEqualTo(userId).andAttr1EqualTo("2");
        List<XnFineclassOrder> xnFineclassOrders = xnFineclassOrderMapper.selectByExample(example1);
        List list=new ArrayList();
        if (!ObjectUtil.isEmpty(xnFineclassOrders)){
            for (XnFineclassOrder xfo:xnFineclassOrders){
                XnTopqualityPersonalRule xnTopqualityPersonalRule = xnTopqualityPersonalRuleMapper.selectByPrimaryKey(xfo.getFid());
                Map<String, Object> stringObjectMap = voUtil.pointFocusInfo(xnTopqualityPersonalRule);
                stringObjectMap.put("isPay",1);
                list.add(stringObjectMap);
            }
        }
        if (pageSize==null){
            pageSize=6;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    @Override
    public MyResult sevip(Integer VideoSchoolId) {
        HlSchoolsevip hlSchoolsevips = hlSchoolsevipMapper.selectByPrimaryKey(VideoSchoolId);
        if (ObjectUtil.isEmpty(hlSchoolsevips))return MyResult.failure("没找到该学校的视频ip地址");
        List list=new ArrayList();
        Map m=new HashMap();
        if (!ObjectUtil.isEmpty(hlSchoolsevips.getLansevip())){
            Map map=new HashMap();
            map.put("LanSevIP","rtmp://"+hlSchoolsevips.getLansevip()+":"+hlSchoolsevips.getLansevport()+"/live/");
            map.put("lanIP","http://"+hlSchoolsevips.getLansevip());
            map.put("name","局域网");
            list.add(map);
        }
        if (!ObjectUtil.isEmpty(hlSchoolsevips.getWansevip())){
            Map map=new HashMap();
            map.put("LanSevIP","rtmp://"+hlSchoolsevips.getWansevip()+":"+hlSchoolsevips.getWansevport()+"/live/");
            map.put("lanIP","http://"+hlSchoolsevips.getWansevip());
            map.put("name","城域网");
            list.add(map);
        }
        if (!ObjectUtil.isEmpty(hlSchoolsevips.getWansevipr())){
            Map map=new HashMap();
            map.put("LanSevIP","rtmp://"+hlSchoolsevips.getWansevipr()+":"+hlSchoolsevips.getInternetport()+"/live/");
            map.put("lanIP","http://"+hlSchoolsevips.getWansevipr());
            map.put("name","互联网");
            list.add(map);
        }

        Map map=new HashMap();
        map.put("tea","_hd_1");
        map.put("stu","_hd_2");
        map.put("LanSevIP",ObjectUtil.isEmpty(hlSchoolsevips.getLansevip())?null:"rtmp://"+hlSchoolsevips.getLansevip()+":"+hlSchoolsevips.getLansevport()+"/live/");
        map.put("WanSevIP",ObjectUtil.isEmpty(hlSchoolsevips.getWansevip())?null:"rtmp://"+hlSchoolsevips.getWansevip()+":"+hlSchoolsevips.getWansevport()+"/live/");
        map.put("WanSevIPR",ObjectUtil.isEmpty(hlSchoolsevips.getWansevipr())?null:"rtmp://"+hlSchoolsevips.getWansevipr()+":"+hlSchoolsevips.getInternetport()+"/live/");
        map.put("ip",list);
        return MyResult.success(map);
    }


    /**
     * 添加点击量
     * @param videoId
     * @param videoType
     * @param videoSchoolId
     * @param videoClassId
     */
    private void addCheckNum(Integer videoId,Integer videoType,Integer videoSchoolId,Integer videoClassId){
        Date date=new Date();
        String s= TimeUtil.TimeToYYYYMMDDHHMMSS(date).substring(0,10);
        System.out.println(s);
        XnCheckNumExample example=new XnCheckNumExample();
        example.createCriteria().andSidEqualTo(videoSchoolId).andCidEqualTo(videoClassId)
                .andVideoIdEqualTo(videoId).andVideoTypeEqualTo(videoType).andAttr1EqualTo(s);
        List<XnCheckNum> xnCheckNums = xnCheckNumMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnCheckNums)){
            XnCheckNum xnCheckNum=new XnCheckNum();
            xnCheckNum.setSid(videoSchoolId);
            xnCheckNum.setCid(videoClassId);
            xnCheckNum.setVideoType(videoType);
            xnCheckNum.setVideoId(videoId);
            xnCheckNum.setCheckNum(1);
            xnCheckNum.setAttr1(s);
            xnCheckNumMapper.insertSelective(xnCheckNum);
        }else {
            xnCheckNums.get(0).setCheckNum(xnCheckNums.get(0).getCheckNum()+1);
            xnCheckNumMapper.updateByPrimaryKeySelective(xnCheckNums.get(0));
        }
    }

}
