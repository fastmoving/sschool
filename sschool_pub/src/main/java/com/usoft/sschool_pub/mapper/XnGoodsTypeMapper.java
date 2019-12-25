package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnGoodsType;
import com.usoft.smartschool.pojo.XnGoodsTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnGoodsTypeMapper {
    int countByExample(XnGoodsTypeExample example);

    int deleteByExample(XnGoodsTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnGoodsType record);

    int insertSelective(XnGoodsType record);

    List<XnGoodsType> selectByExample(XnGoodsTypeExample example);

    XnGoodsType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnGoodsType record, @Param("example") XnGoodsTypeExample example);

    int updateByExample(@Param("record") XnGoodsType record, @Param("example") XnGoodsTypeExample example);

    int updateByPrimaryKeySelective(XnGoodsType record);

    int updateByPrimaryKey(XnGoodsType record);
}