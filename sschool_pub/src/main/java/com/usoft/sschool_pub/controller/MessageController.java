package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.serivice.MessageService;
import com.usoft.sschool_pub.util.SystemParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("pub/message")
public class MessageController {
    @Resource
    private MessageService messageService;
    //我的消息通知
    @GetMapping("myNotice")
    public MyResult myNotice(){
        Integer type = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        return messageService.myNotice(type,schoolId);
    }

    /**
     * 标记消息通知为已读
     * @param mid
     * @return
     */
    @PostMapping("changeMessageState")
    public MyResult changeMessageState(Integer mid){
        return messageService.changeMessageState(mid);
    }

    /**
     * 删除消息
     * @param mid
     * @return
     */
    @PostMapping("deleteMessage")
    public MyResult deleteMessage(Integer mid){
        return messageService.deleteMessage(mid);
    };

    /**
     * 按消息类型删除消息
     * @param messageType
     * @return
     */
    @PostMapping("deleteByType")
    public MyResult deleteByType(Integer messageType){
        return messageService.deleteByType(messageType);
    }
    @GetMapping("messageInfo")
    public MyResult messageInfo(Integer mid){
        Integer schoolId = SystemParam.getSchoolId();
        return messageService.messageInfo(schoolId,mid);
    }
    /**
     * 通讯录列表
     * @param classId
     * @return
     */
    @GetMapping("mailList")
    public MyResult mailList(Integer classId){
        if(classId == null){
            return MyResult.failure("您没有班级");
        }
        Integer type = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        return messageService.mailList(classId,type,schoolId);
    }
    /**
     * 查询班级群聊消息
     * @param classId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("classChat")
    public MyResult classChat(Integer classId,Integer pageNo,Integer pageSize){
        Integer type = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        return messageService.classChat(classId,type,schoolId,pageNo,pageSize);
    }

    /**
     * 发消息
     * @param classId
     * @param content
     * @return
     */
    @PostMapping("addMessage")
    public MyResult addMessage(Integer classId,String content){
        Integer type = SystemParam.getType();
        Integer schoolId = SystemParam.getSchoolId();
        return messageService.addMessage(classId,content,type,schoolId);
    }
}
