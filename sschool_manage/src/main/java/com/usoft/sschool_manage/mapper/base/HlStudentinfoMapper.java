package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.HlStudentinfo;
import com.usoft.smartschool.pojo.HlStudentinfoExample;
import com.usoft.smartschool.pojo.HlStudentinfoKey;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HlStudentinfoMapper {
    int countByExample(HlStudentinfoExample example);

    int deleteByExample(HlStudentinfoExample example);

    int deleteByPrimaryKey(HlStudentinfoKey key);

    int insert(HlStudentinfo record);

    int insertSelective(HlStudentinfo record);

    List<HlStudentinfo> selectByExample(HlStudentinfoExample example);

    HlStudentinfo selectByPrimaryKey(HlStudentinfoKey key);

    int updateByExampleSelective(@Param("record") HlStudentinfo record, @Param("example") HlStudentinfoExample example);

    int updateByExample(@Param("record") HlStudentinfo record, @Param("example") HlStudentinfoExample example);

    int updateByPrimaryKeySelective(HlStudentinfo record);

    int updateByPrimaryKey(HlStudentinfo record);

    /**
     *
     * @param cid
     * @param name
     * @param schoolId
     * @return
     */
    List<Map<String,Object>> findByStudentName(@Param("cid") Integer cid ,@Param("name") String name,@Param("schoolId")Integer schoolId);

    /**
     * 升级
     * @return
     */
    Integer upgrade(@Param("classId")Integer classId,@Param("gradeId")Integer gradeId);
}