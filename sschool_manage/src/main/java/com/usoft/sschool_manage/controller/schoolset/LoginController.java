package com.usoft.sschool_manage.controller.schoolset;

import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.schoolset.CfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author jijh
 * @Date 2019/4/24 16:23
 */
@RestController
@RequestMapping("/manage/user")
public class LoginController  extends BaseController {


    @Autowired
    private CfUserService cfUserService;


    /**
     * 用户登录
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Object getLogin(String loginName, String password){

        return cfUserService.login(loginName,password);
    }




    @GetMapping("outlogin")
    public Object loginout(){
        return cfUserService.loginout();
    }

}
