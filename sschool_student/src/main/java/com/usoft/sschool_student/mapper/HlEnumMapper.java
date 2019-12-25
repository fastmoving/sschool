package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.HlEnum;
import com.usoft.smartschool.pojo.HlEnumExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HlEnumMapper {
    int countByExample(HlEnumExample example);

    int deleteByExample(HlEnumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HlEnum record);

    int insertSelective(HlEnum record);

    List<HlEnum> selectByExample(HlEnumExample example);

    HlEnum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HlEnum record, @Param("example") HlEnumExample example);

    int updateByExample(@Param("record") HlEnum record, @Param("example") HlEnumExample example);

    int updateByPrimaryKeySelective(HlEnum record);

    int updateByPrimaryKey(HlEnum record);
}