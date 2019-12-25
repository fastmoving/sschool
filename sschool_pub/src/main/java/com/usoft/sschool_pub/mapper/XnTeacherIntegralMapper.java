package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnTeacherIntegral;
import com.usoft.smartschool.pojo.XnTeacherIntegralExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnTeacherIntegralMapper {
    int countByExample(XnTeacherIntegralExample example);

    int deleteByExample(XnTeacherIntegralExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnTeacherIntegral record);

    int insertSelective(XnTeacherIntegral record);

    List<XnTeacherIntegral> selectByExample(XnTeacherIntegralExample example);

    XnTeacherIntegral selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnTeacherIntegral record, @Param("example") XnTeacherIntegralExample example);

    int updateByExample(@Param("record") XnTeacherIntegral record, @Param("example") XnTeacherIntegralExample example);

    int updateByPrimaryKeySelective(XnTeacherIntegral record);

    int updateByPrimaryKey(XnTeacherIntegral record);

    Integer totleScore(@Param("sid")Integer sid,@Param("tid")Integer tid);
}