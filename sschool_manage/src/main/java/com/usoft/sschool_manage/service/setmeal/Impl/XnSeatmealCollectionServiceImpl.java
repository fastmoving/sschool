package com.usoft.sschool_manage.service.setmeal.Impl;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnMailOrderMapper;
import com.usoft.sschool_manage.mapper.base.XnSetmealOrderMapper;
import com.usoft.sschool_manage.mapper.base.XnStuFamilyinfoMapper;
import com.usoft.sschool_manage.service.setmeal.XnSeatmealCollectionService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/5/15 14:01
 */
@Service
public class XnSeatmealCollectionServiceImpl implements XnSeatmealCollectionService {

    @Resource
    private XnSetmealOrderMapper xnSetmealOrderMapper;


    @Resource
    private XnStuFamilyinfoMapper xnStuFamilyinfoMapper;

    @Resource
    private XnMailOrderMapper xnMailOrderMapper;



    @Override
    public MyResult totalCollection() {
        Integer schoolId = SystemParam.getSchoolId();
        Map<String,Object> map = new HashMap<>();
        Integer totalBindingMan = xnStuFamilyinfoMapper.getTotalPerson(schoolId);

//        Integer totalOrderNumber = xnSetmealOrderMapper.getTotalOrderNumber(schoolId);
//        BigDecimal totalMoney = xnSetmealOrderMapper.getTotalMoney(schoolId);

        Integer totalOrderNumber = xnMailOrderMapper.getTotalOrderNumber(schoolId);
        BigDecimal totalMoney = xnMailOrderMapper.getTotalMoney(schoolId);

        map.put("bindMan", totalBindingMan==null ? 0: totalBindingMan); //总的绑定人数
        map.put("orderNumber",totalOrderNumber == null ? 0 : totalOrderNumber);//总的缴费订单数
        map.put("orderMoney",totalMoney == null ? 0 : totalMoney);//总的缴费金额
        return MyResult.success(map);
    }

    @Override
    public MyResult roleBindingCollection() {
        Integer schoolId = SystemParam.getSchoolId();
        List<Map<String,Object>> result = xnStuFamilyinfoMapper.getBindRole(schoolId);
        if(ObjectUtil.isEmpty(result))return MyResult.failure("暂无数据");
        int size = result.size();
        String[] relation = new String[size];
        long[] number = new long[size];
        for(int i=0; i<size; i++){
            relation[i] = (String)result.get(i).get("familyRelate");
            number[i] = (long)result.get(i).get("number");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("relation",relation);
        map.put("number",number);
        return MyResult.success(map);
    }

    @Override
    public MyResult orderNumberCollection() {
        Integer schoolId = SystemParam.getSchoolId();

//        List<Map<String,Object>> result = xnSetmealOrderMapper.getOrderNumberByDays(schoolId);

        List<Map<String,Object>> result = xnMailOrderMapper.getOrderNumberByDays(schoolId);

        if(ObjectUtil.isEmpty(result))return MyResult.failure("暂无数据");
        int size = result.size();
        String[] days = new String[size];
        long[] number = new long[size];
        for(int i=0; i<size; i++){
            days[i] = (String)result.get(i).get("days");
            number[i] = (long)result.get(i).get("number");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("days",days);
        map.put("number",number);
        return MyResult.success(map);
        //return MyResult.success(result);
    }

    @Override
    public MyResult orderPriceCollection() {
        Integer schoolId = SystemParam.getSchoolId();

//        List<Map<String,Object>> result = xnSetmealOrderMapper.getOrderPriceByDays(schoolId);

        List<Map<String,Object>> result = xnMailOrderMapper.getOrderPriceByDays(schoolId);

        if(ObjectUtil.isEmpty(result))return MyResult.failure("暂无数据");
        int size = result.size();
        String[] days = new String[size];
        BigDecimal[] price = new BigDecimal[size];
        for(int i=0; i<size; i++){
            days[i] = (String)result.get(i).get("days");
            price[i] = (BigDecimal)result.get(i).get("price");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("days",days);
        map.put("price",price);
        return MyResult.success(map);
    }
}
