package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.HlSchoolsevip;
import com.usoft.smartschool.pojo.HlSchoolsevipExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HlSchoolsevipMapper {
    int countByExample(HlSchoolsevipExample example);

    int deleteByExample(HlSchoolsevipExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(HlSchoolsevip record);

    int insertSelective(HlSchoolsevip record);

    List<HlSchoolsevip> selectByExample(HlSchoolsevipExample example);

    HlSchoolsevip selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") HlSchoolsevip record, @Param("example") HlSchoolsevipExample example);

    int updateByExample(@Param("record") HlSchoolsevip record, @Param("example") HlSchoolsevipExample example);

    int updateByPrimaryKeySelective(HlSchoolsevip record);

    int updateByPrimaryKey(HlSchoolsevip record);
}