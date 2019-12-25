package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnVideoRule;
import com.usoft.smartschool.pojo.XnVideoRuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnVideoRuleMapper {
    int countByExample(XnVideoRuleExample example);

    int deleteByExample(XnVideoRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnVideoRule record);

    int insertSelective(XnVideoRule record);

    List<XnVideoRule> selectByExample(XnVideoRuleExample example);

    XnVideoRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnVideoRule record, @Param("example") XnVideoRuleExample example);

    int updateByExample(@Param("record") XnVideoRule record, @Param("example") XnVideoRuleExample example);

    int updateByPrimaryKeySelective(XnVideoRule record);

    int updateByPrimaryKey(XnVideoRule record);
}