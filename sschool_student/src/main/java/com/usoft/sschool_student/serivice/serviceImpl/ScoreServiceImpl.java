package com.usoft.sschool_student.serivice.serviceImpl;
import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_student.mapper.XnScoreMapper;
import com.usoft.sschool_student.serivice.ScoreService;
import com.usoft.sschool_student.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wlw
 * @data 2019/4/23 0023-16:36
 */
@Service(value = "scoreService")
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private XnScoreMapper xnScoreMapper;

    @Override
    public MyResult term() {
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        List term = xnScoreMapper.term(schoolId, childId);
        for (Object l:term){
            System.out.println("考试类型是："+l);
        }
        /*List list = xnScoreMapper.TestName(schoolId, childId);
        for (Object l:list){
            System.out.println("考试名是："+l);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("term",term);
        map.put("TestName",list);*/
        return MyResult.success(term);
    }

    /**
     * 考试名称
     * @param term
     * @return
     */
    @Override
    public MyResult testName(String term) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        List list = xnScoreMapper.TestName(schoolId, childId,term);
        return MyResult.success(list);
    }

    /**
     * 查询学生的成绩 （最高分???）
     * @param term 学期
     * @return
     */
    @Override
    public MyResult oneStudent(String term, String testName) {
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        List terms = xnScoreMapper.term(schoolId, childId);
        if (ObjectUtil.isEmpty(terms))return MyResult.failure("还没有你的考试信息");
        if(term==null && testName==null){
            XnScoreExample example=new XnScoreExample();
            example.createCriteria().andSidEqualTo(SystemParam.getSchoolId()).andStuidEqualTo(SystemParam.getChildId());
            example.setOrderByClause("id desc");
            List<XnScore> xnScores = xnScoreMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnScores))return MyResult.failure("没有找到该学生的考试信息");
            term=terms.get(0).toString();
            testName=xnScores.get(0).getTestname();
        }else if (term!=null && testName==null){
            XnScoreExample example=new XnScoreExample();
            example.createCriteria().andSidEqualTo(SystemParam.getSchoolId()).andStuidEqualTo(SystemParam.getChildId())
            .andTermEqualTo(term);
            example.setOrderByClause("id desc");
            List<XnScore> xnScores = xnScoreMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnScores))return MyResult.failure("没有找到该学生的考试信息");
            testName=xnScores.get(0).getTestname();
        }else if (term==null && testName!=null){
            XnScoreExample example=new XnScoreExample();
            example.createCriteria().andSidEqualTo(SystemParam.getSchoolId()).andStuidEqualTo(SystemParam.getChildId())
            .andTestnameEqualTo(testName);
            example.setOrderByClause("id desc");
            List<XnScore> xnScores = xnScoreMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnScores))return MyResult.failure("没有找到该学生的考试信息");
            term=xnScores.get(0).getTerm();
        }
        //根据学期和考试类型查询学生的成绩
        Map map=new HashMap();
        //查询该学生的单科信息及总分
        XnScoreExample example=new XnScoreExample();
        example.createCriteria().andStuidEqualTo(childId).andTermEqualTo(term).andTestnameEqualTo(testName)
        .andSidEqualTo(schoolId);
        List<XnScore> xnScores = xnScoreMapper.selectByExample(example);
        if(ObjectUtil.isEmpty(xnScores))return MyResult.failure("没找到学生的成绩信息");
        Double one=0.00;
        int wnum=0;
        for (XnScore xns:xnScores){
            one+=Double.valueOf(xns.getScore());
            wnum+=xns.getWrongnum();
        }
        //int onesc= Math.round(one);
        map.put("sidcount",one);
        map.put("xnScores",xnScores);
        //班级id
        Integer cid = xnScores.get(0).getCid();
        //班级最高分
        Map<String,Object> xnScore = xnScoreMapper.classScore(term, testName, cid);
        System.out.println("最高分"+xnScore.get("sco"));
        map.put("maxScore" ,xnScore.get("sco"));
        //班级平均数据
        Map<String,Object> xnScore1 = xnScoreMapper.avgScore(term, testName, cid);
        System.out.println("平均分"+Double.valueOf(xnScore1.get("sco").toString())*xnScores.size());
        System.out.println("平均错题是:"+Double.valueOf(xnScore1.get("wn").toString())*xnScores.size());
        map.put("avgScore",one/xnScores.size());
        map.put("avgwn",wnum);
        return MyResult.success(map);
    }
}
