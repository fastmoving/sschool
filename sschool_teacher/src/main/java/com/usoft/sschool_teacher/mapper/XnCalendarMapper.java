package com.usoft.sschool_teacher.mapper;

import com.usoft.sschool_teacher.enums.entity.XnCalendarEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author cq
 * @date 2020/5/6 22:55
 */
public interface XnCalendarMapper {

    /**
     * 批量添加日期数据
     * @param list
     */
    void insertCalendarList(@Param("calendar")List<XnCalendarEntity> list);

    /**
     * 批量查询日期数据
     */
    List<XnCalendarEntity> selectCalendarList(@Param("startTime")Date startTime,
                                              @Param("endTime")Date endTime,
                                              @Param("dataTime")String dataTime);
}
