package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnTeacherIntegral;
import com.usoft.smartschool.pojo.XnTeacherIntegralExample;
import com.usoft.sschool_teacher.enums.vo.MyselfEvaluationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnTeacherIntegralMapper {
    int countByExample(XnTeacherIntegralExample example);

    int deleteByExample(XnTeacherIntegralExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnTeacherIntegral record);

    int insertSelective(XnTeacherIntegral record);

    List<XnTeacherIntegral> selectByExample(XnTeacherIntegralExample example);

    XnTeacherIntegral selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnTeacherIntegral record, @Param("example") XnTeacherIntegralExample example);

    int updateByExample(@Param("record") XnTeacherIntegral record, @Param("example") XnTeacherIntegralExample example);

    int updateByPrimaryKeySelective(XnTeacherIntegral record);

    int updateByPrimaryKey(XnTeacherIntegral record);

    /**
     * 教师总积分
     */
    Integer getTeacherIntegral(@Param("teacherId")Integer teacherId);

    /**
     * 获取教师评价
     * @return
     */
    List<MyselfEvaluationVo> getTeacherEvaluation(@Param("teacherId")Integer teacherId,
                                                  @Param("schoolId")Integer schoolId,
                                                  @Param("pageNO")Integer pageNo,
                                                  @Param("pageSize")Integer pageSize);
    Integer getTeacherEvaluationCount(@Param("teacherId")Integer teacherId,
                                                  @Param("schoolId")Integer schoolId);
}