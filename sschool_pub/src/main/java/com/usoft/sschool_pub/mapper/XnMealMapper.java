package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnMeal;
import com.usoft.smartschool.pojo.XnMealExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnMealMapper {
    int countByExample(XnMealExample example);

    int deleteByExample(XnMealExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnMeal record);

    int insertSelective(XnMeal record);

    List<XnMeal> selectByExample(XnMealExample example);

    XnMeal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnMeal record, @Param("example") XnMealExample example);

    int updateByExample(@Param("record") XnMeal record, @Param("example") XnMealExample example);

    int updateByPrimaryKeySelective(XnMeal record);

    int updateByPrimaryKey(XnMeal record);
}