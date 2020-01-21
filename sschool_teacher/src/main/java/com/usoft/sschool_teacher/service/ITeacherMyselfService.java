package com.usoft.sschool_teacher.service;

import com.usoft.smartschool.pojo.XnAddress;

import java.util.List;
import java.util.Map;

/**
 * @Author: 陈秋
 * @Date: 2019/5/16 11:42
 * @Version 1.0
 */
public interface ITeacherMyselfService {

    /**
     * 教师信息
     * @param teacherId
     * @return
     */
    List<Map> getMyselfIfo(int teacherId);

    /**
     * 修改个人证件照
     * @param teacherId
     * @param faceImg
     * @param idImg
     * @return
     */
    int updateMyself(Integer teacherId,String faceImg,String idImg,String code);
    /**
     * 添加地址
     */
    int insertAddress(int teacherId,String linkMan,String phone,String isDefault,
                      String province,String city,String dist,String address,String cityCode);

    /**
     * 查看地址
     */
    List<Map> getAddress(String address,int teacherId,int page,int start);
    int getAddressesCount(int teacherId);

    /**
     * 删除地址
     */
    int deleteAddress(String[] addressIds);

    /**
     * 编辑地址
     */
    int updateAddress(int addressId,int teacherId,String linkMan,String phone,String isDefault,
                      String province,String city,String dist,String address,String cityCode);

    /**
     * 查询默认地址
     */
    XnAddress getDefaultAddress();

    /**
     * 查找领导
     */
    Map getSchoolManager();

    /**
     * 验证身份
     */
    List getMyselfManager();

    /**
     * 教师请假
     */
    int insertAbsent(int teacherId,int type,String begin,String end,String times,
                     String thing);

    /**
     * 我的订单（购物车）
     */
    List<Map> getMyOrder(Integer start,Integer page);
    Integer getMyOrderCount();

    /**
     * 订单详情
     */
    Map getOrderIfo(int orderId);

    /**
     * 我的考勤
     */
    Map getMyTimeBook(int teacherId,String times);

    /**
     * 教师考勤信息
     * @param page
     * @param start
     * @return
     */
    List getTeacherAbsent(Integer page,Integer start,String status);
    Integer getTeacherAbsentCount();
    /**
     * 我的爱心
     */
    List getMyKindness(int teacherId,String page,String start,int goodsStatus);

    /**
     * 更改爱心状态
     */
    int updateKindness(int kindnessId);

    /**
     * 我的课程
     */
    List<Map> getCurriculum () throws Exception;
    List<Map> getWebCurriculum (Integer classId) throws Exception;

    /**
     * 菜单
     * @return
     */
    List<Map> getMenu();
    List<Map> getAppMenu();

    /**
     * 已购课程
     */
    List getVideo(int page,int start);
    int getTeacherVideoCount();

    /**
     * 班主任 班级管理
     */
    List<Map> getClassManage();
    List getClassManage(Integer classId);

    /**
     * 订单套餐
     */
    List<Map> getOrderClassIfo(Integer classId,Integer start,Integer page);
    Integer listXnSetmealOrderCount(Integer classId);
}
