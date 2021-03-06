package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.XnClassAlbum;
import com.usoft.smartschool.pojo.XnClassAlbumExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnClassAlbumMapper {
    int countByExample(XnClassAlbumExample example);

    int deleteByExample(XnClassAlbumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnClassAlbum record);

    int insertSelective(XnClassAlbum record);

    List<XnClassAlbum> selectByExample(XnClassAlbumExample example);

    XnClassAlbum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnClassAlbum record, @Param("example") XnClassAlbumExample example);

    int updateByExample(@Param("record") XnClassAlbum record, @Param("example") XnClassAlbumExample example);

    int updateByPrimaryKeySelective(XnClassAlbum record);

    int updateByPrimaryKey(XnClassAlbum record);

    List<Map<String,Object>> group(@Param("sid")Integer sid,@Param("cid")Integer cid);
}