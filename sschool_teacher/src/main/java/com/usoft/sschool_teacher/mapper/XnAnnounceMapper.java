package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnAnnounce;
import com.usoft.smartschool.pojo.XnAnnounceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnAnnounceMapper {
    int countByExample(XnAnnounceExample example);

    int deleteByExample(XnAnnounceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnAnnounce record);

    int insertSelective(XnAnnounce record);

    List<XnAnnounce> selectByExample(XnAnnounceExample example);

    XnAnnounce selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnAnnounce record, @Param("example") XnAnnounceExample example);

    int updateByExample(@Param("record") XnAnnounce record, @Param("example") XnAnnounceExample example);

    int updateByPrimaryKeySelective(XnAnnounce record);

    int updateByPrimaryKey(XnAnnounce record);
}