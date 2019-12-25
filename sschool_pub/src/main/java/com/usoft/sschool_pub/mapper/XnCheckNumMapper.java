package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnCheckNum;
import com.usoft.smartschool.pojo.XnCheckNumExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnCheckNumMapper {
    int countByExample(XnCheckNumExample example);

    int deleteByExample(XnCheckNumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnCheckNum record);

    int insertSelective(XnCheckNum record);

    List<XnCheckNum> selectByExample(XnCheckNumExample example);

    XnCheckNum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnCheckNum record, @Param("example") XnCheckNumExample example);

    int updateByExample(@Param("record") XnCheckNum record, @Param("example") XnCheckNumExample example);

    int updateByPrimaryKeySelective(XnCheckNum record);

    int updateByPrimaryKey(XnCheckNum record);
}