package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.HlVideoinfonew;
import com.usoft.smartschool.pojo.HlVideoinfonewExample;
import com.usoft.smartschool.pojo.HlVideoinfonewKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HlVideoinfonewMapper {
    int countByExample(HlVideoinfonewExample example);

    int deleteByExample(HlVideoinfonewExample example);

    int deleteByPrimaryKey(HlVideoinfonewKey key);

    int insert(HlVideoinfonew record);

    int insertSelective(HlVideoinfonew record);

    List<HlVideoinfonew> selectByExample(HlVideoinfonewExample example);

    HlVideoinfonew selectByPrimaryKey(HlVideoinfonewKey key);

    int updateByExampleSelective(@Param("record") HlVideoinfonew record, @Param("example") HlVideoinfonewExample example);

    int updateByExample(@Param("record") HlVideoinfonew record, @Param("example") HlVideoinfonewExample example);

    int updateByPrimaryKeySelective(HlVideoinfonew record);

    int updateByPrimaryKey(HlVideoinfonew record);
}