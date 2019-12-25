package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.CfDepartmentMapper;
import com.usoft.sschool_manage.service.schoolset.CfDepartmentService;
import com.usoft.sschool_manage.util.ResultPage;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/4/22 18:31
 */

@Service
public class CfDepartmentServiceImpl implements CfDepartmentService {

    @Resource
    private CfDepartmentMapper CfDepartmentMapper;

    @Override
    public MyResult listAllSchool(String name,Integer pageNo, Integer pageSize) {
        if(pageNo == null) pageNo = 1;
        if(pageSize == null) pageSize = 20;
        Integer schoolId = SystemParam.getSchoolId();
        List<Map<String,Object> > allSchool = CfDepartmentMapper.listAllSchool(name,schoolId);
        if(ObjectUtil.isEmpty(allSchool)){
            return MyResult.failure("暂无数据");
        }

        return ResultPage.getPageResult(allSchool,pageNo,pageSize);
    }
}
