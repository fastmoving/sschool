package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.PrgFile;
import com.usoft.smartschool.pojo.PrgFileExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrgFileMapper {
    int countByExample(PrgFileExample example);

    int deleteByExample(PrgFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PrgFile record);

    int insertSelective(PrgFile record);

    List<PrgFile> selectByExample(PrgFileExample example);

    PrgFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PrgFile record, @Param("example") PrgFileExample example);

    int updateByExample(@Param("record") PrgFile record, @Param("example") PrgFileExample example);

    int updateByPrimaryKeySelective(PrgFile record);

    int updateByPrimaryKey(PrgFile record);
}