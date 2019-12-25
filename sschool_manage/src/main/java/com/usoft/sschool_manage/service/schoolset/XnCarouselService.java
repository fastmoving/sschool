package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 轮播图管理
 * @Author jijh
 * @Date 2019/5/5 9:41
 */
public interface XnCarouselService {

    /**
     * 轮播图列表
     * @param position
     * @return
     */
    MyResult listXnCarousel(Integer position);

    /**
     * 添加或者更新轮播图
     * @param id 轮播图id 修改时需要
     * @param position 位置 1.校园首页  2.学生端首页 3.教师端首页
     * @param type 方向 1.app  2.web
     * @param order 排序
     * @param imgUrl 图片地址
     * @param linkHtml 超链接
     * @return
     */
    MyResult addOrUpdateXnCarousel(Integer id, Integer position, Integer type, Integer order,
                                   String imgUrl,String linkHtml);


    /**
     * 删除轮播图
     * @param ids
     * @return
     */
    MyResult deleteXnCarousel(Integer[] ids);
}
