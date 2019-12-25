package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnLiveApplyCheck;
import com.usoft.smartschool.pojo.XnLiveApplyCheckExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XnLiveApplyCheckMapper {
    int countByExample(XnLiveApplyCheckExample example);

    int deleteByExample(XnLiveApplyCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XnLiveApplyCheck record);

    int insertSelective(XnLiveApplyCheck record);

    List<XnLiveApplyCheck> selectByExample(XnLiveApplyCheckExample example);

    XnLiveApplyCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XnLiveApplyCheck record, @Param("example") XnLiveApplyCheckExample example);

    int updateByExample(@Param("record") XnLiveApplyCheck record, @Param("example") XnLiveApplyCheckExample example);

    int updateByPrimaryKeySelective(XnLiveApplyCheck record);

    int updateByPrimaryKey(XnLiveApplyCheck record);



    List<Map<String,Object>>  listLiveCheck(@Param("className")String className , @Param("subject") String subject, @Param("teacherName")String teacherName);

    List<Map<String,Object>> listLiveCheckDetail(@Param("sid")Integer sid, @Param("cid")Integer cid,@Param("tid") Integer tid);
}