package com.usoft.sschool_teacher.enums.po;

import javax.validation.constraints.NotNull;

/**
 * @author cq
 * @date 2020/4/28 23:14
 */
public class AppHomeWorkTitlePo {
    /**
     * 作业ID
     */
    @NotNull(message = "作业ID不能为空")
    private Integer hwid;

    /**
     * 选择题标题
     */
    private String title;

    /**
     * 选项A
     */
    private String answerA;

    /**
     * 选项B
     */
    private String answerB;

    /**
     * 选项C
     */
    private String answerC;

    /**
     * 选项D
     */
    private String answerD;

    /**
     * 答案
     */
    @NotNull(message = "答案不能为空")
    private Integer rightAnswer;

    public AppHomeWorkTitlePo(Integer hwid, String title, String answerA, String answerB, String answerC, String answerD, Integer rightAnswer) {
        this.hwid = hwid;
        this.title = title;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.rightAnswer = rightAnswer;
    }

    public AppHomeWorkTitlePo() {
    }

    public Integer getHwid() {
        return hwid;
    }

    public void setHwid(Integer hwid) {
        this.hwid = hwid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public Integer getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Integer rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
