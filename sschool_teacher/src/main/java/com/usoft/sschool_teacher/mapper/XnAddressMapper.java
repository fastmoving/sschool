package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnAddress;
import com.usoft.smartschool.pojo.XnAddressExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnAddressMapper {
    int countByExample(XnAddressExample example);

    int deleteByExample(XnAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnAddress record);

    int insertSelective(XnAddress record);

    List<XnAddress> selectByExample(XnAddressExample example);

    XnAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnAddress record, @Param("example") XnAddressExample example);

    int updateByExample(@Param("record") XnAddress record, @Param("example") XnAddressExample example);

    int updateByPrimaryKeySelective(XnAddress record);

    int updateByPrimaryKey(XnAddress record);
    /**
     * 查询默认地址
     */
     List<XnAddress> getIsDefault(@Param("teacherId")int teacherId);

    /**
     * 地址管理
     */
    List<XnAddress> getAddresses(Map key);
    int getAddressesCount(Map key);

    /**
     * 删除地址
     */
    int removeAddress(@Param("addresses")String[] addresses);
}