package com.usoft.sschool_teacher.enums.entity;

/**
 * @author cq
 * @date 2020/5/6 23:05
 */
public class XnCalendarEntity {

    /**
     * id
     */
    private Integer id;

    /**
     * name
     */
    private String dataName;

    /**
     * 时间
     */
    private String dataTime;

    /**
     * 是否是调休
     */
    private Boolean holiday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Boolean getHoliday() {
        return holiday;
    }

    public void setHoliday(Boolean holiday) {
        this.holiday = holiday;
    }

    public XnCalendarEntity() {
    }

    public XnCalendarEntity(Integer id, String dataName, String dataTime, Boolean holiday) {
        this.id = id;
        this.dataName = dataName;
        this.dataTime = dataTime;
        this.holiday = holiday;
    }
}
