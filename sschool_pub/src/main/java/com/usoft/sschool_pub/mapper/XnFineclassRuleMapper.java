package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnFineclassRule;
import com.usoft.smartschool.pojo.XnFineclassRuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnFineclassRuleMapper {
    int countByExample(XnFineclassRuleExample example);

    int deleteByExample(XnFineclassRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnFineclassRule record);

    int insertSelective(XnFineclassRule record);

    List<XnFineclassRule> selectByExample(XnFineclassRuleExample example);

    XnFineclassRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnFineclassRule record, @Param("example") XnFineclassRuleExample example);

    int updateByExample(@Param("record") XnFineclassRule record, @Param("example") XnFineclassRuleExample example);

    int updateByPrimaryKeySelective(XnFineclassRule record);

    int updateByPrimaryKey(XnFineclassRule record);
}