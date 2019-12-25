package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 公告
 * @Author jijh
 * @Date 2019/5/5 13:28
 */
public interface XnAnnounceService {


    /**
     * 公告列表
     * @param title 公告标题
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listXnAnnounce(String title,Integer pageNo, Integer pageSize);


    /**
     * 添加或者修改公告
     * @param id 公告id 修改时需要
     * @param title 公告标题
     * @param content 公告内容
     * @return
     */
    MyResult addOrUpdateXnAnnounce(Integer id, String title, String content,String img);

    /**
     * 删除公告
     * @param ids
     * @return
     */
    MyResult deleteXnAnnounce(Integer[] ids);
}
