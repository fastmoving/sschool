package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnResourceRelation;
import com.usoft.smartschool.pojo.XnResourceRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}