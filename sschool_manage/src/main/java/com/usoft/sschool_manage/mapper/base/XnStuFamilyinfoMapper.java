package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnStuFamilyinfo;
import com.usoft.smartschool.pojo.XnStuFamilyinfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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

    /**
     * 绑定总人数
     * @param schoolId
     * @return
     */
    int getTotalPerson(Integer schoolId);


    List<Map<String,Object>> getBindRole(Integer schoolId);
}