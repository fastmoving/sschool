package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnSchoolIntroduce;
import com.usoft.smartschool.pojo.XnSchoolIntroduceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnSchoolIntroduceMapper {
    int countByExample(XnSchoolIntroduceExample example);

    int deleteByExample(XnSchoolIntroduceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnSchoolIntroduce record);

    int insertSelective(XnSchoolIntroduce record);

    List<XnSchoolIntroduce> selectByExample(XnSchoolIntroduceExample example);

    XnSchoolIntroduce selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnSchoolIntroduce record, @Param("example") XnSchoolIntroduceExample example);

    int updateByExample(@Param("record") XnSchoolIntroduce record, @Param("example") XnSchoolIntroduceExample example);

    int updateByPrimaryKeySelective(XnSchoolIntroduce record);

    int updateByPrimaryKey(XnSchoolIntroduce record);
}