package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnClassChat;
import com.usoft.smartschool.pojo.XnClassChatExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnClassChatMapper {
    int countByExample(XnClassChatExample example);

    int deleteByExample(XnClassChatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnClassChat record);

    int insertSelective(XnClassChat record);

    List<XnClassChat> selectByExample(XnClassChatExample example);

    XnClassChat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnClassChat record, @Param("example") XnClassChatExample example);

    int updateByExample(@Param("record") XnClassChat record, @Param("example") XnClassChatExample example);

    int updateByPrimaryKeySelective(XnClassChat record);

    int updateByPrimaryKey(XnClassChat record);
}