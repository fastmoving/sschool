package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnGoodsManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制层
 * @Author jijh
 * @Date 2019/4/30 13:32
 */

@RestController
@RequestMapping("manage/goodsmanage")
public class XnGoodsManageController  extends BaseController {


    @Autowired
    private XnGoodsManageService xnGoodsManageService;


    /**
     * 商品列表
     * @param name 商品名称
     * @param type 类型
     * @param status 状态
     * @param priceBegin 价格区间大于等于
     * @param priceEnd 价格区间小于等于
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("list")
    public Object listXnGoodsManage(String name, Integer type, Integer status, String priceBegin, String priceEnd, Integer pageNo, Integer pageSize){
        return xnGoodsManageService.listXnGoodsManage(name,type,status,priceBegin,priceEnd,pageNo,pageSize);

    }

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
    @PostMapping("addorupdate")
    public Object addOrUpdateXnGoodsManage(Integer id, Integer typeId, String name, String price,
                                           Integer number, Integer status, Integer isDeliver,
                                           String thumbnail,String desImg, String description){
        return xnGoodsManageService.addOrUpdateGoodsManage(id,typeId,name,price,number,status,isDeliver,thumbnail,desImg,description);
    }


    /**
     * 商品上下架
     * @param id 商品id
     * @param status 1上架 2.下架
     * @return
     */
    @PostMapping("updatastatus")
    public Object updataStatus(Integer id, Integer status){
        return xnGoodsManageService.updateStatus(id, status);
    }


    /**
     * 查看商品详情
     * @param id
     * @return
     */
    @GetMapping("select")
    public Object selectXnGoddsManage(Integer id){
        return xnGoodsManageService.selectXnGoodsManage(id);
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteXnGoodsManage(Integer[] ids){
        return xnGoodsManageService.deleteXnGoodsManage(ids);
    }
}
