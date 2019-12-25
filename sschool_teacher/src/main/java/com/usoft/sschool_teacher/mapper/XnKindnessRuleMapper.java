package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnKindnessRule;
import com.usoft.smartschool.pojo.XnKindnessRuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnKindnessRuleMapper {
    int countByExample(XnKindnessRuleExample example);

    int deleteByExample(XnKindnessRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnKindnessRule record);

    int insertSelective(XnKindnessRule record);

    List<XnKindnessRule> selectByExample(XnKindnessRuleExample example);

    XnKindnessRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnKindnessRule record, @Param("example") XnKindnessRuleExample example);

    int updateByExample(@Param("record") XnKindnessRule record, @Param("example") XnKindnessRuleExample example);

    int updateByPrimaryKeySelective(XnKindnessRule record);

    int updateByPrimaryKey(XnKindnessRule record);
}