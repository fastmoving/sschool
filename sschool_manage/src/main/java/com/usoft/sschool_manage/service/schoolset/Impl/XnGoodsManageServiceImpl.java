package com.usoft.sschool_manage.service.schoolset.Impl;


import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.HlTeacherMapper;
import com.usoft.sschool_manage.mapper.base.XnGoodsManageMapper;
import com.usoft.sschool_manage.mapper.base.XnGoodsTypeMapper;
import com.usoft.sschool_manage.service.schoolset.XnGoodsManageService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @Author jijh
 * @Date 2019/4/29 16:33
 */
@Service
public class XnGoodsManageServiceImpl implements XnGoodsManageService {


    @Resource
    private XnGoodsManageMapper xnGoodsManageMapper;

    @Resource
    private XnGoodsTypeMapper xnGoodsTypeMapper;

    @Resource
    private HlTeacherMapper hlTeacherMapper;
    /**
     * 在线商城
     */
    private static int ONLINE_MALL=1;

    /**
     * 积分商城
     */
    private static int INTEGRAL_MALL=2;
    /**
     * 配送
     */
    private static int DELIVER = 1;
    /**
     * 不配送
     */
    private static int UNDELIVER = 2;
    /**
     * 上架
     */
    private static String USE = "1";

    /**
     * 下架
     */
    private static String UNUSE = "2";

    @Override
    public MyResult listXnGoodsManage(String goodsName, Integer type, Integer status, String priceBegin, String priceEnd, Integer pageNo, Integer pageSize) {
        Integer schoolId = SystemParam.getSchoolId();
        XnGoodsManageExample xnGoodsManageExample = new XnGoodsManageExample();
        XnGoodsManageExample.Criteria criteria = xnGoodsManageExample.createCriteria();
        criteria.andUidEqualTo(schoolId);//查询本学校的商店
        if(!ObjectUtil.isEmpty(goodsName)) criteria.andGoodsnameLike("%"+goodsName+"%");
        if(!ObjectUtil.isEmpty(type)){
            XnGoodsTypeExample xnGoodsTypeExample = new XnGoodsTypeExample();
            XnGoodsTypeExample.Criteria criteria1 = xnGoodsTypeExample.createCriteria();
            int t = type;
            criteria1.andTypeEqualTo((byte)t);
            List<XnGoodsType> xnGoodsTypes = xnGoodsTypeMapper.selectByExample(xnGoodsTypeExample);
            if(!ObjectUtil.isEmpty(xnGoodsTypes)){
                List<Integer> typeIds = new ArrayList<>();
                for(XnGoodsType xnGoodsType : xnGoodsTypes){
                    typeIds.add(xnGoodsType.getId());
                }
                criteria.andTypeidIn(typeIds);
            }
        }
        if(!ObjectUtil.isEmpty(priceBegin)){
            BigDecimal bprice = new BigDecimal(priceBegin);
            criteria.andPriceGreaterThanOrEqualTo(bprice);
        }
        if(!ObjectUtil.isEmpty(priceEnd)){
            BigDecimal eprice = new BigDecimal(priceEnd);
            criteria.andPriceLessThanOrEqualTo(eprice);
        }
        if(!ObjectUtil.isEmpty(status)){
            criteria.andStatusEqualTo(String.valueOf(status));
        }
        List<XnGoodsManage> xnGoodsManages = xnGoodsManageMapper.selectByExample(xnGoodsManageExample);
        if(ObjectUtil.isEmpty(xnGoodsManages))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(XnGoodsManage xnGoodsManage : xnGoodsManages){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnGoodsManage.getId());
            XnGoodsType xnGoodsType = xnGoodsTypeMapper.selectByPrimaryKey(xnGoodsManage.getTypeid());
            map.put("type",xnGoodsType.getName());
            map.put("typeId",xnGoodsManage.getTypeid());
            map.put("pid",xnGoodsType.getPid());
            map.put("status",USE.equals(xnGoodsManage.getStatus()) ? "上架" : "下架");
            map.put("price",xnGoodsManage.getPrice()==null ? xnGoodsManage.getIntegral():xnGoodsManage.getPrice());
            map.put("integrel",xnGoodsManage.getIntegral() !=null ? "是" :"否");
            map.put("number",xnGoodsManage.getNumber());
            map.put("name",xnGoodsManage.getGoodsname());
            map.put("desImg",xnGoodsManage.getDesimg());
            map.put("thumbnail",xnGoodsManage.getThumbnail());
            map.put("isDeliver",xnGoodsManage.getIsdeliver());
            map.put("description",xnGoodsManage.getDescription());
            result.add(map);
        }


        return ResultPage.getPageResult(result,pageNo, pageSize);
    }

    @Override
    public MyResult addOrUpdateGoodsManage(Integer id, Integer typeId, String name, String price, Integer number, Integer status, Integer isDeliver,
                                           String thumbnail, String desImg, String description) {
        Integer schoolId = SystemParam.getSchoolId();
        XnGoodsManage xnGoodsManage = new XnGoodsManage();
        if(ObjectUtil.isEmpty(typeId))return MyResult.failure("请输入商品类型");xnGoodsManage.setTypeid(typeId);
        if(ObjectUtil.isEmpty(name))return MyResult.failure("请输入商品名字");xnGoodsManage.setGoodsname(name);
        if(ObjectUtil.isEmpty(number))return MyResult.failure();xnGoodsManage.setNumber(number);
        if(ObjectUtil.isEmpty(price))return MyResult.failure("请输入价格");
        XnGoodsType xnGoodsType = xnGoodsTypeMapper.selectByPrimaryKey(typeId);
        if(xnGoodsType.getType() == ONLINE_MALL){
            try{
                BigDecimal priceDecimal = new BigDecimal(price);
                xnGoodsManage.setPrice(priceDecimal);
            }catch (Exception e){
                e.printStackTrace();
                return MyResult.failure("价格格式错误");
            }

        }else{
           try{
               xnGoodsManage.setIntegral(Integer.valueOf(price));
           }catch (Exception e){
               e.printStackTrace();
               return MyResult.failure("价格格式错误");
           }
        }
        if(ObjectUtil.isEmpty(status))return MyResult.failure("请选择商品是否上架");xnGoodsManage.setStatus(String.valueOf(status));
        if(ObjectUtil.isEmpty(isDeliver))return MyResult.failure("是否配送");xnGoodsManage.setIsdeliver(Byte.valueOf(String.valueOf(isDeliver)));
        if(!ObjectUtil.isEmpty(thumbnail)){
            if(thumbnail.contains("http")){
                String thum = thumbnail.substring(thumbnail.indexOf("sschoolManageFile"));
                xnGoodsManage.setThumbnail(thum);
            }else{
                xnGoodsManage.setThumbnail(thumbnail);
            }
        }

//        if(!ObjectUtil.isEmpty(idImg)){
//            map.put("idImg",idImg.substring(idImg.indexOf("sschoolManageFile")));
//        }
//        if(!ObjectUtil.isEmpty(faceImg)){
//            map.put("faceImg",faceImg.substring(idImg.indexOf("sschoolManageFile")));
//        }

        if(!ObjectUtil.isEmpty(desImg)){
            String[] desImgs = desImg.split(",");
            List<String> desImgsN = new ArrayList<>();
            for(String des : desImgs){
                if(des.contains("http")){
                    desImgsN.add(des.substring(des.indexOf("sschoolManageFile")));
                }else{
                    desImgsN.add(des);
                }
            }
            xnGoodsManage.setDesimg(String.join(",",desImgsN));
        }
        if(!ObjectUtil.isEmpty(description))xnGoodsManage.setDescription(description);
        String message = "";
        try{
            if(ObjectUtil.isEmpty(id)){
                xnGoodsManage.setCreatetime(new Date());
                xnGoodsManage.setUid(schoolId);
                xnGoodsManageMapper.insert(xnGoodsManage);

                message = "添加成功";
            }else{
                xnGoodsManage.setId(id);
                XnGoodsManage xnGoodsManage1 = xnGoodsManageMapper.selectByPrimaryKey(id);
                if(ObjectUtil.isEmpty(xnGoodsManage1))return MyResult.failure("未知的商品，无法修改");
                xnGoodsManageMapper.updateByPrimaryKeySelective(xnGoodsManage);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }


        return MyResult.success(message);
    }

    @Override
    public MyResult updateStatus(Integer id, Integer status) {
        if(ObjectUtil.isEmpty(id))return MyResult.failure("请选择要修改的商品");
        if(ObjectUtil.isEmpty(status))return MyResult.failure("请选择上架还是下架");
        if(status<1 || status >2)return MyResult.failure("未知的上下架类型");
        XnGoodsManage xnGoodsManage = xnGoodsManageMapper.selectByPrimaryKey(id);
        if(ObjectUtil.isEmpty(xnGoodsManage))return MyResult.failure("当前商品不存在，无法修改");
        String statusStr = String.valueOf(status);
        xnGoodsManage.setStatus(statusStr);
        xnGoodsManageMapper.updateByPrimaryKeySelective(xnGoodsManage);
        return MyResult.success("操作成功");
    }

    @Override
    public MyResult deleteXnGoodsManage(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        XnGoodsManageExample xnGoodsManageExample = new XnGoodsManageExample();
        XnGoodsManageExample.Criteria criteria = xnGoodsManageExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andUidEqualTo(schoolId);
        criteria.andIdIn(Arrays.asList(ids));
        int num = 0;
        try{
            num = xnGoodsManageMapper.deleteByExample(xnGoodsManageExample);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除"+num+"条数据");
    }

    @Override
    public MyResult selectXnGoodsManage(Integer id) {
        Integer schoolId = SystemParam.getSchoolId();
        if(ObjectUtil.isEmpty(id))return MyResult.failure("请选择要查看的商品");
        XnGoodsManage xnGoodsManage = xnGoodsManageMapper.selectByPrimaryKey(id);
        if(ObjectUtil.isEmpty(xnGoodsManage)){
            return MyResult.failure("当前商品不存在");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",xnGoodsManage.getId());
        map.put("name",xnGoodsManage.getGoodsname());
        map.put("number",xnGoodsManage.getNumber());
        map.put("typeId",xnGoodsManage.getTypeid());
        XnGoodsType xnGoodsType =xnGoodsTypeMapper.selectByPrimaryKey(xnGoodsManage.getTypeid());
        map.put("typeName",xnGoodsType.getName());
        map.put("mallType",xnGoodsType.getType());
        if(xnGoodsType.getType()==ONLINE_MALL){
            map.put("price",xnGoodsManage.getPrice());
        }else{
            map.put("price",xnGoodsManage.getIntegral());
        }
        map.put("status",xnGoodsManage.getStatus());
        map.put("isdeliver",xnGoodsManage.getIsdeliver());
        map.put("thumbnail",xnGoodsManage.getThumbnail());
        map.put("desImg",xnGoodsManage.getDesimg());
        map.put("description",xnGoodsManage.getDescription());
        map.put("createTime",xnGoodsManage.getCreatetime());

        map.put("addUser",xnGoodsManage.getUid());
        HlTeacherKey hlTeacherKey = new HlTeacherKey();
        hlTeacherKey.setId(xnGoodsManage.getUid());
        hlTeacherKey.setSchoolid(schoolId);
        HlTeacher hlTeacher = hlTeacherMapper.selectByPrimaryKey(hlTeacherKey);
        map.put("username",hlTeacher.getTname());
        return MyResult.success(map);
    }

//    public static void main(String[] args){
//        String a = "http://one/sschoolManageFile/111.jpg,http://two/sschoolManageFile/222.jpg,http://three/sschoolManageFile/333.jpg";
//        List<String> de = new ArrayList<>();
//        if(!ObjectUtil.isEmpty(a)){
//            String[] desImgs = a.split(",");
//            for(String des : desImgs){
//                if(des.contains("http")){
//                    de.add(des.substring(des.indexOf("sschoolManageFile")));
//                }
//            }
//            System.out.println(String.join(",",de));
//        }
//    }

}
