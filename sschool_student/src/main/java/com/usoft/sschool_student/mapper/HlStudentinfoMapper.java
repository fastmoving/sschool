package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.HlStudentinfo;
import com.usoft.smartschool.pojo.HlStudentinfoExample;
import com.usoft.smartschool.pojo.HlStudentinfoKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}