package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnUserRecord;
import com.usoft.smartschool.pojo.XnUserRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnUserRecordMapper {
    int countByExample(XnUserRecordExample example);

    int deleteByExample(XnUserRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnUserRecord record);

    int insertSelective(XnUserRecord record);

    List<XnUserRecord> selectByExample(XnUserRecordExample example);

    XnUserRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnUserRecord record, @Param("example") XnUserRecordExample example);

    int updateByExample(@Param("record") XnUserRecord record, @Param("example") XnUserRecordExample example);

    int updateByPrimaryKeySelective(XnUserRecord record);

    int updateByPrimaryKey(XnUserRecord record);
}