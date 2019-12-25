package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.FloorInfo;
import com.usoft.smartschool.pojo.FloorInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FloorInfoMapper {
    int countByExample(FloorInfoExample example);

    int deleteByExample(FloorInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FloorInfo record);

    int insertSelective(FloorInfo record);

    List<FloorInfo> selectByExample(FloorInfoExample example);

    FloorInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FloorInfo record, @Param("example") FloorInfoExample example);

    int updateByExample(@Param("record") FloorInfo record, @Param("example") FloorInfoExample example);

    int updateByPrimaryKeySelective(FloorInfo record);

    int updateByPrimaryKey(FloorInfo record);
}