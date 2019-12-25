package com.usoft.sschool_pub.util;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author jijh
 * @Date 2019/4/24 17:21
 */
public class ResultPage {


    /**
     * 返回分页数据
     * @param data
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static MyResult getPageResult(List<?> data, Integer pageNo, Integer pageSize){
        if(ObjectUtil.isEmpty(data)) return MyResult.failure("暂无数据");
        if(pageNo == null ) pageNo =1;
        if(pageSize == null) pageSize = 6;
        //总的数量
        Integer totalNumber = data.size();

        Integer pages = totalNumber/pageSize;
        //总页数
        Integer totalPages = totalNumber%pageSize ==0 ? pages : pages +1;

        List<Object> returnData = new ArrayList<>();
        if(pageNo <= totalPages){
            for(int i=(pageNo-1)*pageSize; i<pageNo*pageSize && i<totalNumber; i++){
                returnData.add(data.get(i));
            }
        }else{
            return MyResult.failure("没有更多了");
        }
        return MyResult.success("查询成功",returnData,pageNo,totalPages,pageSize,totalNumber);
    }

}
