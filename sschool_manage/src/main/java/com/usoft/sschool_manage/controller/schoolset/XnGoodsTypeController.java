package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品分类管理
 * @Author jijh
 * @Date 2019/4/26 17:12
 */

@RestController
@RequestMapping("manage/goodstype")
public class XnGoodsTypeController  extends BaseController {

    @Autowired
    private XnGoodsTypeService xnGoodsTypeService;

    /**
     * 分类列表
     * @return
     */
    @GetMapping("list")
    public Object listXnGoodsType(){
        return xnGoodsTypeService.listXnGoodsType();
    }


    /**
     * 基本分类列表
     * @return
     */
    @GetMapping("listbase")
    public Object listXnGoodsTypeBase(){
        return xnGoodsTypeService.listXnGoodsTypeBase();
    }





    /**
     * 添加商品分类
     * @param id 商品分类id 修改时需要
     * @param name 分类名称
     * @param type 类型 1.在线商城 2.积分商城
     * @param pid 父级id
     * @param isdisplay 是否展示
     * @param orders 排序
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateXnGoodsType(Integer id, String name, Integer type, Integer pid, Integer isdisplay, Integer orders){
        return xnGoodsTypeService.addOrUpdataXnGoodsType(id,name,type,pid,isdisplay,orders);
    }


    /**
     * 修改是否在前台展示
     * @param id
     * @param isdisplay
     * @return
     */
    @PostMapping("updatedis")
    public Object updateIsDisplay(Integer id, Integer isdisplay){
        return xnGoodsTypeService.changeIsDisPlay(id, isdisplay);
    }


    /**
     * 删除商品分类
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteGoodsType(Integer[] ids){
        return xnGoodsTypeService.deleteXnGoodsType(ids);
    }
}
