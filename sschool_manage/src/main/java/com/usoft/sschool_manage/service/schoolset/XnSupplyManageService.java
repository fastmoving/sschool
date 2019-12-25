package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 供应商管理
 * @Author jijh
 * @Date 2019/4/26 14:05
 */
public interface XnSupplyManageService {


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
    MyResult listSupplyManage(String supplyName, String supplyType, String legalPerson, String phone, String operateType,
                              Integer isLegal, Integer pageNo, Integer pageSize);


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
    MyResult addOrUpdateSupplyManage(Integer id, String supplyName, String supplyType, String legalPerson, String phone,
                                     String operateType, Integer isLegal);


    /**
     * 删除供应商
     * @param ids
     * @return
     */
    MyResult deleteSupply(Integer[] ids);
}
