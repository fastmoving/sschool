package com.usoft.sschool_teacher.enums.po;


import javax.validation.constraints.NotNull;

/**
 * @author cq
 * @date 2020/4/29 20:53
 */
public class TimeClassOverPo {
    /**
     * 班级ID
     */
    private String classIds;

    /**
     * 内容
     */
    private String message;

    /**
     * 时间
     */
    @NotNull(message = "时间不能为空")
    private Long time;

    public String getClassIds() {
        return classIds;
    }

    public void setClassIds(String classIds) {
        this.classIds = classIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
