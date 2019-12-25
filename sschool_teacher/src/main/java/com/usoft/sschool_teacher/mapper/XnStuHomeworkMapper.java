package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnStuHomework;
import com.usoft.smartschool.pojo.XnStuHomeworkExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnStuHomeworkMapper {
    int countByExample(XnStuHomeworkExample example);

    int deleteByExample(XnStuHomeworkExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnStuHomework record);

    int insertSelective(XnStuHomework record);

    List<XnStuHomework> selectByExample(XnStuHomeworkExample example);

    List<XnStuHomework> selectByPrimaryKey(Map<String,Object> mapKey);

    int updateByExampleSelective(@Param("record") XnStuHomework record, @Param("example") XnStuHomeworkExample example);

    int updateByExample(@Param("record") XnStuHomework record, @Param("example") XnStuHomeworkExample example);

    int updateByPrimaryKeySelective(XnStuHomework record);

    int updateByPrimaryKey(XnStuHomework record);

    /**
     * 批量导入作业
     * @param xnStuHomeworks
     * @return
     */
    int insertStuHomeworkEs(List<XnStuHomework> xnStuHomeworks);
    int updateStuHwm(Map key);

    /**
     * 作业管理
     * @param key 条件
     * @return
     */
    List<Map<String,Object>> getStuHomeworkEs(Map<String,Object> key);
    int getStuHomeworkEsCount(Map<String,Object> key);
}