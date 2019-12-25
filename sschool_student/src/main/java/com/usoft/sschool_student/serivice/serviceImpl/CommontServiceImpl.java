package com.usoft.sschool_student.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_student.mapper.HlCurriculumMapper;
import com.usoft.sschool_student.mapper.XnResourceRelationMapper;
import com.usoft.sschool_student.mapper.XnTeacherEvaluationMapper;
import com.usoft.sschool_student.serivice.CommontService;
import com.usoft.sschool_student.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("CommontService")
public class CommontServiceImpl implements CommontService {
    @Resource
    private HlCurriculumMapper hlCurriculumMapper;
    @Resource
    private XnTeacherEvaluationMapper xnTeacherEvaluationMapper;
    @Resource
    private XnResourceRelationMapper xnResourceRelationMapper;
    @Resource
    private SearchUtil searchUtil;
    @Resource
    private VoUtil voUtil;

    /**
     * 查询所有教师信息
     * @param schoolId
     * @param childId
     * @return
     */
    @Override
    public MyResult allTea(Integer schoolId, Integer childId) {
        List<Map> list=new ArrayList<>();
        HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, childId);
        List<Map<Object, Object>> maps = hlCurriculumMapper.classTeacherId(schoolId, studentinfo.getClassid());
        //查询班主任
        XnResourceRelationExample example1=new XnResourceRelationExample();
        example1.createCriteria().andResourceatableEqualTo("hl_teacher").andResourcebtableEqualTo("hl_schclass")
                .andResourcebidEqualTo(studentinfo.getClassid());
        List<XnResourceRelation> xnResourceRelations = xnResourceRelationMapper.selectByExample(example1);
        if (!ObjectUtil.isEmpty(xnResourceRelations)){
            Integer teaId = xnResourceRelations.get(0).getResourceaid();
            Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte) 2, teaId, schoolId);
            objectObjectMap.put("subject","班主任");
            objectObjectMap.put("attr1",1);
            XnTeacherEvaluationExample example=new XnTeacherEvaluationExample();
            example.createCriteria().andSidEqualTo(schoolId).andTidEqualTo(teaId).andCidEqualTo(studentinfo.getClassid())
                    .andStuidEqualTo(childId).andAttr1EqualTo("1");
            List<XnTeacherEvaluation> xnTeacherEvaluations = xnTeacherEvaluationMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnTeacherEvaluations)){
                objectObjectMap.put("isEva",1);
            }else {
                objectObjectMap.put("isEva",2);
                objectObjectMap.put("EvaContent",xnTeacherEvaluations.get(0).getDes());
                objectObjectMap.put("EvaTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnTeacherEvaluations.get(0).getCreatetime()));
                objectObjectMap.put("EvaStar",xnTeacherEvaluations.get(0).getStar());
                objectObjectMap.put("commontId",xnTeacherEvaluations.get(0).getId());
            }
            list.add(objectObjectMap);
        }

        for (Map m:maps){
            Integer classTeacher = (int)m.get("ClassTeacher");
            Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte) 2, classTeacher, schoolId);
            objectObjectMap.put("attr1",2);
            XnTeacherEvaluationExample example=new XnTeacherEvaluationExample();
            example.createCriteria().andSidEqualTo(schoolId).andTidEqualTo(classTeacher).andCidEqualTo(studentinfo.getClassid())
            .andStuidEqualTo(childId).andAttr1EqualTo("2");
            List<XnTeacherEvaluation> xnTeacherEvaluations = xnTeacherEvaluationMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnTeacherEvaluations)){
                objectObjectMap.put("isEva",1);
            }else {
                objectObjectMap.put("isEva",2);
                objectObjectMap.put("EvaContent",xnTeacherEvaluations.get(0).getDes());
                objectObjectMap.put("EvaTime",TimeUtil.TimeToYYYYMMDDHHMMSS(xnTeacherEvaluations.get(0).getCreatetime()));
                objectObjectMap.put("EvaStar",xnTeacherEvaluations.get(0).getStar());
                objectObjectMap.put("commontId",xnTeacherEvaluations.get(0).getId());
            }
            list.add(objectObjectMap);
        }
        return MyResult.success(list);
    }

    /**
     * 评论具体内容
     * @param cid
     * @param schoolId
     * @return
     */
    @Override
    public MyResult searchCommontInfo(Integer cid,Integer schoolId) {
        XnTeacherEvaluation xnTeacherEvaluation = xnTeacherEvaluationMapper.selectByPrimaryKey(cid);
        if (ObjectUtil.isEmpty(xnTeacherEvaluation))return MyResult.failure("没找到数据");
        Map<Object, Object> objectObjectMap = voUtil.userPhoto((byte) 2, xnTeacherEvaluation.getTid(), schoolId);
        objectObjectMap.put("createTime", TimeUtil.TimeToYYYYMMDDHHMMSS(xnTeacherEvaluation.getCreatetime()));
        objectObjectMap.put("des",xnTeacherEvaluation.getDes());
        objectObjectMap.put("star",xnTeacherEvaluation.getStar());
        return MyResult.success(objectObjectMap);
    }

    /**
     * 评价老师
     * @param teaId
     * @param star
     * @param des
     * @param attr1
     * @param schoolId
     * @param childId
     * @return
     */
    @Override
    public MyResult commontTea(Integer teaId,Integer star,String des,Integer attr1, Integer schoolId, Integer childId) {
        HlStudentinfo studentinfo = searchUtil.Studentinfo(schoolId, childId);
        if (ObjectUtil.isEmpty(studentinfo))return MyResult.failure("没找到孩子的信息");
        XnTeacherEvaluation xte=new XnTeacherEvaluation();
        xte.setSid(schoolId);
        xte.setCid(studentinfo.getClassid());
        xte.setStuid(childId);
        xte.setTid(teaId);
        xte.setStar(star);
        xte.setDes(des);
        xte.setCreatetime(new Date());
        xte.setAttr1(attr1.toString());
        int i = xnTeacherEvaluationMapper.insertSelective(xte);
        if (i!=1){
            return MyResult.failure("保存评价失败");
        }
        return MyResult.success("保存评价成功");
    }
}
