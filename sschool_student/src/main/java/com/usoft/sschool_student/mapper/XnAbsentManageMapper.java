package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnAbsentManage;
import com.usoft.smartschool.pojo.XnAbsentManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnAbsentManageMapper {
    int countByExample(XnAbsentManageExample example);

    int deleteByExample(XnAbsentManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnAbsentManage record);

    int insertSelective(XnAbsentManage record);

    List<XnAbsentManage> selectByExample(XnAbsentManageExample example);

    XnAbsentManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnAbsentManage record, @Param("example") XnAbsentManageExample example);

    int updateByExample(@Param("record") XnAbsentManage record, @Param("example") XnAbsentManageExample example);

    int updateByPrimaryKeySelective(XnAbsentManage record);

    int updateByPrimaryKey(XnAbsentManage record);
    //按月查询请假记录
    List<XnAbsentManage> getMyAbsent(@Param("record") XnAbsentManage record,@Param("timess") String timess);
}