package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 *
 * 用户信息管理
 * @Author jijh
 * @Date 2019/4/24 14:38
 */
public interface CfUserService {


    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    MyResult login(String loginName, String password);


    /**
     * 退出登录
     * @return
     */
    MyResult loginout();



}
