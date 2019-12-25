package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.PrgInfo;
import com.usoft.smartschool.pojo.PrgInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrgInfoMapper {
    int countByExample(PrgInfoExample example);

    int deleteByExample(PrgInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrgInfo record);

    int insertSelective(PrgInfo record);

    List<PrgInfo> selectByExample(PrgInfoExample example);

    PrgInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PrgInfo record, @Param("example") PrgInfoExample example);

    int updateByExample(@Param("record") PrgInfo record, @Param("example") PrgInfoExample example);

    int updateByPrimaryKeySelective(PrgInfo record);

    int updateByPrimaryKey(PrgInfo record);
}