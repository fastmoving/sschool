package com.usoft.sschool_pub.controller;

import com.google.zxing.WriterException;
import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.mapper.XnOrderCountMapper;
import com.usoft.sschool_pub.serivice.TermianlService;
import com.usoft.sschool_pub.util.ExportExcelUtil;
import com.usoft.sschool_pub.util.QrCodeUtil;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.TimeUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * @author wlw
 * @data 2019/4/25 0025-17:32
 */
@ResponseBody
@Controller
@RequestMapping("/pub/termianl")
@CrossOrigin
public class TermianlController {
    private static final Logger logger = Logger.getLogger(TermianlController.class);
    @Resource
    private TermianlService termianlService;
    @Resource
    private XnOrderCountMapper xnOrderCountMapper;
    /**
     * 查询所有的乡镇和学校
     * @param schoolId
     * @return
     */
    @GetMapping("conditions")
    public MyResult conditions(Integer schoolId){
        return termianlService.conditions(schoolId);
    }

    /**
     * 根据学校id查询班级
     * @param schoolId
     * @return
     */
    @GetMapping("searchClass")
    public MyResult searchClass(Integer schoolId){
        return termianlService.searchClass(schoolId);
    }
    @GetMapping("allTeacherInfo")
    public MyResult allTeacherInfo(Integer schoolId,Integer classId){
        return termianlService.allTeacherInfo(schoolId,classId);
    }
    /**
     * 添加关注
     * @param videoId
     * @param states    0点播视频，1直播，2名师讲堂，3代表取消关注
     * @return
     */
    @PostMapping("/addAttention")
    public MyResult addAttention(Integer videoId,Integer states){
        return termianlService.addAttention(videoId,states);
    }

    /**
     * 取消关注
     * @param videoId
     * @param states
     * @return
     */
    @PostMapping("/deleteAttention")
    public MyResult deleteAttention(Integer videoId,Integer states){
        return termianlService.deleteAttention(videoId, states);
    }
    /**
     * 查询自己关注的视频
     * @param states    0点播视频，1直播，2名师讲堂，3代表取消关注
     * @return
     */
    @GetMapping("/myAttention")
    public MyResult myAttention(Integer states,Integer pageNo,Integer pageSize){
        return termianlService.myAttention(states,pageNo,pageSize);
    }

    /**
     * 查询学校的所有直播
     * @return
     */
    @GetMapping("/searchVideo")
    public MyResult searchVideo(Integer pageNo,Integer pageSize){
        Integer schoolId = SystemParam.getSchoolId();
        return termianlService.searchVideo(schoolId,pageNo,pageSize);
    }

    /**
     * 根据班级查询直播视频地址
     * @param classId
     * @return
     */
    @GetMapping("/classVideo")
    public MyResult classVideo(String classId,String schoolId,String conutyId,Integer pageNo,Integer pageSize){
        return termianlService.classVideo(classId, schoolId,conutyId,pageNo,pageSize);
    }


    /**
     * 试用
     * @param videoClassId
     * @return
     */
    @PostMapping("addTestDays")
    public MyResult addTestDays(Integer videoClassId,Integer videoSchoolId){
        return termianlService.addTestDays(videoClassId,videoSchoolId);
    }

    /**
     * 申请观看视频
     * @param videoClassId 视频所属班级
     * @param videoSchoolId  视频所属学校
     * @return
     */
    @PostMapping("videoCheck")
    public MyResult videoCheck(Integer videoClassId,Integer videoSchoolId){
        return termianlService.videoCheck(videoClassId, videoSchoolId);
    }

    /**
     * 查看点播、直播视频收费信息
     * @param type
     * @param videoId
     * @param videoClassId
     * @param videoSchoolId
     * @return
     */
    @GetMapping("/videoPrice")
    public MyResult videoPrice(Integer type,Integer videoId,Integer videoClassId,Integer videoSchoolId){
        return termianlService.videoPrice(type, videoId, videoClassId, videoSchoolId);
    }

    /**
     * 保存精品课堂订单
     * @param videoId
     * @param price
     * @return
     */
    @PostMapping("savePointFocusOrder")
    public MyResult savePointFocusOrder(Integer videoId,String price){
        return termianlService.savePointFocusOrder(videoId,price);
    }
    /**
     * 保存视频订单
     * @param type 1、点播直播 2精品课堂
     * @param videoId
     * @param videoClassId
     * @param videoSchoolId
     * @param price
     * @return
     */
    @PostMapping("saveVideoOrder")
    public MyResult saveVideoOrder(Integer type,Integer num,Integer videoId,Integer videoClassId,Integer videoSchoolId,String price){
        return termianlService.saveVideoOrder(type,num, videoId, videoClassId, videoSchoolId, price);
    }
    /**
     * 查询该学校的所有点播视频
     * @return
     */
    @GetMapping("/allPrg")
    public MyResult allPrg(Integer schoolId,Integer pageNo,Integer pageSize){
        if (schoolId==null){
            schoolId = SystemParam.getSchoolId();
        }
        return termianlService.allPrg(schoolId,pageNo,pageSize);
    }

    /**
     * 筛选点播视频
     * @param classId
     * @param schoolId
     * @param conutyId
     * @param teaId
     * @param subId
     * @param timeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("screenPrg")
    public MyResult screenPrg(String classId, String schoolId, String conutyId,String teaId,String subId,String timeId, Integer pageNo, Integer pageSize){
        return termianlService.screenPrg(classId,schoolId,conutyId,teaId,subId,timeId,pageNo,pageSize);
    }

    /**
     * 点播、直播视频权限判断
     * @param videoId
     * @param videoType 1、点播 2、直播
     * @param videoSchoolId
     * @param videoClassId
     * @return
     */
    @GetMapping("hasRule")
    public MyResult isRule(Integer videoId,Integer videoType,Integer videoSchoolId,Integer videoClassId){
        return termianlService.isRule(videoId,videoType,videoSchoolId,videoClassId);
    }

    /**
     * 精品课堂筛选条件
     * @return
     */
    @GetMapping("/topqualityLevel")
    public MyResult topqualityLevel(Integer schoolId,String subject){
        return termianlService.topqualityLevel(schoolId,subject);
    }
    /**
     * 精品课堂列表
     * @return
     */
    @GetMapping("pointFocus")
    public MyResult pointFocus(Integer schoolId,String subject,Integer teaId,Integer pageNo,Integer pageSize){
        return termianlService.pointFocus(schoolId,subject,teaId,pageNo,pageSize);
    }

    /**
     * 直播、点播订单查询
     * @param byuuid
     * @return
     */
    @GetMapping("searchByOrder")
    public MyResult searchByOrder(String byuuid){
        return termianlService.searchByOrder(byuuid);
    }

    /**
     * 名师讲堂订单查询
     * @param byuuid
     * @return
     */
    @GetMapping("fineClassOrder")
    public MyResult fineClassOrder(String byuuid){
        return termianlService.fineClassOrder(byuuid);
    };

    /**
     * 改变订单状态
     * @param orderType
     * @param orderId
     * @return
     */
    @PostMapping("/changeOrderStatus")
    public MyResult changeOrderStatus(Integer orderType,String orderId){
        return termianlService.changeOrderStatus(orderType, orderId);
    }

    /**
     * 我的已购课程
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("myVideo")
    public MyResult myVideo(Integer pageNo,Integer pageSize){
        return termianlService.myVideo(pageNo,pageSize);
    }

    /**
     * 查询学校ip地址
     * @param VideoSchoolId
     * @return
     */
    @GetMapping("sevip")
    public MyResult sevip(Integer VideoSchoolId){
        return termianlService.sevip(VideoSchoolId);
    }
    @GetMapping("time")
    public String time(String content){
        /*try {
            QrCodeUtil.createQrCode("D:\\JAVA\\tomcat\\apache-tomcat-8.0.53\\webapps\\scenery\\upload\\images\\00008.jpg",content,800,"png");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
        return "hhh";
    }
}
