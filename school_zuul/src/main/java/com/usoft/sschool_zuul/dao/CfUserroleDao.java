package com.usoft.sschool_zuul.dao;

import com.usoft.sschool_zuul.entity.CfUserrole;

import java.util.List;

public interface CfUserroleDao {


    /**
     * 查询用户的角色。
     * @param cfUserrole
     * @return
     */
    List<CfUserrole> select(CfUserrole cfUserrole);


    /**
     * 根据用户的id查询角色
     * @param userId
     * @return
     */
    CfUserrole selectById(Integer userId);
}
