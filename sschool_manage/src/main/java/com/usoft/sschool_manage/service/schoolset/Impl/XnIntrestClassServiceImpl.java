package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.HlSchclassMapper;
import com.usoft.sschool_manage.mapper.base.HlStudentinfoMapper;
import com.usoft.sschool_manage.mapper.base.XnIntrestClassMapper;
import com.usoft.sschool_manage.mapper.base.XnIntrestEntryMapper;
import com.usoft.sschool_manage.service.schoolset.XnIntrestClassService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author jijh
 * @Date 2019/5/5 17:50
 */
@Service
public class XnIntrestClassServiceImpl implements XnIntrestClassService {

    @Resource
    private XnIntrestClassMapper xnIntrestClassMapper;

    @Resource
    private XnIntrestEntryMapper xnIntrestEntryMapper;

    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;

    @Resource
    private HlSchclassMapper hlSchclassMapper;


    /**
     * 已支付
     */
    private static final String PAYED = "2";


    @Override
    public MyResult listXnIntrestClass(String name, Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        XnIntrestClassExample xnIntrestClassExample = new XnIntrestClassExample();
        XnIntrestClassExample.Criteria criteria = xnIntrestClassExample.createCriteria();
        criteria.andSidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(name)){
            criteria.andClassnameLike("%"+name+"%");
        }
        List<XnIntrestClass> xnIntrestClasses = xnIntrestClassMapper.selectByExample(xnIntrestClassExample);
        if(ObjectUtil.isEmpty(xnIntrestClasses))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(XnIntrestClass xnIntrestClass : xnIntrestClasses){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnIntrestClass.getId());
            map.put("name",xnIntrestClass.getClassname());
            map.put("teacherId",xnIntrestClass.getTid());
            map.put("teacherName",xnIntrestClass.getTeaname());
            map.put("begin", TimeUtil.TimeToYYYYMMDDHHMMSS(xnIntrestClass.getBegindate()));
            map.put("classNum",xnIntrestClass.getClassnum());
            map.put("img",xnIntrestClass.getClassimg());
            map.put("description",xnIntrestClass.getClassdes());
            map.put("price",xnIntrestClass.getAttr1());
            result.add(map);
        }
        return ResultPage.getPageResult(result,pageNo,pageSize);
    }

    @Override
    public MyResult addOrUpdateXnIntrestClass(Integer id, String className, Integer tid, String teacherName, String beginDate, Integer classNum,String img, String classDes,String price) {
        if(ObjectUtil.isEmpty(className))return MyResult.failure("请输入班级名称");
        if(ObjectUtil.isEmpty(tid))return MyResult.failure("请传教师id");
        if(ObjectUtil.isEmpty(teacherName))return MyResult.failure("请输入教师名称");
        if(ObjectUtil.isEmpty(beginDate))return MyResult.failure("请输入开班时间");
        if(ObjectUtil.isEmpty(classNum))return MyResult.failure("请传入班级学生数量");
        if(ObjectUtil.isEmpty(classDes))return MyResult.failure("请输入班级简介");
        if(ObjectUtil.isEmpty(img))return MyResult.failure("请传入图片");
        if(ObjectUtil.isEmpty(price)) return MyResult.failure("请传入价格");
        Integer schoolId = SystemParam.getSchoolId();
        XnIntrestClass xnIntrestClass = new XnIntrestClass();
        xnIntrestClass.setClassdes(classDes);
        xnIntrestClass.setClassname(className);
        xnIntrestClass.setClassnum(classNum);
        xnIntrestClass.setSid(schoolId);
        if(!ObjectUtil.isEmpty(img)){
            if(img.contains("http")){
                String thum = img.substring(img.indexOf("sschoolManageFile"));
                xnIntrestClass.setClassimg(thum);
            }else{
                xnIntrestClass.setClassimg(img);
            }
        }

        try{
            BigDecimal priceIsOk = new BigDecimal(price);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("价格格式有误");
        }
        xnIntrestClass.setAttr1(price);
        try{
            Date date = TimeUtil.YYYYMMDDHHMMSSToTime(beginDate);
            xnIntrestClass.setBegindate(date);
        }catch (ParseException pe){
            pe.printStackTrace();
            return MyResult.failure("时间格式有误");
        }
        xnIntrestClass.setTid(tid);
        xnIntrestClass.setTeaname(teacherName);
        String message = "";
        try{
            if(ObjectUtil.isEmpty(id)){

                xnIntrestClassMapper.insert(xnIntrestClass);
                message = "添加成功";
            }else{
                XnIntrestClassKey xnIntrestClassKey = new XnIntrestClassKey();
                xnIntrestClassKey.setId(id);
                xnIntrestClassKey.setSid(schoolId);
                XnIntrestClass xnIntrestClass1 = xnIntrestClassMapper.selectByPrimaryKey(xnIntrestClassKey);
                if(ObjectUtil.isEmpty(xnIntrestClass1))return MyResult.failure("当前兴趣班不存在，无法修改");
                xnIntrestClass.setId(id);
                xnIntrestClassMapper.updateByPrimaryKeySelective(xnIntrestClass);
                message="修改成功";
            }
        }catch (Exception e ){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }



        return MyResult.success(message);
    }

    @Override
    public MyResult deleteXnIntrestClass(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids)) return MyResult.failure("请选择要删除的数据");
        XnIntrestClassExample xnIntrestClassExample = new XnIntrestClassExample();
        XnIntrestClassExample.Criteria criteria = xnIntrestClassExample.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        int num = 0;
        try{
            num = xnIntrestClassMapper.deleteByExample(xnIntrestClassExample);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除数据"+num+"条");
    }

    @Override
    public MyResult listIntrestClassStudent(Integer id,Integer pageNo, Integer pageSize) {
        Integer schooId = SystemParam.getSchoolId();
        if(ObjectUtil.isEmpty(id))return MyResult.failure("请选择要查看的兴趣班");
        XnIntrestEntryExample xnIntrestEntryExample = new XnIntrestEntryExample();
        XnIntrestEntryExample.Criteria criteria = xnIntrestEntryExample.createCriteria();
        criteria.andIntrestidEqualTo(id);
        List<XnIntrestEntry> xnIntrestEntries = xnIntrestEntryMapper.selectByExample(xnIntrestEntryExample);
        if(ObjectUtil.isEmpty(xnIntrestEntries))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        List<XnIntrestEntry> xnIntrestEntries_list = xnIntrestEntries.stream().filter(c->"2".equals(c.getAttr1())).collect(Collectors.toList());
        for(XnIntrestEntry xnIntrestEntry :xnIntrestEntries_list){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnIntrestEntry.getId());
            HlStudentinfoKey hlStudentinfoKey = new HlStudentinfoKey();
            hlStudentinfoKey.setSchoolid(schooId);
            hlStudentinfoKey.setId(xnIntrestEntry.getStuid());
            HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(hlStudentinfoKey);
            map.put("studentName",hlStudentinfo!=null ?hlStudentinfo.getSname():"未知");
            map.put("sid",xnIntrestEntry.getStuid());
            HlSchclassKey hlSchclassKey = new HlSchclassKey();
            hlSchclassKey.setSchoolid(schooId);
            hlSchclassKey.setId(hlStudentinfo.getClassid());
            HlSchclass hlSchclass = hlSchclassMapper.selectByPrimaryKey(hlSchclassKey);
            map.put("className",hlSchclass!= null ? hlSchclass.getClassname():"未知");
            map.put("phone",hlStudentinfo.getPhone());
            map.put("type",xnIntrestEntry.getEntrytype());
            //map.put("userId",xnIntrestEntry.getAttr3());
            result.add(map);

        }
        return ResultPage.getPageResult(result,pageNo,pageSize);
    }

    /**
     * 添加兴趣班的学生(只能添加，不能修改)
     * @param intrestId 兴趣班id
     * @param id 兴趣班学生列表id--(暂时不用)
     * @param sid 学生id
     * @param parentPhone 家长号码
     * @param entryType 方式 1.线上，2线下
     * @return
     */
    @Override
    public MyResult addOrupdateStudent(Integer intrestId,Integer id, Integer sid, String parentPhone, Integer entryType) {
        Integer schoolId = SystemParam.getSchoolId();
        XnIntrestClassKey xnIntrestClassKey = new XnIntrestClassKey();
        xnIntrestClassKey.setSid(schoolId);
        xnIntrestClassKey.setId(intrestId);

        XnIntrestClass xnIntrestClass = xnIntrestClassMapper.selectByPrimaryKey(xnIntrestClassKey);
        Integer number = xnIntrestClass.getClassnum();

        XnIntrestEntryExample xnIntrestEntryExample = new XnIntrestEntryExample();
        XnIntrestEntryExample.Criteria criteria = xnIntrestEntryExample.createCriteria();
        criteria.andIntrestidEqualTo(intrestId);
        Integer count = xnIntrestEntryMapper.countByExample(xnIntrestEntryExample);
        if(number<=count){
            return MyResult.failure("人数已满，无法添加");
        }


        XnIntrestEntry xnIntrestEntry = new XnIntrestEntry();
        xnIntrestEntry.setCreatetime(new Date());
        xnIntrestEntry.setStuid(sid);
        xnIntrestEntry.setSid(schoolId);
        xnIntrestEntry.setEntrytype(entryType);
        xnIntrestEntry.setIntrestid(intrestId);
        xnIntrestEntry.setAttr1(PAYED);
        xnIntrestEntryMapper.insert(xnIntrestEntry);
        return MyResult.success("添加成功");
    }

    @Override
    public MyResult deleteIntrestClassStudent(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        for(Integer id : ids){
            xnIntrestEntryMapper.deleteByPrimaryKey(id);
        }
        return MyResult.success("删除成功");
    }


}
