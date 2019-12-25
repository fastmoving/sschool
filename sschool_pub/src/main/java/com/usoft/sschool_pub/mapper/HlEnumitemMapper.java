package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.HlEnumitem;
import com.usoft.smartschool.pojo.HlEnumitemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HlEnumitemMapper {
    int countByExample(HlEnumitemExample example);

    int deleteByExample(HlEnumitemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HlEnumitem record);

    int insertSelective(HlEnumitem record);

    List<HlEnumitem> selectByExample(HlEnumitemExample example);

    HlEnumitem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HlEnumitem record, @Param("example") HlEnumitemExample example);

    int updateByExample(@Param("record") HlEnumitem record, @Param("example") HlEnumitemExample example);

    int updateByPrimaryKeySelective(HlEnumitem record);

    int updateByPrimaryKey(HlEnumitem record);
}