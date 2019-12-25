package com.usoft.sschool_pub.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.MessageService;
import com.usoft.sschool_pub.util.ResultPage;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {
    @Resource
    private VoUtil voUtil;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private HlCurriculumMapper hlCurriculumMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private XnTeacherIntegralMapper xnTeacherIntegralMapper;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;
    @Resource
    private XnClassChatMapper xnClassChatMapper;
    @Resource
    private XnMessageCenterMapper xnMessageCenterMapper;
    @Resource
    private XnClassCircleMapper xnClassCircleMapper;
    @Resource
    private XnKindnessSchoolMapper xnKindnessSchoolMapper;

    public static final String mess="放学了，请您尽快确认孩子是否回家";
    //我的消息通知
    @Override
    public MyResult myNotice(Integer type, Integer schoolId) {
        Integer userId=null;
        if (type==1){userId= SystemParam.getChildId(); }
        if (type==2){userId=SystemParam.getUserId();}
        XnMessageCenterExample example=new XnMessageCenterExample();
        example.createCriteria().andAttr2EqualTo(String.valueOf(schoolId)).andUsertypeEqualTo(type).andUseridEqualTo(userId)
        .andIsreadNotEqualTo(2).andMessagetypeNotEqualTo(9);
        example.setOrderByClause("createTime desc");
        List<XnMessageCenter> xnMessageCenters = xnMessageCenterMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnMessageCenters))return MyResult.failure("没有找到您的通知信息");
        List<Map> list=new ArrayList<>();
        for (XnMessageCenter xmc:xnMessageCenters){
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(Byte.valueOf(xmc.getCreateusertype().toString()), xmc.getCreateuserid(), Integer.valueOf(xmc.getAttr2()));
            if (xmc.getMessagetype()==1){
                objectObjectMap.put("messageContent",mess);
            }else {
                objectObjectMap.put("messageContent",xmc.getMessagecontent());
            }
            objectObjectMap.put("createTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xmc.getCreatetime()));
            objectObjectMap.put("isRead",xmc.getIsread());
            objectObjectMap.put("messageId",xmc.getId());
            objectObjectMap.put("messageType",xmc.getMessagetype());
            list.add(objectObjectMap);
        }
        return MyResult.success(list);
    }

    /**
     * 改变消息状态为已读
     * @param mid
     * @return
     */
    @Override
    public MyResult changeMessageState(Integer mid) {
        XnMessageCenter xnMessageCenter = xnMessageCenterMapper.selectByPrimaryKey(mid);
        xnMessageCenter.setIsread(1);
        int i = xnMessageCenterMapper.updateByPrimaryKeySelective(xnMessageCenter);
        if (i!=1){
            return MyResult.failure("改变状态失败");
        }
        return MyResult.success("改变状态成功");
    }

    /**
     * 删除消息
     * @param mid
     * @return
     */
    @Override
    public MyResult deleteMessage(Integer mid) {
        XnMessageCenter xnMessageCenter = xnMessageCenterMapper.selectByPrimaryKey(mid);
        xnMessageCenter.setIsread(2);
        int i = xnMessageCenterMapper.updateByPrimaryKeySelective(xnMessageCenter);
        if (i!=1){
            return MyResult.failure("删除失败");
        }
        return MyResult.success("删除成功");
    }

    /**
     * 按消息类型删除消息
     * @param messageType
     * @return
     */
    @Override
    public MyResult deleteByType(Integer messageType) {
        Integer type = SystemParam.getType();
        Integer uid=0;
        if (type==1){
            uid=SystemParam.getChildId();
        }else {
            uid=SystemParam.getUserId();
        }
        XnMessageCenterExample example=new XnMessageCenterExample();
        example.createCriteria().andUseridEqualTo(uid).andUsertypeEqualTo(type).andAttr2EqualTo(SystemParam.getSchoolId().toString())
        .andMessagetypeEqualTo(messageType).andIsreadNotEqualTo(2);
        List<XnMessageCenter> xnMessageCenters = xnMessageCenterMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnMessageCenters))return MyResult.failure("没有消息");
        int s=0;
        for (XnMessageCenter xmc:xnMessageCenters){
            xmc.setIsread(2);
            int i = xnMessageCenterMapper.updateByPrimaryKeySelective(xmc);
            if (i==1){
                s+=1;
            }
        }
        if (s==xnMessageCenters.size()){
            return MyResult.success("删除成功");
        }else {
            return MyResult.failure("删除失败");
        }

    }

    /**
     * 消息详情
     * @param mid
     * @return
     */
    @Override
    public MyResult messageInfo(Integer schoolId,Integer mid) {
        XnMessageCenter xnMessageCenter = xnMessageCenterMapper.selectByPrimaryKey(mid);
        if (ObjectUtil.isEmpty(xnMessageCenter))return MyResult.failure("没找到该消息");
        Map map=new HashMap();
        if (xnMessageCenter.getMessagetype()==3){
            XnClassCircle xnClassCircle = xnClassCircleMapper.selectByPrimaryKey(xnMessageCenter.getTypeid());
            map = voUtil.searchComment(schoolId, 1, xnClassCircle.getId());
        }
        if (xnMessageCenter.getMessagetype()==4){
            XnKindnessSchool xnKindnessSchool = xnKindnessSchoolMapper.selectByPrimaryKey(xnMessageCenter.getTypeid());
            map= voUtil.searchComment(schoolId, 2, xnKindnessSchool.getId());
        }
        //待确认
        return MyResult.success(map);
    }

    /**
     * 班级通讯录
     * @param type
     * @param schoolId
     * @return
     */
    @Override
    public MyResult mailList(Integer classId,Integer type, Integer schoolId) {
        Integer userId=null;
        if (classId==null){
            if (type==1){
                userId= SystemParam.getChildId();
                HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, userId);
                classId=studentinfo.getClassid();
            }
            if (type==2){
                userId=SystemParam.getUserId();
                List<Map> list = searchUtil.teacherClass(schoolId, userId);
                classId= (Integer) list.get(0).get("classId");
            }
        }else {
            if (type==1){
                userId= SystemParam.getChildId();
            }
            if (type==2){
                userId=SystemParam.getUserId();
            }
        }
        HlStudentinfoExample example=new HlStudentinfoExample();
        example.createCriteria().andSchoolidEqualTo(schoolId).andClassidEqualTo(classId);
        List<HlStudentinfo> hlStudentinfos = hlStudentinfoMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(hlStudentinfos))return MyResult.failure("没有找到该班级的学生信息");
        List<Map> list=new ArrayList<>();
        XnResourceRelationExample example1=new XnResourceRelationExample();
        example1.createCriteria().andResourceatableEqualTo("hl_teacher").andResourcebtableEqualTo("hl_schclass")
                .andResourcebidEqualTo(classId);
        List<XnResourceRelation> xnResourceRelations = xnResourceRelationMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(xnResourceRelations))return MyResult.success("没找到班主任的信息",list);
        //班主任信息
        HlTeacher ht=new HlTeacher();
        ht.setId(xnResourceRelations.get(0).getResourceaid());
        ht.setSchoolid(schoolId);
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
        if (ObjectUtil.isEmpty(hlTeacher))return MyResult.success("没找到老师信息",list);
        Map map=new HashMap();
        map.put("id",hlTeacher.getId());
        map.put("stuName",hlTeacher.getTname());
        if (hlTeacher.getImagesrc()==null){
            map.put("ImageSrc",null);
        }else {
            String str=hlTeacher.getImagesrc();
            //JSON json= JSONObject.parseObject(str);
            Map<String,Object> map2=JSONObject.parseObject(str,Map.class);
            map.put("ImageSrc",map2.get("idImg"));
        }
        Integer integer = xnTeacherIntegralMapper.totleScore(schoolId, userId);
        if (integer==null){
            integer=0;
        }
        map.put("userType",3);
        map.put("totleScore",integer);
        map.put("subject","班主任");
        map.put("phone",hlTeacher.getMobile());
        list.add(map);
        //查询本班的所有教师
        List<Map<String, Object>> maps = hlCurriculumMapper.classTeacherId(schoolId, classId);
        if (ObjectUtil.isEmpty(maps))return MyResult.success("没找到本班的老师信息",list);
        for (int i=0;i<maps.size();i++){
            Map map1=new HashMap();
            //教师信息
            HlTeacher ht1=new HlTeacher();
            ht1.setId(Integer.valueOf(maps.get(i).get("ClassTeacher").toString()));
            ht1.setSchoolid(schoolId);
            HlTeacher hlTeacher1 = hlTeacherMapper.selectByPrimaryKey(ht1);
            if (ObjectUtil.isEmpty(hlTeacher1))return MyResult.success("没找到老师信息",list);
            map1.put("id",hlTeacher1.getId());
            map1.put("stuName",hlTeacher1.getTname());
            if (hlTeacher1.getImagesrc()==null){
                map1.put("ImageSrc",null);
            }else {
                String str=hlTeacher1.getImagesrc();
                //JSON json= JSONObject.parseObject(str);
                Map<String,Object> map2=JSONObject.parseObject(str,Map.class);
                map1.put("ImageSrc",map2.get("idImg"));
            }
            Integer integer1 = xnTeacherIntegralMapper.totleScore(schoolId, userId);
            if (integer1==null){
                integer1=0;
            }
            map1.put("userType",2);
            map1.put("totleScore",integer1);
            //老师教的科目
            map1.put("subject",hlTeacher1.getTsubject());
            map1.put("phone",hlTeacher1.getMobile());
            list.add(map1);
        }

        for (HlStudentinfo hl:hlStudentinfos){
            Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte) 1, hl.getId(), schoolId);
            objectObjectMap.put("userType",1);
            objectObjectMap.put("subject","学生");
            list.add(objectObjectMap);
        }
        return MyResult.success(list);
    }

    /**
     * 查询班级群聊消息
     * @param classId
     * @param type
     * @param schoolId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult classChat(Integer classId, Integer type, Integer schoolId,Integer pageNo,Integer pageSize) {
        Integer userId=null;
        if (classId==null){
            if (type==1){
                userId= SystemParam.getChildId();
                HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, userId);
                classId=studentinfo.getClassid();
            }
            if (type==2){
                userId=SystemParam.getUserId();
                List<Map> list = searchUtil.teacherClass(schoolId, userId);
                classId= (Integer) list.get(0).get("classId");
                if (classId==null){
                    return MyResult.failure("没有找到老师的班级");
                }
            }
        }else {
            if (type==1){
                userId= SystemParam.getChildId();
            }
            if (type==2){
                userId=SystemParam.getUserId();
            }
        }
        XnClassChatExample example=new XnClassChatExample();
        example.createCriteria().andSidEqualTo(schoolId).andCidEqualTo(classId);
        List<XnClassChat> xnClassChats = xnClassChatMapper.selectByExample(example);
        List<Map> list=new ArrayList<>();
        for (XnClassChat xcc:xnClassChats){
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(xcc.getUsertype(), xcc.getUserid(), schoolId);
            objectObjectMap.put("content",xcc.getContent());
            objectObjectMap.put("createtime", TimeUtil.TimeToYYYYMMDDHHMMSS(xcc.getCreatetime()));
            objectObjectMap.put("messageId",xcc.getId());
            if (type==(int)xcc.getUsertype() && userId==xcc.getUserid()){
                objectObjectMap.put("isMy",1);
            }else {
                objectObjectMap.put("isMy",2);
            }
            list.add(objectObjectMap);
        }
        return MyResult.success(list);
    }

    /**
     * 发送消息
     * @param classId
     * @param content
     * @param type
     * @param schoolId
     * @return
     */
    @Override
    public MyResult addMessage(Integer classId, String content, Integer type, Integer schoolId) {
        if (ObjectUtil.isEmpty(content))return MyResult.failure("消息不能为空");
        Integer userId=null;
        if (classId==null){
            if (type==1){
                userId= SystemParam.getChildId();
                HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, userId);
                classId=studentinfo.getClassid();
            }
            if (type==2){
                userId=SystemParam.getUserId();
                List<Map> list = searchUtil.teacherClass(schoolId, userId);
                classId= (Integer) list.get(0).get("classId");
            }
        }else {
            if (type==1){
                userId= SystemParam.getChildId();
            }
            if (type==2){
                userId=SystemParam.getUserId();
            }
        }
        XnClassChat xcc=new XnClassChat();
        xcc.setSid(schoolId);
        xcc.setCid(classId);
        xcc.setUsertype(Byte.parseByte(type.toString()));
        xcc.setUserid(userId);
        xcc.setContent(content);
        xcc.setCreatetime(new Date());
        int i = xnClassChatMapper.insertSelective(xcc);
        if (i!=1){
            return MyResult.failure("保存信息失败");
        }
        XnClassChatExample example=new XnClassChatExample();
        example.createCriteria().andSidEqualTo(schoolId).andCidEqualTo(classId).andUseridEqualTo(userId)
                .andUsertypeEqualTo(Byte.parseByte(type.toString()));
        example.setOrderByClause("id desc");
        List<XnClassChat> xnClassChats = xnClassChatMapper.selectByExample(example);
        Map<Object, Object> objectObjectMap = voUtil.userPhoto(xnClassChats.get(0).getUsertype(), xnClassChats.get(0).getUserid(), schoolId);
        objectObjectMap.put("content",xnClassChats.get(0).getContent());
        objectObjectMap.put("createtime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnClassChats.get(0).getCreatetime()));
        objectObjectMap.put("messageId",xnClassChats.get(0).getId());
        objectObjectMap.put("isMy",1);
        return MyResult.success("发布成功",objectObjectMap);
    }

}
