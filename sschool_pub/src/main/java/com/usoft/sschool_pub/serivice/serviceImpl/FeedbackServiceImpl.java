package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.HlStudentinfoMapper;
import com.usoft.sschool_pub.mapper.HlTeacherMapper;
import com.usoft.sschool_pub.mapper.XnMessageCenterMapper;
import com.usoft.sschool_pub.mapper.XnResourceRelationMapper;
import com.usoft.sschool_pub.serivice.FeedbackService;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("FeedbackService")
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;
    @Resource
    private XnMessageCenterMapper xnMessageCenterMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private VoUtil voUtil;
    @Resource
    private SearchUtil searchUtil;

    @Override
    public MyResult searchMsg() {
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        Integer userId = type == 1 ? SystemParam.getChildId() : SystemParam.getUserId();
        //未读
//        List<Map<String, Object>> maps = xnMessageCenterMapper.msgList(type, userId, schoolId.toString());

        //自己发送的
//        List<Map<String, Object>> maps1 = xnMessageCenterMapper.myList(type, userId, schoolId.toString());

       /* if (maps.size()==0  && maps1.size()==0)return MyResult.failure("没有聊天记录");

        List<Map> list=new ArrayList<>();
        for (Map xmc:maps){
            Byte userType=null;
            if (xmc.get("createUserType").equals(3)){
                userType=2;
            }else {
                userType=Byte.valueOf(String.valueOf(xmc.get("createUserType")));
            }
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(userType, (int)xmc.get("createUserId"), schoolId);
            XnMessageCenterExample example=new XnMessageCenterExample();
            example.createCriteria().andAttr2EqualTo(schoolId.toString()).andCreateuseridEqualTo((Integer) xmc.get("createUserId")).andCreateusertypeEqualTo((Integer) xmc.get("createUserType"))
                    .andUseridEqualTo(userId).andUsertypeEqualTo(type).andIsreadEqualTo(0).andMessagetypeEqualTo(9);
            List<XnMessageCenter> xnMessageCenters = xnMessageCenterMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnMessageCenters)){
                objectObjectMap.put("isRead",false);
            }else {
                objectObjectMap.put("isRead",true);
            }
            objectObjectMap.put("userType",xmc.get("createUserType"));
            list.add(objectObjectMap);
        }
        for (Map m1:maps){
            for (Map m2:maps1){
                if (m1.get("createUserType").equals(m2.get("userType"))&& m1.get("createUserId").equals(m2.get("userId"))){
                    m2.put("userType",null);
                    m2.put("userId",null);
                }
            }
        }
        for (Map m:maps1){
            if (m.get("userType")!=null && m.get("userId")!=null){
                Byte userType=null;
                if (m.get("userType").equals(3)){
                    userType=2;
                }else {
                    userType=Byte.valueOf(String.valueOf(m.get("userType")));
                }
                Map<Object, Object> objectObjectMap = voUtil.userPhoto(userType, (int)m.get("userId"), schoolId);
                objectObjectMap.put("isRead",false);
                objectObjectMap.put("userType",m.get("userType"));
                list.add(objectObjectMap);
            }

        }*/
        List<Map> list = new ArrayList<>();
        List<XnMessageCenter> lst = xnMessageCenterMapper.lst(type, userId, schoolId);
        for (XnMessageCenter xmc : lst) {
            Byte userType = null;
            if (xmc.getCreateusertype().equals(3)) {
                userType = 2;
            } else {
                userType = Byte.valueOf(String.valueOf(xmc.getCreateusertype()));
            }
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(userType, xmc.getCreateuserid(), schoolId);
            if (xmc.getIsread()==1){
                objectObjectMap.put("isRead",false);
            }else {
                objectObjectMap.put("isRead",true);
            }
            objectObjectMap.put("userType", xmc.getCreateusertype());
            String str=TimeUtil.TimeToYYYYMMDDHHMMSS(xmc.getCreatetime()).substring(5,16);
            objectObjectMap.put("sentTime", str);
            objectObjectMap.put("content",xmc.getMessagecontent());
            list.add(objectObjectMap);
        }
        List<XnMessageCenter> mylst = xnMessageCenterMapper.mylst(type, userId, schoolId);
        for (XnMessageCenter m1:lst){
            for (XnMessageCenter m2:mylst){
                if (m1.getCreateusertype().equals(m2.getUsertype())&& m1.getCreateuserid().equals(m2.getUserid())){
                    mylst.remove(m2);
                    break;
                }
            }
        }
        for (XnMessageCenter xmc : mylst) {
            Byte userType = null;
            if (xmc.getUsertype().equals(3)) {
                userType = 2;
            } else {
                userType = Byte.valueOf(String.valueOf(xmc.getUsertype()));
            }
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(userType, xmc.getUserid(), schoolId);
            objectObjectMap.put("isRead",false);
            objectObjectMap.put("userType", xmc.getUsertype());
            String str=TimeUtil.TimeToYYYYMMDDHHMMSS(xmc.getCreatetime()).substring(5,16);
            objectObjectMap.put("sentTime", str);
            objectObjectMap.put("content",xmc.getMessagecontent());
            list.add(objectObjectMap);
        }
        return MyResult.success(list);
    }

    /**
     * 消息详情
     * @param acpType
     * @param acpId
     * @return
     */
    @Override
    public MyResult oneMeg(Integer acpType,Integer acpId) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        Integer userId = type==1?SystemParam.getChildId():SystemParam.getUserId();

        XnMessageCenterExample example=new XnMessageCenterExample();
        XnMessageCenterExample.Criteria criteria = example.createCriteria();
        criteria.andAttr2EqualTo(schoolId.toString()).andMessagetypeEqualTo(9).andCreateusertypeEqualTo(acpType).andCreateuseridEqualTo(acpId)
                .andUsertypeEqualTo(type).andUseridEqualTo(userId);
        XnMessageCenterExample.Criteria criteria1 = example.createCriteria();
        criteria1.andAttr2EqualTo(schoolId.toString()).andMessagetypeEqualTo(9).andCreateusertypeEqualTo(type).andCreateuseridEqualTo(userId)
                .andUsertypeEqualTo(acpType).andUseridEqualTo(acpId);
        example.or(criteria1);
        example.setOrderByClause("createTime ");
        List<XnMessageCenter> xnMessageCenters = xnMessageCenterMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnMessageCenters))return MyResult.failure("没有聊天记录");
        List<Map> list=new ArrayList<>();
        for (XnMessageCenter xmc:xnMessageCenters){
            Map<Object, Object> objectObjectMap = voUtil.userPhoto(Byte.valueOf(xmc.getCreateusertype().toString()), xmc.getCreateuserid(), Integer.valueOf(xmc.getAttr2()));
            String str=TimeUtil.TimeToYYYYMMDDHHMMSS(xmc.getCreatetime()).substring(5,16);
            objectObjectMap.put("sentTime", str);
            objectObjectMap.put("content",xmc.getMessagecontent());
            objectObjectMap.put("userType",xmc.getCreateusertype());
            objectObjectMap.put("messageId",xmc.getId());
            if (xmc.getCreateusertype().equals(type) && xmc.getCreateuserid().equals(userId)){
                objectObjectMap.put("iSend",true);
            }else {
                objectObjectMap.put("iSend",false);
            }
            list.add(objectObjectMap);
            xmc.setIsread(1);
            if (xmc.getUserid()==userId && xmc.getUsertype()==type){
                xnMessageCenterMapper.updateByPrimaryKeySelective(xmc);
            }

        }
        return MyResult.success(list);

    }



    /**
     * 发送消息
     * @param content
     * @return
     */
    @Override
    public MyResult sendMsg(String content,Integer acpType,Integer acpId) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        Integer userId = type==1?SystemParam.getChildId():SystemParam.getUserId();

        //消息写入数据库
        XnMessageCenter xmc=new XnMessageCenter();
        xmc.setMessagecontent(content);
        xmc.setCreatetime(new Date());
        xmc.setCreateusertype(type);
        xmc.setCreateuserid(userId);
        Map<Object, Object> objectObjectMap = voUtil.userPhoto(Byte.valueOf(type.toString()), userId, schoolId);
        if (ObjectUtil.isEmpty(objectObjectMap)){
            xmc.setCreateuser(null);
        }else {
            xmc.setCreateuser(String.valueOf(objectObjectMap.get("stuName")));
        }
        xmc.setIsread(0);
        xmc.setUserid(acpId);
        xmc.setUsertype(acpType);
        xmc.setMessagetype(9);
        xmc.setAttr2(schoolId.toString());
        int i = xnMessageCenterMapper.insertSelective(xmc);
        if (i!=1){
            return MyResult.failure("留言失败");
        }
        return MyResult.success("留言成功");
    }

    /**
     * 删除聊天记录
     * @param acpType
     * @param acpId
     * @return
     */
    @Transactional
    @Override
    public MyResult delete(Integer acpType, Integer acpId) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer type = SystemParam.getType();
        Integer userId = type==1?SystemParam.getChildId():SystemParam.getUserId();

        XnMessageCenterExample example=new XnMessageCenterExample();
        XnMessageCenterExample.Criteria criteria = example.createCriteria();
        criteria.andAttr2EqualTo(schoolId.toString()).andMessagetypeEqualTo(9).andCreateusertypeEqualTo(acpType).andCreateuseridEqualTo(acpId)
                .andUsertypeEqualTo(type).andUseridEqualTo(userId);
        XnMessageCenterExample.Criteria criteria1 = example.createCriteria();
        criteria1.andAttr2EqualTo(schoolId.toString()).andMessagetypeEqualTo(9).andCreateusertypeEqualTo(type).andCreateuseridEqualTo(userId)
                .andUsertypeEqualTo(acpType).andUseridEqualTo(acpId);
        example.or(criteria1);
        example.setOrderByClause("createTime ");
        List<XnMessageCenter> xnMessageCenters = xnMessageCenterMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnMessageCenters))return MyResult.failure("没有聊天记录");
        int m=0;
        for(XnMessageCenter xmc:xnMessageCenters){
            xmc.setAttr1(userId.toString());
            xmc.setAttr3(type.toString());
            int i = xnMessageCenterMapper.updateByPrimaryKeySelective(xmc);
            m=m+i;
        }
        if (m!=xnMessageCenters.size()){
            return MyResult.failure("删除失败");
        }
        return MyResult.success("删除成功");
    }
}
