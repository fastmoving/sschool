package com.usoft.sschool_manage.controller.live;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_manage.service.live.XnCheckNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 视频点击量
 * @Author wlw
 * @Data 2019/08/05
 */
@RestController
@RequestMapping("manage/livecheck")
public class XnCheckNumController {
    @Autowired
    private XnCheckNumService xnCheckNumService;

    /**
     * 总点击量
     * @return
     */
    @GetMapping("/totalNum")
    public MyResult totalNum(){
        return xnCheckNumService.totalNum();
    }

    /**
     * 按日，月，年排序
     * @param time
     * @param videoType
     * @return
     */
    @GetMapping("/liveRank")
    public MyResult liveRank(String time,Integer videoType){
        return xnCheckNumService.liveRank(time,videoType);
    }

    /**
     * 按周查询排行
     * @param start
     * @param end
     * @param videoType
     * @return
     */
    @GetMapping("/RankByWeek")
    public MyResult RankByWeek(String start,String end,Integer videoType){
        return xnCheckNumService.RankByWeek(start,end,videoType);
    }

    /**
     * 直播点播视频缴费记录列表
     * @param classId
     * @param stuName
     * @param phone
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/lists")
    public MyResult list(Integer classId,String stuName,String phone,Integer pageNo,Integer pageSize){
        return xnCheckNumService.list(classId, stuName, phone, pageNo, pageSize);
    }

    /**
     * 精品课堂缴费列表
     * @param classId
     * @param stuName
     * @param phone
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("fineList")
    public MyResult fineList(Integer classId,String stuName,String phone,Integer pageNo,Integer pageSize){
        return xnCheckNumService.fineList(classId, stuName, phone, pageNo, pageSize);
    }
    /**
     * 授权视频或线下购买
     * @param stuId 学生id
     * @param cid   学生班级id
     * @param videoType 1、直播点播 2、精品课堂
     * @param payType 2、线下购买 3、授权
     * @return
     */
    @PostMapping("EmpVideo")
    public MyResult EmpVideo(Integer stuId, Integer cid, Integer videoType, String list, Integer payType){
        return xnCheckNumService.EmpVideo(stuId, cid, videoType, list,payType);
    }

    /**
     * 根据学校班级查询学生信息
     * @param sid
     * @param cid
     * @return
     */
    @GetMapping("allStu")
    public MyResult allStu(Integer sid,Integer cid){
        return xnCheckNumService.allStu(sid, cid);
    }
    @GetMapping("fineClassList")
    public MyResult fineClassList(Integer pageNo,Integer pageSize){
        return xnCheckNumService.fineClassList(pageNo, pageSize);
    }
}
