package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnVideoRulePay;
import com.usoft.smartschool.pojo.XnVideoRulePayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XnVideoRulePayMapper {
    int countByExample(XnVideoRulePayExample example);

    int deleteByExample(XnVideoRulePayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnVideoRulePay record);

    int insertSelective(XnVideoRulePay record);

    List<XnVideoRulePay> selectByExample(XnVideoRulePayExample example);

    XnVideoRulePay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnVideoRulePay record, @Param("example") XnVideoRulePayExample example);

    int updateByExample(@Param("record") XnVideoRulePay record, @Param("example") XnVideoRulePayExample example);

    int updateByPrimaryKeySelective(XnVideoRulePay record);

    int updateByPrimaryKey(XnVideoRulePay record);
}