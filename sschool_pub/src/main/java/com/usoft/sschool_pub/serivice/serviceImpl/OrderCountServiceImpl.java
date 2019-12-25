package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.HlCounty;
import com.usoft.smartschool.pojo.XnOrderCount;
import com.usoft.smartschool.pojo.XnOrderCountExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.*;
import com.usoft.sschool_pub.serivice.OrderCountService;
import com.usoft.sschool_pub.util.ExportExcelUtil;
import com.usoft.sschool_pub.util.ResultPage;
import com.usoft.sschool_pub.util.TimeUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

@Service
public class OrderCountServiceImpl implements OrderCountService {
    @Resource
    private XnOrderCountMapper xnOrderCountMapper;

    private static final String[] str={"编号","县/区","乡镇","学校","年级","班级","用户名",
            "用户类型","手机号","付款金额","付款方式","具体物品","物品分类","购买时间",};
    /**
     * 查询所有统计
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult count(String time,Integer pageNo,Integer pageSize) {
        if (pageSize==null){
            pageSize=10;
        }
        List list=new ArrayList();
        if(time==null || "null".equals(time) || "time".equals(time)){
            XnOrderCountExample example=new XnOrderCountExample();
            example.createCriteria();
            example.setOrderByClause("create_time desc");
            List<XnOrderCount> xnOrderCounts = xnOrderCountMapper.selectByExample(example);
            if (ObjectUtil.isEmpty(xnOrderCounts))return MyResult.failure("没有数据");
            for (XnOrderCount x:xnOrderCounts){
                Map<String, String> stringMap = countInfo(x);
                list.add(stringMap);
            }
        }else {
            if (pageNo==null){
                pageNo=1;
            }
            String str=time+"%";
            Integer witch=(pageNo-1)*pageSize;
            List<XnOrderCount> timecount = xnOrderCountMapper.timecount(str, witch, pageSize);
            for (XnOrderCount x:timecount){
                Map<String, String> stringMap = countInfo(x);
                list.add(stringMap);
            }
        }
        return ResultPage.getPageResult(list,pageNo,pageSize);
    }

    @Override
    public void downloadExcel(String time,HttpServletRequest request, HttpServletResponse response) {
        /*XnOrderCountExample example=new XnOrderCountExample();
        example.createCriteria();*/
        if(time==null || "null".equals(time) || "time".equals(time)){
            Date date=new Date();
            String s= TimeUtil.TimeToYYYYMMDDHHMMSS(date);
            time=s.substring(0,7);
            System.out.println(time);
        }
        List<XnOrderCount> xnOrderCounts = xnOrderCountMapper.countdownload(time+"%");
        //excel文件名
        String fileName = "OrderCount"+System.currentTimeMillis()+".xlsx";
        //sheet名
        String sheetName = "OrderCount";
        String[][] value= new String[xnOrderCounts.size()][str.length];;
        for (int i=0;i<xnOrderCounts.size();i++){
            XnOrderCount xoc=xnOrderCounts.get(i);
            value[i][0] =xoc.getId().toString();
            value[i][1] =xoc.getCountyId();
            value[i][2] =xoc.getTownsId();
            value[i][3] =xoc.getSchoolId();
            value[i][4] =xoc.getAttr1();
            value[i][5] =xoc.getClassId();
            value[i][6] =xoc.getUserid();
            value[i][7] =xoc.getUsertype().toString();
            value[i][8] =xoc.getPhone();
            value[i][9] =xoc.getPrice().toString();
            value[i][10] =xoc.getPayWay().toString();
            value[i][11] =xoc.getProduct();
            value[i][12] =xoc.getProductSys();
            value[i][13] =TimeUtil.TimeToYYYYMMDDHHMMSS(xoc.getCreateTime());
        }
        //数据流输出
        ExportExcelUtil.getHSSFWorkbook(sheetName,fileName,str,value,null,request,response);
    }

    public Map<String,String> countInfo(XnOrderCount xoc){
        Map<String,String> map=new HashMap<>();
        map.put("id",xoc.getId().toString());
        map.put("county_id",xoc.getCountyId());
        map.put("towns_id",xoc.getTownsId());
        map.put("school_id",xoc.getSchoolId());
        map.put("class_id",xoc.getClassId());
        map.put("userid",xoc.getUserid());
        if (xoc.getUsertype()==1){
            map.put("userType","学生");
        }else {
            map.put("userType","老师");
        }
        map.put("phone",xoc.getPhone());
        map.put("price",xoc.getPrice().toString());
        if (xoc.getPayWay()==1){
            map.put("pay_way","微信");
        }else {
            map.put("pay_way","支付宝");
        }
        map.put("product",xoc.getProduct());
        if (xoc.getProductSys().equals("1")){
            map.put("product_sys","商品");
        }
        if (xoc.getProductSys().equals("2")){
            map.put("product_sys","套餐");
        }
        if (xoc.getProductSys().equals("3")){
            map.put("product_sys","视频权限");
        }
        if (xoc.getProductSys().equals("4")){
            map.put("product_sys","兴趣班");
        }
        if (xoc.getProductSys().equals("5")){
            map.put("product_sys","名师讲堂视频");
        }
        map.put("create_time", TimeUtil.TimeToYYYYMMDDHHMMSS(xoc.getCreateTime()));
        map.put("gradeId",xoc.getAttr1());
        return map;
    }
}
