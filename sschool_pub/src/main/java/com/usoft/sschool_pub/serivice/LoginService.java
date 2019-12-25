package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

import java.text.ParseException;

/**
 * @author wlw
 * @data 2019/4/22 0022-14:50
 */
public interface LoginService {
    //查询学校列表
    MyResult schoolList();
    //看ip地址是否正确
    MyResult getPort(Integer schoolId,String ipPath);
    //查询套餐信息
    MyResult searchSetmeal(Integer schoolId);
    //保存套餐订单
    MyResult saveSetmealOrder(Integer schoolId,Integer childId,Integer smid);
    //改变套餐订单状态为支付成功
    MyResult changeOrderInfo(Integer schoolId,Integer userId,String byuuid);
    //查询我购买的套餐
    MyResult PaySetmail();
    //绑定账号
    MyResult bind(Integer schoolId,Integer childId,String familyRelate,String phone);
    //教师登录
    MyResult teacherLogin(String userName,String pwd);
    //登录
    MyResult appLogin(String phone, String pwd);

    //修改密码
    MyResult changePwd(String phone , String pwd, String newpwd);

    //选择几号孩子
    MyResult checkStu(Integer stuId,String phone);
    //查询学生信息
    MyResult stuInfo(String phone);
    //菜谱信息
    MyResult forFood(Integer schoolId);
    //web菜谱
    MyResult webFood();
}
