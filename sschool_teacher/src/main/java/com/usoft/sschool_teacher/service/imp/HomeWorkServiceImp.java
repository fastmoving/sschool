package com.usoft.sschool_teacher.service.imp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usoft.smartschool.pojo.*;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.enums.AnswerEnums;
import com.usoft.sschool_teacher.enums.StateEnum;
import com.usoft.sschool_teacher.exception.CustomException;
import com.usoft.sschool_teacher.exception.MyException;
import com.usoft.sschool_teacher.mapper.*;
import com.usoft.sschool_teacher.service.IHomeWorkService;
import com.usoft.sschool_teacher.util.ConstantsDate;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 陈秋
 * @Date: 2019/5/6 16:09
 * @Version 1.0
 */
@Service
public class HomeWorkServiceImp extends QueryAndInsertImp implements IHomeWorkService {

    @Resource
    private XnHomeworkManageMapper homeworkManageMapper;

    @Resource
    private XnHomeworkTitleMapper homeworkTitleMapper;

    @Resource
    private HlTeacherMapper teacherMapper;

    @Resource
    private HlSchclassMapper schclassMapper;

    @Resource
    private XnStuHomeworkMapper stuHomeworkMapper;

    @Resource
    private HlStudentinfoMapper studentinfoMapper;

    @Resource
    private XnResourceRelationMapper resourceRelationMapper;

    @Resource
    private HlCurriculumMapper curriculumMapper;

    /**
     * 添加作业
     *
     * @param teacherId      教师id
     * @param hwName         作业题目
     * @param hwType         作业类型 1.在线答题 2.上传
     * @param acceptClass    接收班级
     * @param subject        科目
     * @param expireTime     作业提交截止时间
     * @param hwContent      作业内容(答题上传题目的内容)
     * @param hwContentImg   上传作业图片路径
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertHomeWork(int teacherId, String hwName, int hwType, String acceptClass, String subject,
                              String expireTime, String hwContent, String hwContentImg) {
        XnHomeworkManage homeworkManage = new XnHomeworkManage();
        XnIntegralRule rule =queryIntegralRule(ConstantsDate.TOTAL_HOMEWORK);
        if (teacherId == 0){
            return -2;
        }
        homeworkManage.setUid(teacherId);
        homeworkManage.setCreatetime(new Date());
        try {
            homeworkManage.setExpiretime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return -6;
        }
        if (hwContentImg!=null && !"".equals(hwContentImg))
        homeworkManage.setHwcontent(JSONObject.fromObject("{\"hwContent\":\"" + hwContent + "\",\"Img\":\"" + hwContentImg+"\"}").toString());
        homeworkManage.setHwname(hwName);
        if ("".equals(acceptClass) && acceptClass == null){
            return -3;
        }
        String[] classes = acceptClass.split(",");
        try{
            if (classes == null){
                return -9;
            }
            for (String classId:classes) {
                int classID = Integer.parseInt(classId);
                HlSchclassKey key = new HlSchclassKey();
                key.setId(classID);
                HlSchclass schclases = schclassMapper.selectByPrimaryKey(key);
                if (schclases == null){
                    return  -10;
                }
            }
        }catch(Exception e){
            return -8;
        }
        homeworkManage.setAcceptclass(acceptClass);
        if (hwType == 1) {
            homeworkManage.setHwtype(ConstantsDate.ONLINE);
        } else if (hwType == 2) {
            homeworkManage.setHwtype(ConstantsDate.UPLOADING);
        }
        homeworkManage.setSubject(subject);
        int i = homeworkManageMapper.insertSelective(homeworkManage);
        try {
            CustomException.customeIf(i);
        } catch (MyException e) {
            return -1;
        }
        List<XnStuHomework> stuHomeworks = new ArrayList<>();
        for (String info : classes) {
            List<HlStudentinfo> students = studentinfoMapper.getStudentInformation(Integer.parseInt(info));
            stuHomeworks = getListData(students,homeworkManage.getId(),hwType);
           /* for (HlStudentinfo studentID: students) {
                XnStuHomework stuHomework = new XnStuHomework();
                stuHomework.setStuid(studentID.getId());
                stuHomework.setState(ConstantsDate.HOMEWORKSTATE1);
                stuHomework.setHwid(homeworkManage.getId());
                if (hwType==1){
                    stuHomework.setType(ConstantsDate.ONLINE);
                }else if (hwType==2){
                    stuHomework.setType(ConstantsDate.UPLOADING);
                }

                stuHomeworks.add(stuHomework);
            }*/
        }
        int j = stuHomeworkMapper.insertStuHomeworkEs(stuHomeworks);
        try{
            CustomException.customeIf(j);
        } catch(MyException e){
            return -1;
        }
        if (rule!=null && rule.getIsopen() == ConstantsDate.TOTAL_YES) {
            int k = insertIntegral(ConstantsDate.TOTAL_HOMEWORK, rule.getIntegralnumer());
            try {
                CustomException.customeIf(k);
            }catch (MyException e){
                return -1;
            }
        }
        return homeworkManage.getId();
    }

    /**
     * 修改作业
     * @param hmwId 作业ID
     * @param thId 教师ID
     * @param hwName 作业名字
     * @param hwType 作业属性
     * @param acceptClass 接收班级
     * @param subject 科目
     * @param expireTime 最后截止时间
     * @param hwContent 作业内容
     * @param hwContentImg 照片地址
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateHomework(int hmwId,int thId,String hwName,int hwType,String acceptClass,String subject,
                              String expireTime,String hwContent,String hwContentImg) {
        XnHomeworkManage homework = new XnHomeworkManage();
        homework.setId(hmwId);
        if (subject!=null && !"".equals(subject)){
            homework.setSubject(subject);
        }
        if (hwType == 1){
            homework.setHwtype(ConstantsDate.ONLINE);
        }else if (hwType == 2){
            homework.setHwtype(ConstantsDate.UPLOADING);
        }
        if (hwName!=null && !"".equals(hwName)){
            homework.setHwname(hwName);
        }
        if (acceptClass!=null && !"".equals(acceptClass)){
            homework.setAcceptclass(acceptClass);
        }
        if (expireTime != null && !"".equals(expireTime)){
            try {
                homework.setExpiretime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
            } catch (ParseException e) {
                e.printStackTrace();
                return -6;
            }
        }
        homework.setHwcontent(JSONObject.fromObject("{\"hwContent\":\"" + hwContent + "\",\"Img\":\"" + hwContentImg+"\"}").toString());
        homework.setUid(thId);
        int i = homeworkManageMapper.updateByPrimaryKeySelective(homework);
        try{
            CustomException.customeIf(i);
        } catch(MyException e){
            return -1;
        }
        return i;
    }

    //上传选择题
    @Override
    @Transactional
    public int insertHomeworkTitle(int hwid, String title, String answerA, String answerB, String answerC, String answerD, int rightAnswer) {
            XnHomeworkTitle homeworkTitle = new XnHomeworkTitle();
            if (title == null || "".equals(title)){
                return -4;
            }
            homeworkTitle.setTitle(title);
            homeworkTitle.setAnswera(answerA);
            homeworkTitle.setAnswerb(answerB);
            homeworkTitle.setAnswerc(answerC);
            homeworkTitle.setAnswerd(answerD);
            if (hwid == 0) {
                return -7;
            }
            homeworkTitle.setHwid(hwid);
            if (rightAnswer == 0){
                return -5;
            }
            if (rightAnswer == 1){
                homeworkTitle.setRightanswer(ConstantsDate.A);
            }else if (rightAnswer == 2){
                homeworkTitle.setRightanswer(ConstantsDate.B);
            }else if (rightAnswer == 3){
                homeworkTitle.setRightanswer(ConstantsDate.C);
            }else if (rightAnswer == 4){
                homeworkTitle.setRightanswer(ConstantsDate.D);
            }
            int j = homeworkTitleMapper.insertSelective(homeworkTitle);
            try {
                CustomException.customeIf(j);
            }catch(MyException e){
                return -1;
            }
        return j;
    }

    //选择科目
    @Override
    public HlTeacher getSubject(int teacherId) {
        HlTeacherKey key = new HlTeacherKey();
        key.setId(teacherId);
        HlTeacher teacher = teacherMapper.selectByPrimaryKey(key);
        return teacher;
    }

    /**
     * 作业管理
     * @param teacherId 教师ID
     * @param state 状态 1.未做 2.已做已提交 3.已审批
     * @param stuName 学生名字
     * @param className 班级名字
     * @param hwmName 作业名字
     * @param page 页码
     * @param start 开始下标
     * @return
     */
    @Override
    public List<Map<String, Object>> getHomeworkmanager(Integer teacherId, Integer state, String stuName,
                                                        String className, String hwmName, Integer page, Integer start,
                                                        String subject,String classId,Integer code) {
        Map key = getKey( teacherId,  state,stuName, className,
                hwmName, subject, classId, code);
        key.put("page",start);
        key.put("start",(page-1)*start);

        List<Map<String,Object>> data = stuHomeworkMapper.getStuHomeworkEs(key);
        if (data.size()==0){
            return new ArrayList<>();
        }
        List<Map<String,Object>> dataList = new ArrayList<>();
        for (Map<String,Object> dataMap : data) {
            dataMap.put("end_time",dataMap.get("expireTime").toString().substring(0,16));
            dataMap.put("create_time",dataMap.get("createTime").toString().substring(0,16));
            if (dataMap.get("state")!=null && !"".equals(dataMap.get("state"))){
                int states = Integer.parseInt(dataMap.get("state").toString());
                dataMap.put("states", StateEnum.getState(states));
            }else {
                dataMap.put("states", "");
            }
            dataList.add(dataMap);
        }
        return dataList;
    }

    @Override
    public int getStuHomeworkEsCount(int teacherId, int state,String stuName,String className,
                                     String hwmName, String subject,String classId,int code) {
        Map key = getKey( teacherId,  state,stuName, className,
                hwmName,subject, classId, code);
        int count = stuHomeworkMapper.getStuHomeworkEsCount(key);
        return count;
    }

    public Map getKey(int teacherId, int state,String stuName,String className,
                      String hwmName, String subject,String classId,int code){
        Map<String,Object> key = new HashMap<>();
        if (code==0){
            key.put("state",state);
            key.put("code",0);
        }else if (code==1){
            key.put("code",1);
            key.put("state",state);

        }
        key.put("teacherId",teacherId);
        if(!"".equals(stuName) && stuName!=null){
            key.put("stuName",stuName);
        }else {
            key.put("stuName",null);
        }
        if (!"".equals(className) && className!=null){
            key.put("className",className);
        }else {
            key.put("className",null);
        }
        if (!"".equals(hwmName) && hwmName!=null){
            key.put("hwmName",hwmName);
        }else {
            key.put("hwmName",null);
        }
        if (classId!=null && !"".equals(classId)){
            key.put("classId",Integer.parseInt(classId));
        }else{
            key.put("classId",null);
        }
        if (!"".equals(subject) && subject!=null){
            key.put("subject",subject);
        }else {
            key.put("subject",null);
        }
        return key;
    }
    /**
     * 作业管理web
     * @param teacherId 教师id
     * @param hewName 作业名
     * @param type 作业属性 1，在线；2,是上传
     * @param classId 班级id
     * @param expireTime 截止时间
     * @return
     */
    @Override
    public Map getHomeworkWeb(int teacherId,String hewName,String type,
                              String classId,String expireTime,int page,int start) throws Exception{
        Map<String,Object> key = new HashMap<>();
        key.put("hewName",hewName);
        key.put("page",page);
        key.put("start",(start-1)*page);
        key.put("teacherId",teacherId);
        if (type!=null && !"".equals(type)){
            key.put("type",Integer.parseInt(type.trim()));
        }else{
            key.put("type",null);
        }
        if (expireTime!=null && !"".equals(expireTime)){
            key.put("expireTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
        }else {
            key.put("expireTime",null);
        }
        List<XnHomeworkManage> homeworks = homeworkManageMapper.getHomeworkWeb(key);
        int i = homeworkManageMapper.getHomeworkWebCount(key);
        if (homeworks.size() ==0){
            return null;
        }
        List listData = new ArrayList<>();
        if(classId!=null && !"".equals(classId)){
            int num = 0;
            for (XnHomeworkManage homework: homeworks){
                String[] classes = homework.getAcceptclass().split(",");
                for (String string: classes){
                    if (Integer.parseInt(classId) == Integer.parseInt(string)){
                        XnHomeworkManage hmk = getHomework(homework);
                        Map resData = getHomeworkMap(hmk);
                        if (resData!=null){
                            listData.add(resData);
                        }
                        num++;
                    }
                }
            }
            key.put("listData",listData);
            key.put("num",num);
            return key;
        }
        for (XnHomeworkManage homework: homeworks) {
            XnHomeworkManage hmk = getHomework(homework);
            Map resData = getHomeworkMap(hmk);
            listData.add(resData);
        }
        key.put("listData",listData);
        key.put("num",i);
        return key;
    }

    public XnHomeworkManage getHomework(XnHomeworkManage hmw){
        String className = "";
        String classIdes = hmw.getAcceptclass();
        String[] classIds = classIdes.split(",");
        for (String classid:classIds) {
            HlSchclassKey key = new HlSchclassKey();
            key.setId(Integer.parseInt(classid));
            HlSchclass  schclass=schclassMapper.selectByPrimaryKey(key);
            className += schclass.getClassname()+" ";
        }
        hmw.setAcceptclass(className);
        System.out.println(hmw.getExpiretime());
        return hmw;
    }

    public Map getHomeworkMap(XnHomeworkManage hmw){
        Map data = new HashMap();
        data.put("id",hmw.getId());
        data.put("subject",hmw.getSubject());
        data.put("acceptclass",hmw.getAcceptclass());
        try {
            if (hmw.getHwcontent()!=null && !"".equals(hmw.getHwcontent())){
                Map map = JSONObject.fromObject(hmw.getHwcontent());
                data.put("hw_content",map.get("hwContent"));
                data.put("hw_img",map.get("Img"));
            }
        }catch (Exception e){
            e.printStackTrace();
           return null;
        }
        data.put("hwContent",hmw.getHwcontent());
        data.put("hewName",hmw.getHwname());
        data.put("hwtype",hmw.getHwtype());
        data.put("uid",hmw.getUid());
        data.put("expireTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(hmw.getExpiretime()));
        data.put("createtime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(hmw.getCreatetime()));
        return data;
    }

    /**
     * 作业详情
     * @param homeworkId 作业id
     * @param studentId   学生ID
     * @return
     */
    @Override
    public Map<String, Object> getHomeworkInformation(int homeworkId,int studentId) {
        String[] anwes = new String[]{};
        String[] sAnws = new String[]{};
        String tAnswer = "";
        int j = 0;
        Map<String,Object> resData = new HashMap<>();
        List<Map<String,Object>> data = new ArrayList<>();
        int awSuccess = 0;
        int awError = 0;
        List<Map<String,Object>> homeworkData = homeworkManageMapper.getHomeworkInformation(homeworkId);
        if (homeworkData == null || homeworkData.size() == 0){
            resData.put("msg","没有数据");
            return resData;
        }
        if (homeworkData.get(0).get("hwContent")!=null && !"".equals(homeworkData.get(0).get("hwContent"))) {
            Map map_data = JSONObject.fromObject(homeworkData.get(0).get("hwContent"));
            resData.put("hw_content", map_data.get("hwContent"));
            resData.put("hw_content_img", map_data.get("Img"));
        }
        resData.put("teacherId",homeworkData.get(0).get("teacherId"));
        resData.put("hewId",homeworkData.get(0).get("hewId"));
        //查询学生作业
        Map<String,Object> mapKey = new HashMap<>();
        mapKey.put("hewId",homeworkId);
        mapKey.put("stuId",studentId);
        List<XnStuHomework> stuHomeworks =  stuHomeworkMapper.selectByPrimaryKey(mapKey);
        if (stuHomeworks != null || stuHomeworks.size()!=0){
            resData.put("stuId",studentId);
            for (XnStuHomework stuHomework : stuHomeworks){
                if (stuHomework.getState() != 1){
                    if (stuHomework.getType() == 1 && stuHomework.getAnswercontent()!=null && !"".equals(stuHomework.getAnswercontent())) {
                        sAnws = stuHomework.getAnswercontent().split(",");
                    }
                }
            }
        }
        for (XnStuHomework xsth : stuHomeworks) {
            if (xsth.getState() == 1){
                for (Map<String,Object> mapData:homeworkData) {
                    Map<String, Object> homeworkTitle = getData(mapData);
                    try {
                        homeworkTitle.put("answer", AnswerEnums.getMessage(Integer.parseInt(mapData.get("rightAnswer").toString())));
                    } catch (Exception e) {
                        resData.put("message", "查询选择题答案为空");
                        return resData;
                    }
                    data.add(homeworkTitle);
                }
                resData.put("choice",data);
            }
           else if (xsth.getState() == 2){
                if (xsth.getType() == 2) {
                    //resData.put("choice",homeworkData);
                    Map dataStud = JSONObject.fromObject(xsth.getAnswercontent());
                    resData.put("stud_hwContent", dataStud.get("hwContent"));
                    resData.put("stud_Img", dataStud.get("Img"));
                }
                if (xsth.getType() == 1) {
                    //在线判断作业正确性
                    if (homeworkData.get(0).get("title") != null && !"".equals(homeworkData.get(0).get("title"))) {
                        for (int i = 0; i < homeworkData.size() - 1; i++) {
                            Map<String, Object> aw = homeworkData.get(i);
                            tAnswer += aw.get("rightAnswer").toString() + ",";
                        }
                        tAnswer += homeworkData.get(homeworkData.size() - 1).get("rightAnswer");
                        anwes = tAnswer.trim().split(",");
                        for (Map<String, Object> mapData : homeworkData) {
                            Map<String, Object> homeworkTitle = getData(mapData);
                            try {
                                homeworkTitle.put("answer", AnswerEnums.getMessage(Integer.parseInt(mapData.get("rightAnswer").toString())));
                            } catch (Exception e) {
                                resData.put("message", "查询选择题答案为空");
                                return resData;
                            }
                            if (sAnws != null && sAnws.length > j) {
                                homeworkTitle.put("sAnswer", AnswerEnums.getMessage(Integer.parseInt(sAnws[j])));
                                if (Integer.parseInt(anwes[j]) == Integer.parseInt(sAnws[j])) {
                                    awSuccess++;
                                    homeworkTitle.put("judge", true);
                                } else {
                                    awError++;
                                    homeworkTitle.put("judge", false);
                                }
                            } else {
                                homeworkTitle.put("sAnswer", "");
                                homeworkTitle.put("judge", "");
                            }
                            j++;
                            data.add(homeworkTitle);
                        }
                    }
                    resData.put("choice", data);
                    resData.put("awSuccess", awSuccess);
                    resData.put("awError", awError);
                }
            }
           else if (xsth.getState()==3){
                if (xsth.getType() == 1) {
                    //在线判断作业正确性
                    if (homeworkData.get(0).get("title") != null && !"".equals(homeworkData.get(0).get("title"))) {
                        for (int i = 0; i < homeworkData.size() - 1; i++) {
                            Map<String, Object> a_w = homeworkData.get(i);
                            tAnswer += a_w.get("rightAnswer").toString() + ",";
                        }
                        tAnswer += homeworkData.get(homeworkData.size() - 1).get("rightAnswer");
                        anwes = tAnswer.trim().split(",");
                        for (Map<String, Object> mapData : homeworkData) {
                            Map<String, Object> homeworkTitle = getData(mapData);
                            if (sAnws != null && sAnws.length > j) {
                                homeworkTitle.put("sAnswer", AnswerEnums.getMessage(Integer.parseInt(sAnws[j])));
                                if (Integer.parseInt(anwes[j]) != Integer.parseInt(sAnws[j])) {
                                    awError++;
                                    homeworkTitle.put("judge", false);
                                } else {
                                    awSuccess++;
                                    homeworkTitle.put("judge", true);
                                }
                            } else {
                                homeworkTitle.put("sAnswer", "");
                                homeworkTitle.put("judge", "");
                            }
                            try {
                                homeworkTitle.put("answer", AnswerEnums.getMessage(Integer.parseInt(mapData.get("rightAnswer").toString())));
                            } catch (Exception e) {
                                resData.put("message", "查询选择题答案为空");
                                return resData;
                            }
                            j++;
                            data.add(homeworkTitle);
                        }
                    }
                    resData.put("choice", data);
                    resData.put("awSuccess", awSuccess);
                    resData.put("awError", awError);
                }
                if (xsth.getType() == 2) {
                    //resData.put("choice",homeworkData);
                    Map dataStud = JSONObject.fromObject(xsth.getAnswercontent());
                    resData.put("stud_hwContent", dataStud.get("hwContent"));
                    resData.put("stud_Img", dataStud.get("Img"));
                }
                Map teacherCommentKey = new HashMap();
                teacherCommentKey.put("teacherId",SystemParam.getUserId());
                teacherCommentKey.put("stuHomeworkId",xsth.getId());
                List<XnResourceRelation> resourceRelations = resourceRelationMapper.getTeacherComment(teacherCommentKey);
                if (resourceRelations.size()!=0) {
                    resData.put("imgPath", resourceRelations.get(0).getAttr1());
                    resData.put("comment",resourceRelations.get(0).getAttr2());
                }
            }
            resData.put("stu_hwm_id",xsth.getId());
        }
        return resData;
    }

    public Map<String,Object> getData(Map<String,Object> mapData){
        Map<String,Object> homeworkTitle = new HashMap<>();
        homeworkTitle.put("title",mapData.get("title"));
        homeworkTitle.put("A",mapData.get("answerA"));
        homeworkTitle.put("B",mapData.get("answerB"));
        homeworkTitle.put("C",mapData.get("answerC"));
        homeworkTitle.put("D",mapData.get("answerD"));
        return homeworkTitle;
    }

    /**
     * 教师评语
     * @param imgPath 评语照片地址
     * @param comment 评语内容
     * @param teacherId 教师id
     * @param stuId 学生id
     * @param hwmId 作业id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertComment(String imgPath, String comment,int teacherId,int stuId,int hwmId) {
        //查询学生作业
        Map<String,Object> mapKey = new HashMap<>();
        mapKey.put("hewId",hwmId);
        mapKey.put("stuId",stuId);
        List<XnStuHomework> stuHomeworks =  stuHomeworkMapper.selectByPrimaryKey(mapKey);
        if (stuHomeworks == null && "".equals(stuHomeworks)){
            return -2;
        }
        List<XnResourceRelation> data = new ArrayList<>();
        List keyList = new ArrayList();
        for (XnStuHomework xnStuHomework : stuHomeworks){
            XnResourceRelation resourceRelation = getResourceRelation(teacherId,xnStuHomework.getId().toString(),imgPath,comment);
            data.add(resourceRelation);
            keyList.add(xnStuHomework.getId());
        }
        int i = resourceRelationMapper.insertTeacherComment(data);
        Map key = new HashMap();
        key.put("state",ConstantsDate.HOMEWORKSTATE3);
        key.put("list",keyList);
        int j = stuHomeworkMapper.updateStuHwm(key);
        try{
            CustomException.customeIf(i);
            CustomException.customeIf(j);
        }catch(Exception e){
            return -1;
        }
        return i;
    }

    /**
     * 教师批量评语
     * @param imgPath 评语照片地址
     * @param comment 评语类容
     * @param teacherId 教师ID
     * @param stuHmwIds 学生作业ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertComments(String imgPath, String comment, int teacherId, String stuHmwIds) {
        String[] shmIds = stuHmwIds.split(",");
        List<XnResourceRelation> data = new ArrayList<>();
        for (String id : shmIds) {
            XnResourceRelation resourceRelation = getResourceRelation(teacherId,id,imgPath,comment);
            data.add(resourceRelation);
        }
        Map key = new HashMap();
        key.put("state",ConstantsDate.HOMEWORKSTATE3);
        List dataKey = new ArrayList();
        if (shmIds.length!=0){
            for (String s : shmIds) {
                dataKey.add(Integer.parseInt(s));
            }
        }
        key.put("list",dataKey);
        int i = resourceRelationMapper.insertTeacherComment(data);
        int j = stuHomeworkMapper.updateStuHwm(key);
        try{
            CustomException.customeIf(i);
            CustomException.customeIf(j);
        }catch(Exception e){
            return -2;
        }
        return i;
    }

    public XnResourceRelation  getResourceRelation(int teacherId,String id,String imgPath,String comment){
        XnResourceRelation resourceRelation = new XnResourceRelation();
        resourceRelation.setResourceatable(ConstantsDate.HLTEACHER);
        resourceRelation.setResourceaid(teacherId);
        resourceRelation.setResourcebtable(ConstantsDate.XNSTUHOMEWORK);
        resourceRelation.setResourcebid(Integer.parseInt(id.trim()));
        resourceRelation.setIsdelete(ConstantsDate.DELETE_ON);
        resourceRelation.setCreatetime(new Date());
        resourceRelation.setAttr1(imgPath);
        resourceRelation.setAttr2(comment);
        return resourceRelation;
    }

    /**
     * 作业详情 web
     * @param hwmId 作业ID
     * @return
     */
    @Override
    public Map<String, Object> getHomeworkInformation(int hwmId) {
        Map<String,Object> data = new HashMap<>();
        List<Map<String,Object>> dataList = new ArrayList<>();
        List<Map<String,Object>> homework =  homeworkManageMapper.getHomeworkInformation(hwmId);
        if (homework==null || homework.size()==0){
            return null;
        }
        if (homework.get(0).get("hwContent")!=null && !"".equals(homework.get(0).get("hwContent"))){
            data.put("hwContent",homework.get(0).get("hwContent"));
            Map map_data = JSONObject.fromObject(homework.get(0).get("hwContent"));
            data.put("hw_content",map_data.get("hwContent"));
            data.put("hw_content_img",map_data.get("Img"));
        }
        data.put("hewId",homework.get(0).get("hewId"));
        data.put("expireTime",homework.get(0).get("expireTime").toString().substring(0,16));
        data.put("teacherId",homework.get(0).get("teacherId"));
        data.put("subject",homework.get(0).get("subject"));
        //检索班级名字
        if(homework.get(0).get("acceptClass")==null || "".equals(homework.get(0).get("acceptClass"))){
            return null;
        }
        String[] classIds = homework.get(0).get("acceptClass").toString().split(",");
        HlSchclassKey key = new HlSchclassKey();
        String class_name = "";
        for(String id: classIds){
            key.setId(Integer.parseInt(id));
            HlSchclass schclass = schclassMapper.selectByPrimaryKey(key);
            class_name = schclass.getClassname() + " ";
        }
        Map dataMap = new HashMap();
        dataMap.put("class_name",class_name);
        dataMap.put("class_id",homework.get(0).get("acceptClass"));
        data.put("acceptClass",dataMap);
        data.put("homeworkName",homework.get(0).get("hwName"));
        if (homework.get(0).get("title")!=null && !"".equals(homework.get(0).get("title"))){
            for (Map<String,Object> mapData : homework){
                Map<String,Object> homeworkTitle = getData(mapData);
                homeworkTitle.put("rightAnswer", AnswerEnums.getMessage(Integer.parseInt(mapData.get("rightAnswer").toString())));
                homeworkTitle.put("title_id",mapData.get("titleId"));
                dataList.add(homeworkTitle);
            }
        }
        data.put("choice",dataList);
        return data;
    }

    /**
     * web发布作业
     * @param hwName 作业名字
     * @param hwType 作业类型
     * @param acceptClass 接收班级
     * @param subject 接收科目
     * @param expireTime 截止时间
     * @param hwContent 作业内容
     * @param hwContentImg 作业内容照片地址
     * @param array
     * @return
     */
    @Override
    @Transactional
    public int insertHomework(String hwName,Integer hwType,String acceptClass,String subject,
                              String expireTime,String hwContent,String hwContentImg,String array){
        XnIntegralRule rule = queryIntegralRule(ConstantsDate.TOTAL_HOMEWORK);
        XnHomeworkManage homeworkManage = new XnHomeworkManage();
        homeworkManage.setUid(SystemParam.getUserId());
        homeworkManage.setHwname(hwName);
        homeworkManage.setSubject(subject);
        homeworkManage.setCreatetime(new Date());
        try {
            //homeworkManage.setExpiretime(new SimpleDateFormat("yyyy-MM-dd HH:dd:ss").parse(expireTime));
            homeworkManage.setExpiretime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expireTime));
        }catch (ParseException e){
            return  -2;
        }
        if (hwType == 1) {
            homeworkManage.setHwtype(ConstantsDate.ONLINE);
        } else if (hwType == 2) {
            homeworkManage.setHwtype(ConstantsDate.UPLOADING);
        }
        homeworkManage.setAcceptclass(acceptClass);
        String homework_content = "{\"hwContent\":\"" + hwContent +""+ "\",\"Img\":\"" + hwContentImg+""+"\"}";
//        if ((hwContentImg!=null && !"".equals(hwContentImg)) || (hwContent !=null && "".equals(hwContent)))
        homeworkManage.setHwcontent(JSONObject.fromObject(homework_content).toString());

        int i = homeworkManageMapper.insertSelective(homeworkManage);
        try {
            CustomException.customeIf(i);
        }catch (Exception e){
            return -1;
        }

        //查询班级学生
        String[] classId = acceptClass.split(",");
        List<XnStuHomework> stuHomeworks = new ArrayList<>();
        for (String info : classId) {
            List<HlStudentinfo> students = studentinfoMapper.getStudentInformation(Integer.parseInt(info));
            if(students.size()<1)return -3;
            stuHomeworks.addAll(getListData(students,homeworkManage.getId(),hwType));
        }
        int j = stuHomeworkMapper.insertStuHomeworkEs(stuHomeworks);
        try{
            CustomException.customeIf(j);
        } catch(Exception e){
            return -1;
        }
        if (array!=null && !"".equals(array)){
            List<XnHomeworkTitle> homeworkTitles =new Gson().fromJson(array, new TypeToken<List<XnHomeworkTitle>>(){}.getType());
            List<XnHomeworkTitle> key = new ArrayList<>();
            for (int g=0;g<homeworkTitles.size();g++){
                homeworkTitles.get(g).setHwid(homeworkManage.getId());
                key.add(homeworkTitles.get(g));
            }
            int h = homeworkTitleMapper.insertEs(key);
            try {
                CustomException.customeIf(h);
            }catch (MyException e){
                return -1;
            }
        }
        if (rule!=null && rule.getIsopen()==ConstantsDate.TOTAL_YES){
            int k = insertIntegral(ConstantsDate.TOTAL_HOMEWORK,rule.getIntegralnumer());
            try {
                CustomException.customeIf(k);
            }catch (MyException e){
                return -1;
            }
        }
        return i;
    }

    public List<XnStuHomework> getListData(List<HlStudentinfo> students,int hwid,int hwType){
        List<XnStuHomework> stuHomeworks = new ArrayList<>();
        for (HlStudentinfo studentID: students) {
            XnStuHomework stuHomework = new XnStuHomework();
            stuHomework.setStuid(studentID.getId());
            stuHomework.setState(ConstantsDate.HOMEWORKSTATE1);
            stuHomework.setHwid(hwid);
            if (hwType==1){
                stuHomework.setType(ConstantsDate.ONLINE);
            }else if (hwType==2){
                stuHomework.setType(ConstantsDate.UPLOADING);
            }
            stuHomeworks.add(stuHomework);
        }
        return stuHomeworks;
    }

    /**
     * 发布作业 根据该老师教授科目选择班级
     * @param subject 科目
     * @return
     */
    @Override
    public List getClasses(String subject){
        Integer teacherId = SystemParam.getUserId();
        Integer schoolId = SystemParam.getSchoolId();
        Map key = new HashMap();
        key.put("schoolId",schoolId);
        key.put("teacherId",teacherId);
        key.put("subject",subject);
        return curriculumMapper.getClasses(key);
    }
}
