package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.CfUser;
import com.usoft.smartschool.pojo.CfUserExample;
import com.usoft.smartschool.pojo.CfUserKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CfUserMapper {
    int countByExample(CfUserExample example);

    int deleteByExample(CfUserExample example);

    int deleteByPrimaryKey(CfUserKey key);

    int insert(CfUser record);

    int insertSelective(CfUser record);

    List<CfUser> selectByExample(CfUserExample example);

    CfUser selectByPrimaryKey(CfUserKey key);

    int updateByExampleSelective(@Param("record") CfUser record, @Param("example") CfUserExample example);

    int updateByExample(@Param("record") CfUser record, @Param("example") CfUserExample example);

    int updateByPrimaryKeySelective(CfUser record);

    int updateByPrimaryKey(CfUser record);
}