package com.usoft.sschool_student.mapper;

import com.usoft.smartschool.pojo.XnScore;
import com.usoft.smartschool.pojo.XnScoreExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnScoreMapper {
    int countByExample(XnScoreExample example);

    int deleteByExample(XnScoreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnScore record);

    int insertSelective(XnScore record);

    List<XnScore> selectByExample(XnScoreExample example);

    XnScore selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnScore record, @Param("example") XnScoreExample example);

    int updateByExample(@Param("record") XnScore record, @Param("example") XnScoreExample example);

    int updateByPrimaryKeySelective(XnScore record);

    int updateByPrimaryKey(XnScore record);
    //班级最高分
    Map<String,Object> classScore(@Param("term") String term, @Param("testname") String testname, @Param("cid") Integer cid);
    //班级平均分
    Map<String,Object> avgScore(@Param("term") String term,@Param("testname") String testname,@Param("cid") Integer cid);
    //查询学期
    List<Map<String,Object>> term(@Param("sid")Integer sid,@Param("cid")Integer cid);
    //查询考试名
    List<Map<String,Object>> TestName(@Param("sid")Integer sid,@Param("cid")Integer cid,@Param("term")String term);
}