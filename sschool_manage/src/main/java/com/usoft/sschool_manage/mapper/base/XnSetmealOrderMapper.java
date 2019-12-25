package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnSetmealOrder;
import com.usoft.smartschool.pojo.XnSetmealOrderExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
     * 订单列表
     * @param studentName
     * @param phone
     * @param orderTimeBegin
     * @param orderTimeEnd
     * @param priceBegin
     * @param priceEnd
     * @param orderState
     * @param setMealId
     * @param ordernumber
     * @param schoolId
     * @return
     */
    List<Map<String,Object>> listXnSetmealOrder(@Param("studentName") String studentName, @Param("phone") String phone,
                                                @Param("orderTimeBegin") String orderTimeBegin, @Param("orderTimeEnd") String orderTimeEnd,
                                                @Param("priceBegin") String priceBegin, @Param("priceEnd") String priceEnd,@Param("orderState") Integer orderState,
                                                @Param("setMealId") Integer setMealId, @Param("ordernumber") String ordernumber,@Param("schoolId") Integer schoolId);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    Map<String,Object> selectXnSetmealOrder(Integer orderId);


    /**
     * 总的缴费订单数
     * @param schoolId
     * @return
     */
    int getTotalOrderNumber(int schoolId);

    /**
     * 总的缴费记录数
     * @param schoolId
     * @return
     */
    BigDecimal getTotalMoney(int schoolId);


    /**
     * 订单数统计(按天)
     */
    List<Map<String,Object>> getOrderNumberByDays(Integer schoolId);


    /**
     * 订单金额统计(按天)
     */
    List<Map<String,Object>> getOrderPriceByDays(Integer schoolId);

}