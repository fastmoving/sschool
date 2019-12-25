package com.usoft.sschool_teacher.mapper;


import com.usoft.smartschool.pojo.XnCarousel;
import com.usoft.smartschool.pojo.XnCarouselExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XnCarouselMapper {
    int countByExample(XnCarouselExample example);

    int deleteByExample(XnCarouselExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnCarousel record);

    int insertSelective(XnCarousel record);

    List<XnCarousel> selectByExample(XnCarouselExample example);

    XnCarousel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnCarousel record, @Param("example") XnCarouselExample example);

    int updateByExample(@Param("record") XnCarousel record, @Param("example") XnCarouselExample example);

    int updateByPrimaryKeySelective(XnCarousel record);

    int updateByPrimaryKey(XnCarousel record);
}