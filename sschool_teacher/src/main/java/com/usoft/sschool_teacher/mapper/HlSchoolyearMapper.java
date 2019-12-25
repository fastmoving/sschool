package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.HlSchoolyear;
import com.usoft.smartschool.pojo.HlSchoolyearExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HlSchoolyearMapper {
    int countByExample(HlSchoolyearExample example);

    int deleteByExample(HlSchoolyearExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HlSchoolyear record);

    int insertSelective(HlSchoolyear record);

    List<HlSchoolyear> selectByExample(HlSchoolyearExample example);

    HlSchoolyear selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HlSchoolyear record, @Param("example") HlSchoolyearExample example);

    int updateByExample(@Param("record") HlSchoolyear record, @Param("example") HlSchoolyearExample example);

    int updateByPrimaryKeySelective(HlSchoolyear record);

    int updateByPrimaryKey(HlSchoolyear record);
}