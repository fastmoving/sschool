package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnStuFamilyinfo;
import com.usoft.smartschool.pojo.XnStuFamilyinfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnStuFamilyinfoMapper {
    int countByExample(XnStuFamilyinfoExample example);

    int deleteByExample(XnStuFamilyinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnStuFamilyinfo record);

    int insertSelective(XnStuFamilyinfo record);

    List<XnStuFamilyinfo> selectByExample(XnStuFamilyinfoExample example);

    XnStuFamilyinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnStuFamilyinfo record, @Param("example") XnStuFamilyinfoExample example);

    int updateByExample(@Param("record") XnStuFamilyinfo record, @Param("example") XnStuFamilyinfoExample example);

    int updateByPrimaryKeySelective(XnStuFamilyinfo record);

    int updateByPrimaryKey(XnStuFamilyinfo record);
}