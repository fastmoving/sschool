package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.HlEnum;
import com.usoft.smartschool.pojo.HlEnumExample;
import com.usoft.smartschool.pojo.HlEnumitem;
import com.usoft.smartschool.pojo.HlEnumitemExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.HlEnumMapper;
import com.usoft.sschool_manage.mapper.base.HlEnumitemMapper;
import com.usoft.sschool_manage.service.schoolset.BasicDataSelectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基本数据选择接口
 * @Author jijh
 * @Date 2019/4/23 14:58
 */
@Service
public class BasicDataSelectServiceImpl implements BasicDataSelectService {

    @Resource
    private HlEnumMapper hlEnumMapper;

    @Resource
    private HlEnumitemMapper hlEnumitemMapper;

    @Override
    public MyResult findAll(Integer pageNo, Integer pageSize) {
        if(ObjectUtil.isEmpty(pageNo)) pageNo = 1;
        if(ObjectUtil.isEmpty(pageSize)) pageSize = 20;
        HlEnumExample hlEnumExample = new HlEnumExample();
        HlEnumExample.Criteria criteria = hlEnumExample.createCriteria();
        //hlEnumExample.
        List<HlEnum> hlEnums = hlEnumMapper.findByPages((pageNo-1)*pageSize,pageSize);
        Integer totalNumber = hlEnumMapper.countByExample(null);
        Integer totalPage = totalNumber%pageSize ==0? totalNumber/pageSize : totalNumber/pageSize+1;
        return MyResult.success("查询成功",hlEnums,pageNo,totalPage,pageSize,totalNumber);
    }

    @Override
    public MyResult findByEnumId(Integer enumId) {
        if(ObjectUtil.isEmpty(enumId))return MyResult.failure("请选择要查看的类型");
        HlEnumitemExample hlEnumitemExample = new HlEnumitemExample();
        HlEnumitemExample.Criteria criteria = hlEnumitemExample.createCriteria();
        criteria.andEnumidEqualTo(enumId);
        List<HlEnumitem> hlEnumitems = hlEnumitemMapper.selectByExample(hlEnumitemExample);
        if(ObjectUtil.isEmpty(hlEnumitems))return MyResult.failure("暂无数据");

        return MyResult.success(hlEnumitems);
    }
}
