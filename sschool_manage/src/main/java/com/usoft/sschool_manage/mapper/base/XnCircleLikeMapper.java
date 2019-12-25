package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnCircleLike;
import com.usoft.smartschool.pojo.XnCircleLikeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnCircleLikeMapper {
    int countByExample(XnCircleLikeExample example);

    int deleteByExample(XnCircleLikeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnCircleLike record);

    int insertSelective(XnCircleLike record);

    List<XnCircleLike> selectByExample(XnCircleLikeExample example);

    XnCircleLike selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnCircleLike record, @Param("example") XnCircleLikeExample example);

    int updateByExample(@Param("record") XnCircleLike record, @Param("example") XnCircleLikeExample example);

    int updateByPrimaryKeySelective(XnCircleLike record);

    int updateByPrimaryKey(XnCircleLike record);
}