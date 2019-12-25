package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnAttention;
import com.usoft.smartschool.pojo.XnAttentionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnAttentionMapper {
    int countByExample(XnAttentionExample example);

    int deleteByExample(XnAttentionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(XnAttention record);

    int insertSelective(XnAttention record);

    List<XnAttention> selectByExample(XnAttentionExample example);

    XnAttention selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") XnAttention record, @Param("example") XnAttentionExample example);

    int updateByExample(@Param("record") XnAttention record, @Param("example") XnAttentionExample example);

    int updateByPrimaryKeySelective(XnAttention record);

    int updateByPrimaryKey(XnAttention record);
}