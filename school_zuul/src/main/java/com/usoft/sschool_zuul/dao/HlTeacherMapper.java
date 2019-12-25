package com.usoft.sschool_zuul.dao;


import com.usoft.sschool_zuul.entity.HlTeacher;
import com.usoft.sschool_zuul.entity.HlTeacherExample;
import com.usoft.sschool_zuul.entity.HlTeacherKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HlTeacherMapper {
    int countByExample(HlTeacherExample example);

    int deleteByExample(HlTeacherExample example);

    int deleteByPrimaryKey(HlTeacherKey key);

    int insert(HlTeacher record);

    int insertSelective(HlTeacher record);

    List<HlTeacher> selectByExample(HlTeacherExample example);

    HlTeacher selectByPrimaryKey(HlTeacherKey key);

    int updateByExampleSelective(@Param("record") HlTeacher record, @Param("example") HlTeacherExample example);

    int updateByExample(@Param("record") HlTeacher record, @Param("example") HlTeacherExample example);

    int updateByPrimaryKeySelective(HlTeacher record);

    int updateByPrimaryKey(HlTeacher record);

    List getMyselfManager(Map key);
}