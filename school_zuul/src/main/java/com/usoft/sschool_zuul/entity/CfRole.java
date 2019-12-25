package com.usoft.sschool_zuul.entity;

import java.util.Date;

/**
 * 角色表
 */
public class CfRole {

    private Integer roleId;

    private String roleName;

    private String roleDesc;

    private Integer createUserId;

    private Date createDate;

    private Integer modifyUserId;

    private Date modifyDate;

    private String RecordStatus;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getRecordStatus() {
        return RecordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        RecordStatus = recordStatus;
    }
}
