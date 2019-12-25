package com.usoft.sschool_manage.service.order.Impl;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnGoodsOrderMapper;
import com.usoft.sschool_manage.service.order.XnGoodsOrderCollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/5/21 14:44
 */
@Service
public class XnGoodsOrderCollectionServiceImpl implements XnGoodsOrderCollectionService {

    @Resource
    private XnGoodsOrderMapper xnGoodsOrderMapper;

    @Override
    public MyResult TotalOrderAndAveragePrice(String beginTime, String endTime) {
        Map<String,Object> collection = xnGoodsOrderMapper.totalOrderAndAveragePrice(beginTime, endTime);
        if(ObjectUtil.isEmpty(collection))return MyResult.failure("暂无数据");

        Long number = (Long)collection.get("num");
        BigDecimal price = (BigDecimal)collection.get("price");
        if(number == null || price == null){
            return MyResult.failure("暂无数据");
        }

        BigDecimal averagePrice = price.divide(new BigDecimal(number),2,BigDecimal.ROUND_HALF_EVEN);
        //price.divide()

        Map<String,Object> result = new HashMap<>();
        result.put("number",number);
        result.put("averagePrice",averagePrice);

        return MyResult.success(result);
    }

    @Override
    public MyResult OrderTypeCollection(Integer type,String beginTime, String endTime) {
        if(ObjectUtil.isEmpty(type))return MyResult.failure("请选择要查看的类型");
        List<Map<String,Object>> collection = xnGoodsOrderMapper.orderTypeCollection(type,beginTime,endTime);
        if(ObjectUtil.isEmpty(collection))return MyResult.failure("暂无数据");
        int size = collection.size();
        String[] state = new String[size];
        long[] number = new long[size];
        for(int i=0; i<size; i++){
            Map<String,Object> map = collection.get(i);
            state[i] = (String)map.get("state");
            number[i] = (long)map.get("total");
        }
        Map<String,Object> result = new HashMap<>();
        result.put("state",state);
        result.put("number",number);
        return MyResult.success(result);
    }

    @Override
    public MyResult YearDoneCollection(String beginYear, String endYear) {
        List<Map<String,Object>> yearCollection = xnGoodsOrderMapper.yearDoneCollection(beginYear,endYear);
        if(ObjectUtil.isEmpty(yearCollection))return MyResult.failure("暂无数据");
        int size = yearCollection.size();
        String[] year = new String[size];
        long[] number = new long[size];
        String by = (String)yearCollection.get(0).get("buyTimes");
        String ey = (String)yearCollection.get(size-1).get("buyTimes");
        for(int i=0;i<size; i++){
            Map<String,Object> map = yearCollection.get(i);
            year[i] = (String)map.get("buyTimes")+"年";
            number[i] = (long)map.get("number");
        }

        Map<String,Object> result = new HashMap<>();
        result.put("times",year);
        result.put("number",number);
        String title = by+"-"+ey+"年订单成交量统计";
        result.put("title",title);
        return MyResult.success(result);
    }

    @Override
    public MyResult MonthDoneCollection(String year) {
        if(ObjectUtil.isEmpty(year))return MyResult.failure("请选择要查看月的年份");
        List<Map<String,Object>> monthCollection = xnGoodsOrderMapper.monthDoneCollection(year);
        if(ObjectUtil.isEmpty(monthCollection))return MyResult.failure("暂无数据");
        int size = monthCollection.size();
        String[] month = new String[size];
        long[] number = new long[size];
        String by = (String)monthCollection.get(0).get("buyTimes");
        String ey = (String)monthCollection.get(size-1).get("buyTimes");
        for(int i=0;i<size; i++){
            Map<String,Object> map = monthCollection.get(i);
            month[i] = (String)map.get("buyTimes")+"月";
            number[i] = (long)map.get("number");
        }

        Map<String,Object> result = new HashMap<>();
        result.put("times",month);
        result.put("number",number);
        String title = year+"年全年订单成交量统计";
        result.put("title",title);
        return MyResult.success(result);
    }

    @Override
    public MyResult DaysDoneCollection(String month) {
        if(ObjectUtil.isEmpty(month))return MyResult.failure("请选择要查看天的月份");
        List<Map<String,Object>> daythCollection = xnGoodsOrderMapper.dayDoneCollection(month);
        if(ObjectUtil.isEmpty(daythCollection))return MyResult.failure("暂无数据");
        int size = daythCollection.size();
        String[] day = new String[size];
        long[] number = new long[size];
        String by = (String)daythCollection.get(0).get("buyTimes");
        String ey = (String)daythCollection.get(size-1).get("buyTimes");
        for(int i=0;i<size; i++){
            Map<String,Object> map = daythCollection.get(i);
            day[i] = (String)map.get("buyTimes");
            number[i] = (long)map.get("number");
        }

        Map<String,Object> result = new HashMap<>();
        result.put("times",day);
        result.put("number",number);
        String title = month+"订单成交量统计";
        result.put("title",title);
        return MyResult.success(result);
    }

    @Override
    public MyResult hourDoneCollection(String day) {
        if(ObjectUtil.isEmpty(day))return MyResult.failure("请选择要查看时的日期");
        List<Map<String,Object>> hourCollection = xnGoodsOrderMapper.hourDoneCollection(day);
        if(ObjectUtil.isEmpty(hourCollection))return MyResult.failure("暂无数据");
        int size = hourCollection.size();
        String[] hour = new String[size];
        long[] number = new long[size];
        for(int i=0;i<size; i++){
            Map<String,Object> map = hourCollection.get(i);
            hour[i] = (String)map.get("buyTimes");
            number[i] = (long)map.get("number");
        }

        Map<String,Object> result = new HashMap<>();
        result.put("times",hour);
        result.put("number",number);
        String title = day+"订单成交量统计";
        result.put("title",title);
        return MyResult.success(result);
    }


}
