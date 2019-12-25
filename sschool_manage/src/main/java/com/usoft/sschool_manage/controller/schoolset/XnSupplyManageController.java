package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.XnSupplyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 供应商控制层
 * @Author jijh
 * @Date 2019/4/26 16:28
 */
@RestController
@RequestMapping("manage/supply")
public class XnSupplyManageController  extends BaseController {

    @Autowired
    private XnSupplyManageService xnSupplyManageService;


    /**
     * 供应商列表  条件查询
     * @param supplyName 供应商名称
     * @param supplyType 供应商类型
     * @param legalPerson 负责人名称
     * @param phone 联系方式
     * @param operateType 经营产品
     * @param isLegal 供应商资质 0否 1.是
     * @param pageSize 每页大小
     * @param pageNo 页数
     * @return
     */
    @GetMapping("list")
    public Object listXnSupply(String supplyName, String supplyType, String legalPerson, String phone, String operateType,
                               Integer isLegal, Integer pageNo, Integer pageSize){
        return xnSupplyManageService.listSupplyManage(supplyName,supplyType,legalPerson,phone,operateType,isLegal,pageNo,pageSize);

    }

    /**
     * 添加或者修改供应商
     * @param id 供应商id  修改时需要
     * @param supplyName 供应商名称
     * @param supplyType 供应商类型
     * @param legalPerson 负责人
     * @param phone 联系方式
     * @param operateType 运营产品
     * @param isLegal 是否有资质
     * @return
     */
    @PostMapping("addorupdate")
    public Object addOrUpdateXnSupply(Integer id, String supplyName, String supplyType, String legalPerson, String phone,
                                      String operateType, Integer isLegal){
        return xnSupplyManageService.addOrUpdateSupplyManage(id,supplyName,supplyType,legalPerson,phone,operateType,isLegal);
    }


    /**
     * 删除供应商
     * @param ids
     * @return
     */
    @DeleteMapping("delete")
    public Object deleteXnSupply(Integer[] ids){
        return xnSupplyManageService.deleteSupply(ids);

    }


}
