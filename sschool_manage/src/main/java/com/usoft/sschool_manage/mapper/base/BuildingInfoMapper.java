package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.BuildingInfo;
import com.usoft.smartschool.pojo.BuildingInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BuildingInfoMapper {
    int countByExample(BuildingInfoExample example);

    int deleteByExample(BuildingInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BuildingInfo record);

    int insertSelective(BuildingInfo record);

    List<BuildingInfo> selectByExample(BuildingInfoExample example);

    BuildingInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BuildingInfo record, @Param("example") BuildingInfoExample example);

    int updateByExample(@Param("record") BuildingInfo record, @Param("example") BuildingInfoExample example);

    int updateByPrimaryKeySelective(BuildingInfo record);

    int updateByPrimaryKey(BuildingInfo record);
}