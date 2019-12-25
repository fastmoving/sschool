package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.*;
import com.usoft.sschool_manage.service.schoolset.HlSchclassService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/4/24 16:58
 */
@Service
public class HlSchoolClassServiceImpl implements HlSchclassService {

    @Resource
    private HlSchclassMapper hlSchclassMapper;

    @Resource
    private CfDepartmentMapper cfDepartmentMapper;

    @Resource
    private HlEnumitemMapper hlEnumitemMapper;

    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;

    @Resource
    private HlTeacherMapper teacherMapper;

    @Resource
    private HlStudentinfoMapper studentinfoMapper;

    /**
     * 启用
     */
    private static int USE = 1;
    /**
     * 禁用
     */
    private static int UNUSE = 2;

    /**
     * 班主任
     */
    private static final String MAJORTEACHER="48";

    /**
     * 关联表 数据正常
     */
    private static final byte NORMAL = 2;

    @Override
    public MyResult listClassMessage(Integer sid, Integer gid, String name) {

        HlSchclassExample hlSchclassExample = new HlSchclassExample();
        HlSchclassExample.Criteria criteria = hlSchclassExample.createCriteria();
        criteria.andSchoolidEqualTo(sid);
        criteria.andIsuseidEqualTo(USE);
        if (!ObjectUtil.isEmpty(name)) {
            criteria.andClassnameLike("%"+name+"%");
        }
        if(!ObjectUtil.isEmpty(gid)){
            criteria.andGradetypeEqualTo(gid);
        }
        List<HlSchclass>  hlSchclasses = hlSchclassMapper.selectByExample(hlSchclassExample);
        if(ObjectUtil.isEmpty(hlSchclasses))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(HlSchclass hlSchclass : hlSchclasses){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hlSchclass.getId());
            map.put("name",hlSchclass.getClassname());
            result.add(map);
        }
        return MyResult.success(result);
    }

    @Override
    public MyResult listSchclass(String className, String teacherName, Integer grade, Integer schoolId, Integer pageNo, Integer pageSize) {
        HlSchclassExample hlSchclassExample = new HlSchclassExample();
        HlSchclassExample.Criteria criteria = hlSchclassExample.createCriteria();
        criteria.andIsuseidEqualTo(USE);
        if(!ObjectUtil.isEmpty(className))criteria.andClassnameLike("%"+className+"%");
        if(!ObjectUtil.isEmpty(teacherName))criteria.andClassteachLike("%"+teacherName+"%");
        if(!ObjectUtil.isEmpty(grade))criteria.andGradetypeEqualTo(grade);
        if(!ObjectUtil.isEmpty(schoolId))criteria.andSchoolidEqualTo(schoolId);
        hlSchclassExample.setOrderByClause("classTeach asc");
        List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(hlSchclassExample);
        if(ObjectUtil.isEmpty(hlSchclasses)){
            return MyResult.failure("暂无数据");
        }
        if(ObjectUtil.isEmpty(pageNo)) pageNo = 1;
        if(ObjectUtil.isEmpty(pageSize)) pageSize = 20;

        List<Map<String,Object>> result = new ArrayList<>();
        for(HlSchclass hlSchclass : hlSchclasses){
            Map<String,Object> map = new HashMap<>();
            Integer id = hlSchclass.getId();
            map.put("id",id);
            map.put("classTeach",hlSchclass.getClassteach());
            map.put("classNum",hlSchclass.getClassnum());
            map.put("className",hlSchclass.getClassname());
            map.put("classDes",hlSchclass.getClassdes());
            map.put("classImg",hlSchclass.getClassimg());
            map.put("isUsed",hlSchclass.getIsuseid());
            //学校信息
            CfDepartment cfDepartment = cfDepartmentMapper.selectByPrimaryKey(hlSchclass.getSchoolid());
            map.put("schoolId",hlSchclass.getSchoolid());
            map.put("schoolName",cfDepartment.getDeptname());
            //年级信息
            HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(hlSchclass.getGradetype());
            map.put("gradeId",hlSchclass.getGradetype());
            map.put("gradeType",hlEnumitem.getEnumitemname());
            //班主任id,根据关联表查询
            XnResourceRelationExample xnResourceRelationExample = new XnResourceRelationExample();
            XnResourceRelationExample.Criteria criteria1 = xnResourceRelationExample.createCriteria();
            criteria1.andResourcebidEqualTo(id);
            criteria1.andResourcebtableEqualTo("hl_schclass");
            criteria1.andResourceatableEqualTo("hl_teacher");
            criteria1.andTypeEqualTo(MAJORTEACHER);
            criteria1.andIsdeleteEqualTo(NORMAL);
            List<XnResourceRelation> xnResourceRelations = xnResourceRelationMapper.selectByExample(xnResourceRelationExample);
            if(ObjectUtil.isEmpty(xnResourceRelations)){
                map.put("teacherId",null);
            }else {
                XnResourceRelation xnResourceRelation =  xnResourceRelations.get(0);
                map.put("teacherId",xnResourceRelation.getResourceaid());
            }
            result.add(map);

        }
        return ResultPage.getPageResult(result,pageNo,pageSize);
    }


    @Override
    @Transactional
    public MyResult addOrupdateSchclass(Integer id, String className, Integer sid,Integer teacherId, String teacherName, Integer grade, Integer stuNum, String classDes, String classImg) {
        HlSchclass hlSchclass  = new HlSchclass();
        Integer schoolId = SystemParam.getSchoolId();
        if(ObjectUtil.isEmpty(className))return MyResult.failure("请输入班级名称");

        if(ObjectUtil.isEmpty(teacherName))return MyResult.failure("请输入班主任名称");
        if(ObjectUtil.isEmpty(grade))return MyResult.failure("请选择年级");
        if(ObjectUtil.isEmpty(stuNum))return MyResult.failure("输入学生数量");
        if(stuNum<0)return MyResult.failure("学生数量不能小于零");
        if(!ObjectUtil.isEmpty(classDes))hlSchclass.setClassdes(classDes);
        hlSchclass.setClassimg(classImg);

        hlSchclass.setClassteach(teacherName);
        hlSchclass.setGradetype(grade);
        hlSchclass.setSchoolid(schoolId);
        hlSchclass.setClassnum(stuNum);
        String message = "";

        try{
            if(ObjectUtil.isEmpty(id)){
                if(className.contains("年级"))return MyResult.failure("班级名称不能包含年级两个字");
                HlEnumitem hlEnumitem = hlEnumitemMapper.selectByPrimaryKey(grade);
                String gradeName = hlEnumitem.getEnumitemname();
                className = gradeName+className.trim();
                hlSchclass.setClassname(className);
                //班级名称去重
                HlSchclassExample hlSchclassExample = new HlSchclassExample();
                HlSchclassExample.Criteria criteria = hlSchclassExample.createCriteria();
                criteria.andClassnameEqualTo(className.trim());
                criteria.andSchoolidEqualTo(schoolId);
                List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(hlSchclassExample);
                if(!ObjectUtil.isEmpty(hlSchclasses))return MyResult.failure("班级名称已经存在");
                hlSchclass.setIsuseid(USE);
                hlSchclassMapper.insert(hlSchclass);

                //插入关联表
                XnResourceRelation xnResourceRelation = new XnResourceRelation();
                xnResourceRelation.setCreatetime(new Date());
                xnResourceRelation.setResourceaid(teacherId);
                xnResourceRelation.setResourceatable("hl_teacher");
                xnResourceRelation.setResourcebid(hlSchclass.getId());
                xnResourceRelation.setResourcebtable("hl_schclass");
                xnResourceRelation.setIsdelete(NORMAL);
                xnResourceRelation.setType(MAJORTEACHER);
                xnResourceRelationMapper.insert(xnResourceRelation);
                message = "添加成功";
            }else{
                hlSchclass.setId(id);
                //查找班级id是否存在
                HlSchclassKey hlSchclassKey= new HlSchclassKey();
                hlSchclassKey.setId(id);
                hlSchclassKey.setSchoolid(schoolId);
                HlSchclass hlSchclass1 = hlSchclassMapper.selectByPrimaryKey(hlSchclassKey);
                if(ObjectUtil.isEmpty(hlSchclass1))return MyResult.failure("未发现当前班级信息，无法修改");
                //班级名称去重
                HlSchclassExample hlSchclassExample = new HlSchclassExample();
                HlSchclassExample.Criteria criteriaclass = hlSchclassExample.createCriteria();
                criteriaclass.andClassnameEqualTo(className.trim());
                criteriaclass.andIdNotEqualTo(id);
                List<HlSchclass> hlSchclasses = hlSchclassMapper.selectByExample(hlSchclassExample);
                if(!ObjectUtil.isEmpty(hlSchclasses))return MyResult.failure("班级名称已存在，请重新输入名称");
                //是否需要更新关联表
                XnResourceRelationExample xnResourceRelationExample = new XnResourceRelationExample();
                XnResourceRelationExample.Criteria criteria = xnResourceRelationExample.createCriteria();
                criteria.andResourcebidEqualTo(id);
                criteria.andResourcebtableEqualTo("hl_schclass");
                criteria.andResourceatableEqualTo("hl_teacher");
                criteria.andTypeEqualTo(MAJORTEACHER);
                criteria.andIsdeleteEqualTo(NORMAL);
                List<XnResourceRelation> xnResourceRelations = xnResourceRelationMapper.selectByExample(xnResourceRelationExample);
                if(ObjectUtil.isEmpty(xnResourceRelations)){
                    XnResourceRelation xnResourceRelation = new XnResourceRelation();
                    xnResourceRelation.setCreatetime(new Date());
                    xnResourceRelation.setResourceaid(teacherId);
                    xnResourceRelation.setResourceatable("hl_teacher");
                    xnResourceRelation.setResourcebid(id);
                    xnResourceRelation.setResourcebtable("hl_schclass");
                    xnResourceRelation.setIsdelete(NORMAL);
                    xnResourceRelation.setType(MAJORTEACHER);
                    xnResourceRelationMapper.insert(xnResourceRelation);
                }else{
                    XnResourceRelation xnResourceRelation =  xnResourceRelations.get(0);
                    if(!teacherId.equals(xnResourceRelation.getResourceaid())){
                        xnResourceRelation.setResourceaid(teacherId);
                        xnResourceRelationMapper.updateByPrimaryKey(xnResourceRelation);
                    }
                }

                int i = hlSchclassMapper.updateByPrimaryKeySelective(hlSchclass);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return MyResult.failure("操作失败");
        }
        return MyResult.success(message);
    }

    @Override
    public MyResult deleteSchclass(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids)){
            return MyResult.failure("请选择要删除的数据");
        }
        Integer schoolId = SystemParam.getSchoolId();
        int deleteNum = 0;
        for(int id : ids){
            HlSchclass hlSchclass = new HlSchclass();
            hlSchclass.setId(id);
            hlSchclass.setSchoolid(schoolId);
            hlSchclass.setIsuseid(UNUSE);
            deleteNum +=hlSchclassMapper.updateByPrimaryKeySelective(hlSchclass);

        }

        return MyResult.success("成功删除"+deleteNum+"条数据");
    }

    @Override
    public MyResult importSchclass(HttpServletRequest request) {
        return null;
    }


    public static void main(String[] args){
        String name = "二年级五班";
        if(name.contains(" ")){
            System.out.println("有空字符串");
        }
    }

    @Override
    @Transactional
    public MyResult upgrade(){
        int v;
//        Integer user_id = SystemParam.getUserId();
//        Integer user_type = SystemParam.getUserType();
//        Integer school_id = SystemParam.getSchoolId();
//
//        //判断权限
//        if(user_type != 1)return MyResult.failure("你不能操作！");
//        Map key = new HashMap();
//        key.put("teacherId",user_id);
//        key.put("schoolId",school_id);
//        List teacher_list = teacherMapper.getMyselfManager(key);
//        if(teacher_list.size()<1)return MyResult.failure("您不能操作！");

        //判断时间是否到了
        try {
            if(new Date().before(new SimpleDateFormat("yyyy-MM-dd").parse(Calendar.getInstance().get(Calendar.YEAR)+"-08-27")))
                return MyResult.failure("还没有到升级时间！");
        }catch (Exception e){
            e.printStackTrace();
            return new MyResult(e,"时间格式不对！");
        }
        //判断是否修改
        XnResourceRelationExample relationExample = new XnResourceRelationExample();
        XnResourceRelationExample.Criteria criteria = relationExample.createCriteria();
        criteria.andResourceatableEqualTo("user");
        criteria.andResourcebtableEqualTo("class");
        criteria.andResourcetypeEqualTo("upgrade");
        try {
            criteria.andCreatetimeGreaterThan(new SimpleDateFormat("yyyy-MM-dd").parse(Calendar.getInstance().get(Calendar.YEAR)+"-08-27"));
        }catch (Exception e){
            e.printStackTrace();
            return new MyResult(e,"时间格式不对！");
        }
        criteria.andResourcebidEqualTo(1);
        List<XnResourceRelation> relations = xnResourceRelationMapper.selectByExample(relationExample);
        if(relations.size()>0)return MyResult.failure("已经完成升级！");

        //查询修改班级
        HlSchclassExample schclassExample = new HlSchclassExample();
        HlSchclassExample.Criteria class_criteria = schclassExample.createCriteria();
        class_criteria.andGradetypeNotEqualTo(29);
        class_criteria.andIsuseidNotEqualTo(2);
        List<HlSchclass> classes = hlSchclassMapper.selectByExample(schclassExample);

        //修改班级 and 学生
        if(classes.size()<1)return MyResult.failure();
        for(HlSchclass c:classes){
            //修改班级
            c.setClassname(getGrade(c.getGradetype())+c.getClassname().substring(3));
            c.setGradetype(c.getGradetype()+1);
            int i = hlSchclassMapper.updateByPrimaryKeySelective(c);
            try {
                v = 1/i;
            }catch (Exception e){
                e.printStackTrace();
                return new MyResult(e,"无此班级！");
            }
            int j = studentinfoMapper.upgrade(c.getId(),c.getGradetype());
            try {
                v = 1/j;
            }catch (Exception e){
                e.printStackTrace();
                return new MyResult(e,"班级没有学生！");
            }
        }

        //添加修改状态
        XnResourceRelation resourceRelation = new XnResourceRelation();
        resourceRelation.setCreatetime(new Date());
        resourceRelation.setResourceaid(SystemParam.getUserId());
        resourceRelation.setResourceatable("user");
        resourceRelation.setResourcebid(1);
        resourceRelation.setResourcebtable("class");
        resourceRelation.setResourcetype("upgrade");
        int r =xnResourceRelationMapper.insertSelective(resourceRelation);
        try {
            v = 1/r;
        }catch (Exception e){
            e.printStackTrace();
            return new MyResult(e,"系统故障！");
        }
        return MyResult.success();
    }

    private String getGrade(Integer grade){
        switch (grade){
            case 20: return "二年级";
            case 21: return "三年级";
            case 22: return "四年级";
            case 23: return "五年级";
            case 24: return "六年级";
            case 25: return "七年级";
            case 26: return "八年级";
            case 27: return "九年级";
        }
        return "";
    }
}
