package com.usoft.sschool_teacher.mapper;


import com.usoft.smartschool.pojo.XnScore;
import com.usoft.smartschool.pojo.XnScoreExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnScoreMapper {
    int countByExample(XnScoreExample example);

    int deleteByExample(XnScoreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnScore record);

    int insertSelective(XnScore record);

    List<XnScore> selectByExample(XnScoreExample example);

    XnScore selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnScore record, @Param("example") XnScoreExample example);

    int updateByExample(@Param("record") XnScore record, @Param("example") XnScoreExample example);

    int updateByPrimaryKeySelective(XnScore record);

    int updateByPrimaryKey(XnScore record);

    /**
     * 获取考试类型
     * @param classId
     * @return
     */
    List getExamType(@Param("classId")int classId);

    /**
     * 获取学期
     * @param classId
     * @return
     */
    List getSemester(@Param("classId")int classId);

    /**
     * 教师单科成绩
     */
    List<XnScore> getScoreSubject(Map key);
    Integer getScoreSubjectCount(Map key);

    /**
     * 年级单科成绩和多课成绩
     */
    List<Map>  getClassScore(Map key);

    /**
     * 获取科目
     */
    List<Map> getSubject(Map key);
}