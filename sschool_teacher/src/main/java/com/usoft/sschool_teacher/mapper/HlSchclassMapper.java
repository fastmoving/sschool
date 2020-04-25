package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.HlSchclass;
import com.usoft.smartschool.pojo.HlSchclassExample;
import com.usoft.smartschool.pojo.HlSchclassKey;
import com.usoft.sschool_teacher.enums.entity.XnTimingSchool;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HlSchclassMapper {
    int countByExample(HlSchclassExample example);

    int deleteByExample(HlSchclassExample example);

    int deleteByPrimaryKey(HlSchclassKey key);

    int insert(HlSchclass record);

    int insertSelective(HlSchclass record);

    List<HlSchclass> selectByExample(HlSchclassExample example);

    HlSchclass selectByPrimaryKey(HlSchclassKey key);

    int updateByExampleSelective(@Param("record") HlSchclass record, @Param("example") HlSchclassExample example);

    int updateByExample(@Param("record") HlSchclass record, @Param("example") HlSchclassExample example);

    int updateByPrimaryKeySelective(HlSchclass record);

    int updateByPrimaryKey(HlSchclass record);

    /**
     * 获取管理班级
     * @return
     */
    List<Map<String,Object>>  getManagerClass(Map key);
    int getManagerClassCount(@Param("teacherId")Integer teacherId);

    /**
     * 管理班级
     */
    List<HlSchclass> getClassChecking(Map key);

    /**
     * 获取全校班级id
     * @param schoolId
     * @return
     */
    List<String> getSchoolClassId(@Param("schoolId")Integer schoolId);

    /**
     * 获取还处于设置状态的定时器
     * @return
     */
    List<XnTimingSchool> getTiming();

    /**
     * 添加定时器
     * @param xnTimingSchool
     */
    void addTimingSchool(@Param("item") XnTimingSchool xnTimingSchool);

    /**
     * 修改定时器
     */
    void updateTiming(@Param("item") XnTimingSchool xnTimingSchool);
}