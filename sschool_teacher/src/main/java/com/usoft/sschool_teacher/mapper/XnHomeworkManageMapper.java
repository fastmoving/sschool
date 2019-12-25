package com.usoft.sschool_teacher.mapper;

import com.usoft.smartschool.pojo.XnHomeworkManage;
import com.usoft.smartschool.pojo.XnHomeworkManageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnHomeworkManageMapper {
    int countByExample(XnHomeworkManageExample example);

    int deleteByExample(XnHomeworkManageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnHomeworkManage record);

    int insertSelective(XnHomeworkManage record);

    List<XnHomeworkManage> selectByExampleWithBLOBs(XnHomeworkManageExample example);

    List<XnHomeworkManage> selectByExample(XnHomeworkManageExample example);

    XnHomeworkManage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnHomeworkManage record, @Param("example") XnHomeworkManageExample example);

    int updateByExampleWithBLOBs(@Param("record") XnHomeworkManage record, @Param("example") XnHomeworkManageExample example);

    int updateByExample(@Param("record") XnHomeworkManage record, @Param("example") XnHomeworkManageExample example);

    int updateByPrimaryKeySelective(XnHomeworkManage record);

    int updateByPrimaryKeyWithBLOBs(XnHomeworkManage record);

    int updateByPrimaryKey(XnHomeworkManage record);

    /**
     * 作业详情
     * @param homeworkId
     * @return
     */
    List<Map<String,Object>> getHomeworkInformation(@Param("homeworkId")int homeworkId);

    /**
     * 作业管理web端
     * @param map 查询条件
     * @return
     */
    List<XnHomeworkManage> getHomeworkWeb(Map<String,Object> map);
    int getHomeworkWebCount(Map<String,Object> map);
}