package com.usoft.sschool_student.serivice.serviceImpl;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.mapper.HlStudentinfoMapper;
import com.usoft.sschool_student.mapper.XnCarouselMapper;
import com.usoft.sschool_student.serivice.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wlw
 * @data 2019/4/23 0023-16:04
 */
@Service(value = "indexService")
public class IndexServiceImpl implements IndexService {
    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;
    @Resource
    private XnCarouselMapper xnCarouselMapper;

    /**
     * 主页显示学生信息
     * @param stuId
     * @return
     */
    @Override
    public MyResult stuIfo(Integer stuId, Integer schoolId) {
        HlStudentinfoKey hl=new HlStudentinfoKey();
        hl.setId(stuId);
        hl.setSchoolid(schoolId);
        HlStudentinfo hlStudentinfo = hlStudentinfoMapper.selectByPrimaryKey(hl);
        if (hlStudentinfo==null){
            return MyResult.failure("没找到学生的信息");
        }
        return MyResult.success(hlStudentinfo);
    }

    /**
     * 轮播图
     * @param types app=1？web=2
     * @return
     */
    @Override
    public MyResult carousel(Byte types,Byte position){
        XnCarouselExample example=new XnCarouselExample();
        example.createCriteria().andTypeEqualTo(types).andPositionEqualTo(position);
        List<XnCarousel> xnCarousels = xnCarouselMapper.selectByExample(example);
        if (xnCarousels.size()==0){
            return MyResult.failure("没找到轮播图的信息");
        }
        return MyResult.success(xnCarousels);
    }
}
