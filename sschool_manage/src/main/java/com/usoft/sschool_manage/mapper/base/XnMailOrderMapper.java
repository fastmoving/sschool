package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnMailOrder;
import com.usoft.smartschool.pojo.XnMailOrderExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
    List<Map<String,Object>> listXnMailOrder( @Param("studentName") String studentName, @Param("phone") String phone,
                                             @Param("orderTimeBegin") String orderTimeBegin, @Param("orderTimeEnd") String orderTimeEnd,
                                             @Param("priceBegin") String priceBegin, @Param("priceEnd") String priceEnd,
                                             @Param("setMealId") Integer setMealId, @Param("ordernumber") String ordernumber,
                                              @Param("orderState") Integer orderState,@Param("schoolId") Integer schoolId);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    Map<String,Object> selectXnMailOrder(Integer orderId);

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