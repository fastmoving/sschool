package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.smartschool.pojo.XnFood;
import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnFoodManageService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author jijh
 * @Date 2019/4/22 16:13
 */
@RestController
@RequestMapping("manage/food")
public class XnFoodController  extends BaseController {


    @Autowired
    private XnFoodManageService xnFoodManageService;


    /**
     * 菜谱列表
     * @return
     */
    @GetMapping("/list")
    public Object getFoodList(Integer foodTime, Integer week){
        return xnFoodManageService.listXnFood(foodTime,week);

    }

    /**
     * 添加菜谱或者更新菜谱
     * @param sid 学校id
     * @param foodTime 时间 1.早  2中  3.晚
     * @param foodName 菜名
     * @param week 周几  0123456-->日一二三四五六
     * @return
     */
    @PostMapping("insert")
    public Object addFood(Integer id, Integer sid, int foodTime, String foodName, Integer week){
        XnFood xnFood = new XnFood();
        xnFood.setId(id);
        xnFood.setFoodname(foodName);
        xnFood.setFoodtime((byte)foodTime);
        Integer schoolId = SystemParam.getSchoolId();
        if(sid != null){
            xnFood.setSid(sid);
        }else{
            xnFood.setSid(schoolId);
        }
        xnFood.setWeek(week);
        return xnFoodManageService.insertXnFood(xnFood);
    }

    /**
     * 删除菜谱
     * @param ids 菜谱id
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteFood(Integer[] ids){
        return xnFoodManageService.deleteXnFood(ids);
    }

//    /**
//     * 更新菜谱
//     * @param id 菜谱id
//     * @param sid 学校id
//     * @param foodTime 菜谱时间 1.早  2.中  3.晚
//     * @param foodName 菜名
//     * @param week 周几  0123456-->日一二三四五六
//     * @return
//     */
//    @PutMapping("update")
//    public Object updateFood(Integer id, Integer sid, int foodTime, String foodName, Integer week){
//
//        XnFood xnFood = new XnFood();
//        xnFood.setId(id);
//        xnFood.setFoodname(foodName);
//        xnFood.setFoodtime((byte)foodTime);
//        xnFood.setSid(sid);
//        xnFood.setWeek(week);
//        return xnFoodManageService.updateXnFood(xnFood);
//    }

}

