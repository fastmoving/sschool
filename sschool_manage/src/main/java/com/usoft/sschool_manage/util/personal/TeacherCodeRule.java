package com.usoft.sschool_manage.util.personal;

/**
 * 教师编号生成规则类
 * @Author jijh
 * @Date 2019/4/29 13:44
 */
public class TeacherCodeRule {


    /**
     * 教师编码生成规则 乡镇ID+学校id+四位数字
     * @param countyId
     * @param deptId
     * @param count
     * @return
     */
    public  static String createTeacherCode(Integer countyId, Integer deptId, Integer count){
        String cid = String.valueOf(countyId);
        String did = String.valueOf(deptId);
        String countstr = String.valueOf(count);
        while(countstr.length()<4){
            countstr = "0"+countstr;
        }
        String teacherCode = cid + did + countstr;
        return teacherCode;
    }


}
