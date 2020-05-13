package com.usoft.sschool_teacher.enums.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author jjh
 * @date 2020/5/12 21:17
 */
public class MyselfEvaluationVo {

    /**
     * id
     */
    private Integer id;

    /**
     * 学校id
     */
    private Integer sid;

    /**
     * 班级id
     */
    private Integer cid;

    /**
     * 学生id
     */
    private Integer stuid;

    /**
     * 星级
     */
    private Integer star;

    /**
     * 教师id
     */
    private Integer tid;

    /**
     * 评论描述
     */
    private String des;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public MyselfEvaluationVo(Integer id, Integer sid, Integer cid, Integer stuid, Integer star, Integer tid, String des, Date createTime) {
        this.id = id;
        this.sid = sid;
        this.cid = cid;
        this.stuid = stuid;
        this.star = star;
        this.tid = tid;
        this.des = des;
        this.createTime = createTime;
    }

    public MyselfEvaluationVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
