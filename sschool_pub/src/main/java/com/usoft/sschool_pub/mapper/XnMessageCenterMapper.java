package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnMessageCenter;
import com.usoft.smartschool.pojo.XnMessageCenterExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnMessageCenterMapper {
    int countByExample(XnMessageCenterExample example);

    int deleteByExample(XnMessageCenterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnMessageCenter record);

    int insertSelective(XnMessageCenter record);

    List<XnMessageCenter> selectByExample(XnMessageCenterExample example);

    XnMessageCenter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnMessageCenter record, @Param("example") XnMessageCenterExample example);

    int updateByExample(@Param("record") XnMessageCenter record, @Param("example") XnMessageCenterExample example);

    int updateByPrimaryKeySelective(XnMessageCenter record);

    int updateByPrimaryKey(XnMessageCenter record);

    List<Map<String,Object>>  teaMsg(@Param("userid")Integer userid, @Param("attr2")String attr2);

    List<Map<String,Object>> msgList(@Param("usertype") Integer usertype,@Param("userid")Integer userid, @Param("attr2")String attr2);

    List<Map<String,Object>> isReadList(@Param("usertype") Integer usertype,@Param("userid")Integer userid, @Param("attr2")String attr2);

    List<Map<String,Object>> myList(@Param("usertype") Integer usertype,@Param("userid")Integer userid, @Param("attr2")String attr2);

    List<XnMessageCenter> lst(@Param("usertype") Integer usertype,@Param("userid")Integer userid, @Param("attr2") Integer attr2);

    List<XnMessageCenter> mylst(@Param("usertype") Integer usertype,@Param("userid")Integer userid, @Param("attr2") Integer attr2);

}