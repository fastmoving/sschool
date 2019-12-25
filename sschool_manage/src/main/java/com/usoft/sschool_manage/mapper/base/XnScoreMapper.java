package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnScore;
import com.usoft.smartschool.pojo.XnScoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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