package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnGoodsOrder;
import com.usoft.smartschool.pojo.XnGoodsOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnGoodsOrderMapper {
    int countByExample(XnGoodsOrderExample example);

    int deleteByExample(XnGoodsOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnGoodsOrder record);

    int insertSelective(XnGoodsOrder record);

    List<XnGoodsOrder> selectByExample(XnGoodsOrderExample example);

    XnGoodsOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnGoodsOrder record, @Param("example") XnGoodsOrderExample example);

    int updateByExample(@Param("record") XnGoodsOrder record, @Param("example") XnGoodsOrderExample example);

    int updateByPrimaryKeySelective(XnGoodsOrder record);

    int updateByPrimaryKey(XnGoodsOrder record);

    /**
     * 我的订单
     * @param key
     * @return
     */
    List<Map> getMyOrder(Map key);
    Integer getMyOrderCount(Map key);

    /**
     * 订单详情
     */
    List<Map> getOrderIfo(Map key);
}