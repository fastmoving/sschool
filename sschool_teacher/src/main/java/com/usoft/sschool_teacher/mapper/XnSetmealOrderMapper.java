package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnSetmealOrder;
import com.usoft.smartschool.pojo.XnSetmealOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnSetmealOrderMapper {
    int countByExample(XnSetmealOrderExample example);

    int deleteByExample(XnSetmealOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnSetmealOrder record);

    int insertSelective(XnSetmealOrder record);

    List<XnSetmealOrder> selectByExample(XnSetmealOrderExample example);

    XnSetmealOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnSetmealOrder record, @Param("example") XnSetmealOrderExample example);

    int updateByExample(@Param("record") XnSetmealOrder record, @Param("example") XnSetmealOrderExample example);

    int updateByPrimaryKeySelective(XnSetmealOrder record);

    int updateByPrimaryKey(XnSetmealOrder record);

    /**
     * 订阅套餐
     */
    List<Map> listXnSetmealOrder(Map key);
    Integer listXnSetmealOrderCount(Map key);
}