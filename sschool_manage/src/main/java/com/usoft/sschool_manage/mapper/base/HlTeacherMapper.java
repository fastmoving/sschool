package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.HlTeacher;
import com.usoft.smartschool.pojo.HlTeacherExample;
import com.usoft.smartschool.pojo.HlTeacherKey;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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