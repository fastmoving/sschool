package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnKindnessUnschoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 非校园爱心
 * @Author jijh
 * @Date 2019/5/7 14:20
 */
@RestController
@RequestMapping("manage/kindnessunschool")
public class XnKindnessUnschoolController  extends BaseController {


    @Autowired
    private XnKindnessUnschoolService xnKindnessUnschoolService;
    /**
     * 非校园爱心列表
     * @param type 类型 1.爱心人士 2.爱心企业
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("list")
    public Object listXnKindnessUnschool(Integer type, Integer pageNo, Integer pageSize){
        return xnKindnessUnschoolService.listXnKindnessUnschool(type,pageNo,pageSize);
    }

    /**
     * 添加或者修改非校园爱心
     * @param id 校园爱心id 修改时需要
     * @param  type 校园爱心类型 1.爱心人士 2.爱心企业
     * @param name 名称
     * @param img 图片
     * @param description 描述
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateXnKindnessUnschool(Integer id, Integer type, String name,String img, String description){
        return xnKindnessUnschoolService.addOrUpdateXnKindnessUnschool(id,type,name,img,description);
    }

    /**
     * 非校园爱心删除
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteXnKindnessUnschool(Integer[] ids){
        return xnKindnessUnschoolService.deleteXnKindnessUnschool(ids);

    }

}
