package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnIntrestEntry;
import com.usoft.smartschool.pojo.XnIntrestEntryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnIntrestEntryMapper {
    int countByExample(XnIntrestEntryExample example);

    int deleteByExample(XnIntrestEntryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnIntrestEntry record);

    int insertSelective(XnIntrestEntry record);

    List<XnIntrestEntry> selectByExample(XnIntrestEntryExample example);

    XnIntrestEntry selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnIntrestEntry record, @Param("example") XnIntrestEntryExample example);

    int updateByExample(@Param("record") XnIntrestEntry record, @Param("example") XnIntrestEntryExample example);

    int updateByPrimaryKeySelective(XnIntrestEntry record);

    int updateByPrimaryKey(XnIntrestEntry record);
}