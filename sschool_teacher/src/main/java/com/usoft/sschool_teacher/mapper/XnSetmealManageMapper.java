package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnSetmealManage;
import com.usoft.smartschool.pojo.XnSetmealManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnSetmealManageMapper {
    int countByExample(XnSetmealManageExample example);

    int deleteByExample(XnSetmealManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnSetmealManage record);

    int insertSelective(XnSetmealManage record);

    List<XnSetmealManage> selectByExample(XnSetmealManageExample example);

    XnSetmealManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnSetmealManage record, @Param("example") XnSetmealManageExample example);

    int updateByExample(@Param("record") XnSetmealManage record, @Param("example") XnSetmealManageExample example);

    int updateByPrimaryKeySelective(XnSetmealManage record);

    int updateByPrimaryKey(XnSetmealManage record);
}