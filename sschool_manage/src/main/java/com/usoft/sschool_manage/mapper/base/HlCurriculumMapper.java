package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.HlCurriculum;
import com.usoft.smartschool.pojo.HlCurriculumExample;
import com.usoft.smartschool.pojo.HlCurriculumKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HlCurriculumMapper {
    int countByExample(HlCurriculumExample example);

    int deleteByExample(HlCurriculumExample example);

    int deleteByPrimaryKey(HlCurriculumKey key);

    int insert(HlCurriculum record);

    int insertSelective(HlCurriculum record);

    List<HlCurriculum> selectByExample(HlCurriculumExample example);

    HlCurriculum selectByPrimaryKey(HlCurriculumKey key);

    int updateByExampleSelective(@Param("record") HlCurriculum record, @Param("example") HlCurriculumExample example);

    int updateByExample(@Param("record") HlCurriculum record, @Param("example") HlCurriculumExample example);

    int updateByPrimaryKeySelective(HlCurriculum record);

    int updateByPrimaryKey(HlCurriculum record);
}