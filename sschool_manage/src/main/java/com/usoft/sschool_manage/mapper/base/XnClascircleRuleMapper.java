package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnClascircleRule;
import com.usoft.smartschool.pojo.XnClascircleRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XnClascircleRuleMapper {
    int countByExample(XnClascircleRuleExample example);

    int deleteByExample(XnClascircleRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnClascircleRule record);

    int insertSelective(XnClascircleRule record);

    List<XnClascircleRule> selectByExample(XnClascircleRuleExample example);

    XnClascircleRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnClascircleRule record, @Param("example") XnClascircleRuleExample example);

    int updateByExample(@Param("record") XnClascircleRule record, @Param("example") XnClascircleRuleExample example);

    int updateByPrimaryKeySelective(XnClascircleRule record);

    int updateByPrimaryKey(XnClascircleRule record);
}