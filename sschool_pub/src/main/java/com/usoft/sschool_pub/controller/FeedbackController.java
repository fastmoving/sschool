package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.serivice.FeedbackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("pub/feedback")
public class FeedbackController {
    @Resource
    private FeedbackService feedbackService;

    /**
     * 查询消息记录列表
     * @return
     */
    @GetMapping("searchMsg")
    public MyResult searchMsg(){
        return feedbackService.searchMsg();
    }
    /**
     * 单个聊天信息记录
     * @param acpType
     * @return
     */
    @GetMapping("oneMeg")
    public MyResult oneMeg(Integer acpType,Integer acpId){
        return feedbackService.oneMeg(acpType,acpId);
    }
    /**
     * 发送消息
     * @param content
     * @return
     */
    @PostMapping("sendMsg")
    public MyResult sendMsg(String content,Integer acpType,Integer acpId){
        return feedbackService.sendMsg(content,acpType,acpId);
    }

    @PostMapping("delete")
    public MyResult delete(Integer acpType,Integer acpId ){
        return feedbackService.delete(acpType, acpId);
    }
}
