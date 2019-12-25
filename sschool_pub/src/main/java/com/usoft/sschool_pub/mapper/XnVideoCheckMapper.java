package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnVideoCheck;
import com.usoft.smartschool.pojo.XnVideoCheckExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnVideoCheckMapper {
    int countByExample(XnVideoCheckExample example);

    int deleteByExample(XnVideoCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnVideoCheck record);

    int insertSelective(XnVideoCheck record);

    List<XnVideoCheck> selectByExample(XnVideoCheckExample example);

    XnVideoCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnVideoCheck record, @Param("example") XnVideoCheckExample example);

    int updateByExample(@Param("record") XnVideoCheck record, @Param("example") XnVideoCheckExample example);

    int updateByPrimaryKeySelective(XnVideoCheck record);

    int updateByPrimaryKey(XnVideoCheck record);
}