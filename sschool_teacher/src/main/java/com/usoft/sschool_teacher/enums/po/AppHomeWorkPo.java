package com.usoft.sschool_teacher.enums.po;

import javax.validation.constraints.NotNull;

/**
 * @author cq
 * @date 2020/4/28 23:13
 */
public class AppHomeWorkPo {
    /**
     * 教师ID
     */
    private String teacherId;

    /**
     * 作业名
     */
    private String hwName;

    /**
     * 作业类型 1.在线答题 2.上传
     */
    @NotNull(message = "作业类型不能为空")
    private Integer hwType;

    /**
     * 班级ID 多个ID用“，”隔开
     */
    @NotNull(message = "班级ID不能为空")
    private String acceptClass;

    /**
     * 科目
     */
    @NotNull(message = "科目不能为空")
    private String subject;

    /**
     * 截止时间
     */
    private String expireTime;

    /**
     * 作业类容
     */
    private String hwContent;

    /**
     * 照片
     */
    private String hwContentImg;

    public AppHomeWorkPo(String teacherId, String hwName, Integer hwType, String acceptClass, String subject, String expireTime, String hwContent, String hwContentImg) {
        this.teacherId = teacherId;
        this.hwName = hwName;
        this.hwType = hwType;
        this.acceptClass = acceptClass;
        this.subject = subject;
        this.expireTime = expireTime;
        this.hwContent = hwContent;
        this.hwContentImg = hwContentImg;
    }

    public AppHomeWorkPo() {
        super();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getHwName() {
        return hwName;
    }

    public void setHwName(String hwName) {
        this.hwName = hwName;
    }

    public Integer getHwType() {
        return hwType;
    }

    public void setHwType(Integer hwType) {
        this.hwType = hwType;
    }

    public String getAcceptClass() {
        return acceptClass;
    }

    public void setAcceptClass(String acceptClass) {
        this.acceptClass = acceptClass;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getHwContent() {
        return hwContent;
    }

    public void setHwContent(String hwContent) {
        this.hwContent = hwContent;
    }

    public String getHwContentImg() {
        return hwContentImg;
    }

    public void setHwContentImg(String hwContentImg) {
        this.hwContentImg = hwContentImg;
    }
}
