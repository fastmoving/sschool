package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.HlCurriculumset;
import com.usoft.smartschool.pojo.HlCurriculumsetExample;
import com.usoft.smartschool.pojo.HlCurriculumsetKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HlCurriculumsetMapper {
    int countByExample(HlCurriculumsetExample example);

    int deleteByExample(HlCurriculumsetExample example);

    int deleteByPrimaryKey(HlCurriculumsetKey key);

    int insert(HlCurriculumset record);

    int insertSelective(HlCurriculumset record);

    List<HlCurriculumset> selectByExample(HlCurriculumsetExample example);

    HlCurriculumset selectByPrimaryKey(HlCurriculumsetKey key);

    int updateByExampleSelective(@Param("record") HlCurriculumset record, @Param("example") HlCurriculumsetExample example);

    int updateByExample(@Param("record") HlCurriculumset record, @Param("example") HlCurriculumsetExample example);

    int updateByPrimaryKeySelective(HlCurriculumset record);

    int updateByPrimaryKey(HlCurriculumset record);
}