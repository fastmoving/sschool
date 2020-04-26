package com.usoft.sschool_teacher.controllers.myselfController;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.enums.po.TeacherAddressPo;
import com.usoft.sschool_teacher.service.ITeacherMyselfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * author : 陈秋
 * time : 2019-05-17
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/address")
public class AddressController {

    @Autowired
    private ITeacherMyselfService myselfService;

    /**
     * 添加收货地址
     * @param po 添加条件
     * @return
     */
    @PostMapping("/insertAddress")
    public MyResult insertAddress(@Valid TeacherAddressPo po){
        Integer thId = SystemParam.getUserId();
        int i = myselfService.insertAddress(thId, po.getLinkMan(),  po.getPhone(),
                po.getIsDefault(), po.getProvince(),  po.getCity(),  po.getDist(),
                po.getAddress(), po.getCityCode());
        if (i==-2){
            return new MyResult(2,"网络延迟","");
        }
        return new MyResult(1,"success",i);
    }

    /**
     * 查询收货地址
     */
    @GetMapping("/getAddress")
    public MyResult getAddress(String addressId,String pageSize,String currentPage){
        int thId = SystemParam.getUserId();
        int page = 0;
        int start = 0;
        try{
            //thId = Integer.parseInt(teacherId.trim());
            page = Integer.parseInt(pageSize.trim());
            start = Integer.parseInt(currentPage.trim());
        }catch (Exception e){
            return new MyResult(2,"教师ID格式不正确或是分页数据没有","");
        }
        List<Map> result = myselfService.getAddress(addressId,thId,page,start);
        int resNum = myselfService.getAddressesCount(thId);
        if (result==null){
            return new MyResult(2,"没有数据",null);
        }
        return new MyResult(1,"success",result,start,((resNum+page)/page),page,resNum);
    }

    /**
     * 删除地址
     * @param addressIds 地址ID
     * @return
     */
    @DeleteMapping("/removeAddress")
    public MyResult removeAddress(String addressIds){
        if (addressIds==null || "".equals(addressIds)){
            return new MyResult(2,"上传地址ID",null);
        }
        String[] adrsIds = addressIds.split(",");
        int i = myselfService.deleteAddress(adrsIds);
        if (i>0){
            return new MyResult(1,"success",null);
        }else if (i==-1){
            return new MyResult(2,"网络延迟",null);
        }
        return null;
    }

    /**
     * 编辑地址
     * @param po 请求定制
     * @return
     */
    @PostMapping("/updateAddress")
    public MyResult updateAddress(@Valid TeacherAddressPo po){
        Integer thId = SystemParam.getUserId();
        if(po.getId() == null ){
            return new MyResult(2,"缺少参数ID",null);
        }
        int i = myselfService.updateAddress( po.getId(), thId, po.getLinkMan(),  po.getPhone(),
                po.getIsDefault(), po.getProvince(),  po.getCity(),  po.getDist(),
                po.getAddress(), po.getCityCode());

        if (i==-1 || i==-2){
            return new MyResult(2,"网络延迟",null);
        }else if (i>0){
            return new MyResult(1,"success",null);
        }
        return null;
    }

    /**
     * 查询默认地址
     */
    @GetMapping("/getDefaultAddress")
    public MyResult getDefaultAddress(){
        if (myselfService.getDefaultAddress()==null)return new MyResult(2,"没有数据","");
        return new MyResult(1,"success",myselfService.getDefaultAddress());
    }
}
