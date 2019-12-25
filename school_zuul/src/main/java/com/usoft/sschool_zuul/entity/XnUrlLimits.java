package com.usoft.sschool_zuul.entity;

import java.io.Serializable;

public class XnUrlLimits  implements Serializable {

    private Integer id;


    private Integer roleId;

    private Integer url;

    private String attr1;

    private String attr2;

    private String attr3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUrl() {
        return url;
    }

    public void setUrl(Integer url) {
        this.url = url;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }
}
