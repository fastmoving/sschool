package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnTeacherEvaluation;
import com.usoft.smartschool.pojo.XnTeacherEvaluationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnTeacherEvaluationMapper {
    int countByExample(XnTeacherEvaluationExample example);

    int deleteByExample(XnTeacherEvaluationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnTeacherEvaluation record);

    int insertSelective(XnTeacherEvaluation record);

    List<XnTeacherEvaluation> selectByExample(XnTeacherEvaluationExample example);

    XnTeacherEvaluation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnTeacherEvaluation record, @Param("example") XnTeacherEvaluationExample example);

    int updateByExample(@Param("record") XnTeacherEvaluation record, @Param("example") XnTeacherEvaluationExample example);

    int updateByPrimaryKeySelective(XnTeacherEvaluation record);

    int updateByPrimaryKey(XnTeacherEvaluation record);
}