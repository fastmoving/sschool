package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.CfDepartment;
import com.usoft.smartschool.pojo.CfDepartmentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CfDepartmentMapper {
    int countByExample(CfDepartmentExample example);

    int deleteByExample(CfDepartmentExample example);

    int deleteByPrimaryKey(Integer deptid);

    int insert(CfDepartment record);

    int insertSelective(CfDepartment record);

    List<CfDepartment> selectByExampleWithBLOBs(CfDepartmentExample example);

    List<CfDepartment> selectByExample(CfDepartmentExample example);

    CfDepartment selectByPrimaryKey(Integer deptid);

    int updateByExampleSelective(@Param("record") CfDepartment record, @Param("example") CfDepartmentExample example);

    int updateByExampleWithBLOBs(@Param("record") CfDepartment record, @Param("example") CfDepartmentExample example);

    int updateByExample(@Param("record") CfDepartment record, @Param("example") CfDepartmentExample example);

    int updateByPrimaryKeySelective(CfDepartment record);

    int updateByPrimaryKeyWithBLOBs(CfDepartment record);

    int updateByPrimaryKey(CfDepartment record);
}