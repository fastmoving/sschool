package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnAnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公告控制层
 * @Author jijh
 * @Date 2019/5/5 16:03
 */
@RestController
@RequestMapping("manage/xnannounce")
public class XnAnnounceController  extends BaseController {

    @Autowired
    private XnAnnounceService xnAnnounceService;

    /**
     * 公告列表
     * @param title 公告标题
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("list")
    public Object listXnAnnounce(String title, Integer pageNo, Integer pageSize){
        return xnAnnounceService.listXnAnnounce(title,pageNo,pageSize);
    }


    /**
     * 添加或者修改公告
     * @param id 公告id 修改时需要
     * @param title 公告标题
     * @param content 公告内容
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateXnAnnounce(Integer id, String title, String content,String img){
        return xnAnnounceService.addOrUpdateXnAnnounce(id,title,content,img);
    }

    /**
     * 删除公告
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteXnAnnounce(Integer[] ids){
        return xnAnnounceService.deleteXnAnnounce(ids);
    }
}
