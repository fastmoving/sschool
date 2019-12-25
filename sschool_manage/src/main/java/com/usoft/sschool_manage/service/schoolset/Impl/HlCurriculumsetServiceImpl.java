package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.HlCurriculumset;
import com.usoft.smartschool.pojo.HlCurriculumsetExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.HlCurriculumsetMapper;
import com.usoft.sschool_manage.service.schoolset.HlCurriculumsetService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author jijh
 * @Date 2019/4/23 16:43
 */
@Service
public class HlCurriculumsetServiceImpl implements HlCurriculumsetService {

    @Resource
    private HlCurriculumsetMapper hlCurriculumsetMapper;

    /**
     * 某个学校的课时列表
     * @param sid
     * @return
     */
    @Override
    public MyResult listCurriculumsetMessage(Integer sid) {
        HlCurriculumsetExample hlCurriculumsetExample = new HlCurriculumsetExample();
        HlCurriculumsetExample.Criteria criteria = hlCurriculumsetExample.createCriteria();
        Integer schoolId = SystemParam.getSchoolId();
        criteria.andSchoolidEqualTo(schoolId);
        List<HlCurriculumset> hlCurriculumsets = hlCurriculumsetMapper.selectByExample(hlCurriculumsetExample);
        if(ObjectUtil.isEmpty(hlCurriculumsets)){
            return MyResult.failure("暂无数据");
        }
        List<Map<String,Object>> result = new ArrayList<>();
        for(HlCurriculumset hlCurriculumset : hlCurriculumsets){
            Map<String,Object> map = new HashMap<>();
            map.put("id",hlCurriculumset.getId());
            map.put("name",hlCurriculumset.getLesson());
            map.put("time",hlCurriculumset.getCurrstart()+"-"+hlCurriculumset.getCurrend());
           // map.put("end",hlCurriculumset.getCurrend());
            result.add(map);
        }
        return MyResult.success(result);
    }
}
