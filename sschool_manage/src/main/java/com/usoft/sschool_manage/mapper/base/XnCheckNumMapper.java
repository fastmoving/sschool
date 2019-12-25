package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnCheckNum;
import com.usoft.smartschool.pojo.XnCheckNumExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnCheckNumMapper {
    int countByExample(XnCheckNumExample example);

    int deleteByExample(XnCheckNumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnCheckNum record);

    int insertSelective(XnCheckNum record);

    List<XnCheckNum> selectByExample(XnCheckNumExample example);

    XnCheckNum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnCheckNum record, @Param("example") XnCheckNumExample example);

    int updateByExample(@Param("record") XnCheckNum record, @Param("example") XnCheckNumExample example);

    int updateByPrimaryKeySelective(XnCheckNum record);

    int updateByPrimaryKey(XnCheckNum record);
    //总点击量
    Map<String, Object> totalNum(@Param("sid") Integer sid, @Param("videoType") Integer videoType);
    //直播点击排序
    List<Map<String,Object>> liveRank(@Param("time") String time,@Param("sid")Integer sid,@Param("videoType")Integer videoType);
    //按周统计
    List<Map<String,Object>> RankByWeek(@Param("start") String start, @Param("end") String end,@Param("videoType") Integer videoType,@Param("sid")Integer sid);
}