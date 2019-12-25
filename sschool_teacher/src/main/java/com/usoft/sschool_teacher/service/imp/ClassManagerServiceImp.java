package com.usoft.sschool_teacher.service.imp;

import com.usoft.smartschool.pojo.*;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.enums.AbsentEnums;
import com.usoft.sschool_teacher.enums.CommonEnums;
import com.usoft.sschool_teacher.exception.CustomException;
import com.usoft.sschool_teacher.exception.MyException;
import com.usoft.sschool_teacher.mapper.*;
import com.usoft.sschool_teacher.service.IClassManagerService;
import com.usoft.sschool_teacher.util.ConstantsDate;
import net.sf.json.JSONObject;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 陈秋
 * @Date: 2019/5/5 17:52
 * @Version 1.0
 */
@Service
public class ClassManagerServiceImp extends QueryAndInsertImp implements IClassManagerService {
    @Resource
    private XnIntrestClassMapper intrestClassMapper;

    @Resource
    private HlSchclassMapper schclassMapper;

    @Resource
    private HlStudentinfoMapper studentinfoMapper;

    @Resource
    private XnAbsentManageMapper absentManageMapper;

    @Resource
    private XnClassCircleMapper classCircleMapper;

    @Resource
    private XnClassAlbumMapper classAlbumMapper;

    @Resource
    private HlTeacherMapper teacherMapper;

    @Resource
    private XnMessageCenterMapper messageCenterMapper;

    @Resource
    private XnCircleLikeMapper circleLikeMapper;

    @Resource
    private XnUserRecordMapper recordMapper;

    @Resource
    private XnCommentTableMapper commentTableMapper;
    private HlSchclassKey hlSchclassKey_key = new HlSchclassKey();
    /**
     * 查询教师管理班级
     * @param teacherId
     * @return
     */
    @Override
    public List<Map<String, Object>> getManagerClass(Integer teacherId,int page,int start) {
        List<XnIntrestClass> intrestClass = intrestClassMapper.getManagerInsertApp(teacherId);
        Map<String,Object> key = new HashMap<>();
        key.put("teacherId",teacherId);
        key.put("page",page);
        key.put("start",start);
        List<Map<String,Object>> classManager =  schclassMapper.getManagerClass(key);
        List<Map<String,Object>> dataList = new ArrayList<>();
        if (classManager.size()!=0){
            for (Map<String,Object> mp : classManager){
                Map<String,Object> mapData1 = new HashMap<>();
                mapData1.put("classId",mp.get("classId"));
                mapData1.put("className",mp.get("ClassName"));
                mapData1.put("teacherName",mp.get("Tname"));
                mapData1.put("time","");
                mapData1.put("classPhotos",mp.get("ClassImg"));
                mapData1.put("teacherId",mp.get("teacherId"));
                mapData1.put("classDes",mp.get("ClassDes"));
                mapData1.put("signType","");
                mapData1.put("classNum",mp.get("ClassNum"));
                mapData1.put("schoolId",mp.get("SchoolId"));
                if(mp!=null && mp.get("GradeType")!=null && !"".equals(mp.get("GradeType"))){
                    mapData1.put("grade", CommonEnums.getMessage(Integer.parseInt(mp.get("GradeType").toString().trim())));
                }else {
                    mapData1.put("grade", "");
                }
                if(mp!=null && mp.get("resourceBId")!=null && !"".equals(mp.get("resourceBId"))){
                    if (mp.get("resourceBId") == mp.get("teacherId")){
                        mapData1.put("classWork", "1");
                    }
                }else {
                    mapData1.put("classWork", "0");
                }
                mapData1.put("classCode",mp.get("ClassCode")!=null?mp.get("ClassCode"):"");
                mapData1.put("classTeach",mp.get("classTeach"));
                mapData1.put("ip",mp.get("IP"));
                mapData1.put("code",0);
                dataList.add(mapData1);
            }
        }
        if (intrestClass.size()!=0){
            for (XnIntrestClass xn : intrestClass){
                Map<String,Object> mapData1 = new HashMap<>();
                mapData1.put("classId",xn.getId());
                mapData1.put("className",xn.getClassname());
                mapData1.put("teacherName",xn.getTeaname());
                mapData1.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(xn.getBegindate()));
                mapData1.put("classPhotos",xn.getClassimg());
                mapData1.put("teacherId",xn.getTid());
                mapData1.put("signType",xn.getSigntype()!=null?xn.getSigntype():"");
                mapData1.put("classDes",xn.getClassdes());
                mapData1.put("classNum",xn.getClassnum());
                mapData1.put("schoolId",xn.getSid());
                mapData1.put("grade","");
                mapData1.put("classCode","");
                mapData1.put("classTeach","");
                mapData1.put("ip","");
                mapData1.put("classWork","");
                mapData1.put("code",1);
                dataList.add(mapData1);
            }
        }
        return dataList;
    }

    /**
     * 管理班级数量
     * @param teacherId
     * @return
     */
    @Override
    public int getManagerClassAppCount(Integer teacherId) {
        return intrestClassMapper.getManagerInsertAppCount(teacherId);
    }

    @Override
    public int getManagerClassCount(Integer teacherId) {
        return schclassMapper.getManagerClassCount(teacherId);
    }


    /**
     * 获取班级相册
     * @param classId 班级ID
     * @return
     */
    @Override
    public List<Map> getClassPhotos(int classId){
        List<XnClassAlbum> data = classAlbumMapper.getClassPhotos(classId);
        List<Map> listData = new ArrayList<>();
        if (data.size()==0){
            return null;
        }
        for (XnClassAlbum classAlbum:data){
            Map dataKey = new HashMap();
            dataKey.put("img_url",classAlbum.getImgurl());
            dataKey.put("img_name",classAlbum.getImgname());
            dataKey.put("id",classAlbum.getId());
            dataKey.put("img_create_time",classAlbum.getCreatetime().toString().substring(0,16));
            listData.add(dataKey);
        }
        return listData;
    }

    /**
     * 请假详情
     * @param absentId 请假ID
     * @return
     */
    @Override
    public Map getAbsentIfo(int absentId) {
        HlSchclass class_res = new HlSchclass();
        XnAbsentManage absentIfo = absentManageMapper.selectByPrimaryKey(absentId);
        if(absentIfo.getType() == ConstantsDate.CIRCLE_TEACHER){
            class_res.setClassname("教师");
        }else{
            hlSchclassKey_key.setId(absentIfo.getClassid());
            hlSchclassKey_key.setSchoolid(absentIfo.getSchoolid());
            class_res = schclassMapper.selectByPrimaryKey(hlSchclassKey_key);
        }
        if (absentIfo==null){
            return null;
        }
        Map mapData =  getXnabsentManager(absentIfo,class_res);
        return mapData;
    }

    /**
     * 班级审核管理
     * @param teacherId 教师ID
     * @param classId 班级ID
     * @param start 页码
     * @param page 没也显示条数
     * @return
     */
    @Override
    public List getAbsent(int teacherId, String classId,String status, int start, int page) {
        Map key = getKey(teacherId, classId, status, start, page);
        List<XnAbsentManage> absentManages= absentManageMapper.getAbsent(key);
        List listData = new ArrayList();
        if (absentManages.size()!=0){
            for (XnAbsentManage xnAbsentManage : absentManages){
                if (xnAbsentManage!=null){
                    hlSchclassKey_key.setId(xnAbsentManage.getClassid());
                    hlSchclassKey_key.setSchoolid(xnAbsentManage.getSchoolid());
                    HlSchclass class_res = schclassMapper.selectByPrimaryKey(hlSchclassKey_key);
                    Map data =  getXnabsentManager(xnAbsentManage,class_res);
                    listData.add(data);
                }
            }
        }
        return listData;
    }

    @Override
    public int getAbsentCount(int teacherId,String classId,String status,int start,int page){
        Map key = getKey(teacherId, classId, status, start, page);
        return absentManageMapper.getAbsentCount(key);
    }

    public Map getKey(int teacherId,String classId,String status,int start,int page){
        Map key = new HashMap();
        key.put("teacherId",teacherId);
        if (classId!=null && !"".equals(classId)){
            key.put("classId",classId.trim());
        }else {
            key.put("classId",null);
        }
        if (status!=null && !"".equals(status)){
            key.put("status",Integer.parseInt(status.trim()));
        }else {
            key.put("status",null);
        }
        key.put("page",page);
        key.put("start",(start-1)*page);
        return key;
    }

    public Map getXnabsentManager(XnAbsentManage xnAbsentManage,HlSchclass class_res){
        Map data = new HashMap();
        data.put("id",xnAbsentManage.getId());
        data.put("begin",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(xnAbsentManage.getBegindate()));
        data.put("end",new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(xnAbsentManage.getEnddate()));
        data.put("name",xnAbsentManage.getUsername());
        data.put("thing",xnAbsentManage.getAttr1());
        data.put("status", AbsentEnums.getMessage(xnAbsentManage.getStatus()));
        data.put("times",xnAbsentManage.getAttr2());
        data.put("class_name",class_res.getClassname());
        data.put("type",CommonEnums.getMessage(xnAbsentManage.getAbsenttype()));
        return data;
    }

    /**
     * 审批请假
     * @param absentId 请假ID集
     * @return
     */
    @Override
    @Transactional
    public int updateAbsent(String[] absentId,int status){

        //审批请假
        List list = new ArrayList();
        for (String id:absentId) {
            Map key = new HashMap();
            key.put("id",Integer.parseInt(id));
            list.add(key);
        }
        Map key = new HashMap();
        key.put("list",list);
        key.put("status",status);
        int i = absentManageMapper.updateAbsent(key);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -1;
        }
        //添加积分
        XnIntegralRule rule = queryIntegralRule(ConstantsDate.TOTAL_PASS);
        if (rule!=null && rule.getIsopen()==ConstantsDate.TOTAL_YES){
            int k = insertIntegral(ConstantsDate.TOTAL_PASS,rule.getIntegralnumer());
            try {
                CustomException.customeIf(k);
            }catch (MyException e){
                return -1;
            }
        }
        return i;
    }

    /**
     * 验证班主任
     */
    @Override
    public List<Integer> selectMangerClass(List list){
        return absentManageMapper.getClassIdes(list);
    }

    /**
     *  班级圈审核管理
     * @param classId
     * @param page
     * @param start
     * @return
     */
    @Override
    public List<Map> getClassCircle(int classId, int page, int start) {
        Map key = new HashMap();
        key.put("classId",classId);
        key.put("page",page);
        key.put("start",(start-1)*page);
        key.put("circleId",null);
        return getClassCircle(key);
    }

    @Override
    public List<Map> getWebClassCircle(Integer classId, int page, int start,Integer status){
        Map key = new HashMap();
        key.put("start",(page-1)*start);
        key.put("page",start);
        key.put("classId",classId);
        key.put("teacherId",SystemParam.getUserId());
        key.put("status",status);
        //班级圈
        List<Map> data = classCircleMapper.getClassCircle1(key);
        List<Map> res_data = new ArrayList<>();
        if (data.size()==0) return null;
        for (Map mapData:data){
            int type = Integer.parseInt(mapData.get("userType").toString());
            int id = Integer.parseInt(mapData.get("userId").toString());
            Map ifo_key = new HashMap();
            ifo_key.put("type",type);
            ifo_key.put("user_id",id);
            //头像
            Map data_map = classCircleMapper.getPhoto(ifo_key);
            Map img_data = JSONObject.fromObject(data_map.get("photos"));
            if (type == 2) {
                if (!img_data.containsKey("idImg")) {
                    mapData.put("img", "");
                } else {
                    mapData.put("img", img_data.get("idImg"));
                }
            }else {
                if (!img_data.containsKey("faceImg")) {
                    mapData.put("img", "");
                } else {
                    mapData.put("img", img_data.get("faceImg"));
                }
            }
            if (!mapData.containsKey("fileUrl"))mapData.put("fileUrl","");
            mapData.put("SName",data_map.get("SName"));
            mapData.put("time",mapData.get("createTime").toString().substring(0,16));
            if (!data_map.containsKey("ClassName")){
                mapData.put("ClassName","教师");
            }else {
                mapData.put("ClassName",data_map.get("ClassName"));
            }
            mapData.put("like_sums",mapData.containsKey("like_sums")?mapData.get("like_sums"):"");
            mapData.put("record_sums",mapData.containsKey("record_sums")?mapData.get("record_sums"):"");
            //查询评论
            Integer circle_id = Integer.parseInt(mapData.get("id").toString());
            Map key_comment = new HashMap();
            key_comment.put("sid",SystemParam.getSchoolId());
            key_comment.put("id",circle_id);
            List<XnCommentTable> commentTables =commentTableMapper.getCircle(key_comment);
            List<Map> root_list = new ArrayList<>();
            List<Map> body_list = new ArrayList<>();
            if (commentTables.size()!=0) {
                for (XnCommentTable commentTable : commentTables) {
                    if (commentTable.getParentid() == 0){
                        Map comment_map_data = getData(commentTable);
                        root_list.add(comment_map_data);
                    }
                    if (commentTable.getParentid() != 0){
                        Map list_comment_map = getData(commentTable);
                        body_list.add(list_comment_map);
                    }
                }
                if (root_list!=null && !root_list.isEmpty()){
                    for (Map map_data:root_list){
                        List<Map> child = Lists.newArrayList();
                        getList(map_data,body_list,child);
                        map_data.put("child",child);
                    }
                }
                mapData.put("comments",root_list);
            }
            res_data.add(mapData);
        }
        return res_data;
    }

    public void getList(Map rootList,List<Map> bodyList,List<Map> child){
        List<Map> res_data = Lists.newArrayList();
        List<Map> body_data = Lists.newArrayList();
        if (rootList!=null && !rootList.isEmpty()){
           for (Map map_data:bodyList){
               if (rootList.get("id").equals(map_data.get("parentId"))){
                   res_data.add(map_data);
                   map_data.put("name",rootList.get("comment_name"));
                   child.add(map_data);
               }else {
                   body_data.add(map_data);
               }
           }
        }
        if (!res_data.isEmpty()){
            for (Map childMap:res_data){
                getList(childMap,body_data,child);
            }
        }
        return ;
    }

    public Map getData(XnCommentTable data){
        Map data_map = new HashMap();
        data_map.put("comment_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getCreatetime()));
        data_map.put("comment_img",data.getUserimg()==null?"":data.getUserimg());
        data_map.put("comment_name",data.getUsername());
        data_map.put("comment_content",data.getContent());
        data_map.put("parentId",data.getParentid());
        data_map.put("id",data.getId());
        data_map.put("user_id",data.getUserid());
        data_map.put("user_status",data.getType());
        return data_map;
    }

    @Override
    public Integer getClassCircle1Count(Integer classId,Integer status){
        Map key = new HashMap();
        key.put("classId",classId);
        key.put("teacherId",SystemParam.getUserId());
        key.put("status",status);
        return classCircleMapper.getClassCircle1Count(key);
    }

    @Override
    public int getClassCircleCount(int classId) {
        return classCircleMapper.getClassCircleCount(classId);
    }

    /**
     *  班级圈详情
     * @param circleId 班级圈ID
     * @return
     */
    @Override
    @Transactional
    public List<Map> getCircleIfo(int circleId){
        Map key = new HashMap();
        key.put("classId",null);
        key.put("page",null);
        key.put("start",null);
        key.put("circleId",circleId);
        key.put("code",1);
        XnUserRecord userRecord = recordMapper.selectByPrimaryKey(circleId);
        if (userRecord ==null ){
            XnUserRecord record = new XnUserRecord();
            record.setSid(SystemParam.getSchoolId());
            record.setUsertype(ConstantsDate.CIRCLE_TEACHER);
            record.setUserid(SystemParam.getUserId());
            record.setCreatetime(new Date());
            record.setAttr1(String.valueOf(circleId));
            record.setType(ConstantsDate.CLASS_CIRCLE);
            int i = recordMapper.insertSelective(record);
            try {
                CustomException.customeIf(i);
            }catch (MyException e){
                return null;
            }
        }
        return getClassCircle(key);
    }

    /**
     * 点赞数
     * @param circleId
     * @return
     */
    @Override
    public Integer getCount(int circleId){
        return circleLikeMapper.getCount(circleId);
    }

    public List<Map> getClassCircle(Map key){
        List<Map> data = new ArrayList<>();
        if (!key.containsKey("code"))
            data = classCircleMapper.getClassCircle(key);
        else if (key.containsKey("code"))
            data = classCircleMapper.getClassCircleIfo(key);
        if (data.size()==0){
            return null;
        }
        List listData = new ArrayList();
        for (Map map:data) {
            map.put("time",map.get("createTime").toString().substring(0,16));
            Map mapData = JSONObject.fromObject(map.get("SPhoto"));
            String nnnnn = "{\"faceImg\":\"sschoolManageFile/201906//bfb0501c-480e-42cd-8528-89ed5bc1bb69.JPG\",\"idImg\":\"sschoolManageFile/201906//e985d12e-c4ba-4164-b539-4f651b578842.JPG\"}";
            if (map.containsKey("faceImg"))map.put("img",mapData.get("faceImg"));
            if(!map.containsKey("fileUrl")){
                map.put("fileUrl","");
            }
            listData.add(map);
        }
        return listData;
    }

    /**
     * 审核班级圈
     * @param circleIds 班级圈ID集合
     * @return
     */
    @Override
    @Transactional
    public int updateCircle(String[] circleIds,int status){
        XnIntegralRule rule = queryIntegralRule(ConstantsDate.TOTAL_CIRCLE);
        Map key = new HashMap();
        key.put("status",status);
        List circle = new ArrayList();
        for (String string:circleIds){
            Map key1 = new HashMap();
            key1.put("id",Integer.parseInt(string));
            circle.add(key1);
        }
        key.put("list",circle);
        int i = classCircleMapper.updateCircle(key);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -1;
        }
        if (rule!=null || rule.getIsopen()==ConstantsDate.TOTAL_YES){
            int k = insertIntegral(ConstantsDate.TOTAL_CIRCLE,rule.getIntegralnumer());
            try {
                CustomException.customeIf(k);
            }catch (MyException e){
                return -1;
            }
        }
        return i;
    }

    /**
     * 一键放学
     * @param classIds 班级ID集合
     * @return
     */
    @Override
    @Transactional
    public int insertClassOver(String[] classIds,String message,int code,String schoolCode){
        //int schoolId = SystemParam.getSchoolId();
        int teacherId = 0;
        String schoolId=new String();
        if (code==0) {
            teacherId = SystemParam.getUserId();
            schoolId = SystemParam.getSchoolId().toString();
        }else{
            teacherId = code;
            schoolId = schoolCode;
        }
        //int teacherId = 1;
        List classId = new ArrayList();
        for (String i : classIds){
            classId.add(Integer.parseInt(i));
        }
        //List<XnStuFamilyinfo> dataFamily = familyinfoMapper.getStuFamilies(classId);
        Map key = new HashMap();
        key.put("list",classId);
        key.put("schoolId",SystemParam.getSchoolId());
        List dataFamily = studentinfoMapper.getStudentID(key);
        List<Map> data = teacherMapper.getTeacherIfo(teacherId);
        if (dataFamily.size()==0){
            return -2;
        }
        if (data.size()==0){
            return -3;
        }
        List key_data = getMessage(dataFamily,message,teacherId,data.get(0).get("Tname").toString(),ConstantsDate.MESSAGE_CLASS_OVER,schoolId);
        int i = messageCenterMapper.insertClassOver(key_data);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -1;
        }
        return i;
    }

    /**
     * 获取普通班级
     * @param currentPage 当前页
     * @param pageSize 显示条数
     * @return
     */
    @Override
    public List getManagerClass2(String currentPage,String pageSize){
        Integer page = 50;
        Integer start = 1;
        if (currentPage !=null && !"".equals(currentPage)){
            start = Integer.parseInt(currentPage.trim());
        }
        if (pageSize !=null && !"".equals(pageSize)){
            page = Integer.parseInt(pageSize.trim());
        }
        Map key = new HashMap();
        key.put("page",page);
        key.put("start",(start-1)*page);
        key.put("teacherId",SystemParam.getUserId());
        List<Map<String,Object>> list = schclassMapper.getManagerClass(key);
        List data = Lists.newArrayList();
        list.stream()
                .forEach(mp->{
                    Map<String,Object> mapData1 = new HashMap<>();
                    mapData1.put("classId",mp.get("classId"));
                    mapData1.put("className",mp.get("ClassName"));
                    mapData1.put("classPhotos",mp.get("ClassImg"));
                    mapData1.put("teacherId",mp.get("teacherId"));
                    mapData1.put("classDes",mp.get("ClassDes"));
                    /*mapData1.put("signType","");
                    mapData1.put("time","");*/
                    mapData1.put("teacherName",mp.get("Tname"));
                    mapData1.put("classNum",mp.get("ClassNum"));
                    mapData1.put("schoolId",mp.get("SchoolId"));
                    if(mp.containsKey("resourceBId") && mp.get("GradeType")!=null && !"".equals(mp.get("GradeType"))){
                        mapData1.put("grade", CommonEnums.getMessage(Integer.parseInt(mp.get("GradeType").toString().trim())));
                    }else {
                        mapData1.put("grade", "");
                    }
                    if(mp.containsKey("resourceBId") && mp.get("resourceBId")!=null && !"".equals(mp.get("resourceBId"))){
                        if (mp.get("resourceBId") == mp.get("teacherId")){
                            mapData1.put("classWork", "1");
                        }
                    }else {
                        mapData1.put("classWork", "0");
                    }
                    /*mapData1.put("code",0);*/
                    mapData1.put("classCode",mp.get("ClassCode")!=null?mp.get("ClassCode"):"");
                    mapData1.put("classTeach",mp.get("classTeach"));
                    mapData1.put("ip",mp.get("IP"));
                    data.add(mapData1);
                });
        return data;
    }

    /**
     * 班级通知
     * @param classIds
     * @return
     */
    @Override
    @Transactional
    public int insertClassInform(String[] classIds,String message){
        //int schoolId = SystemParam.getSchoolId();
        int teacherId = SystemParam.getUserId();
        String schoolId = SystemParam.getSchoolId().toString();
        //int teacherId = 1;
        if (classIds.length==0){
            return -1;
        }
        List listData = new ArrayList();
        for (int i=0;i<classIds.length;i++){
            listData.add(Integer.parseInt(classIds[i]));
        }
        //List<XnStuFamilyinfo> familyData = familyinfoMapper.getStuFamilies(listData);
        Map key = new HashMap();
        key.put("schoolId",SystemParam.getSchoolId());
        key.put("list",listData);
        List familyData = studentinfoMapper.getStudentID(key);
        List<Map> data = teacherMapper.getTeacherIfo(teacherId);
        if (familyData.size()==0){
            return -2;
        }
        List key_data = getMessage(familyData,message,teacherId,data.get(0).get("Tname").toString(),ConstantsDate.MESSAGE_CLASS_MESSAGE,schoolId);
        int i = messageCenterMapper.insertClassOver(key_data);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -3;
        }
        return i;
    }

    public List getMessage(List<Integer> familyData,String message,int teacherId,String createuser,int type,String schoolId){
        List key = new ArrayList<>();
        for(Integer id:familyData){
            XnMessageCenter messageCenter = new XnMessageCenter();
            messageCenter.setCreateusertype(ConstantsDate.CENTER_TEACHER);
            messageCenter.setCreatetime(new Date());
            messageCenter.setIsread(ConstantsDate.READ_NO);
            messageCenter.setUsertype(ConstantsDate.CENTER_PARENTS);
            messageCenter.setMessagetype(type);
            messageCenter.setMessagecontent(message);
            messageCenter.setCreateuser(createuser);
            messageCenter.setCreateuserid(teacherId);
            messageCenter.setUserid(id);
            messageCenter.setAttr2(schoolId);
            key.add(messageCenter);
        }
        return key;
    }

    /**
     * 班级考勤
     * @param classId 班级ID
     * @return
     */
    @Override
    public Map getClassChecking(int classId){
        int classNum = 0;
        Map key = new HashMap();
        key.put("classId",classId);
        key.put("schoolId",SystemParam.getSchoolId());
        List<HlSchclass> classData = schclassMapper.getClassChecking(key);
        if (classData.size()==0){
            return null;
        }
        for(HlSchclass class_data:classData){
            classNum = class_data.getClassnum();
        }
        key.put("class_num",classNum);
        List<XnAbsentManage> absentData = absentManageMapper.getClassChecking(key);
        key.put("absent_num",absentData.size());
        List absent_num = new ArrayList();
        if (absentData.size()!=0){
            for (XnAbsentManage absent:absentData){
                Map data_list = new HashMap();
                data_list.put("name",absent.getUsername());
                data_list.put("state","请假");
                data_list.put("time","");
                absent_num.add(data_list);
            }
        }
        key.put("table",absent_num);
        return key;
    }
}
