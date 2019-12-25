package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnTopqualityPersonalRule;
import com.usoft.smartschool.pojo.XnTopqualityPersonalRuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnTopqualityPersonalRuleMapper {
    int countByExample(XnTopqualityPersonalRuleExample example);

    int deleteByExample(XnTopqualityPersonalRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnTopqualityPersonalRule record);

    int insertSelective(XnTopqualityPersonalRule record);

    List<XnTopqualityPersonalRule> selectByExample(XnTopqualityPersonalRuleExample example);

    XnTopqualityPersonalRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnTopqualityPersonalRule record, @Param("example") XnTopqualityPersonalRuleExample example);

    int updateByExample(@Param("record") XnTopqualityPersonalRule record, @Param("example") XnTopqualityPersonalRuleExample example);

    int updateByPrimaryKeySelective(XnTopqualityPersonalRule record);

    int updateByPrimaryKey(XnTopqualityPersonalRule record);
}