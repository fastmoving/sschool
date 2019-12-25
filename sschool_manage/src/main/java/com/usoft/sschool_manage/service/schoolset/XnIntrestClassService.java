package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 兴趣班业务层
 * @Author jijh
 * @Date 2019/5/5 17:23
 */
public interface XnIntrestClassService {

    /**
     * 兴趣班列表
     * @param name
     * @return
     */
    MyResult listXnIntrestClass(String name,Integer pageNo, Integer pageSize);

    /**
     * 添加或者修改兴趣班
     * @param id  兴趣班id  修改时需要
     * @param className 兴趣班名称
     * @param tid 教师id
     * @param teacherName 教师名称
     * @param beginDate 开班时间
     * @param classNum 学生数量
     * @param img 兴趣班图片
     * @param classDes 兴趣班简介
     * @param price 价格
     * @return
     */
    MyResult addOrUpdateXnIntrestClass(Integer id, String className, Integer tid, String teacherName,
                                       String beginDate, Integer classNum,String img,  String classDes,String price);

    /**
     * 兴趣班删除
     * @param ids
     * @return
     */
    MyResult deleteXnIntrestClass(Integer[] ids);


    /**
     * 兴趣班报名学生列表
     * @param id 兴趣班id
     * @return
     */
    MyResult listIntrestClassStudent(Integer id, Integer pageNo, Integer pageSize);

    /**
     * 添加或者修改兴趣班学生信息
     * @param id 兴趣班学生列表id
     * @param sid 学生id
     * @param parentPhone 家长号码
     * @param entryType 方式 1.线上，2线下
     * @return
     */
    MyResult addOrupdateStudent(Integer intrestId, Integer id, Integer sid, String parentPhone, Integer entryType);


    /**
     * 删除兴趣班学生
     * @param ids
     * @return
     */
    MyResult deleteIntrestClassStudent(Integer[] ids);
}
