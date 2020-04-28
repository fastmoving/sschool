package com.usoft.sschool_teacher.enums.po;

/**
 * @author cq
 * @date 2020/4/28 17:24
 * 发布作业条件
 */
public class WebHomeWorkPo {
    /**
     * 作业名
     */
    private String hwName;

    /**
     * 作业类型 1.在线答题 2.上传
     */
    private Integer hwType;

    /**
     * 接收班级 班级ID用“，”隔开
     */
    private String acceptClass;

    /**
     * 科目
     */
    private String subject;

    /**
     * 作业提交截止时间 例如2019-05-10 09:47:40
     */
    private String expireTime;

    /**
     * 作业内容(答题上传题目的内容)
     */
    private String hwContent;

    /**
     * 上传作业图片路径
     */
    private String hwContentImg;

    /**
     * 选择题数组字符串 例如下面例子 例子{‘title’:’选择题题目’,’answera’:’选择1’,’answerb’:’选择2’,
     * ’answerc’:’选择3’,’answerd’:’选择4’,’rightanswer’:’答案 1234分别代表ABCD’}
     */
    private String array;

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

    public String getArray() {
        return array;
    }

    public void setArray(String array) {
        this.array = array;
    }

    public WebHomeWorkPo() {
    }

    public WebHomeWorkPo(String hwName, Integer hwType, String acceptClass, String subject, String expireTime, String hwContent, String hwContentImg, String array) {
        this.hwName = hwName;
        this.hwType = hwType;
        this.acceptClass = acceptClass;
        this.subject = subject;
        this.expireTime = expireTime;
        this.hwContent = hwContent;
        this.hwContentImg = hwContentImg;
        this.array = array;
    }
}
