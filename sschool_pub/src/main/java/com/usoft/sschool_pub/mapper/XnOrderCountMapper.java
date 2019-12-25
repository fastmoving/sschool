package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.HlCounty;
import com.usoft.smartschool.pojo.XnOrderCount;
import com.usoft.smartschool.pojo.XnOrderCountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnOrderCountMapper {
    int countByExample(XnOrderCountExample example);

    int deleteByExample(XnOrderCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnOrderCount record);

    int insertSelective(XnOrderCount record);

    List<XnOrderCount> selectByExample(XnOrderCountExample example);

    XnOrderCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnOrderCount record, @Param("example") XnOrderCountExample example);

    int updateByExample(@Param("record") XnOrderCount record, @Param("example") XnOrderCountExample example);

    int updateByPrimaryKeySelective(XnOrderCount record);

    int updateByPrimaryKey(XnOrderCount record);
    //按月查询
    List<XnOrderCount> timecount(@Param("times")String times, @Param("witch")Integer witch, @Param("pagesize")Integer pagesize);
    //不分页按月查询
    List<XnOrderCount> countdownload(String times);
}