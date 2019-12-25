package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnMailOrder;
import com.usoft.smartschool.pojo.XnMailOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnMailOrderMapper {
    int countByExample(XnMailOrderExample example);

    int deleteByExample(XnMailOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnMailOrder record);

    int insertSelective(XnMailOrder record);

    List<XnMailOrder> selectByExample(XnMailOrderExample example);

    XnMailOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnMailOrder record, @Param("example") XnMailOrderExample example);

    int updateByExample(@Param("record") XnMailOrder record, @Param("example") XnMailOrderExample example);

    int updateByPrimaryKeySelective(XnMailOrder record);

    int updateByPrimaryKey(XnMailOrder record);
}