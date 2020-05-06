package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.ClassCircleService;
import com.usoft.sschool_pub.util.ResultPage;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import com.usoft.sschool_pub.util.UploadFileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

@Service("ClassCircleService")
public class ClassCircleServiceImpl implements ClassCircleService {
    @Resource
    private VoUtil voUtil;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private XnClassCircleMapper xnClassCircleMapper;
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private XnCircleLikeMapper xnCircleLikeMapper;
    @Resource
    private XnCommentTableMapper xnCommentTableMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private XnClascircleRuleMapper xnClascircleRuleMapper;
    @Resource
    private XnUserRecordMapper xnUserRecordMapper;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;
    @Resource
    private XnClassAlbumMapper xnClassAlbumMapper;
    @Resource
    private HlCurriculumMapper hlCurriculumMapper;
    @Resource
    private UploadFileUtil uploadFileUtil;
    @Resource
    private XnTeacherIntegralMapper xnTeacherIntegralMapper;
    @Resource
    private XnMessageCenterMapper xnMessageCenterMapper;

    @Resource
    private XnIntegralRuleMapper xnIntegralRuleMapper;

    private final byte classPic = 4;
    private final byte attr = 1;
    /**
     * 查询教师班级积分
     * @param schoolId
     * @param userId
     * @return
     */
    @Override
    public MyResult teacherInfo(Integer schoolId, Integer userId) {
        List<Map> list = searchUtil.teacherClass(schoolId, userId);
        return MyResult.success(list);
    }

    /**
     * 查询班级圈
     * @param schoolId
     * @return
     */
    @Override
    public MyResult searchClassCircle(Integer schoolId, Integer type,Integer classId,Integer pageNo,Integer pageSize) {
        Integer userId=null;
        if (type==1){
            userId = SystemParam.getChildId();
            //找到班级id
            HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, userId);
            if (ObjectUtil.isEmpty(studentinfo))return MyResult.failure("未找到该孩子的信息");
            classId=studentinfo.getClassid();
        }else {
            userId=SystemParam.getUserId();
            if (classId == null || "".equals(classId) || "null".equals(classId)){
                List<Map> list = searchUtil.teacherClass(schoolId, userId);
                if (ObjectUtil.isEmpty(list) || list.get(0).get("classId")==null || "null".equals(list.get(0).get("classId")))return MyResult.failure("没有找到您教授的班级信息，请输入");
                classId= (Integer) list.get(0).get("classId");
            }
        }
        if (pageSize==null){
            pageSize=6;
        }
        int s=0;
        if (pageNo!=null && pageNo!=0){
            s=(pageNo-1)*pageSize;
        }
        //找到班级圈数据
        XnClassCircleExample example1=new XnClassCircleExample();
        example1.createCriteria().andSchoolidEqualTo(schoolId).andClassidEqualTo(classId).andStatusEqualTo((byte)2);
        example1.setOrderByClause("id desc");
        example1.setStartRow(s);
        example1.setPageSize(pageSize);
        List<XnClassCircle> xnClassCircles = xnClassCircleMapper.selectByExample(example1);
        int total = xnClassCircleMapper.countByExample(example1);
        if (ObjectUtil.isEmpty(xnClassCircles))return MyResult.failure("没有了！");
        List<Map> list=new ArrayList<>();
        Map map=new HashMap();
        for (XnClassCircle xcc:xnClassCircles){
            Map<String, Object> stringObjectMap = voUtil.classCircleInfo(schoolId, xcc);
            map.put("classId",stringObjectMap.get("class"));
            //查询该用户是否对该条班级圈进行了点赞
            XnCircleLikeExample example=new XnCircleLikeExample();
            example.createCriteria().andCircleidEqualTo(xcc.getId()).andUidEqualTo(userId).andAttr1EqualTo(type.toString())
            .andAttr2EqualTo("1");
            List<XnCircleLike> xnCircleLikes = xnCircleLikeMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnCircleLikes)){
                stringObjectMap.put("isLike",1);
            }else {
                stringObjectMap.put("isLike",2);
            }
            //查询该班级圈访问人数
            XnUserRecordExample example2=new XnUserRecordExample();
            example2.createCriteria().andSidEqualTo(schoolId)
                    .andAttr1EqualTo(xcc.getId().toString());
            example2.setOrderByClause("id desc");
            List<XnUserRecord> xnUserRecords = xnUserRecordMapper.selectByExample(example2);
            String[] str=new String[3];
            str[0]=null;
            str[1]=null;
            str[2]=null;
            if (ObjectUtil.isEmpty(xnUserRecords)){
                stringObjectMap.put("requestNum",0);
                stringObjectMap.put("numImg",str);
            }else {
                stringObjectMap.put("requestNum",xnUserRecords.size());
                int m=0;
                if (xnUserRecords.size()>3){
                    m=3;
                }else {
                    m=xnUserRecords.size();
                }
                for (int i=0;i<m;i++){
                    Map<Object, Object> objectObjectMap = voUtil.userPhoto(xnUserRecords.get(i).getUsertype(), xnUserRecords.get(i).getUserid(), xnUserRecords.get(i).getSid());
                    if (objectObjectMap.get("ImageSrc")==null){
                        str[2-i]=null;
                    }else {
                        str[2-i]=objectObjectMap.get("ImageSrc").toString();
                    }

                }
                stringObjectMap.put("numImg",str);
            }
            list.add(stringObjectMap);
        }
        list.add(map);
        int totalPage=0;
        if(total%pageSize==0){
            totalPage=total/pageSize;
        }else {
            totalPage=total/pageSize+1;
        }
        return MyResult.success("查询成功",list,pageNo,totalPage,pageSize,total);
        //return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 查询我的班级圈
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult myCricel(Integer pageNo,Integer pageSize) {
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        Integer userId=0;
        if (type==2){
            userId=SystemParam.getUserId();
        }else {
            userId=SystemParam.getChildId();
        }
        if (pageSize==null){
            pageSize=6;
        }
        int s=0;
        if (pageNo!=null && pageNo!=0){
            s=(pageNo-1)*pageSize;
        }
        XnClassCircleExample example=new XnClassCircleExample();
        example.createCriteria().andUseridEqualTo(userId).andSchoolidEqualTo(SystemParam.getSchoolId())
                .andUsertypeEqualTo(type);
        example.setOrderByClause("id desc");
        example.setStartRow(s);
        example.setPageSize(pageSize);
        List<XnClassCircle> xnClassCircles = xnClassCircleMapper.selectByExample(example);
        int total = xnClassCircleMapper.countByExample(example);
        if (ObjectUtil.isEmpty(xnClassCircles))return MyResult.failure("没有了！");
        List<Map> list=new ArrayList<>();
        Map map=new HashMap();
        for (XnClassCircle xcc:xnClassCircles){
            Map<String, Object> stringObjectMap = voUtil.classCircleInfo(SystemParam.getSchoolId(), xcc);
            map.put("classId",stringObjectMap.get("class"));
            //查询该用户是否对该条班级圈进行了点赞
            XnCircleLikeExample example1=new XnCircleLikeExample();
            example1.createCriteria().andCircleidEqualTo(xcc.getId()).andUidEqualTo(userId).andAttr1EqualTo(type.toString())
                    .andAttr2EqualTo("1");
            List<XnCircleLike> xnCircleLikes = xnCircleLikeMapper.selectByExample(example1);
            if (ObjectUtil.isEmpty(xnCircleLikes)){
                stringObjectMap.put("isLike",1);
            }else {
                stringObjectMap.put("isLike",2);
            }
            //查询该班级圈访问人数
            XnUserRecordExample example2=new XnUserRecordExample();
            example2.createCriteria().andSidEqualTo(SystemParam.getSchoolId())
                    .andAttr1EqualTo(xcc.getId().toString());
            example2.setOrderByClause("id desc");
            List<XnUserRecord> xnUserRecords = xnUserRecordMapper.selectByExample(example2);
            String[] str=new String[3];
            str[0]=null;
            str[1]=null;
            str[2]=null;
            if (ObjectUtil.isEmpty(xnUserRecords)){
                stringObjectMap.put("requestNum",0);
                stringObjectMap.put("numImg",str);
            }else {
                stringObjectMap.put("requestNum",xnUserRecords.size());
                int m=0;
                if (xnUserRecords.size()>3){
                    m=3;
                }else {
                    m=xnUserRecords.size();
                }
                for (int i=0;i<m;i++){
                    Map<Object, Object> objectObjectMap = voUtil.userPhoto(xnUserRecords.get(i).getUsertype(), xnUserRecords.get(i).getUserid(), xnUserRecords.get(i).getSid());
                    if (objectObjectMap.get("ImageSrc")==null){
                        str[2-i]=null;
                    }else {
                        str[2-i]=objectObjectMap.get("ImageSrc").toString();
                    }

                }
                stringObjectMap.put("numImg",str);
            }
            list.add(stringObjectMap);
        }
        list.add(map);
        int totalPage=0;
        if(total%pageSize==0){
            totalPage=total/pageSize;
        }else {
            totalPage=total/pageSize+1;
        }
        return MyResult.success("查询成功",list,pageNo,totalPage,pageSize,total);
    }


    /**
     * 查询选中的班级圈的信息
     * @param ccid
     * @param schoolId
     * @return
     */
    @Override
    public MyResult ClassCircleInfo(Integer ccid,Integer schoolId) {
        Integer userId=0;
        Byte b= Byte.valueOf(SystemParam.getType().toString());
        if (b==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        XnClassCircle xnClassCircle = xnClassCircleMapper.selectByPrimaryKey(ccid);
        if (ObjectUtil.isEmpty(xnClassCircle))return MyResult.failure("没找到该班级圈");
        Map<String, Object> stringObjectMap1 = voUtil.classCircleInfo(schoolId, xnClassCircle);
        List<Map<String, Object>> maps = voUtil.commentInfo(schoolId, 1, ccid);
        stringObjectMap1.put("comment",maps);
        //添加访问人
        XnUserRecordExample example=new XnUserRecordExample();
        example.createCriteria().andSidEqualTo(schoolId)
                .andTypeEqualTo(1).andUseridEqualTo(userId).andUsertypeEqualTo(b)
                .andAttr1EqualTo(ccid.toString());
        List<XnUserRecord> xnUserRecords = xnUserRecordMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnUserRecords)){
            XnUserRecord xn=new XnUserRecord();
            xn.setSid(schoolId);
            xn.setType(1);
            xn.setUserid(userId);
            xn.setUsertype(b);
            xn.setCreatetime(new Date());
            xn.setAttr1(ccid.toString());
            xnUserRecordMapper.insertSelective(xn);
        }
        return MyResult.success(stringObjectMap1);
    }

    /**
     * 评论班级圈
     * @param schoolId
     * @param ccid
     * @param content
     * @param parentId
     * @param userType
     * @return
     */
    @Override
    public MyResult addcomment(Integer schoolId,Integer ccid, String content, Integer parentId,Byte userType) {
        XnCommentTable xct=new XnCommentTable();
        xct.setSid(schoolId);
        xct.setType((byte)1);
        xct.setTypeid(ccid);
        xct.setContent(content);
        if (parentId==null){
            parentId=0;
        }
        xct.setParentid(parentId);
        xct.setStatus((byte)1);
        xct.setUsertype(userType);
        xct.setCreatetime(new Date());
        Integer userId=null;
        if (userType==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        Map<Object, Object> objectObjectMap = voUtil.userPhoto(userType, userId, schoolId);
        xct.setUsername(objectObjectMap.get("stuName").toString());
        xct.setUserimg(objectObjectMap.get("ImageSrc")==null?null:objectObjectMap.get("ImageSrc").toString());
        xct.setUserid(Integer.valueOf(objectObjectMap.get("id").toString()));
        int i = xnCommentTableMapper.insertSelective(xct);
        //给发布人发消息
        XnMessageCenter xmc=new XnMessageCenter();
        xmc.setMessagecontent(objectObjectMap.get("stuName")+"评论了你的班级圈");
        xmc.setCreatetime(new Date());
        xmc.setCreateusertype(Integer.valueOf(userType.toString()));
        xmc.setCreateuserid(userId);
        xmc.setCreateuser(objectObjectMap.get("stuName").toString());
        xmc.setIsread(0);
        XnClassCircle xnClassCircle = xnClassCircleMapper.selectByPrimaryKey(ccid);
        xmc.setUserid(xnClassCircle.getUserid());
        xmc.setUsertype(Integer.valueOf(xnClassCircle.getUsertype().toString()));
        xmc.setMessagetype(3);
        xmc.setType(2);
        xmc.setTypeid(ccid);
        xmc.setAttr2(schoolId.toString());
        int i1 = xnMessageCenterMapper.insertSelective(xmc);
        if (i!=1 || i1!=1){
            return MyResult.failure("保存或发送消息通知评论失败了");
        }
        //return ClassCircleInfo(ccid,schoolId);
        return MyResult.success("评论成功");
    }

    /**
     * 点赞
     * @param ccid
     * @param schoolId
     * @param type
     * @return
     */
    @Override
    public MyResult addLike(Integer ccid, Integer schoolId, Integer type,Integer classIds) {
        if (ObjectUtil.isEmpty(ccid))return MyResult.failure("请输入要点赞的班级圈");
        Integer userId=null;
        Integer classId=null;
        if(type==1){
            userId=SystemParam.getChildId();
            HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, userId);
        }else {
            userId=SystemParam.getUserId();
            List<Map> list = searchUtil.teacherClass(schoolId, userId);
            if(classIds == null){
                classId= (Integer)list.get(0).get("classId");
            }else{
                classId = classIds;
            }
        }
        XnCircleLikeExample example=new XnCircleLikeExample();
        example.createCriteria().andSidEqualTo(schoolId).andCidEqualTo(classId).andCircleidEqualTo(ccid)
                .andUidEqualTo(userId).andAttr1EqualTo(type.toString());
        List<XnCircleLike> xnCircleLikes = xnCircleLikeMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnCircleLikes))return MyResult.failure("已经点过赞了，不能重复点击");
        Map<Object, Object> objectObjectMap = voUtil.userPhoto(Byte.valueOf(type.toString()), userId, schoolId);
        XnCircleLike xcl=new XnCircleLike();
        xcl.setAttr1(type.toString());
        xcl.setUid(userId);
        xcl.setCid(classId);
        xcl.setSid(schoolId);
        xcl.setCircleid(ccid);
        xcl.setCreatetime(new Date());
        xcl.setAttr2("1");
        int i = xnCircleLikeMapper.insertSelective(xcl);
        //给发布人发消息
        XnMessageCenter xmc=new XnMessageCenter();
        xmc.setMessagecontent(objectObjectMap.get("stuName")+"赞了你发布的班级圈");
        xmc.setCreatetime(new Date());
        xmc.setCreateusertype(Integer.valueOf(type.toString()));
        xmc.setCreateuserid(userId);
        xmc.setCreateuser(objectObjectMap.get("stuName").toString());
        xmc.setIsread(0);
        XnClassCircle xnClassCircle = xnClassCircleMapper.selectByPrimaryKey(ccid);
        xmc.setUserid(xnClassCircle.getUserid());
        xmc.setUsertype(Integer.valueOf(xnClassCircle.getUsertype().toString()));
        xmc.setMessagetype(3);
        xmc.setType(1);
        xmc.setTypeid(ccid);
        xmc.setAttr2(schoolId.toString());
        int i1 = xnMessageCenterMapper.insertSelective(xmc);
        if (i!=1||i1!=1){
            return MyResult.failure("点赞或发送消息失败失败");
        }
        return MyResult.success("点赞成功");
    }

    /**
     * 删除点赞
     * @param ccid
     * @return
     */
    @Override
    public MyResult deleteLike(Integer ccid,Integer schoolId, Integer type) {
        if (ObjectUtil.isEmpty(ccid))return MyResult.failure("请输入要点赞的班级圈的id");
        Integer userId=null;
        Integer classId=null;
        if(type==1){
            userId=SystemParam.getChildId();
            HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, userId);
            classId=studentinfo.getClassid();
        }else {
            userId=SystemParam.getUserId();
            List<Map> list = searchUtil.teacherClass(schoolId, userId);
            classId= (Integer) list.get(0).get("classId");
        }
        XnCircleLikeExample example=new XnCircleLikeExample();
        example.createCriteria().andSidEqualTo(schoolId).andUidEqualTo(userId).andAttr1EqualTo(type.toString()).andCircleidEqualTo(ccid);
        List<XnCircleLike> xnCircleLikes = xnCircleLikeMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnCircleLikes))return MyResult.failure("没有找到点赞信息");

        xnCircleLikes.get(0).setAttr2("2");
        int i = xnCircleLikeMapper.updateByPrimaryKeySelective(xnCircleLikes.get(0));
        if (i!=1){
            return MyResult.failure("取消失败");
        }
        return MyResult.success("取消成功");
    }

    /**
     * 发表班级圈
     * @param schoolId
     * @param userType
     * @param description
     * @return
     */
    @Override
    public MyResult addClassCircle(Integer classId, Integer schoolId, Byte userType, HttpServletRequest request, String description) {
        Integer userId=null;
        if (userType==1){
            userId=SystemParam.getChildId();
            HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, userId);
            classId=studentinfo.getClassid();
        }else {
            userId=SystemParam.getUserId();
            if (classId==null){
                XnResourceRelationExample example=new XnResourceRelationExample();
                example.createCriteria().andResourceatableEqualTo("hl_teacher").andResourceaidEqualTo(SystemParam.getUserId())
                        .andResourcebtableEqualTo("hl_schclass").andIsdeleteEqualTo((byte)2);
                List<XnResourceRelation> xnResourceRelations = xnResourceRelationMapper.selectByExample(example);
                if (ObjectUtil.isEmpty(xnResourceRelations))return MyResult.failure("没有找到您教授的班级信息，请输入");
                classId=xnResourceRelations.get(0).getResourcebid();
            }
        }
        XnClassCircle xcc=new XnClassCircle();
        MyResult myResult = uploadFileUtil.uploadFiles(request);
        if (myResult.getStatus()!=1){
            xcc.setFileurl(null);
        }else {
            xcc.setFileurl(String.valueOf(myResult.getData()));
        }
        System.out.println("图片路径是："+myResult.getData());
        //添加班级圈
        xcc.setSchoolid(schoolId);
        xcc.setClassid(classId);
        xcc.setUserid(userId);
        xcc.setUsertype(userType);
        xcc.setDescription(description);
        xcc.setCreatetime(new Date());
        //查询学校是否开启了班级圈审核
        XnClascircleRuleExample example1=new XnClascircleRuleExample();
        example1.createCriteria().andLuidEqualTo(schoolId);
        List<XnClascircleRule> xnClascircleRules = xnClascircleRuleMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(xnClascircleRules)){
            xcc.setStatus((byte)2);
        }
        if(xnClascircleRules.get(0).getChecktype()==1 && xnClascircleRules.get(0).getIsopen()==1){
            //教师发布直接通过
            if (userType==2){
                xcc.setStatus((byte)2);
            }else {
                xcc.setStatus((byte)1);
            }
        }else {
            xcc.setStatus((byte)2);
        }

        int i = xnClassCircleMapper.insertSelective(xcc);
        if (i==0){
            return MyResult.failure("添加失败");
        }
        return MyResult.success("发表成功");
    }

    /**
     * 添加班级图片
     * @param classId
     * @param schoolId
     * @param type
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MyResult addClassPic(Integer classId,HttpServletRequest request,String albumName,String imgName, Integer schoolId, Integer type) {
        if (type==1)return MyResult.failure("你不是老师，不能上传");
        if (classId==null)return MyResult.failure("请输入班级id");
        XnClassAlbum xca=new XnClassAlbum();
        xca.setSid(schoolId);
        xca.setCid(classId);
        if (albumName!=null){
            xca.setAlbumname(albumName);
        }
        MyResult myResult = uploadFileUtil.uploadFiles(request);
        if (myResult.getStatus()!=1)return MyResult.failure(myResult.getMessage());
        System.out.println("图片路径是："+myResult.getData());
        String[] str=String.valueOf(myResult.getData()).split(",");
        int m=0;
        for (int i=0;i<str.length;i++){
            xca.setImgname(imgName);
            xca.setImgurl(str[i]);
            xca.setCreatetime(new Date());
            xca.setUserid(SystemParam.getUserId());
            int j=xnClassAlbumMapper.insertSelective(xca);
            m=m+j;
        }
        //添加积分
        int school_id = SystemParam.getSchoolId();
        Map key = new HashMap();
        key.put("schoolId",school_id);
        key.put("function",classPic);
        List<XnIntegralRule> rule = xnIntegralRuleMapper.queryIntegral(key);
        if (!rule.isEmpty()){
            if ( rule.get(0).getIsopen()==1){
                XnIntegralRule integralRule = rule.get(0);
                Integer teacherId = SystemParam.getUserId();
                XnTeacherIntegral integral = new XnTeacherIntegral();
                integral.setSid(schoolId);
                integral.setTid(teacherId);
                integral.setType(attr);
                integral.setCreatetime(new Date());
                integral.setNumber(integralRule.getIntegralnumer());
                int i1 = xnTeacherIntegralMapper.insertSelective(integral);
                if (m==str.length && i1==1){
                    return MyResult.success("上传成功");
                } else {
                    try {
                        throw new Exception("添加失败");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (m!=str.length){
            return MyResult.failure("保存图片失败");
        }
        return MyResult.success("上传成功");
    }

    /**
     * 查询班级圈照片
     * @param classId
     * @param schoolId
     * @param type
     * @return
     */
    @Override
    public MyResult searchClassPic(Integer classId, Integer schoolId, Integer type) {
        if (type==1){
            Integer childId = SystemParam.getChildId();
            HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, childId);
            classId=studentinfo.getClassid();
        }else {
            Integer userId = SystemParam.getUserId();
            List<Map<String, Object>> maps = hlCurriculumMapper.teaClassId(schoolId, userId);
            if (classId==null){
                if (ObjectUtil.isEmpty(maps))return MyResult.failure("没找到老师对应的班级信息");
                classId= (Integer) maps.get(0).get("ClassId");
            }
        }
        List<Map<String, Object>> group = xnClassAlbumMapper.group(schoolId, classId);
        if (ObjectUtil.isEmpty(group))return MyResult.failure("没有找到相册信息");
        List<Map> list=new ArrayList<>();
        for (Map m:group){
            Map map=new HashMap();
            String days = m.get("days").toString();
            map.put("days",days);
            try {
                String str=days+" 00:00:00";
                String d=days+" 23:59:59";
                XnClassAlbumExample example=new XnClassAlbumExample();
                example.createCriteria().andSidEqualTo(schoolId).andCidEqualTo(classId)
                        .andCreatetimeBetween(TimeUtil.YYYYMMDDHHMMSSToTime(str),TimeUtil.YYYYMMDDHHMMSSToTime(d));
                List<XnClassAlbum> xnClassAlbums = xnClassAlbumMapper.selectByExample(example);
                List<Map> li=new ArrayList<>();
                for (XnClassAlbum xca:xnClassAlbums) {
                    Map map1 = new HashMap();
                    map1.put("id", xca.getId());
                    map1.put("sid", xca.getSid());
                    map1.put("cid", xca.getCid());
                    if (xca.getAlbumname() == null) {
                        map1.put("albumName", null);
                    } else {
                        map1.put("albumName", xca.getAlbumname());
                    }
                    if (xca.getImgname() == null) {
                        map1.put("imgName", null);
                    } else {
                        map1.put("imgName", xca.getImgname());
                    }
                    map1.put("imgUrl", xca.getImgurl());
                    map1.put("createTime", TimeUtil.TimeToYYYYMMDDHHMMSS(xca.getCreatetime()));
                    map1.put("userId", xca.getUserid());
                    li.add(map1);
                }
                if (ObjectUtil.isEmpty(xnClassAlbums)){
                    map.put("picInfo",null);
                }else {
                    map.put("picInfo",li);
                }
                list.add(map);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return MyResult.success(list);
    }

    /**
     * 删除班级圈照片
     * @param cpid
     * @return
     */
    @Override
    public MyResult deleteClassPic(String cpid) {
        if (cpid==null){
            return MyResult.failure("请选择要删除的班级圈相片");
        }
        if (SystemParam.getType()==1){
            return MyResult.failure("不是老师，没有权限删除");
        }
        String[] strings=cpid.split(",");
        int i=0;
        for (String s:strings){
            int a = xnClassAlbumMapper.deleteByPrimaryKey(Integer.valueOf(s));
            i+=a;
        }
        if (i!= strings.length){
            return MyResult.failure("删除失败");
        }
        return MyResult.success("删除成功");
    }

    /**
     * 删除班级圈
     * @param ccid
     * @param schoolId
     * @param type
     * @return
     */
    @Override
    public MyResult deleteClassCircle(String ccid, Integer schoolId, Integer type) {
        if (ccid==null){
            return MyResult.failure("请选择要删除的班级圈");
        }
        String[] str=ccid.split(",");
        int i=0;
        for (String s:str) {
            int oid=Integer.valueOf(s);
            XnClassCircle xnClassCircle = xnClassCircleMapper.selectByPrimaryKey(oid);
            if (type==1){
                Integer childId = SystemParam.getChildId();
                Integer userid = xnClassCircle.getUserid();
                if (childId!=userid){
                    return MyResult.failure("不是你的班级圈，不能删除");
                }else {
                    int i1 = xnClassCircleMapper.deleteByPrimaryKey(oid);
                    if (i1==1){
                        i=1;
                    }
                }
            }
            if (type==2){
                int i1 = xnClassCircleMapper.deleteByPrimaryKey(oid);
                if (i1==1){
                    i=1;
                }

            }
        }
        if (i!=1){
            return MyResult.failure("删除失败");
        }
        return MyResult.success("删除成功");
    }

}
