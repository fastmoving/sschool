package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnHomeworkTitle;
import com.usoft.smartschool.pojo.XnHomeworkTitleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnHomeworkTitleMapper {
    int countByExample(XnHomeworkTitleExample example);

    int deleteByExample(XnHomeworkTitleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnHomeworkTitle record);

    int insertSelective(XnHomeworkTitle record);

    List<XnHomeworkTitle> selectByExample(XnHomeworkTitleExample example);

    XnHomeworkTitle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnHomeworkTitle record, @Param("example") XnHomeworkTitleExample example);

    int updateByExample(@Param("record") XnHomeworkTitle record, @Param("example") XnHomeworkTitleExample example);

    int updateByPrimaryKeySelective(XnHomeworkTitle record);

    int updateByPrimaryKey(XnHomeworkTitle record);

    /**
     * 批量存入选择
     */
    int insertEs(List<XnHomeworkTitle> xnHomeworkTitles);
}