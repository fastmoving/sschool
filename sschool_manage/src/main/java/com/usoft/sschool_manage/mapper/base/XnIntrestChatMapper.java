package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnIntrestChat;
import com.usoft.smartschool.pojo.XnIntrestChatExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnIntrestChatMapper {
    int countByExample(XnIntrestChatExample example);

    int deleteByExample(XnIntrestChatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnIntrestChat record);

    int insertSelective(XnIntrestChat record);

    List<XnIntrestChat> selectByExample(XnIntrestChatExample example);

    XnIntrestChat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnIntrestChat record, @Param("example") XnIntrestChatExample example);

    int updateByExample(@Param("record") XnIntrestChat record, @Param("example") XnIntrestChatExample example);

    int updateByPrimaryKeySelective(XnIntrestChat record);

    int updateByPrimaryKey(XnIntrestChat record);
}