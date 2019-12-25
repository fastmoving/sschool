package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.HlSchclass;
import com.usoft.smartschool.pojo.HlSchclassExample;
import com.usoft.smartschool.pojo.HlSchclassKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HlSchclassMapper {
    int countByExample(HlSchclassExample example);

    int deleteByExample(HlSchclassExample example);

    int deleteByPrimaryKey(HlSchclassKey key);

    int insert(HlSchclass record);

    int insertSelective(HlSchclass record);

    List<HlSchclass> selectByExample(HlSchclassExample example);

    HlSchclass selectByPrimaryKey(HlSchclassKey key);

    int updateByExampleSelective(@Param("record") HlSchclass record, @Param("example") HlSchclassExample example);

    int updateByExample(@Param("record") HlSchclass record, @Param("example") HlSchclassExample example);

    int updateByPrimaryKeySelective(HlSchclass record);

    int updateByPrimaryKey(HlSchclass record);
}