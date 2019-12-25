package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.ClassroomInfo;
import com.usoft.smartschool.pojo.ClassroomInfoExample;
import com.usoft.smartschool.pojo.ClassroomInfoKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassroomInfoMapper {
    int countByExample(ClassroomInfoExample example);

    int deleteByExample(ClassroomInfoExample example);

    int deleteByPrimaryKey(ClassroomInfoKey key);

    int insert(ClassroomInfo record);

    int insertSelective(ClassroomInfo record);

    List<ClassroomInfo> selectByExample(ClassroomInfoExample example);

    ClassroomInfo selectByPrimaryKey(ClassroomInfoKey key);

    int updateByExampleSelective(@Param("record") ClassroomInfo record, @Param("example") ClassroomInfoExample example);

    int updateByExample(@Param("record") ClassroomInfo record, @Param("example") ClassroomInfoExample example);

    int updateByPrimaryKeySelective(ClassroomInfo record);

    int updateByPrimaryKey(ClassroomInfo record);
}