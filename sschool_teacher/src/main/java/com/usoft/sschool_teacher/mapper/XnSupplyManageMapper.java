package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnSupplyManage;
import com.usoft.smartschool.pojo.XnSupplyManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnSupplyManageMapper {
    int countByExample(XnSupplyManageExample example);

    int deleteByExample(XnSupplyManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnSupplyManage record);

    int insertSelective(XnSupplyManage record);

    List<XnSupplyManage> selectByExample(XnSupplyManageExample example);

    XnSupplyManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnSupplyManage record, @Param("example") XnSupplyManageExample example);

    int updateByExample(@Param("record") XnSupplyManage record, @Param("example") XnSupplyManageExample example);

    int updateByPrimaryKeySelective(XnSupplyManage record);

    int updateByPrimaryKey(XnSupplyManage record);
}