package com.usoft.sschool_teacher.enums.entity;

import java.util.Date;

/**
 * @author cq
 * @date 2020/4/22 20:35
 */
public class XnTimingSchool {
    /**
     * id
     */
    private Long id;

    /**
     * 设置者ID
     */
    private Integer userId;

    /**
     * 设置时间
     */
    private Date xnTiming;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 设置属性
     */
    private Integer xnType;

    /**
     * 是否开启 1.是。2.否
     *
     */
    private Integer isEnable;

    /**
     * 说明
     */
    private String description;

    /**
     * 学校ID
     */
    private Integer schoolId;

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public XnTimingSchool(Long id, Integer userId, Date xnTiming, String classId, Integer xnType, Integer isEnable, String description) {
        this.id = id;
        this.userId = userId;
        this.xnTiming = xnTiming;
        this.classId = classId;
        this.xnType = xnType;
        this.isEnable = isEnable;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getXnTiming() {
        return xnTiming;
    }

    public void setXnTiming(Date xnTiming) {
        this.xnTiming = xnTiming;
    }

    public Integer getXnType() {
        return xnType;
    }

    public void setXnType(Integer xnType) {
        this.xnType = xnType;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public XnTimingSchool() {
    }
}
