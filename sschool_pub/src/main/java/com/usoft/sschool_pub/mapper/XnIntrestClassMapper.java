package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnIntrestClass;
import com.usoft.smartschool.pojo.XnIntrestClassExample;
import com.usoft.smartschool.pojo.XnIntrestClassKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnIntrestClassMapper {
    int countByExample(XnIntrestClassExample example);

    int deleteByExample(XnIntrestClassExample example);

    int deleteByPrimaryKey(XnIntrestClassKey key);

    int insert(XnIntrestClass record);

    int insertSelective(XnIntrestClass record);

    List<XnIntrestClass> selectByExample(XnIntrestClassExample example);

    XnIntrestClass selectByPrimaryKey(XnIntrestClassKey key);

    int updateByExampleSelective(@Param("record") XnIntrestClass record, @Param("example") XnIntrestClassExample example);

    int updateByExample(@Param("record") XnIntrestClass record, @Param("example") XnIntrestClassExample example);

    int updateByPrimaryKeySelective(XnIntrestClass record);

    int updateByPrimaryKey(XnIntrestClass record);
}