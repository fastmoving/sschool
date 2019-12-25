package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 教师风采
 * @Author jijh
 * @Date 2019/5/5 17:18
 */
public interface XnTeacherApperanceService {


    /**
     * 通过选择教师，异步填充教师数据
     * @param id 教师id
     * @return
     */
    MyResult getTeacherMessage(Integer  id);


    /**
     * 教师风采照列表
     * @param teacherName 教师名称
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listXnTeacherApperance(String teacherName, Integer pageNo, Integer pageSize);

    /**
     * 添加或者修改教师风采照
     * @param id 风采id 修改时需要
     * @param tid 教师id
     * @param subject 科目
     * @param img 风采照地址
     * @param description 介绍
     * @return
     */
    MyResult addOrUpdateXnTeaxherApperance(Integer id, Integer tid, String subject, String img,
                                           String description);

    /**
     * 删除教师风采照
     * @param ids 风采照id
     * @return
     */
    MyResult deleteXnTeacherApperance(Integer[] ids);
}
