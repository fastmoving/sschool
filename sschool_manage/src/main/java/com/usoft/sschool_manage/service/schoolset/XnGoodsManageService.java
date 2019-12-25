package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 商品业务层
 * @Author jijh
 * @Date 2019/4/29 16:26
 */
public interface  XnGoodsManageService {


    /**
     * 商品列表
     * @param goodsName 商品名称
     * @param type 类型
     * @param status 状态
     * @param priceBegin 价格区间大于等于
     * @param priceEnd 价格区间小于等于
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listXnGoodsManage(String goodsName, Integer type, Integer status, String priceBegin, String priceEnd,
                               Integer pageNo, Integer pageSize);

    /**
     * 添加或者修改商品
     * @param id 商品id 修改时需要
     * @param typeId 类型id
     * @param name 商品名称
     * @param price 价格
     * @param number 库存
     * @param status 是否上架
     * @param isDeliver 是否配送
     * @param thumbnail 缩略图
     * @param desImg 介绍图
     * @param description 描述
     * @return
     */
    MyResult addOrUpdateGoodsManage(Integer id, Integer typeId, String name, String price,
                                    Integer number, Integer status, Integer isDeliver,
                                    String thumbnail,String desImg, String description);


    /**商品上下架
     *
     * @param id
     * @param status
     * @return
     */
    MyResult updateStatus(Integer id, Integer status);

    /**
     * 删除商品
     * @param ids
     * @return
     */
    MyResult deleteXnGoodsManage(Integer[] ids);

    /**
     * 查看商品详情
     * @param id
     * @return
     */
    MyResult selectXnGoodsManage(Integer id);
}
