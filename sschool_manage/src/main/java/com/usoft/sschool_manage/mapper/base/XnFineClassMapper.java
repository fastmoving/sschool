package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnFineClass;
import com.usoft.smartschool.pojo.XnFineClassExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnFineClassMapper {
    int countByExample(XnFineClassExample example);

    int deleteByExample(XnFineClassExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnFineClass record);

    int insertSelective(XnFineClass record);

    List<XnFineClass> selectByExample(XnFineClassExample example);

    XnFineClass selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnFineClass record, @Param("example") XnFineClassExample example);

    int updateByExample(@Param("record") XnFineClass record, @Param("example") XnFineClassExample example);

    int updateByPrimaryKeySelective(XnFineClass record);

    int updateByPrimaryKey(XnFineClass record);
}