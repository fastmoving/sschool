package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnStuHomework;
import com.usoft.smartschool.pojo.XnStuHomeworkExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnStuHomeworkMapper {
    int countByExample(XnStuHomeworkExample example);

    int deleteByExample(XnStuHomeworkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnStuHomework record);

    int insertSelective(XnStuHomework record);

    List<XnStuHomework> selectByExample(XnStuHomeworkExample example);

    /**
     * 查询学生未做作业 并倒序排序
     * @return
     */
    List<XnStuHomework> selectByXnNotHomework(XnStuHomeworkExample example);

    XnStuHomework selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnStuHomework record, @Param("example") XnStuHomeworkExample example);

    int updateByExample(@Param("record") XnStuHomework record, @Param("example") XnStuHomeworkExample example);

    int updateByPrimaryKeySelective(XnStuHomework record);

    int updateByPrimaryKey(XnStuHomework record);
}