package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 成绩管理控制层
 * @Author jijh
 * @Date 2019/4/26 14:58
 */
@RestController
@RequestMapping("manage/score")
public class XnScoreController  extends BaseController {

    @Autowired
    private XnScoreService xnScoreService;

    /**
     * 成绩列表 (条件模糊查询)
     * @param studentName 学生姓名
     * @param term 学期
     * @param schoolId 学校id
     * @param gradeId 年级id
     * @param classId 班级id
     * @param testName 考试名称
     * @param subject 科目
     * @param score 分数
     * @param wrongNumber 错题数量
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("list")
    public Object listXnScore(String studentName, String term, Integer schoolId, Integer gradeId, Integer classId, String testName, String subject
            , String score, Integer wrongNumber,Integer pageNo, Integer pageSize){
        return xnScoreService.listXnScore(studentName,term,schoolId,gradeId,classId,testName,subject,score,wrongNumber,pageNo,pageSize);
    }

    /**
     * 添加或者更新成绩
     * @param id 成绩id 修改时需要
     * @param studentName 学生姓名
     * @param studentId  学生id
     * @param schoolId 学校id`
     * @param classId 班级id
     * @param term  学期
     * @param testName 考试名称
     * @param subject 科目
     * @param score 分数
     * @param wrongNumber 错题数量
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateXnScore(Integer id, Integer studentId, String studentName, Integer schoolId
            , Integer classId, String term, String testName, String subject, String score, Integer wrongNumber){
        return xnScoreService.addOrUpdateXnScore(id,studentId,studentName,schoolId,classId,term,testName,subject,score,wrongNumber);
    }

    /**
     * 删除成绩
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteXnScores(Integer[] ids){
        return xnScoreService.deleteXnScore(ids);

    }
}
