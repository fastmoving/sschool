package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnSupplyManage;
import com.usoft.smartschool.pojo.XnSupplyManageExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnSupplyManageMapper;
import com.usoft.sschool_manage.service.schoolset.XnSupplyManageService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 供应商业务层
 * @Author jijh
 * @Date 2019/4/26 16:01
 */
@Service
public class XnSupplyManageServiceImpl implements XnSupplyManageService {

    @Resource
    private XnSupplyManageMapper supplyManageMapper;


    @Override
    public MyResult listSupplyManage(String supplyName, String supplyType, String legalPerson, String phone, String operateType, Integer isLegal, Integer pageNo, Integer pageSize) {
        XnSupplyManageExample xnSupplyManageExample  = new XnSupplyManageExample();

        XnSupplyManageExample.Criteria criteria = xnSupplyManageExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andUidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(supplyName))criteria.andSupplynameLike("%"+supplyName+"%");
        if(!ObjectUtil.isEmpty(legalPerson))criteria.andLegalpersonLike("%"+legalPerson+"%");
        if(!ObjectUtil.isEmpty(supplyType))criteria.andSupplytypeLike("%"+supplyType+"%");
        if(!ObjectUtil.isEmpty(phone))criteria.andPhoneLike("%"+phone+"%");
        if(!ObjectUtil.isEmpty(operateType))criteria.andOperatetypeLike("%"+operateType+"%");
        if(!ObjectUtil.isEmpty(isLegal)){
            int legal = isLegal;
            criteria.andIslegalEqualTo((byte)legal);
        }
        List<XnSupplyManage> xnSupplyManages  = supplyManageMapper.selectByExample(xnSupplyManageExample);
        if(ObjectUtil.isEmpty(xnSupplyManages))return MyResult.failure("暂无数据");
        if(pageNo == null) pageNo = 1;
        if(pageSize == null) pageSize = 20;


        return ResultPage.getPageResult(xnSupplyManages,pageNo, pageSize);
    }

    @Override
    public MyResult addOrUpdateSupplyManage(Integer id, String supplyName, String supplyType, String legalPerson, String phone, String operateType, Integer isLegal) {
        Integer schoolId = SystemParam.getSchoolId();
        XnSupplyManage xnSupplyManage = new XnSupplyManage();
        if(ObjectUtil.isEmpty(supplyName))return MyResult.failure("供应商名称？");
        if(ObjectUtil.isEmpty(supplyType))return MyResult.failure("供应商类型？");
        if(ObjectUtil.isEmpty(legalPerson))return MyResult.failure("负责人？");
        if(ObjectUtil.isEmpty(phone))return MyResult.failure("联系方式?");
        if(ObjectUtil.isEmpty(operateType))return MyResult.failure("运营产品？");
        if(ObjectUtil.isEmpty(isLegal))return MyResult.failure("是否有资质？");
        xnSupplyManage.setSupplyname(supplyName);
        int legal = isLegal;
        xnSupplyManage.setIslegal((byte)legal);
        xnSupplyManage.setSupplytype(supplyType);
        xnSupplyManage.setLegalperson(legalPerson);
        xnSupplyManage.setPhone(phone);
        xnSupplyManage.setOperatetype(operateType);
        xnSupplyManage.setUid(schoolId);
        String message = "";
        try {
            if(ObjectUtil.isEmpty(id)){
                xnSupplyManage.setCreatetime(new Date());
                supplyManageMapper.insert(xnSupplyManage);
                message = "添加成功";
            }else{
                XnSupplyManage xnSupplyManage1 = supplyManageMapper.selectByPrimaryKey(id);
                if(ObjectUtil.isEmpty(xnSupplyManage1))return MyResult.failure("当前供应商未找到，无法修改");
                xnSupplyManage.setId(id);
                supplyManageMapper.updateByPrimaryKeySelective(xnSupplyManage);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        return MyResult.success(message);
    }

    @Override
    public MyResult deleteSupply(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        XnSupplyManageExample xnSupplyManageExample = new XnSupplyManageExample();
        XnSupplyManageExample.Criteria criteria = xnSupplyManageExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andUidEqualTo(schoolId);
        criteria.andIdIn(Arrays.asList(ids));
        int num = 0;
        try{
            num = supplyManageMapper.deleteByExample(xnSupplyManageExample);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除"+num+"条数据");
    }
}
