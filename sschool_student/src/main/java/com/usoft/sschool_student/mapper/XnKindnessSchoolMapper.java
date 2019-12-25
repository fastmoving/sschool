package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnKindnessSchool;
import com.usoft.smartschool.pojo.XnKindnessSchoolExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnKindnessSchoolMapper {
    int countByExample(XnKindnessSchoolExample example);

    int deleteByExample(XnKindnessSchoolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnKindnessSchool record);

    int insertSelective(XnKindnessSchool record);

    List<XnKindnessSchool> selectByExample(XnKindnessSchoolExample example);

    XnKindnessSchool selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnKindnessSchool record, @Param("example") XnKindnessSchoolExample example);

    int updateByExample(@Param("record") XnKindnessSchool record, @Param("example") XnKindnessSchoolExample example);

    int updateByPrimaryKeySelective(XnKindnessSchool record);

    int updateByPrimaryKey(XnKindnessSchool record);
}