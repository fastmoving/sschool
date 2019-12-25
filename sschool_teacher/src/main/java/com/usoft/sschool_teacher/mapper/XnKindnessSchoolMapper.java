package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnKindnessSchool;
import com.usoft.smartschool.pojo.XnKindnessSchoolExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 我的爱心
     * @param key
     * @return
     */
    List getMyKindness(Map key);
}