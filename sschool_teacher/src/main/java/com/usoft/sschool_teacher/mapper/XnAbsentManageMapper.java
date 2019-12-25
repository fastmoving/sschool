package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnAbsentManage;
import com.usoft.smartschool.pojo.XnAbsentManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnAbsentManageMapper {
    int countByExample(XnAbsentManageExample example);

    int deleteByExample(XnAbsentManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnAbsentManage record);

    int insertSelective(XnAbsentManage record);

    List<XnAbsentManage> selectByExample(XnAbsentManageExample example);

    XnAbsentManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnAbsentManage record, @Param("example") XnAbsentManageExample example);

    int updateByExample(@Param("record") XnAbsentManage record, @Param("example") XnAbsentManageExample example);

    int updateByPrimaryKeySelective(XnAbsentManage record);

    int updateByPrimaryKey(XnAbsentManage record);

    /**
     * 审批管理
     */
    List<XnAbsentManage> getAbsent(Map key);
    int getAbsentCount(Map key);

    /**
     * 审批请假
     * @param key
     * @return
     */
    int updateAbsent(Map key);

    /**
     * 我的考勤
     */
    List<XnAbsentManage> getMyTimeBook(Map key);

    /**
     * 教师请假信息
     */
    List<XnAbsentManage> getTeacherAbsent(Map key);
    Integer getTeacherAbsentCount(@Param("schoolId")Integer schoolId);

    /**
     * 班级审核
     */
    List<XnAbsentManage> getClassChecking(Map key);

    /**
     * 审批班级
     */
    List<Integer> getClassIdes(List list);
}