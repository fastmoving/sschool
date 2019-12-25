package com.usoft.sschool_manage.service.live.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.*;
import com.usoft.sschool_manage.service.live.XnLiveApplyCheckService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/5/24 9:39
 */
@Service
public class XnLiveApplyCheckServiceImpl implements XnLiveApplyCheckService {

    @Resource
    private XnLiveApplyCheckMapper xnLiveApplyCheckMapper;
    @Resource
    private XnVideoCheckMapper xnVideoCheckMapper;
    @Resource
    private CfDepartmentMapper cfDepartmentMapper;
    @Resource
    private HlCountyMapper hlCountyMapper;
    @Resource
    private HlSchclassMapper hlSchclassMapper;
    @Resource
    private HlTeacherMapper hlTeacherMapper;
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private TerminalInfoMapper terminalInfoMapper;
    @Resource
    private HlSchoolsevipMapper hlSchoolsevipMapper;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;

    @Override
    public MyResult listXnLiveApplyCheck(Integer sid,Integer cid, Integer pageNo, Integer pageSize) {
        //List<Map<String,Object>> result = xnLiveApplyCheckMapper.listLiveCheck(className, subject, teacherName);
        if (sid==null || "".equals(sid) || "null".equals(sid)){
            sid=SystemParam.getSchoolId();
        }
        XnVideoCheckExample example=new XnVideoCheckExample();
        XnVideoCheckExample.Criteria criteria = example.createCriteria();
        criteria.andAttr1EqualTo(sid.toString()).andCheckstatusNotEqualTo(2);
        if (cid!=null && !"".equals(cid) && !"null".equals(cid)){
            criteria.andTypeidEqualTo(cid);
        }
        example.setOrderByClause("createTime desc");;
        List<XnVideoCheck> xnVideoChecks = xnVideoCheckMapper.selectByExample(example);
        if(ObjectUtil.isEmpty(xnVideoChecks))return MyResult.failure("暂无数据");
        List list=new LinkedList();
        for (XnVideoCheck xvc:xnVideoChecks){
            Map map=new HashMap();
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(Integer.valueOf(xvc.getAttr1()));
            if (ObjectUtil.isEmpty(cfDepartment)){
                map.put("county",null);
            }else {
                HlCounty hlCounty = hlCountyMapper.selectByPrimaryKey(cfDepartment.getCountyid());
                map.put("county",hlCounty.getName());
            }
            CfDepartment cfDepartment1 = cfDepartmentMapper.selectByPrimaryKey(xvc.getSid());
            map.put("userSid",cfDepartment1.getDeptname());
            Map<Object, Object> objectObjectMap = userPhoto(xvc.getUsertype(), xvc.getUid(), xvc.getSid());
            map.put("class",objectObjectMap.get("class"));
            map.put("userName",objectObjectMap.get("stuName"));
            map.put("cid",xvc.getTypeid());
            HlSchclass hs=new HlSchclass();
            hs.setId(xvc.getTypeid());hs.setSchoolid(Integer.valueOf(xvc.getAttr1()));
            HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
            map.put("content",hlSchclass.getClassname());
            map.put("id",xvc.getId());
            map.put("status",xvc.getCheckstatus());
            map.put("userType",xvc.getUsertype());
            list.add(map);
        }

        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    @Override
    public MyResult listXnLiveApplyCheckDetail(Integer sid, Integer cid,Integer pageNo, Integer pageSize) {
        /*if(ObjectUtil.isEmpty(sid))return MyResult.failure("请传入参数：学校id");
        if(ObjectUtil.isEmpty(cid))return MyResult.failure("请传入参数：班级id");*/
        //List<Map<String,Object>> result = xnLiveApplyCheckMapper.listLiveCheckDetail(sid, cid);
        TerminalInfoExample example=new TerminalInfoExample();
        TerminalInfoExample.Criteria criteria = example.createCriteria();
        if (sid==null || "".equals(sid) || "null".equals(sid)){
            sid=SystemParam.getSchoolId();
        }
        criteria.andSidEqualTo(sid);
        if (cid!=null && !"".equals(cid) && !"null".equals(cid)){
            criteria.andIdEqualTo(cid);
        }
        List<TerminalInfo> terminalInfos = terminalInfoMapper.selectByExample(example);
        if(ObjectUtil.isEmpty(terminalInfos))return MyResult.failure("暂无数据");
        List<Map> list=new LinkedList<>();
        for (TerminalInfo ti:terminalInfos){
            Map<String, Object> stringObjectMap = videoPath(sid, ti);
            list.add(stringObjectMap);
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    @Override
    @Transactional
    public MyResult checkTheLiveApply(Integer[] ids, Integer status) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要审核的数据");
        if(ObjectUtil.isEmpty(status)) return MyResult.failure("请选择审核的状态");
        if(status<1 || status>3)return MyResult.failure("未知的审核状态");
        try{
            for(int i=0; i<ids.length; i++){
                Integer id = ids[i];
                XnVideoCheck xnVideoCheck = xnVideoCheckMapper.selectByPrimaryKey(id);
                xnVideoCheck.setCheckstatus(status);
                xnVideoCheckMapper.updateByPrimaryKeySelective(xnVideoCheck);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MyResult.failure("审核失败");
        }

        return MyResult.success("审核成功");
    }
    public Map<Object,Object>  userPhoto(Integer userType,Integer userId,Integer schoolId){
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
                    String[] s=map2.get("faceImg").toString().split(",");
                    map.put("ImageSrc",map2.get("faceImg"));
                    map.put("idImg",s);
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

    /**
     * 查询学校的视频播放地址
     * @param schoolId
     * @return
     */
    public Map<String,Object> videoPath(Integer schoolId,TerminalInfo ti){
        HlSchoolsevip hlSchoolsevips = hlSchoolsevipMapper.selectByPrimaryKey(schoolId);
        if (ObjectUtil.isEmpty(hlSchoolsevips))return null;
        //String servPath1 = "rtmp://" + hlSchoolsevips.getLansevip() + ":1935/live/" + ti.getTerminalMac() + "_hd_1";
        CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(schoolId);
        Map map=new HashMap();
        map.put("id",ti.getId());
        //map.put("servPath1",ti.getTerminalMac());
        map.put("schoolId",schoolId);
        map.put("DeptCode",cfDepartment.getDeptcode());
        map.put("schoolName",cfDepartment.getDeptname());
        map.put("CountyID",cfDepartment.getCountyid());
        HlCounty hlCounty = hlCountyMapper.selectByPrimaryKey(cfDepartment.getCountyid());
        map.put("CountyName",hlCounty.getName());
        map.put("classId",ti.getId());
        HlSchclass hs=new HlSchclass();
        hs.setId(ti.getId());hs.setSchoolid(schoolId);
        HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hs);
        if (ObjectUtil.isEmpty(hlSchclass)){
            map.put("className",null);
        }else {
            map.put("className",hlSchclass.getClassname());
        }
        XnResourceRelationExample example1 = new XnResourceRelationExample();
        example1.createCriteria().andResourceatableEqualTo("hl_teacher").andResourcebtableEqualTo("hl_schclass")
                .andResourcebidEqualTo(ti.getId());
        List<XnResourceRelation> xnResourceRelations = xnResourceRelationMapper.selectByExample(example1);
        if (ObjectUtil.isEmpty(xnResourceRelations)){
            map.put("teaName","无");
        }else {
            HlTeacher ht=new HlTeacher();
            ht.setId(xnResourceRelations.get(0).getResourceaid());
            ht.setSchoolid(schoolId);
            HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(ht);
            map.put("teaid",hlTeacher.getId());
            map.put("teaName",hlTeacher.getTname());
        }
        return map;
    }
}
