package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnClassCircle;
import com.usoft.smartschool.pojo.XnClassCircleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnClassCircleMapper {
    int countByExample(XnClassCircleExample example);

    int deleteByExample(XnClassCircleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnClassCircle record);

    int insertSelective(XnClassCircle record);

    List<XnClassCircle> selectByExample(XnClassCircleExample example);

    XnClassCircle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnClassCircle record, @Param("example") XnClassCircleExample example);

    int updateByExample(@Param("record") XnClassCircle record, @Param("example") XnClassCircleExample example);

    int updateByPrimaryKeySelective(XnClassCircle record);

    int updateByPrimaryKey(XnClassCircle record);
}