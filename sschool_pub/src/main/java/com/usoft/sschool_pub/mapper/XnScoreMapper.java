package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnScore;
import com.usoft.smartschool.pojo.XnScoreExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnScoreMapper {
    int countByExample(XnScoreExample example);

    int deleteByExample(XnScoreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnScore record);

    int insertSelective(XnScore record);

    List<XnScore> selectByExample(XnScoreExample example);

    XnScore selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnScore record, @Param("example") XnScoreExample example);

    int updateByExample(@Param("record") XnScore record, @Param("example") XnScoreExample example);

    int updateByPrimaryKeySelective(XnScore record);

    int updateByPrimaryKey(XnScore record);
}