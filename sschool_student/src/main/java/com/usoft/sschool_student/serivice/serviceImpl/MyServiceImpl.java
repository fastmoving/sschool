package com.usoft.sschool_student.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_student.mapper.XnAddressMapper;
import com.usoft.sschool_student.mapper.XnAttentionMapper;
import com.usoft.sschool_student.serivice.MyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * @author wlw
 * @data 2019/4/30 0030-10:03
 */

@Service("MyService")
public class MyServiceImpl implements MyService {
    @Resource
    private XnAddressMapper xnAddressMapper;
    //查询用户的收获地址
    @Override
    public MyResult getMyAddress(Integer stufamilyId, Byte type) {
        XnAddressExample example=new XnAddressExample();
        example.createCriteria().andUseridEqualTo(stufamilyId).andTypeEqualTo(type);
        example.setOrderByClause("isDefault");
        List<XnAddress> xnAddresses = xnAddressMapper.selectByExample(example);
        if (xnAddresses.size()==0){
            return MyResult.failure("没有该用户的收获地址");
        }
        return MyResult.success(xnAddresses);
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
    @Override
    public MyResult changeMyAddress(Integer stufamilyId,Integer addressId ,String province,String city,String dist,String address,String phone,String username,Byte isDefault,String attr1) {
        XnAddress attention = xnAddressMapper.selectByPrimaryKey(addressId);
        attention.setProvince(province);
        attention.setCity(city);
        attention.setDist(dist);
        attention.setAddress(address);
        attention.setPhone(phone);
        attention.setUsername(username);
        attention.setAttr1(attr1);
        if (isDefault==1){
            XnAddressExample example=new XnAddressExample();
            example.createCriteria().andUseridEqualTo(stufamilyId).andIsdefaultEqualTo((byte)1);
            List<XnAddress> xnAddresses = xnAddressMapper.selectByExample(example);
            if (!ObjectUtil.isEmpty(xnAddresses)){
                xnAddresses.get(0).setIsdefault((byte)2);
                xnAddressMapper.updateByPrimaryKeySelective(xnAddresses.get(0));
            }
            attention.setIsdefault((byte)1);
        }else {
            attention.setIsdefault((byte)2);
        }
        int i = xnAddressMapper.updateByPrimaryKeySelective(attention);
        if (i==0){
            return MyResult.failure("用户修改收货地址失败");
        }
        XnAddressExample example=new XnAddressExample();
        example.createCriteria().andUseridEqualTo(stufamilyId);
        example.setOrderByClause("isDefault");
        List<XnAddress> xnAddresses = xnAddressMapper.selectByExample(example);
        return MyResult.success(xnAddresses);
    }

    /**
     * 添加收货地址
     * @param stufamilyId
     * @param type
     * @param province
     * @param city
     * @param dist
     * @param address
     * @param phone
     * @param username
     * @return
     */
    @Override
    public MyResult addMyAddress(Integer stufamilyId,Byte type, String province, String city, String dist, String address, String phone, String username,Byte isDefault,String attr1) {
        XnAddress at=new XnAddress();
       at.setUserid(stufamilyId);
       at.setType(type);
       at.setProvince(province);
       at.setCity(city);
       at.setDist(dist);
       at.setAddress(address);
       at.setPhone(phone);
       at.setUsername(username);
       at.setAttr1(attr1);
       at.setCreatetime(new Date());
       if (isDefault==1){
           XnAddressExample example=new XnAddressExample();
           example.createCriteria().andUseridEqualTo(stufamilyId).andTypeEqualTo(type).andIsdefaultEqualTo((byte)1);
           List<XnAddress> xnAddresses = xnAddressMapper.selectByExample(example);
           if (!ObjectUtil.isEmpty(xnAddresses)){
               xnAddresses.get(0).setIsdefault((byte)2);
               xnAddressMapper.updateByPrimaryKeySelective(xnAddresses.get(0));
           }
           at.setIsdefault((byte)1);
       }else {
           at.setIsdefault((byte)2);
       }

        int insert = xnAddressMapper.insert(at);
        if (insert!=1){
            return MyResult.failure("增加收货地址失败");
        }
        XnAddressExample example=new XnAddressExample();
        example.createCriteria().andUseridEqualTo(stufamilyId).andTypeEqualTo(type);
        example.setOrderByClause("isDefault");
        List<XnAddress> xnAddresses = xnAddressMapper.selectByExample(example);
        return MyResult.success(xnAddresses);
    }

    /**
     * 删除收货地址
     * @param addressId
     * @return
     */
    @Override
    public MyResult deleteMyAddress(String addressId) {
        String[] str=addressId.split(",");
        int delete=0;
        for (String st:str){
            delete = xnAddressMapper.deleteByPrimaryKey(Integer.valueOf(st));
        }
        if (delete==0){
            return MyResult.failure("删除收货地址失败");
        }
        return MyResult.success(delete);
    }
}
