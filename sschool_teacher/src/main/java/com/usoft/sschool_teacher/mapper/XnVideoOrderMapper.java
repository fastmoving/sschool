package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnVideoOrder;
import com.usoft.smartschool.pojo.XnVideoOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnVideoOrderMapper {
    int countByExample(XnVideoOrderExample example);

    int deleteByExample(XnVideoOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnVideoOrder record);

    int insertSelective(XnVideoOrder record);

    List<XnVideoOrder> selectByExample(XnVideoOrderExample example);

    XnVideoOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnVideoOrder record, @Param("example") XnVideoOrderExample example);

    int updateByExample(@Param("record") XnVideoOrder record, @Param("example") XnVideoOrderExample example);

    int updateByPrimaryKeySelective(XnVideoOrder record);

    int updateByPrimaryKey(XnVideoOrder record);

    //已购课程
    List<? extends Map> getTeacherVideo(Map Key);
    int getTeacherVideoCount(@Param("teacherId")int teacherId);
}