package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnMessageCenter;
import com.usoft.smartschool.pojo.XnMessageCenterExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}