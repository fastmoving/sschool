package com.usoft.sschool_manage.mapper.base;

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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 订单列表
     * @param type  类型 1.现金 2.积分
     * @param userName 用户名
     * @param orderState 订单状态 1.待支付 2.已支付  3.已取消 4.已删除
     * @param buyTimeBegin 下单开始时间
     * @param buyTimeEnd 下单结束时间
     * @param priceBegin 订单金额大于等于
     * @param priceEnd 订单金额小于等于
     * @param phone 联系方式
     * @param orderNumber 订单号码
     * @return
     */
    List<Map<String,Object>> listGoodsOrder(@Param("type") Integer type, @Param("userName")String userName, @Param("orderState")Integer orderState,
                                            @Param("buyTimeBegin")String buyTimeBegin, @Param("buyTimeEnd")String buyTimeEnd, @Param("priceBegin")String priceBegin,
                                            @Param("priceEnd")String priceEnd, @Param("phone")String phone, @Param("orderNumber")String orderNumber,@Param("sid")String sid);


    /**
     * 商品订单统计，按照订单的状态
     * @param type 订单类型 1.现金 2.积分
     * @param beginTime 订单开始时间
     * @param endTime 订单结束时间
     * @return
     */
    List<Map<String,Object>> orderTypeCollection(@Param("type") Integer type,@Param("beginTime") String beginTime,@Param("endTime") String endTime);

    /**
     * 订单量年统计
     */
    List<Map<String,Object>> yearDoneCollection(@Param("beginYear") String beginYear, @Param("endYear") String endYear);

    /**
     * 订单月统计
     */
    List<Map<String,Object>> monthDoneCollection(@Param("year") String year);

    /**
     * 订单日统计
     */
    List<Map<String,Object>> dayDoneCollection(@Param("month") String month);

    /**
     * 订单时统计
     * @param day
     * @return
     */
    List<Map<String,Object>> hourDoneCollection(String day);


    /**
     * 总成功订单数和总价格
     * @param beginTime
     * @param endTime
     * @return
     */
    Map<String,Object> totalOrderAndAveragePrice(@Param("beginTime") String beginTime,@Param("endTime") String endTime);

    //Map<String,Object> averagePrice(@Param("beginTime") String beginTime,@Param("endTime") String endTime);
}