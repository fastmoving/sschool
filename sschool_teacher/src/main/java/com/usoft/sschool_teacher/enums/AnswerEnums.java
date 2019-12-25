package com.usoft.sschool_teacher.enums;

/**
 * @Author: 陈秋
 * @Date: 2019/5/13 17:40
 * @Version 1.0
 */
public enum AnswerEnums {
    ANSWER(0,"未做"),
    ANSWER_A(1,"A"),
    ANSWER_B(2,"B"),
    ANSWER_C(3,"C"),
    ANSWER_D(4,"D");
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

    AnswerEnums(int status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public static String getMessage(int statu){
        AnswerEnums[] enums = AnswerEnums.values();
        for(int i=0;i<enums.length;i++){
            if (statu == enums[i].getStatus()){
                return enums[i].getMessage();
            }
        }
        return "";
    }
}
