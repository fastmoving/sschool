package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnTopqualityPersonalRule;
import com.usoft.smartschool.pojo.XnTopqualityPersonalRuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnTopqualityPersonalRuleMapper {
    int countByExample(XnTopqualityPersonalRuleExample example);

    int deleteByExample(XnTopqualityPersonalRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnTopqualityPersonalRule record);

    int insertSelective(XnTopqualityPersonalRule record);

    List<XnTopqualityPersonalRule> selectByExample(XnTopqualityPersonalRuleExample example);

    XnTopqualityPersonalRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnTopqualityPersonalRule record, @Param("example") XnTopqualityPersonalRuleExample example);

    int updateByExample(@Param("record") XnTopqualityPersonalRule record, @Param("example") XnTopqualityPersonalRuleExample example);

    int updateByPrimaryKeySelective(XnTopqualityPersonalRule record);

    int updateByPrimaryKey(XnTopqualityPersonalRule record);
    //所有学校
    List<Map<String,Object>> sid();
    //所有科目
    List<Map<String,Object>> subject();
    //学校科目
    List<Map<String,Object>> schoolsubject(Integer sid);
    //所有老师
    List<Map<String,Object>> tid();
    //学校老师
    List<Map<String,Object>> schooltid(Integer sid);
    //科目老师
    List<Map<String,Object>> subtid(String subject);
    //学校科目老师
    List<Map<String,Object>> schoolSubTid(@Param("sid") Integer sid,@Param("subject") String subject);
}