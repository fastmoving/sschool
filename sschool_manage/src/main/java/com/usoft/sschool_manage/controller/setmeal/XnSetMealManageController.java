package com.usoft.sschool_manage.controller.setmeal;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.setmeal.XnSetmealManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 套餐管理
 * @Author jijh
 * @Date 2019/5/9 17:12
 */
@RestController
@RequestMapping("/manage/setmeal")
public class XnSetMealManageController extends BaseController {


    @Autowired
    private XnSetmealManageService xnSetmealManageService;
    /**
     * 套餐查看
     * @return
     */
    @GetMapping("list")
    public Object listXnSetMealManage(){
        return xnSetmealManageService.listXnSetmealManage();
    }

    /**
     * 套餐添加
     * @param id 套餐ID
     * @param name 套餐名称
     * @param price 套餐价格
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param bindMan 绑定人数
     * @param timeType 时间属性
     * @param des 描述介绍
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateXnSetmealManage(Integer id,String name, String price, String beginDate,
                                             String endDate, Integer bindMan, String des,Integer timeType,Integer attr1){
        return xnSetmealManageService.addOrUpdateXnSetmealManage(id,name,price,beginDate,endDate,bindMan,des,timeType,attr1);
    }

    /**
     * 删除套餐
     * @param ids 套餐名称
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteUpdateXnSetmealManage(Integer[] ids){
        return xnSetmealManageService.deleteXnSetmealManage(ids);
    }

    /**
     * 查看套餐
     * @param id
     * @return
     */
    @GetMapping("select")
    public Object selectXnSetmealManage(Integer id){
        return xnSetmealManageService.selectXnSetmealManage(id);
    }

}
