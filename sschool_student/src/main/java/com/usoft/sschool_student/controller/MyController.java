package com.usoft.sschool_student.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.serivice.MyService;
import com.usoft.sschool_student.util.SystemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wlw
 * @data 2019/4/30 0030-10:00
 */
@RequestMapping("/student/my")
@CrossOrigin
@Controller
@ResponseBody
public class MyController {
    @Resource
    private MyService myService;

    /**
     * 查看收货地址
     * @return
     */
    @GetMapping("getMyAddress")
    private MyResult getMyAddress(){
        Integer userId = SystemParam.getUserId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return myService.getMyAddress(userId,type);
    }

    /**
     * 修改收货地址
     * @param addressId
     * @param province
     * @param city
     * @param dist
     * @param address
     * @param phone
     * @param username
     * @return
     */
    @PostMapping("changeMyAddress")
    public MyResult changeMyAddress(Integer addressId ,String province,String city,String dist,String address,String phone,String username,Byte isDefault,String attr1){
        Integer userId = SystemParam.getUserId();
        return myService.changeMyAddress(userId,addressId,province,city,dist,address,phone,username,isDefault,attr1);
    }

    /**
     * 添加收货地址
     * @param province
     * @param city
     * @param dist
     * @param address
     * @param phone
     * @param username
     * @return
     */
    @PostMapping("addMyAddress")
    public MyResult addMyAddress(String province,String city,String dist,String address,String phone,String username,Byte isDefault,String attr1){
        Integer userId = SystemParam.getUserId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return myService.addMyAddress(userId,type,province,city,dist,address,phone,username,isDefault,attr1);
    }

    /**
     * 删除收货地址
     * @param addressId
     * @return
     */
    @PostMapping("/deleteMyAddress")
    public MyResult deleteMyAddress(String addressId ){
        return myService.deleteMyAddress(addressId);
    }
}
