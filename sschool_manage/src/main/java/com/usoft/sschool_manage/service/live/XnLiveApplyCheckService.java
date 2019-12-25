package com.usoft.sschool_manage.service.live;

import com.usoft.smartschool.util.MyResult;

/**
 * 视频审核规则
 * @Author jijh
 * @Date 2019/5/23 18:31
 */
public interface XnLiveApplyCheckService {


    /**
     * 视频审核列表
     * @param cid 视频班级id
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @return
     */
    MyResult listXnLiveApplyCheck(Integer sid,Integer cid, Integer pageNo,
                                  Integer pageSize);


    /**
     * 视频审核列表详情
     * @param sid 学校id
     * @param cid 班级id
     * @return
     */
    MyResult listXnLiveApplyCheckDetail(Integer sid, Integer cid,Integer pageNo, Integer pageSize);


    /**
     * 审核视频
     * @param ids 视频id(数组)
     * @param status 审核状态 2.通过 3.驳回
     * @return
     */
    MyResult checkTheLiveApply(Integer[] ids, Integer status);

}
