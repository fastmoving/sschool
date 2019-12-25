package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnAbsentManage;
import com.usoft.smartschool.pojo.XnAbsentManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnAbsentManageMapper {
    int countByExample(XnAbsentManageExample example);

    int deleteByExample(XnAbsentManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnAbsentManage record);

    int insertSelective(XnAbsentManage record);

    List<XnAbsentManage> selectByExample(XnAbsentManageExample example);

    XnAbsentManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnAbsentManage record, @Param("example") XnAbsentManageExample example);

    int updateByExample(@Param("record") XnAbsentManage record, @Param("example") XnAbsentManageExample example);

    int updateByPrimaryKeySelective(XnAbsentManage record);

    int updateByPrimaryKey(XnAbsentManage record);
}