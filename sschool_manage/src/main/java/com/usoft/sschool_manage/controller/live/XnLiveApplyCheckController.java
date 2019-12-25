package com.usoft.sschool_manage.controller.live;

import com.usoft.sschool_manage.mapper.base.XnVideoCheckMapper;
import com.usoft.sschool_manage.service.live.XnLiveApplyCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 视频审核
 * @Author jijh
 * @Date 2019/5/24 14:51
 */
@RestController
@RequestMapping("manage/livecheck")
public class XnLiveApplyCheckController {


    @Autowired
    private XnLiveApplyCheckService xnLiveApplyCheckService;

    /**
     * 视频审核列表
     * @param cid 视频班级id
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    @GetMapping("list")
    public Object listXnLiveApplyCheck(Integer sid,Integer cid, Integer pageNo,
                                       Integer pageSize){
        return xnLiveApplyCheckService.listXnLiveApplyCheck(sid,cid, pageNo, pageSize);

    }


    /**
     * 视频列表
     * @param sid 学校id
     * @param cid 班级id
     * @return
     */
    @GetMapping("listdetail")
    public Object listXnLiveApplyCheckDetail(Integer sid, Integer cid , Integer pageNo, Integer pageSize){
        return xnLiveApplyCheckService.listXnLiveApplyCheckDetail(sid, cid, pageNo, pageSize);
    }


    /**
     * 审核视频
     * @param ids 视频id(数组)
     * @param status 审核状态 2.通过 3.驳回
     * @return
     */
    @PostMapping("check")
    public Object checkXnLiveApplyCheckDetail(Integer[] ids, Integer status){
        return xnLiveApplyCheckService.checkTheLiveApply(ids,status);
    }
}
