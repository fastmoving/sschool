package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnCarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 轮播图管理
 * @Author jijh
 * @Date 2019/5/5 11:41
 */

@RestController
@RequestMapping("manage/xncarousel")
public class XnCarouselController  extends BaseController {


    @Autowired
    private XnCarouselService xnCarouselService;


    /**
     * 轮播图列表
     * @param position
     * @return
     */
    @GetMapping("list")
    public Object listXncarousel(Integer position){
        return xnCarouselService.listXnCarousel(position);
    }


    /**
     * 添加或者修改轮播图
     * @param id
     * @param position
     * @param type
     * @param order
     * @param imgUrl
     * @param linkHtml
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateCarousel(Integer id, Integer position, Integer type, Integer order, String imgUrl, String linkHtml){
        return xnCarouselService.addOrUpdateXnCarousel(id,position,type,order,imgUrl,linkHtml);
    }


    /**
     * 删除轮播图
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteXnCarousel(Integer[] ids){
        return xnCarouselService.deleteXnCarousel(ids);
    }

}
