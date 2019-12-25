package com.usoft.sschool_student.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_student.mapper.*;
import com.usoft.sschool_student.serivice.HomeWorkService;
import com.usoft.sschool_student.util.ResultPage;
import com.usoft.sschool_student.util.SystemParam;
import com.usoft.sschool_student.util.TimeUtil;
import com.usoft.sschool_student.util.UploadFileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author wlw
 * @data 2019/4/24 0024-9:51
 */
@Service(value = "homeWorkService")
public class HomeWorkServiceImpl implements HomeWorkService {
    @Resource
    private XnHomeworkManageMapper xnHomeworkManageMapper;
    @Resource
    private XnStuHomeworkMapper xnStuHomeworkMapper;
    @Resource
    private XnHomeworkTitleMapper xnHomeworkTitleMapper;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private UploadFileUtil uploadFileUtil;
    /**
     * 查询该学生的所有作业
     * @param stuId
     * @return
     */
    @Override
    public MyResult serachAll(Integer stuId,Integer pageNo,Integer pageSize) {
        XnStuHomeworkExample example=new XnStuHomeworkExample();
        example.createCriteria().andStuidEqualTo(stuId);
        example.setOrderByClause("submitTime desc");
        List<XnStuHomework> xnStuHomeworks = xnStuHomeworkMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuHomeworks))return MyResult.failure("没有作业信息");

        List<Map> list=new ArrayList<>();
        for (XnStuHomework xsh:xnStuHomeworks){
            Map map=new HashMap();
            XnHomeworkManage xnHomeworkManage = xnHomeworkManageMapper.selectByPrimaryKey(xsh.getHwid());
            map.put("id",xsh.getId());
            map.put("hwid",xsh.getHwid());
            map.put("state",xsh.getState());
            map.put("hwtype",xsh.getType());
            HlTeacher ht=new HlTeacher();
            ht.setId(xnHomeworkManage.getUid());
            ht.setSchoolid(SystemParam.getSchoolId());
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            if (ObjectUtil.isEmpty(hlTeacher)){
                map.put("teaName","无");
            }else {
                map.put("teaName",hlTeacher.getTname());
            }
            if (ObjectUtil.isEmpty(xnHomeworkManage)){
                map.put("subject",null);
                map.put("hwname",null);
                map.put("expiretime", null);
                map.put("createtime",null);
            }else{
                map.put("subject",xnHomeworkManage.getSubject());
                map.put("hwname",xnHomeworkManage.getHwname());
                map.put("expiretime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnHomeworkManage.getExpiretime()));
                map.put("createtime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnHomeworkManage.getCreatetime()));
            }
            list.add(map);
        }
        if (pageSize==null || pageSize==0 || "".equals(pageSize)){
            pageSize=6;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 未做作业
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult notdidHwk(Integer pageNo, Integer pageSize) {
        XnStuHomeworkExample example=new XnStuHomeworkExample();
        example.createCriteria().andStuidEqualTo(SystemParam.getChildId()).andStateEqualTo((byte)1);
        example.setOrderByClause("submitTime desc");
        List<XnStuHomework> xnStuHomeworks = xnStuHomeworkMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuHomeworks))return MyResult.failure("没有作业信息");

        List<Map> list=new ArrayList<>();
        for (XnStuHomework xsh:xnStuHomeworks){
            Map map=new HashMap();
            XnHomeworkManage xnHomeworkManage = xnHomeworkManageMapper.selectByPrimaryKey(xsh.getHwid());
            map.put("id",xsh.getId());
            map.put("hwid",xsh.getHwid());
            map.put("state",xsh.getState());
            map.put("hwtype",xsh.getType());
            map.put("teaId",xnHomeworkManage.getUid());
            HlTeacher ht=new HlTeacher();
            ht.setId(xnHomeworkManage.getUid());
            ht.setSchoolid(SystemParam.getSchoolId());
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            if (ObjectUtil.isEmpty(hlTeacher)){
                map.put("teaName","无");
            }else {
                map.put("teaName",hlTeacher.getTname());
            }
            if (ObjectUtil.isEmpty(xnHomeworkManage)){
                map.put("subject",null);
                map.put("hwname",null);
                map.put("expiretime", null);
                map.put("createtime",null);
            }else{
                map.put("subject",xnHomeworkManage.getSubject());
                map.put("hwname",xnHomeworkManage.getHwname());
                if (xnHomeworkManage.getExpiretime()!=null){
                    map.put("expiretime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnHomeworkManage.getExpiretime()));
                }else {
                    map.put("expiretime", xnHomeworkManage.getExpiretime());
                }
                if (xnHomeworkManage.getCreatetime()!=null){
                    map.put("createtime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnHomeworkManage.getCreatetime()));
                }else {
                    map.put("createtime", xnHomeworkManage.getCreatetime());
                }
            }
            list.add(map);
        }
        if (pageSize==null || pageSize==0 || "".equals(pageSize)){
            pageSize=6;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 已做作业列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult didHwk(Integer pageNo, Integer pageSize) {
        XnStuHomeworkExample example=new XnStuHomeworkExample();
        example.createCriteria().andStuidEqualTo(SystemParam.getChildId()).andStateNotEqualTo((byte)1);
        example.setOrderByClause("submitTime desc");
        List<XnStuHomework> xnStuHomeworks = xnStuHomeworkMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuHomeworks))return MyResult.failure("没有作业信息");

        List<Map> list=new ArrayList<>();
        for (XnStuHomework xsh:xnStuHomeworks){
            Map map=new HashMap();
            XnHomeworkManage xnHomeworkManage = xnHomeworkManageMapper.selectByPrimaryKey(xsh.getHwid());
            map.put("id",xsh.getId());
            map.put("hwid",xsh.getHwid());
            map.put("state",xsh.getState());
            map.put("hwtype",xsh.getType());
            HlTeacher ht=new HlTeacher();
            ht.setId(xnHomeworkManage.getUid());
            ht.setSchoolid(SystemParam.getSchoolId());
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            if (ObjectUtil.isEmpty(hlTeacher)){
                map.put("teaName","无");
            }else {
                map.put("teaName",hlTeacher.getTname());
            }
            if (ObjectUtil.isEmpty(xnHomeworkManage)){
                map.put("subject",null);
                map.put("hwname",null);
                map.put("expiretime", null);
                map.put("createtime",null);
            }else{
                map.put("subject",xnHomeworkManage.getSubject());
                map.put("hwname",xnHomeworkManage.getHwname());
                if (xnHomeworkManage.getExpiretime()!=null){
                    map.put("expiretime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnHomeworkManage.getExpiretime()));
                }else {
                    map.put("expiretime", xnHomeworkManage.getExpiretime());
                }
                if (xnHomeworkManage.getCreatetime()!=null){
                    map.put("createtime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnHomeworkManage.getCreatetime()));
                }else {
                    map.put("createtime", xnHomeworkManage.getCreatetime());
                }
            }
            list.add(map);
        }
        if (pageSize==null || pageSize==0 || "".equals(pageSize)){
            pageSize=6;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 单个作业详情
     * @param hwId
     * @return
     */
    @Override
    public MyResult homeworkInfo(Integer hwId) {
        Map map=new HashMap();
        //查询上传的图片题
        XnHomeworkManage xnHomeworkManage = xnHomeworkManageMapper.selectByPrimaryKey(hwId);
        if (ObjectUtil.isEmpty(xnHomeworkManage))return MyResult.failure("没找到作业信息");
        if (xnHomeworkManage.getHwtype()==2){
            if (ObjectUtil.isEmpty(xnHomeworkManage.getHwcontent())){
                map.put("coutent",null);
                map.put("hwid",hwId);
                map.put("hwType",2);
            }else {
                List<Map> list=new ArrayList<>();
                JSON json= JSONObject.parseObject(xnHomeworkManage.getHwcontent());
                Map<String,Object> map1=JSONObject.toJavaObject(json,Map.class);
                Map map2=new HashMap();
                map2.put("title",map1.get("hwContent"));
                map2.put("Img",map1.get("Img").toString().split(","));
                list.add(map2);
                map.put("coutent",list);
                map.put("id",hwId);
                map.put("hwType",2);
            }
        }
        if (xnHomeworkManage.getHwtype()==1){
            XnHomeworkTitleExample example=new XnHomeworkTitleExample();
            example.createCriteria().andHwidEqualTo(hwId);
            example.setOrderByClause("id");
            List<XnHomeworkTitle> xnHomeworkTitles = xnHomeworkTitleMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnHomeworkTitles)){
                map.put("coutent",null);
                map.put("id",hwId);
                map.put("hwType",1);
            }
            map.put("coutent",xnHomeworkTitles);
            map.put("id",hwId);
            map.put("hwType",1);
        }
        return MyResult.success(map);
    }

    /**
     * 提交作业
     * @param stuId
     * @param hwId
     * @param ans
     * @return
     */
    @Override
    public MyResult subHomework(Integer stuId,Integer hwId, String ans) {
        XnStuHomeworkExample example=new XnStuHomeworkExample();
        example.createCriteria().andStuidEqualTo(stuId).andHwidEqualTo(hwId).andStateEqualTo((byte)1)
                .andTypeEqualTo((byte)1);
        List<XnStuHomework> xnStuHomeworks = xnStuHomeworkMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuHomeworks))return MyResult.failure("没找到该作业信息");
        XnHomeworkManage xnHomeworkManage = xnHomeworkManageMapper.selectByPrimaryKey(hwId);
        long time = xnHomeworkManage.getExpiretime().getTime();
        long time1=new Date().getTime();
        if (time<time1){
            return MyResult.failure("已过最后提交时间，不能提交");
        }
        xnStuHomeworks.get(0).setAnswercontent(ans);
        xnStuHomeworks.get(0).setState((byte)2);
        xnStuHomeworks.get(0).setSubmittime(new Date());
        int i = xnStuHomeworkMapper.updateByPrimaryKeySelective(xnStuHomeworks.get(0));
        if (i!=1){
            return MyResult.failure("提交失败");
        }
        return MyResult.success("提交成功");
    }

    /**
     * 上传答题图片
     * @param request
     * @param hwId
     * @param childId
     * @return
     */
    @Override
    public MyResult addHomework(HttpServletRequest request, Integer hwId, Integer childId,String content) {
        MyResult myResult = uploadFileUtil.uploadFiles(request);
        if (myResult.getStatus()!=1){
            return MyResult.failure("上传失败");
        }
        XnStuHomeworkExample example=new XnStuHomeworkExample();
        example.createCriteria().andStuidEqualTo(childId).andHwidEqualTo(hwId).andStateEqualTo((byte)1)
                .andTypeEqualTo((byte)2);
        List<XnStuHomework> xnStuHomeworks = xnStuHomeworkMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuHomeworks))return MyResult.failure("没找到该作业信息");
        XnHomeworkManage xnHomeworkManage = xnHomeworkManageMapper.selectByPrimaryKey(hwId);
        long time = xnHomeworkManage.getExpiretime().getTime();
        long time1=new Date().getTime();
        if (time<time1){
            return MyResult.failure("已过最后提交时间，不能提交");
        }
        String str=null;
        Map<String,Object> map = new HashMap<>();
        if (content==null){
            map.put("hwContent","");
            map.put("Img",myResult.getData().toString());
            str = JSONObject.toJSONString(map);
           //str=JSONObject.toJSON("{\"hwContent\":\"" + "\",\"Img\":\"" + myResult.getData().toString()+"\"}").toString();
        }else {
            map.put("hwContent",content);
            map.put("Img",myResult.getData().toString());
            str = JSONObject.toJSONString(map);
            //str=JSONObject.toJSON("{\"hwContent\":\"" + content + "\",\"Img\":\"" + myResult.getData().toString()+"\"}").toString();
        }

        xnStuHomeworks.get(0).setAnswercontent(str);
        xnStuHomeworks.get(0).setState((byte)2);
        xnStuHomeworks.get(0).setSubmittime(new Date());
        int i = xnStuHomeworkMapper.updateByPrimaryKeySelective(xnStuHomeworks.get(0));
        if (i!=1){
            return MyResult.failure("提交失败");
        }
        return MyResult.success("提交成功");
    }

    /**
     * 已做作业详情
     * @param hwId
     * @return
     */
    @Override
    public MyResult hasdidHomeWork(Integer hwId) {
        List<Map> list1=new ArrayList<>();
        XnStuHomeworkExample example=new XnStuHomeworkExample();
        example.createCriteria().andStuidEqualTo(SystemParam.getChildId()).andHwidEqualTo(hwId);
        example.setOrderByClause("id desc");
        List<XnStuHomework> xnStuHomeworks = xnStuHomeworkMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnStuHomeworks))return MyResult.failure("没找到该作业信息");
        Map map=new HashMap();
        //查询教师审批结果
        XnResourceRelationExample example2=new XnResourceRelationExample();
        example2.createCriteria().andResourcebidEqualTo(xnStuHomeworks.get(0).getId()).andResourcebtableEqualTo("xn_stu_homework")
                .andResourceatableEqualTo("hl_teacher").andIsdeleteEqualTo((byte)2);
        List<XnResourceRelation> xnResourceRelations = xnResourceRelationMapper.selectByExample(example2);
        String img=null;
        String content=null;
        if (xnResourceRelations.size()!=0){
            if (xnResourceRelations.get(0).getAttr1()!=null && !"".equals(xnResourceRelations.get(0).getAttr1())){
                img=xnResourceRelations.get(0).getAttr1();
            }
            if (xnResourceRelations.get(0).getAttr2()!=null && !"".equals(xnResourceRelations.get(0).getAttr2())){
                content=xnResourceRelations.get(0).getAttr2();
            }
        }
        //查询上传的图片题
        XnHomeworkManage xnHomeworkManage = xnHomeworkManageMapper.selectByPrimaryKey(hwId);
        if (ObjectUtil.isEmpty(xnHomeworkManage))return MyResult.failure("没找到作业信息");
        if (xnHomeworkManage.getHwtype()==2){
            if (ObjectUtil.isEmpty(xnHomeworkManage.getHwcontent())){
                map.put("coutent",null);
                map.put("hwid",hwId);
                map.put("hwType",2);
                map.put("ansContent",xnStuHomeworks.get(0).getAnswercontent());
                JSON jsonObject1 = JSONObject.parseObject(xnStuHomeworks.get(0).getAnswercontent());
                Map<String,Object> map8=JSONObject.toJavaObject(jsonObject1,Map.class);
                Map map4=new HashMap();
                map4.put("hwContent",map8.get("hwContent"));
                map4.put("Img",map8.get("Img").toString().split(","));
                List<Map> list2=new ArrayList<>();
                list2.add(map4);
                map.put("myAns",list2);
            }else {
                List<Map> list=new ArrayList<>();
                JSON json= JSONObject.parseObject(xnHomeworkManage.getHwcontent());
                Map<String,Object> map1=JSONObject.toJavaObject(json,Map.class);
                Map map2=new HashMap();
                map2.put("title",map1.get("hwContent"));
                map2.put("Img",map1.get("Img").toString().split(","));
                list.add(map2);
                map.put("coutent",list);
                map.put("id",hwId);
                map.put("hwType",2);
                map.put("ansContent",xnStuHomeworks.get(0).getAnswercontent());
                JSON jsonObject = JSONObject.parseObject(xnStuHomeworks.get(0).getAnswercontent());
                Map<String,Object> map3=JSONObject.toJavaObject(jsonObject,Map.class);
                Map map4=new HashMap();
                map4.put("hwContent",map3.get("hwContent"));
                map4.put("Img",map3.get("Img").toString().split(","));
                List<Map> list2=new ArrayList<>();
                list2.add(map4);
                map.put("myAns",list2);
                map.put("teaContent",content);
                map.put("teaImg",img);
            }
        }
        if (xnHomeworkManage.getHwtype()==1){
            XnHomeworkTitleExample example1=new XnHomeworkTitleExample();
            example1.createCriteria().andHwidEqualTo(hwId);
            List<XnHomeworkTitle> xnHomeworkTitles = xnHomeworkTitleMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(xnHomeworkTitles)){
                map.put("coutent",null);
                map.put("id",hwId);
                map.put("hwType",1);
                map.put("ansContent",xnStuHomeworks.get(0).getAnswercontent());
            }else {
                String[] str=xnStuHomeworks.get(0).getAnswercontent().split(",");
                for(int i=0;i<xnHomeworkTitles.size();i++){
                    Map m=new HashMap();
                    m.put("id",xnHomeworkTitles.get(i).getId());
                    m.put("title",xnHomeworkTitles.get(i).getTitle());
                    m.put("answera",xnHomeworkTitles.get(i).getAnswera());
                    m.put("answerb",xnHomeworkTitles.get(i).getAnswerb());
                    m.put("answerc",xnHomeworkTitles.get(i).getAnswerc());
                    m.put("answerd",xnHomeworkTitles.get(i).getAnswerd());
                    m.put("rightanswer",xnHomeworkTitles.get(i).getRightanswer());
                    m.put("hwid",xnHomeworkTitles.get(i).getHwid());
                    String s =null;
                    if (i<str.length){
                       s= str[i];
                    }
                    m.put("myAns",s);
                    list1.add(m);
                }
                map.put("coutent",list1);
                map.put("id",hwId);
                map.put("hwType",1);
                map.put("ansContent",xnStuHomeworks.get(0).getAnswercontent());
                map.put("teaContent",content);
                map.put("teaImg",img);
            }

        }
        return MyResult.success(map);
    }

}
