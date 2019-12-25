package com.usoft.sschool_manage.controller.live;

import com.usoft.sschool_manage.service.live.XnTopQualityPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 精品课堂表
 * @Author jijh
 * @Date 2019/7/9 15:58
 */

@RestController
@RequestMapping("manage/qualitypersonal")
public class XnTopQualityPersonalController {


    @Autowired
    private XnTopQualityPersonalService xnTopQualityPersonalService;


    /**
     * 精品课堂列表
     * @return
     */
    @GetMapping("list")
    public Object listXnTopQualityPersonal(Integer pageNo, Integer pageSize){
        return xnTopQualityPersonalService.listXnTopQualityPersonalClass(pageNo, pageSize);
    }

    /**
     * 精品课堂添加或者修改
     * @param id 精品课堂id 修改时需要
     * @param subject 科目
     * @param tid 教师id
     * @param tPrice 教师价格，
     * @param sPrice
     * @param videoUrl
     * @param isPersonal
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrupdateTopQualityPersonal(Integer id, String subject, Integer tid, String tPrice, String sPrice, String videoUrl, String isPersonal){
        return xnTopQualityPersonalService.addOrUpdateXnTopQualityPersonalClass(id, subject, tid, tPrice, sPrice, videoUrl, isPersonal);
    }
}
