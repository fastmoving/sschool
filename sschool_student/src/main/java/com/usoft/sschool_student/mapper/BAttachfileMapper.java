package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.BAttachfile;
import com.usoft.smartschool.pojo.BAttachfileExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BAttachfileMapper {
    int countByExample(BAttachfileExample example);

    int deleteByExample(BAttachfileExample example);

    int deleteByPrimaryKey(Integer attfileId);

    int insert(BAttachfile record);

    int insertSelective(BAttachfile record);

    List<BAttachfile> selectByExample(BAttachfileExample example);

    BAttachfile selectByPrimaryKey(Integer attfileId);

    int updateByExampleSelective(@Param("record") BAttachfile record, @Param("example") BAttachfileExample example);

    int updateByExample(@Param("record") BAttachfile record, @Param("example") BAttachfileExample example);

    int updateByPrimaryKeySelective(BAttachfile record);

    int updateByPrimaryKey(BAttachfile record);
}