package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnClassCircle;
import com.usoft.smartschool.pojo.XnClassCircleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnClassCircleMapper {
    int countByExample(XnClassCircleExample example);

    int deleteByExample(XnClassCircleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnClassCircle record);

    int insertSelective(XnClassCircle record);

    List<XnClassCircle> selectByExample(XnClassCircleExample example);

    XnClassCircle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnClassCircle record, @Param("example") XnClassCircleExample example);

    int updateByExample(@Param("record") XnClassCircle record, @Param("example") XnClassCircleExample example);

    int updateByPrimaryKeySelective(XnClassCircle record);

    int updateByPrimaryKey(XnClassCircle record);

    /**
     * 获取班级圈审核信息
     */
    List<Map> getClassCircle(Map key);
    List<Map> getClassCircle1(Map key);
    Integer getClassCircle1Count(Map key);
    Map getPhoto(Map key);
    int getClassCircleCount(@Param("classId")int classId);

    /**
     * 审批班级圈
     */
    int updateCircle(Map key);

    /**
     * 班级圈详情
     */
    List<Map> getClassCircleIfo(Map key);
}