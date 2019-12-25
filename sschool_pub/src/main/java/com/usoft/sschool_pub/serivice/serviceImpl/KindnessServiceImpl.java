package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.KindnessService;
import com.usoft.sschool_pub.util.ResultPage;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import com.usoft.sschool_pub.util.UploadFileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("KindnessService")
public class KindnessServiceImpl implements KindnessService {
    @Resource
    private XnKindnessSchoolMapper xnKindnessSchoolMapper;
    @Resource
    private XnKindnessUnschoolMapper xnKindnessUnschoolMapper;
    @Resource
    private XnCommentTableMapper xnCommentTableMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private XnKindnessRuleMapper xnKindnessRuleMapper;
    @Resource
    private VoUtil voUtil;
    @Resource
    private UploadFileUtil uploadFileUtil;
    @Resource
    private XnUserRecordMapper xnUserRecordMapper;
    /**
     * 查询所有校园爱心
     * @param schoolId
     * @return
     */
    @Override
    public MyResult allKindness(Integer schoolId,Integer pageNo,Integer pageSize) {
        if (pageSize==null){
            pageSize=6;
        }
        int s=0;
        if (pageNo!=null && pageNo!=0){
            s=(pageNo-1)*pageSize;
        }
        XnKindnessSchoolExample example=new XnKindnessSchoolExample();
        example.createCriteria().andSchoolidEqualTo(schoolId).andStatusEqualTo("1").andGoodsstatusEqualTo(1);
        example.setOrderByClause("id desc");
        example.setStartRow(s);
        example.setPageSize(pageSize);
        List<XnKindnessSchool> xnKindnessSchools = xnKindnessSchoolMapper.selectByExample(example);
        int total = xnKindnessSchoolMapper.countByExample(example);
        if (xnKindnessSchools.size()==0){
            return MyResult.failure("没有爱心信息");
        }
        List list=new ArrayList();
        for (XnKindnessSchool xks:xnKindnessSchools){
            Map<String, Object> stringObjectMap = voUtil.kindnessInfo(schoolId, xks);
            list.add(stringObjectMap);
        }
        int totalPage=0;
        if(total%pageSize==0){
            totalPage=total/pageSize;
        }else {
            totalPage=total/pageSize+1;
        }
        return MyResult.success("查询成功",list,pageNo,totalPage,pageSize,total);
    }

    /**
     * 爱心人士、企业
     * @param schoolId
     * @param type
     * @return
     */
    @Override
    public MyResult kindnessPersion(Integer schoolId, Byte type) {
        XnKindnessUnschoolExample example=new XnKindnessUnschoolExample();
        example.createCriteria().andTypeEqualTo(type).andSidEqualTo(schoolId).andStatusEqualTo((byte)1);
        example.setOrderByClause("createTime desc");
        List<XnKindnessUnschool> xnKindnessUnschools = xnKindnessUnschoolMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnKindnessUnschools))return MyResult.failure("还没有这个介绍");
        List<Map> list=new ArrayList<>();
        for (XnKindnessUnschool xku:xnKindnessUnschools){
            Map map=new HashMap();
            map.put("id",xku.getId());
            map.put("name",xku.getName());
            map.put("img",xku.getImg());
            map.put("des",xku.getDes());
            map.put("createtime",TimeUtil.TimeToYYYYMMDDHHMMSS(xku.getCreatetime()));
            list.add(map);
        }
        return MyResult.success(list);
    }

    /**
     * 查看单个校园爱心详情
     * @param id
     * @return
     */
    @Override
    public MyResult kindnessMsg(Integer id) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId=0;
        Byte b= Byte.valueOf(SystemParam.getType().toString());
        if (b==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        //添加访问人数
        XnUserRecordExample example=new XnUserRecordExample();
        example.createCriteria().andSidEqualTo(schoolId)
        .andTypeEqualTo(2).andUseridEqualTo(userId).andUsertypeEqualTo(b)
        .andAttr1EqualTo(id.toString());
        List<XnUserRecord> xnUserRecords = xnUserRecordMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnUserRecords)){
            XnUserRecord xn=new XnUserRecord();
            xn.setSid(schoolId);
            xn.setType(2);
            xn.setUserid(userId);
            xn.setUsertype(b);
            xn.setCreatetime(new Date());
            xn.setAttr1(id.toString());
            xnUserRecordMapper.insertSelective(xn);
        }
        XnKindnessSchool xnKindnessSchool = xnKindnessSchoolMapper.selectByPrimaryKey(id);
        Map<String, Object> stringObjectMap1 = voUtil.kindnessInfo(schoolId, xnKindnessSchool);
        List<Map<String, Object>> maps = voUtil.commentInfo(schoolId, 2, id);
        stringObjectMap1.put("comment",maps);
        return MyResult.success(stringObjectMap1);
    }

    /**
     * 爱心留言
     * @param kid
     * @return
     */
    @Override
    public MyResult commentKindness(Integer kid,String content,Integer parentId) {
        if (kid==null || kid==0){
            return MyResult.failure("爱心id错误");
        }
        if (parentId==null || "".equals(parentId)){
            parentId=0;
        }
        Integer schoolId = SystemParam.getSchoolId();
        XnCommentTable xct=new XnCommentTable();
        xct.setSid(schoolId);
        xct.setType((byte)2);
        xct.setTypeid(kid);
        xct.setContent(content);
        xct.setParentid(parentId);
        xct.setStatus((byte)1);
        xct.setCreatetime(new Date());
        Integer type = SystemParam.getType();
        xct.setUsertype(Byte.parseByte(type.toString()));
        if (type==1){
            Integer childId = SystemParam.getChildId();
            HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId,childId);
            xct.setUsername(studentinfo.getSname());
            xct.setUserid(childId);
            xct.setUserimg(studentinfo.getSphoto());
        }
        if (type==2){
            Integer userId = SystemParam.getUserId();
            HlTeacherExample example=new HlTeacherExample();
            example.createCriteria().andSchoolidEqualTo(schoolId).andIdEqualTo(userId);
            List<HlTeacher> hlTeachers = hlTeacherMapper.selectByExample(example);
            xct.setUsername(hlTeachers.get(0).getTname());
            xct.setUserid(hlTeachers.get(0).getId());
            xct.setUserimg(hlTeachers.get(0).getImagesrc());
        }
        int i = xnCommentTableMapper.insertSelective(xct);
        if (i!=1){
            return MyResult.failure("保存失败");
        }
        return  MyResult.success("发送成功");
    }

    /**
     * 查看爱心排行
     * @param schoolId
     * @return
     */
    @Override
    public MyResult kindnessRanking(Integer schoolId,Byte types) {
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        Integer userId=null;
        if (type==1){
            userId=SystemParam.getChildId();
        }else {
            userId=SystemParam.getUserId();
        }
        List<Map<String, Object>> list = xnKindnessSchoolMapper.kindnessRanking(schoolId);
        int mykindnum=1;
        List<Map<String, Object>> list1 =new ArrayList<>();
        Map map1=new HashMap();
        Map<Object, Object> objectMap = voUtil.userPhoto(type, userId, schoolId);
        map1.put("id",objectMap.get("id"));
        map1.put("stuName",objectMap.get("stuName"));
        map1.put("num",0);
        map1.put("class",objectMap.get("class"));
        map1.put("ImageSrc",objectMap.get("ImageSrc"));
        for(Map l:list){
            if (l.get("uid")!=userId && l.get("ut")!=type){
                mykindnum+=1;
            }else {
                map1.put("num",l.get("num"));
                break;
            }

        }
        map1.put("myRank",mykindnum);
        list1.add(map1);
        List<Map<String, Object>> list2 =new ArrayList<>();
        if (list.size()>13){
            for (int i=0;i<12;i++){
                list2.add(list.get(i));
            }
        }
        if (list.size()<=12){
            for (int i=0;i<list.size();i++){
                list2.add(list.get(i));
            }
        }
        System.out.println("我的排名是："+mykindnum);
        System.out.println("我的信息是："+list1.get(0));
        Map map=new HashMap();

        map.put("myInfo",list1.get(0));
        List<Map> list3=new ArrayList<>();
        for (Map ma:list2){
            Map map2=new HashMap();
            Map<Object, Object> objectMap1 = voUtil.userPhoto(Byte.valueOf(ma.get("ut").toString()), Integer.valueOf(ma.get("uid").toString()), schoolId);
            map2.put("id",objectMap1.get("id"));
            map2.put("stuName",objectMap1.get("stuName"));
            map2.put("num",ma.get("num"));
            map2.put("class",objectMap1.get("class"));
            map2.put("ImageSrc",objectMap1.get("ImageSrc"));
            list3.add(map2);
        }
        map.put("rank",list3);
        return MyResult.success(map);
    }

    /**
     * 我要做爱心
     * @param schoolId
     * @param userType
     * @param description
     * @param phone
     * @param goodsType
     * @return
     */
    @Override
    public MyResult addMyKindness(Integer schoolId, Byte userType, HttpServletRequest request, String description, String phone, Integer goodsType) {
        Integer userId=0;
        XnKindnessSchool xks=new XnKindnessSchool();
        xks.setSchoolid(schoolId);
        xks.setUsertype(userType);
        if (userType==1){
            userId=SystemParam.getChildId();
            xks.setUserid(userId);
        }
        if (userType==2){
            userId = SystemParam.getUserId();
            xks.setUserid(userId);
        }
        MyResult myResult = uploadFileUtil.uploadFiles(request);
        String path=null;
        if (myResult.getStatus()==1){
            path=myResult.getData().toString();
        }
        System.out.println("图片路径是："+path);
        xks.setImg(path);
        xks.setDescription(description);
        xks.setPhone(phone);
        xks.setGoodstype(goodsType);
        xks.setGoodsstatus(1);
        xks.setStatus("1");
        xks.setCreatetime(new Date());
        int i = xnKindnessSchoolMapper.insertSelective(xks);
        if (i==0)return MyResult.failure("保存失败");
        //查询我的爱心
        return MyResult.success("保存成功");
    }

    /**
     * 查询我的爱心
     * @param schoolId
     * @param userType
     * @return
     */
    @Override
    public MyResult myKindness(Integer kindnessType,Integer schoolId,Byte userType,Integer pageNo,Integer pageSize) {
        Integer stuId=null;
        if (userType==1){
            stuId=SystemParam.getChildId();
        }else {
            stuId=SystemParam.getUserId();
        }
        if (kindnessType==null){
            kindnessType=1;
        }
        XnKindnessSchoolExample example=new XnKindnessSchoolExample();
        example.createCriteria().andSchoolidEqualTo(schoolId).andUseridEqualTo(stuId).andUsertypeEqualTo(userType)
        .andStatusEqualTo("1").andGoodsstatusEqualTo(kindnessType);
        example.setOrderByClause("id desc");
        List<XnKindnessSchool> xnKindnessSchools = xnKindnessSchoolMapper.selectByExample(example);
        if (ObjectUtil.isEmpty(xnKindnessSchools))return MyResult.failure("没有找到你的爱心信息");
        List<Map> list=new ArrayList<>();
        for (XnKindnessSchool xks1:xnKindnessSchools){
            Map<String, Object> stringObjectMap = voUtil.kindnessInfo(schoolId, xks1);
            list.add(stringObjectMap);
        }
        if (pageSize==null){
            pageSize=6;
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    /**
     * 修改爱心物品信息
     * @param description
     * @param phone
     * @param goodsType
     * @return
     */
    @Override
    public MyResult changeMyKindness(Integer kindnessId, String description, String phone, Integer goodsType) {
        Integer stuId=null;
        Integer type = SystemParam.getType();
        if (type==1){
            stuId=SystemParam.getChildId();
        }else {
            stuId=SystemParam.getUserId();
        }
        XnKindnessSchool xks = xnKindnessSchoolMapper.selectByPrimaryKey(kindnessId);
        if (ObjectUtil.isEmpty(xks))return MyResult.failure("没有找到该爱心信息");
        if (!(xks.getUsertype().intValue()==type) || !(xks.getUserid()==stuId) || !(xks.getSchoolid()==SystemParam.getSchoolId())){
            return MyResult.failure("不是你发布的爱心，不能修改");
        }
        xks.setDescription(description);
        xks.setPhone(phone);
        xks.setGoodstype(goodsType);
        int i = xnKindnessSchoolMapper.updateByPrimaryKeySelective(xks);
        if (i==0){
            return MyResult.failure("修改失败");
        }
        List<Map> list=new ArrayList<>();
        Map<String, Object> stringObjectMap1 = voUtil.kindnessInfo(SystemParam.getSchoolId(), xks);
        List<Map<String, Object>> maps = voUtil.commentInfo(SystemParam.getSchoolId(), 2, kindnessId);
        stringObjectMap1.put("comment",maps);
        list.add(stringObjectMap1);
        return MyResult.success(list);
    }

    /**
     * 完成爱心
     * @param kindnessId
     * @return
     */
    @Override
    public MyResult signMyKindness(Integer kindnessId) {
        Integer stuId=null;
        Integer type = SystemParam.getType();
        if (type==1){
            stuId=SystemParam.getChildId();
        }else {
            stuId=SystemParam.getUserId();
        }
        XnKindnessSchool xks = xnKindnessSchoolMapper.selectByPrimaryKey(kindnessId);
        if (ObjectUtil.isEmpty(xks)) return MyResult.failure("没有找到该爱心信息");
        if (!(xks.getUsertype().intValue()==type) || !(xks.getUserid()==stuId) || !(xks.getSchoolid()==SystemParam.getSchoolId())){
            return MyResult.failure("不是你发布的爱心，不能修改状态");
        }
        if (xks.getGoodsstatus() == 2) {
            return MyResult.failure("该校园爱心是已领状态，无需签收");
        }
        xks.setGoodsstatus(2);
        //查询爱心规则
        XnKindnessRuleExample example=new XnKindnessRuleExample();
        example.createCriteria().andIsopenEqualTo((byte)1).andSidEqualTo(SystemParam.getSchoolId());
        List<XnKindnessRule> xnKindnessRules = xnKindnessRuleMapper.selectByExample(example);
        if (!ObjectUtil.isEmpty(xnKindnessRules)){
            xks.setKindnum(xnKindnessRules.get(0).getPerkind());
        }
        int i = xnKindnessSchoolMapper.updateByPrimaryKeySelective(xks);
        if (i == 0) {
            return MyResult.failure("修改失败");
        }
        return MyResult.success("修改成功");
    }

    /**
     * 删除爱心
     * @param kindnessId
     * @return
     */
    @Override
    public MyResult deleteKindness(Integer kindnessId) {
        Integer stuId=null;
        Integer type = SystemParam.getType();
        if (type==1){
            stuId=SystemParam.getChildId();
        }else {
            stuId=SystemParam.getUserId();
        }
        XnKindnessSchool xks = xnKindnessSchoolMapper.selectByPrimaryKey(kindnessId);
        if (ObjectUtil.isEmpty(xks)) return MyResult.failure("没有找到该爱心信息");
        if (!(xks.getUsertype().intValue()==type) || !(xks.getUserid()==stuId) || !(xks.getSchoolid()==SystemParam.getSchoolId())){
            return MyResult.failure("不是你发布的爱心，不能修改状态");
        }
        if ("2".equals(xks.getStatus())) {
            return MyResult.failure("该校园爱心已是删除状态，无需删除");
        }
        xks.setStatus("2");
        int i = xnKindnessSchoolMapper.updateByPrimaryKeySelective(xks);
        if (i == 0) {
            return MyResult.failure("删除失败");
        }
        return MyResult.success("删除成功");
    }
}
