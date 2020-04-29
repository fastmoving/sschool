package com.usoft.sschool_teacher.enums.po;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cq
 * @date 2020/4/29 10:41
 */
public class HomeWorkManagePo {
    /**
     * 教师ID
     */
    private String teacherId;

    /**
     * 状态 1.未做 2.已做已提交 3.已审批
     */
    private Integer state;

    /**
     * 学生名字
     */
    private String stuName;

    /**
     * 班级名字
     */
    private String className;

    /**
     * 作业名字
     */
    private String hwmName;

    /**
     * 当前页
     */
    private Integer currentPage = 1;

    /**
     * 显示条数
     */
    private Integer pageSize = 10;

    /**
     * 科目
     */
    private String subject;

    /**
     * 班级ID
     */
    private String classId;

    /**
     * 请求头
     */
    private HttpServletRequest request;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHwmName() {
        return hwmName;
    }

    public void setHwmName(String hwmName) {
        this.hwmName = hwmName;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
