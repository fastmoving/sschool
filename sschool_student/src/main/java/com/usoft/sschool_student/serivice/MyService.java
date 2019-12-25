package com.usoft.sschool_student.serivice;

import com.usoft.smartschool.util.MyResult;

/**
 * @author wlw
 * @data 2019/4/30 0030-10:02
 */
public interface MyService {
    //查询我的收获地址
    MyResult getMyAddress(Integer stufamilyId , Byte type);
    //修改收货地址
    MyResult changeMyAddress(Integer stufamilyId,Integer addressId ,String province,String city,String dist,String address,String phone,String username,Byte isDefault,String attr1);
    //添加收货地址
    MyResult addMyAddress(Integer stufamilyId,Byte type,String province,String city,String dist,String address,String phone,String username,Byte isDefault,String attr1);
    //删除收货地址
    MyResult deleteMyAddress(String addressId );
}
