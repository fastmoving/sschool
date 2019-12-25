package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnFood;
import com.usoft.smartschool.pojo.XnFoodExample;
import com.usoft.smartschool.pojo.XnFoodKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnFoodMapper {
    int countByExample(XnFoodExample example);

    int deleteByExample(XnFoodExample example);

    int deleteByPrimaryKey(XnFoodKey key);

    int insert(XnFood record);

    int insertSelective(XnFood record);

    List<XnFood> selectByExample(XnFoodExample example);

    XnFood selectByPrimaryKey(XnFoodKey key);

    int updateByExampleSelective(@Param("record") XnFood record, @Param("example") XnFoodExample example);

    int updateByExample(@Param("record") XnFood record, @Param("example") XnFoodExample example);

    int updateByPrimaryKeySelective(XnFood record);

    int updateByPrimaryKey(XnFood record);
}