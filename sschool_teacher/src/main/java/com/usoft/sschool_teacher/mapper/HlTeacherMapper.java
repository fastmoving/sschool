package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.HlTeacher;
import com.usoft.smartschool.pojo.HlTeacherExample;
import com.usoft.smartschool.pojo.HlTeacherKey;
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
    /**
     * 查询教师ID
     */
    List<Map> getTeacherIfo(@Param("teacherId")int teacherId);

    /**
     * 查找校长
     */
    List<HlTeacher> getSchoolManager(@Param("schoolId")Integer schoolId);

    /**
     * 验证当前身份
     * @param key
     * @return
     */
    List<HlTeacher> getMyselfManager(Map key);
}