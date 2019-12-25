package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.pojo.XnFood;
import com.usoft.smartschool.util.MyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 菜谱管理
 * @Author jijh
 * @Date 2019/4/22 15:43
 */
public interface XnFoodManageService {

    /**
     * 菜谱列表
     * @return
     */
    MyResult listXnFood(Integer foodTime, Integer week);

    /**
     * 增加菜谱
     * @param xnFood
     * @return
     */
    MyResult insertXnFood(XnFood xnFood);

    /**
     * 删除菜谱
     * @param ids
     * @return
     */
    MyResult deleteXnFood(Integer[] ids);


    /**
     * 修改菜谱
     * @param xnFood
     * @return
     */
    MyResult updateXnFood(XnFood xnFood);

    /**
     * 查看菜谱
     * @param foodId
     * @return
     */
    MyResult selectXnFood(Integer foodId);

    /**
     * 导入菜谱 excel
     * @param request
     * @return
     */
    MyResult importXnFood(HttpServletRequest request);



}
