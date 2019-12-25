package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnKindnessUnschool;
import com.usoft.smartschool.pojo.XnKindnessUnschoolExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnKindnessUnschoolMapper {
    int countByExample(XnKindnessUnschoolExample example);

    int deleteByExample(XnKindnessUnschoolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnKindnessUnschool record);

    int insertSelective(XnKindnessUnschool record);

    List<XnKindnessUnschool> selectByExample(XnKindnessUnschoolExample example);

    XnKindnessUnschool selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnKindnessUnschool record, @Param("example") XnKindnessUnschoolExample example);

    int updateByExample(@Param("record") XnKindnessUnschool record, @Param("example") XnKindnessUnschoolExample example);

    int updateByPrimaryKeySelective(XnKindnessUnschool record);

    int updateByPrimaryKey(XnKindnessUnschool record);
}