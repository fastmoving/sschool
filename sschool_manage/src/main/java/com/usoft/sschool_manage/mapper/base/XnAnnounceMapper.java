package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnAnnounce;
import com.usoft.smartschool.pojo.XnAnnounceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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