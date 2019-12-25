package com.usoft.sschool_teacher.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/5/16 14:04
 * @Version 1.0
 * 公共二维枚举类
 */
public enum CommonEnums {
    /**
     * 学科
     */
    SUBJECT_LANGUAGE(32,"语文"),

    SUBJECT_MATH(33,"数学"),

    SUBJECT_ENGLISH(37,"英语"),

    SUBJECT_SPORTS(34,"体育"),

    SUBJECT_MUSIC(35,"音乐"),

    SUBJECT_PHYSICS(211,"物理"),

    SUBJECT_CHEMISTRY(212,"化学"),

    SUBJECT_BIOLOGY(213,"生物"),

    SUBJECT_HISTORY(215,"历史"),

    SUBJECT_POLITICS(214,"政治"),

    SUBJECT_GEOGRAPHY(216,"地理"),

    SUBJECT_FINE_ARTS(36,"美术"),

    SUBJECT_SCIENCE(209,"科学"),

    SUBJECT_IDEOLOGY_AND_MORALITY(217,"思想品德"),

    SUBJECT_GDJY(225,"道德与法治"),

    /**
     * 请假类型
     */
    ABSENT_DISEASE( 233,"病假"),

    ABSENT_THING(234,"事假"),

    ABSENT_MARRY(235,"婚假"),

    ABSENT_PRODUCE(236,"产假"),

    ABSENT_OTHER(237,"其他"),

    /**
     * 默认地址
     */
    ADDRESS_DEFAULT(1,"是"),

    ADDRESS_NO_DEFULT(2,"否"),

    /**
     * 年级id
     */
    CLASSYEAR1(20,"一年级"),

    CLASSYEAR2(21,"二年级"),

    CLASSYEAR3(22,"三年级"),

    CLASSYEAR4(23,"四年级"),

    CLASSYEAR5(24,"五年级"),

    CLASSYEAR6(25,"六年级"),

    CLASSYEAR7(26,"七年级"),

    CLASSYEAR8(27,"八年级"),

    CLASSYEAR9(28,"九年级"),

    /**
     * 是不是当前学年
     */
    YEAR_NO(193,"否"),

    YEAR_YES(192,"是"),

    /**
     * 用户角色
     */
    ROLE_TEACHER(49,"授课老师"),

    ROLE_CLASSWORK(48,"班主任"),

    ROLE_RECTOR(47,"校长"),

    /**
     * 用户性别
     */
    SEX_MAN(73,"男"),

    SEX_WOMAN(74,"女");

    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    CommonEnums(int status, String message) {
        this.status = status;
        this.message = message;
    }
    public static String getMessage(int status){
        CommonEnums[] enums = CommonEnums.values();
        for (int i=0;i<enums.length;i++){
            if (status == enums[i].getStatus()){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
