package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnTeaApperance;
import com.usoft.smartschool.pojo.XnTeaApperanceExample;
import com.usoft.smartschool.pojo.XnTeaApperanceKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XnTeaApperanceMapper {
    int countByExample(XnTeaApperanceExample example);

    int deleteByExample(XnTeaApperanceExample example);

    int deleteByPrimaryKey(XnTeaApperanceKey key);

    int insert(XnTeaApperance record);

    int insertSelective(XnTeaApperance record);

    List<XnTeaApperance> selectByExample(XnTeaApperanceExample example);

    XnTeaApperance selectByPrimaryKey(XnTeaApperanceKey key);

    int updateByExampleSelective(@Param("record") XnTeaApperance record, @Param("example") XnTeaApperanceExample example);

    int updateByExample(@Param("record") XnTeaApperance record, @Param("example") XnTeaApperanceExample example);

    int updateByPrimaryKeySelective(XnTeaApperance record);

    int updateByPrimaryKey(XnTeaApperance record);
}