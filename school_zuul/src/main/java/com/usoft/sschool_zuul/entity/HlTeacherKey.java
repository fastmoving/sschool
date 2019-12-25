package com.usoft.sschool_zuul.entity;

public class HlTeacherKey {

    public HlTeacherKey(){

    }

    public HlTeacherKey(Integer id, Integer schoolId){
        this.id = id;
        this.schoolid = schoolId;
    }
    private Integer id;

    private Integer schoolid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Integer schoolid) {
        this.schoolid = schoolid;
    }
}