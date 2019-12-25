package com.usoft.sschool_student.serivice.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.pojo.HlStudentinfo;
import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.mapper.HlStudentinfoMapper;
import com.usoft.sschool_student.serivice.PersionCenterService;
import com.usoft.sschool_student.util.UploadFileUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("PersionCenterService")
public class PersionCenterServiceImpl implements PersionCenterService {
    @Resource
    private VoUtil voUtil;
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private UploadFileUtil uploadFileUtil;
    /**
     * 查询孩子信息
     * @param schoolId
     * @param userId
     * @return
     */
    @Override
    public MyResult stuInfo(Integer schoolId, Integer userId) {
        HlStudentinfo hs=new HlStudentinfo();
        hs.setId(userId);
        hs.setSchoolid(schoolId);
        HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(hs);
        Map<Object, Object> objectObjectMap = voUtil.stuInfo(hlStudentinfo);
        return MyResult.success(objectObjectMap);
    }

    /**
     * 上传头像
     * @param request
     * @param schoolId
     * @param userId
     * @return
     */
    @Override
    public MyResult addSphoto(HttpServletRequest request,Integer schoolId,Integer userId) {

        MyResult myResult = uploadFileUtil.uploadFiles(request);
        if (myResult.getStatus()!=1)return MyResult.failure(myResult.getMessage());
        HlStudentinfo hs=new HlStudentinfo();
        hs.setId(userId);
        hs.setSchoolid(schoolId);
        HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(hs);
        String sphoto = hlStudentinfo.getSphoto();
        JSON json= JSONObject.parseObject(sphoto);
        Map<String,Object> map1=JSONObject.toJavaObject(json,Map.class);
        Map map=new HashMap();
        map.put("faceImg",myResult.getData());
        System.out.println("头像是"+myResult.getData());
        map.put("idImg",map1.get("idImg"));
        JSONObject js=new JSONObject(map);
        String s=js.toString();
        hlStudentinfo.setSphoto(s);
        int i = hlStudentinfoMapper.updateByPrimaryKeySelective(hlStudentinfo);
        if (i!=1)return MyResult.failure("头像保存失败");
        HlStudentinfo hlStudentinfo1 = hlStudentinfoMapper.selectByPrimaryKey(hs);
        Map<Object, Object> objectObjectMap = voUtil.stuInfo(hlStudentinfo1);
        return MyResult.success(objectObjectMap);
    }

    @Override
    public MyResult uploadFile(HttpServletRequest request) {
        MyResult myResult = uploadFileUtil.uploadFiles(request);
        if (myResult.getStatus()!=1)return MyResult.failure(myResult.getMessage());
        return myResult;
    }

    /**
     * 上传风采照
     * @param idImg
     * @param schoolId
     * @param userId
     * @return
     */
    @Override
    public MyResult addIdImg(String idImg, Integer schoolId, Integer userId) {
        HlStudentinfo hs=new HlStudentinfo();
        hs.setId(userId);
        hs.setSchoolid(schoolId);
        HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(hs);
        String sphoto = hlStudentinfo.getSphoto();
        JSON json= JSONObject.parseObject(sphoto);
        Map<String,Object> map1=JSONObject.toJavaObject(json,Map.class);
        Map map=new HashMap();
        map.put("idImg",idImg);
        map.put("faceImg",map1.get("faceImg"));
        JSONObject js=new JSONObject(map);
        String s=js.toString();
        hlStudentinfo.setSphoto(s);
        int i = hlStudentinfoMapper.updateByPrimaryKeySelective(hlStudentinfo);
        if (i!=1)return MyResult.failure("风采照保存失败");
        HlStudentinfo hlStudentinfo1 = hlStudentinfoMapper.selectByPrimaryKey(hs);
        Map<Object, Object> objectObjectMap = voUtil.stuInfo(hlStudentinfo1);
        return MyResult.success(objectObjectMap);
    }
}
