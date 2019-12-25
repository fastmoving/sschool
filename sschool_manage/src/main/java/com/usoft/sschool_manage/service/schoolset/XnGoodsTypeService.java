package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 商品分类管理
 * @Author jijh
 * @Date 2019/4/26 14:12
 */
public interface XnGoodsTypeService {


    /**
     * 商品分类列表
     * @return
     */
    MyResult listXnGoodsType();


    MyResult listXnGoodsTypeBase();

    /**
     * 添加商品分类
     * @param id 商品分类id 修改时需要
     * @param name 分类名称
     * @param type 类型 1.在线商城 2.积分商城
     * @param pid 父级id
     * @param isDisplay 是否展示
     * @param orders 排序
     * @return
     */
    MyResult addOrUpdataXnGoodsType(Integer id,String name, Integer type, Integer pid,
                                    Integer isDisplay, Integer orders);



    MyResult changeIsDisPlay(Integer id, Integer isDisplay);

    /**
     * 删除商品分类
     * @param ids
     * @return
     */
    MyResult deleteXnGoodsType(Integer[] ids);
}
