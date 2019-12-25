package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.serivice.SchProService;
import com.usoft.sschool_pub.util.SystemParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wlw
 * @data 2019/4/24 0024-15:18
 */
@Controller
@RequestMapping("/pub/schoolInfo")
@ResponseBody
@CrossOrigin
public class SchProController {
    private static final Logger log= LoggerFactory.getLogger(LoginController.class);
    @Resource
    private SchProService schProService;

    /**
     * 轮播图
     * @param type 位置 1.校园首页 2.学生端首页 3.教师端首页
     * @param position  	方向 1.app 2.web
     * @return
     */
    @GetMapping("carousel")
    public MyResult carousel(Integer schoolId,Byte type,Byte position){
//        if (type==null||position==null||type>3||position>2){
//            return MyResult.failure("参数错误");
//        }
        if (schoolId==null) {
            if (SystemParam.getSchoolId() == null) {
                schoolId = 1;
            }else {
                schoolId = SystemParam.getSchoolId();
            }
        }
        return schProService.carousel(schoolId,type,position);
    }
    /**
     * 学校简介
     * @return
     */
    @GetMapping("/schoolInf")
    public MyResult schoolInfo(Integer schoolId){
        if (schoolId==null) {
            if (SystemParam.getSchoolId() == null) {
                schoolId = 1;
            }else {
                schoolId = SystemParam.getSchoolId();
            }
        }
        return schProService.schoolInfo(schoolId);
    }

    /**
     * 学校公告
     * @return
     */
    @GetMapping("/announce")
    public MyResult announce(Integer schoolId,Integer pageNo,Integer pageSize){
        if (schoolId==null) {
            if (SystemParam.getSchoolId() == null) {
                schoolId = 1;
            }else {
                schoolId = SystemParam.getSchoolId();
            }
        }
        return schProService.announce(schoolId,pageNo,pageSize);
    }

    /**
     * 公告详情
     * @param aid
     * @return
     */
    @GetMapping("announceInfo")
    public MyResult announceInfo(Integer aid){
        return schProService.announceInfo(aid);
    }
    /**
     * 教师风采
     * @return
     */
    @GetMapping("/teacherApperance")
    public MyResult teacherApperance(Integer schoolId,Integer pageNo,Integer pageSize){
        if (schoolId==null) {
            if (SystemParam.getSchoolId() == null) {
                schoolId = 1;
            }else {
                schoolId = SystemParam.getSchoolId();
            }
        }
        return schProService.teacherApperance(schoolId,pageNo,pageSize);
    }

    /**
     * 兴趣班列表
     * @return
     */
    @GetMapping("/intClass")
    public MyResult intClass(Integer schoolId,Integer pageNo,Integer pageSize){
        if (schoolId==null) {
            if (SystemParam.getSchoolId() == null) {
                schoolId = 1;
            }else {
                schoolId = SystemParam.getSchoolId();
            }
        }
        return schProService.intClass(schoolId,pageNo,pageSize);
    }

    /**
     * 兴趣班详情
     * @param intrestClassId
     * @return
     */
    @GetMapping("/intrestClassInfo")
    public MyResult intrestClassInfo(Integer schoolId,Integer intrestClassId){
        if (intrestClassId==null){
            return MyResult.failure("请选择兴趣班");
        }
        if (schoolId==null) {
            if (SystemParam.getSchoolId() == null) {
                schoolId = 1;
            }else {
                schoolId = SystemParam.getSchoolId();
            }
        }
        return schProService.intrestClassInfo(intrestClassId,schoolId);
    }

    /**
     * 兴趣班沟通查询聊天记录
     * @param IntrestId
     * @return
     */
    @PostMapping("/searchCommunicate")
    public MyResult communicate(Integer IntrestId){
        if (IntrestId==null){
            return MyResult.failure("参数错误");
        }
        return schProService.communicate(IntrestId);
    }

    /**
     * 兴趣班学生发送信息
     * @param IntrestId
     * @param content
     * @return
     */
    @PostMapping("/addcommunicate")
    public MyResult addcommunicate(Integer IntrestId,String content){
        if (IntrestId==null||content==null){
            return MyResult.failure("参数错误");
        }
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return schProService.addcommunicate(IntrestId,content,schoolId,childId);
    }
    @GetMapping("teaInterstId")
    public MyResult teaInterstId(){
        return schProService.teaInterstId();
    }
    /**
     * 教师回复兴趣班的学生家长咨询问题
     * @param IntrestId
     * @param recevieId
     * @param content
     * @return
     */
    @PostMapping("/teaReply")
    public MyResult teaReply(Integer IntrestId,Integer recevieId,String content){
        return schProService.teaReply(IntrestId,recevieId,content);
    }

    /**
     * 教师查看兴趣班聊天列表
     * @return
     */
    @GetMapping("teaMsgList")
    public MyResult teaMsgList(){
        return schProService.teaMsgList();
    }

    /**
     * 教师查询单个聊天记录
     * @param IntrestId
     * @param userId 学生id
     * @return
     */
    @PostMapping("/teaSearchChat")
    public MyResult teaSearchChat(Integer IntrestId,Integer userId ){
        return schProService.teaSearchChat(IntrestId,userId);
    }
    /**
     * 兴趣班报名表
     * @param entryType
     * @param icid
     * @return
     */
    @PostMapping("enterClass")
    public MyResult enterClass(Integer entryType,Integer icid){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return schProService.enterClass(schoolId,childId,entryType,icid);
    }

    /**
     * 兴趣班根据订单id查询订单详情
     * @param orderId
     * @return
     */
    @GetMapping("searchOrder")
    public MyResult searchOrder(Integer orderId){
        return schProService.searchOrder(orderId);
    }
    /**
     * 支付成功后改变订单状态
     * @param ieid
     * @return
     */
    @PostMapping("changeOrder")
    public MyResult changeOrder(Integer ieid){
        return schProService.changeOrder(ieid);
    }
}
