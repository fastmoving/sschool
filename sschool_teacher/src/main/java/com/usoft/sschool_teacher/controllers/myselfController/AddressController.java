package com.usoft.sschool_teacher.controllers.myselfController;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.ITeacherMyselfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param teacherId
     * @param linkMan
     * @param phone
     * @param isDefault
     * @param province
     * @param city
     * @param dist
     * @param address
     * @param cityCode
     * @return
     */
    @PostMapping("/insertAddress")
    public MyResult insertAddress(String teacherId,String linkMan,String phone,String isDefault,
                                  String province,String city,String dist,String address,String cityCode){
        int thId = SystemParam.getUserId();
        try{
            //thId = Integer.parseInt(teacherId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的教师id不正确","");
        }
        if (linkMan == null && "".equals(linkMan)){
            return new MyResult(2,"请填写联系人","");
        }
        if (phone == null && "".equals(phone)){
            return new MyResult(2,"请填写联系电话","");
        }
        int i = myselfService.insertAddress(thId, linkMan,  phone,  isDefault,
                province,  city,  dist,  address, cityCode);
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
     * @param addressId
     * @param teacherId
     * @param linkMan
     * @param phone
     * @param isDefault
     * @param province
     * @param city
     * @param dist
     * @param address
     * @param cityCode
     * @return
     */
    @PostMapping("/updateAddress")
    public MyResult updateAddress(String addressId,String teacherId,String linkMan,String phone,String isDefault,
                                  String province,String city,String dist,String address,String cityCode){
        int thId = SystemParam.getUserId();
        int id = 0;
        try{
           // thId = Integer.parseInt(teacherId.trim());
            id = Integer.parseInt(addressId.trim());
        }catch (Exception e){
            return new MyResult(2,"上传的教师id不正确","");
        }
        if (linkMan == null && "".equals(linkMan)){
            return new MyResult(2,"请填写联系人","");
        }
        if (phone == null && "".equals(phone)){
            return new MyResult(2,"请填写联系电话","");
        }
        int i = myselfService.updateAddress( id, thId, linkMan, phone, isDefault,
                                     province, city, dist, address,cityCode);
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
