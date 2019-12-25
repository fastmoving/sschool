package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnFineclassOrder;
import com.usoft.smartschool.pojo.XnFineclassOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnFineclassOrderMapper {
    int countByExample(XnFineclassOrderExample example);

    int deleteByExample(XnFineclassOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnFineclassOrder record);

    int insertSelective(XnFineclassOrder record);

    List<XnFineclassOrder> selectByExample(XnFineclassOrderExample example);

    XnFineclassOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnFineclassOrder record, @Param("example") XnFineclassOrderExample example);

    int updateByExample(@Param("record") XnFineclassOrder record, @Param("example") XnFineclassOrderExample example);

    int updateByPrimaryKeySelective(XnFineclassOrder record);

    int updateByPrimaryKey(XnFineclassOrder record);
}