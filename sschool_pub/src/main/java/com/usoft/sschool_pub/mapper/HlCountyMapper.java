package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.HlCounty;
import com.usoft.smartschool.pojo.HlCountyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HlCountyMapper {
    int countByExample(HlCountyExample example);

    int deleteByExample(HlCountyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HlCounty record);

    int insertSelective(HlCounty record);

    List<HlCounty> selectByExample(HlCountyExample example);

    HlCounty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HlCounty record, @Param("example") HlCountyExample example);

    int updateByExample(@Param("record") HlCounty record, @Param("example") HlCountyExample example);

    int updateByPrimaryKeySelective(HlCounty record);

    int updateByPrimaryKey(HlCounty record);
}