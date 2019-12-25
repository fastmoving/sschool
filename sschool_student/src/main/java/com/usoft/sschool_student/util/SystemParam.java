package com.usoft.sschool_student.util;

/**
 * Description: 封装系统公共参数 <br/>
 *
 * @version: 1.0 <br/>
 */
public class SystemParam {

    private static ThreadLocal<Integer> userIdThreadLocal = new ThreadLocal<>();
    
    private static ThreadLocal<Integer> userTypeThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<Integer>  schoolIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<Integer>  childIdThreadLocal = new ThreadLocal<>();

    private static ThreadLocal<Integer>  typeThreadLocal = new ThreadLocal<>();

    public static Integer getType() {
        return typeThreadLocal.get();
    }

    public static void setType(Integer type) {typeThreadLocal.set(type);}

    public static void removeType(){
        typeThreadLocal.remove();
    }

    public static void setUserId(Integer userId) {
        userIdThreadLocal.set(userId);
    }

    public static void removeUserId(){
        userIdThreadLocal.remove();
    }

    public static Integer getUserId(){
        return userIdThreadLocal.get();
    }
    
    public static void setUserType(Integer userType) {
    	userTypeThreadLocal.set(userType);
    }
    
    public static void removeUserType() {
    	userTypeThreadLocal.remove();
    }
    
    public static Integer getUserType() {
    	return userTypeThreadLocal.get();
    }

    public static void setSchoolId(Integer schoolId){
     schoolIdThreadLocal.set(schoolId);
    }

    public static Integer getSchoolId(){return schoolIdThreadLocal.get();}

    public static void removeSchoolId(){schoolIdThreadLocal.remove();}

    public static void setChildId(Integer childId){
        childIdThreadLocal.set(childId);
    }

    public static Integer getChildId(){
        return childIdThreadLocal.get();
    }

    public static void removeChildId(){childIdThreadLocal.remove();}


}
