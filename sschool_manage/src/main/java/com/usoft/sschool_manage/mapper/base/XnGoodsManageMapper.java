package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnGoodsManage;
import com.usoft.smartschool.pojo.XnGoodsManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XnGoodsManageMapper {
    int countByExample(XnGoodsManageExample example);

    int deleteByExample(XnGoodsManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnGoodsManage record);

    int insertSelective(XnGoodsManage record);

    List<XnGoodsManage> selectByExample(XnGoodsManageExample example);

    XnGoodsManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnGoodsManage record, @Param("example") XnGoodsManageExample example);

    int updateByExample(@Param("record") XnGoodsManage record, @Param("example") XnGoodsManageExample example);

    int updateByPrimaryKeySelective(XnGoodsManage record);

    int updateByPrimaryKey(XnGoodsManage record);
}