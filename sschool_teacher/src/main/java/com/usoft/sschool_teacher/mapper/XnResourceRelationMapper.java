package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnResourceRelation;
import com.usoft.smartschool.pojo.XnResourceRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnResourceRelationMapper {
    int countByExample(XnResourceRelationExample example);

    int deleteByExample(XnResourceRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnResourceRelation record);

    int insertSelective(XnResourceRelation record);

    List<XnResourceRelation> selectByExample(XnResourceRelationExample example);

    XnResourceRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnResourceRelation record, @Param("example") XnResourceRelationExample example);

    int updateByExample(@Param("record") XnResourceRelation record, @Param("example") XnResourceRelationExample example);

    int updateByPrimaryKeySelective(XnResourceRelation record);

    int updateByPrimaryKey(XnResourceRelation record);

    /**
     * 教师评论添加
     * @return
     */
    int insertTeacherComment(List<XnResourceRelation> xnResourceRelations);

    /**
     * 班主任 管理班级
     */
    List<Map> getClassManage(@Param("teacherId")int teacherId);
    List getClassManage2(Map key);

    /**
     * 获取教师评语
     */
    List<XnResourceRelation> getTeacherComment(Map key);

    /**
     * 判断是否是该班班主任
     *
     */
    List<XnResourceRelation> getTeacherClassIs(@Param("tId")Integer tId);
}