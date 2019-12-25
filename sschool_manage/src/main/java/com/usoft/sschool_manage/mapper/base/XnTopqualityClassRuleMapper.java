package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnTopqualityClassRule;
import com.usoft.smartschool.pojo.XnTopqualityClassRuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnTopqualityClassRuleMapper {
    int countByExample(XnTopqualityClassRuleExample example);

    int deleteByExample(XnTopqualityClassRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnTopqualityClassRule record);

    int insertSelective(XnTopqualityClassRule record);

    List<XnTopqualityClassRule> selectByExample(XnTopqualityClassRuleExample example);

    XnTopqualityClassRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnTopqualityClassRule record, @Param("example") XnTopqualityClassRuleExample example);

    int updateByExample(@Param("record") XnTopqualityClassRule record, @Param("example") XnTopqualityClassRuleExample example);

    int updateByPrimaryKeySelective(XnTopqualityClassRule record);

    int updateByPrimaryKey(XnTopqualityClassRule record);
}