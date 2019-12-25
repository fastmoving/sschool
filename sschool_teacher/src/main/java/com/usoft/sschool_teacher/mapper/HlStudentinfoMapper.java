package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.HlStudentinfo;
import com.usoft.smartschool.pojo.HlStudentinfoExample;
import com.usoft.smartschool.pojo.HlStudentinfoKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HlStudentinfoMapper {
    int countByExample(HlStudentinfoExample example);

    int deleteByExample(HlStudentinfoExample example);

    int deleteByPrimaryKey(HlStudentinfoKey key);

    int insert(HlStudentinfo record);

    int insertSelective(HlStudentinfo record);

    List<HlStudentinfo> selectByExample(HlStudentinfoExample example);

    HlStudentinfo selectByPrimaryKey(HlStudentinfoKey key);

    int updateByExampleSelective(@Param("record") HlStudentinfo record, @Param("example") HlStudentinfoExample example);

    int updateByExample(@Param("record") HlStudentinfo record, @Param("example") HlStudentinfoExample example);

    int updateByPrimaryKeySelective(HlStudentinfo record);

    int updateByPrimaryKey(HlStudentinfo record);

    List<HlStudentinfo> getStudentInformation(@Param("classId")int classId);

    /**
     * 获取学生ID
     */
    List<Integer> getStudentID(Map key);
}