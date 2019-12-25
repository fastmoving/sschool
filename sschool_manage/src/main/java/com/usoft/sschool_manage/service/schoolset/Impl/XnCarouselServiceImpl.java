package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnCarousel;
import com.usoft.smartschool.pojo.XnCarouselExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnCarouselMapper;
import com.usoft.sschool_manage.service.schoolset.XnCarouselService;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 轮播图管理
 * @Author jijh
 * @Date 2019/5/5 10:23
 */
@Service
public class XnCarouselServiceImpl implements XnCarouselService {


    @Resource
    private XnCarouselMapper xnCarouselMapper;

    /**
     * 轮播图位置
     */
    private static byte SCHOOL_HOME = 1; //校园首页
    private static byte STU_HOME = 2; //学生首页
    private static byte TEA_HOME = 3; //教师首页

    /**
     * 轮播图端
     */
    private static byte APP  =1; //APP端
    private static byte PC = 2; //PC端


    @Override
    public MyResult listXnCarousel(Integer position) {
        XnCarouselExample xnCarouselExample = new XnCarouselExample();
        XnCarouselExample.Criteria criteria = xnCarouselExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andUidEqualTo(schoolId);
        if(!ObjectUtil.isEmpty(position)){
            int pos = position;
            criteria.andPositionEqualTo((byte)pos);
        }
        List<XnCarousel> xnCarousels = xnCarouselMapper.selectByExample(xnCarouselExample);
        if(ObjectUtil.isEmpty(xnCarousels))return MyResult.failure("暂无数据");
        List<Map<String,Object>> result = new ArrayList<>();
        for(XnCarousel xnCarousel : xnCarousels){
            Map<String,Object> map = new HashMap<>();
            map.put("id",xnCarousel.getId());
            map.put("position",xnCarousel.getPosition());
            map.put("type",xnCarousel.getType());
            map.put("imgUrl",xnCarousel.getImgurl());
            map.put("linkHtml",xnCarousel.getLinkhtml());
            map.put("createTime",TimeUtil.TimeToYYYYMMDD(xnCarousel.getCreatetime()));
            map.put("order",xnCarousel.getOrder());
            result.add(map);
        }

        return MyResult.success(result);
    }

    @Override
    public MyResult addOrUpdateXnCarousel(Integer id, Integer position, Integer type, Integer order, String imgUrl, String linkHtml) {
        if(ObjectUtil.isEmpty(position))return MyResult.failure("请选择位置");
        int posi = position;
        byte pos = (byte)posi;
        if(ObjectUtil.isEmpty(type))return MyResult.failure("请选择app或者pc端");
        int typ = type;
        byte ty = (byte)typ;
        if(ObjectUtil.isEmpty(order)) return MyResult.failure("请输入顺序");
        if(ObjectUtil.isEmpty(imgUrl))return MyResult.failure("请选择图片");
        if(ObjectUtil.isEmpty(linkHtml))return MyResult.failure("请选择链接地址");
        String message = "";
        Integer schoolId = SystemParam.getSchoolId();
        XnCarousel xnCarousel = new XnCarousel();
        xnCarousel.setCreatetime(new Date());
        xnCarousel.setType(ty);
        xnCarousel.setPosition(pos);
        xnCarousel.setImgurl(imgUrl);
        xnCarousel.setLinkhtml(linkHtml);
        xnCarousel.setOrder(order);
        xnCarousel.setUid(schoolId);
        try{
            if(ObjectUtil.isEmpty(id)){

                //xnCarousel.setCreateuser();
                xnCarouselMapper.insert(xnCarousel);
                message = "添加成功";
            }else{
                XnCarousel xnCarousel1 = xnCarouselMapper.selectByPrimaryKey(id);
                if(ObjectUtil.isEmpty(xnCarousel1))return MyResult.failure("未发现当前的轮播图。无法修改");
                xnCarousel.setId(id);
                xnCarouselMapper.updateByPrimaryKeySelective(xnCarousel);
                message = "修改成功";
            }
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("操作失败");
        }
        return MyResult.success(message);
    }

    @Override
    public MyResult deleteXnCarousel(Integer[] ids) {
        if(ObjectUtil.isEmpty(ids))return MyResult.failure("请选择要删除的数据");
        XnCarouselExample xnCarouselExample = new XnCarouselExample();
        XnCarouselExample.Criteria criteria = xnCarouselExample.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andUidEqualTo(schoolId);
        int num = 0;
        try {
            num +=xnCarouselMapper.deleteByExample(xnCarouselExample);
        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("删除失败");
        }
        return MyResult.success("成功删除了"+num+"条数据");
    }
}
