package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.HlVideomanagement;
import com.usoft.smartschool.pojo.HlVideomanagementExample;
import com.usoft.smartschool.pojo.HlVideomanagementKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HlVideomanagementMapper {
    int countByExample(HlVideomanagementExample example);

    int deleteByExample(HlVideomanagementExample example);

    int deleteByPrimaryKey(HlVideomanagementKey key);

    int insert(HlVideomanagement record);

    int insertSelective(HlVideomanagement record);

    List<HlVideomanagement> selectByExample(HlVideomanagementExample example);

    HlVideomanagement selectByPrimaryKey(HlVideomanagementKey key);

    int updateByExampleSelective(@Param("record") HlVideomanagement record, @Param("example") HlVideomanagementExample example);

    int updateByExample(@Param("record") HlVideomanagement record, @Param("example") HlVideomanagementExample example);

    int updateByPrimaryKeySelective(HlVideomanagement record);

    int updateByPrimaryKey(HlVideomanagement record);
}