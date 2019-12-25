package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.serivice.LoginService;
import com.usoft.sschool_pub.util.RedisUtil;
import com.usoft.sschool_pub.util.SystemParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wlw
 * @data 2019/4/22 0022-14:35
 */
@Controller
@RequestMapping("/pub/log")
@ResponseBody
@CrossOrigin
public class LoginController {
    private static final Logger log= LoggerFactory.getLogger(LoginController.class);
    @Resource
    private LoginService loginServices;

    @Autowired
    private RedisUtil redisUtil;
    @GetMapping("schoolList")
    public MyResult schoolList(){
        return loginServices.schoolList();
    }
    @GetMapping("getPort")
    public MyResult getPort(Integer schoolId,String ipPath){
        return loginServices.getPort(schoolId, ipPath);
    }
    /**
     * 查询套餐
     * @return
     */
    @GetMapping("searchSetmeal")
    public MyResult searchSetmeal(){
        Integer schoolId = SystemParam.getSchoolId();
        return loginServices.searchSetmeal(schoolId);
    }

    /**
     * 保存套餐订单
     * @param smid
     * @return
     */
    @PostMapping("saveSetmealOrder")
    public MyResult saveSetmealOrder(Integer smid){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return loginServices.saveSetmealOrder(schoolId,childId,smid);
    }

    /**
     * 支付后改变订单状态为已支付
     * @param byuuid
     * @return
     */
    @PostMapping("changeOrderInfo")
    public MyResult changeOrderInfo(String byuuid){
        Integer schoolId = SystemParam.getSchoolId();
        Integer userId = SystemParam.getUserId();
        return loginServices.changeOrderInfo(schoolId,userId,byuuid);
    }
    /**
     * 查询我购买的套餐
     * @return
     */
    @GetMapping("PaySetmail")
    public MyResult PaySetmail(){
        return loginServices.PaySetmail();
    }
    /**
     * 绑定家长
     * @param smid
     * @param familyRelate
     * @param phone
     * @return
     */
    @PostMapping("bind")
    public MyResult bind(Integer smid, String familyRelate, String phone){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return loginServices.bind(schoolId,childId,familyRelate,phone);
    }
    /**
     * 教师登录
     * @param userName
     * @param pwd
     * @return
     */
    @PostMapping("/teacherLogin")
    public MyResult teacherLogin(String userName,String pwd){
        return loginServices.teacherLogin(userName,pwd);
    }
    /**
     * 登录
     * @param phone
     * @param pwd
     * @return
     */
    @PostMapping("/login")
    public MyResult login(String phone ,String pwd){
        return loginServices.appLogin(phone,pwd);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public MyResult logout(){
        Integer userId =SystemParam.getUserId();
        String token = String.valueOf(redisUtil.get(userId+"token"));
        if(!ObjectUtil.isEmpty(token)){
            redisUtil.remove(userId+"token");
        }
        return MyResult.success("退出成功");
    }
    /**
     * 修改密码
     * @param phone
     * @param pwd
     * @param newpwd
     * @return
     */
    @PostMapping("/changePwd")
    public MyResult changePwd(String phone ,String pwd,String newpwd){
        return loginServices.changePwd(phone,pwd,newpwd);
    }

    /**
     * 选择几号孩子
     * @param stuId
     * @return
     */
    @GetMapping("/checkStu")
    public MyResult checkStu(Integer stuId,String phone){
        return loginServices.checkStu(stuId,phone);
    }

    /**
     * 查询登录账户的孩子信息
     * @return
     */
    @GetMapping("/stuInfo")
    public MyResult stuInfo(String phone){
        return loginServices.stuInfo(phone);
    }

    /**
     * 菜谱信息
     * @return
     */
    @GetMapping("/food")
    public MyResult forFood(){
        log.info("查找菜谱信息");
        Integer schoolId = SystemParam.getSchoolId();
        return loginServices.forFood(schoolId);
    }
    @GetMapping("/webFood")
    public MyResult webFood(){
        return loginServices.webFood();
    }
}

