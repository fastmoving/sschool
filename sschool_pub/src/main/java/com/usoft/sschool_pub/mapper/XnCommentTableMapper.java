package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnCommentTable;
import com.usoft.smartschool.pojo.XnCommentTableExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnCommentTableMapper {
    int countByExample(XnCommentTableExample example);

    int deleteByExample(XnCommentTableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnCommentTable record);

    int insertSelective(XnCommentTable record);

    List<XnCommentTable> selectByExample(XnCommentTableExample example);

    XnCommentTable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnCommentTable record, @Param("example") XnCommentTableExample example);

    int updateByExample(@Param("record") XnCommentTable record, @Param("example") XnCommentTableExample example);

    int updateByPrimaryKeySelective(XnCommentTable record);

    int updateByPrimaryKey(XnCommentTable record);
}