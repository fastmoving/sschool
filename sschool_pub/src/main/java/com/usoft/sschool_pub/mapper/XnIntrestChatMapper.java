package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnIntrestChat;
import com.usoft.smartschool.pojo.XnIntrestChatExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    //家长查询兴趣班聊天记录
    List<XnIntrestChat> selectCommunicate(@Param("sid")Integer sid, @Param("intrestid")Integer intrestid, @Param("userid")Integer userid, @Param("aplyId")Integer aplyId);
    //教师查询聊天记录列表
    List<Map<String, Object>>teaMsgList(@Param("sid")Integer sid, @Param("attr1")String attr1);
    //老师查询兴趣班与单个家长聊天记录
    List<XnIntrestChat> teaSearchChat(@Param("sid")Integer sid, @Param("intrestid")Integer intrestid, @Param("userid")Integer userid, @Param("aplyId")Integer aplyId);
}