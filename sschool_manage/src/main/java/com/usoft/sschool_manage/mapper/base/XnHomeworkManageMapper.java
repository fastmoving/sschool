package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnHomeworkManage;
import com.usoft.smartschool.pojo.XnHomeworkManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
}