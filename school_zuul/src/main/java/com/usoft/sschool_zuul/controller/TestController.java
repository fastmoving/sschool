package com.usoft.sschool_zuul.controller;

import com.usoft.sschool_zuul.dao.XnUrlDao;
import com.usoft.sschool_zuul.entity.XnUrl;
import com.usoft.sschool_zuul.service.XnUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {


    @Autowired
    private XnUrlService xnUrlService;



    @RequestMapping("xnurl")
    public Object getXnUrl(){
        XnUrl xnUrl = new XnUrl();
        List<XnUrl> xnUrls = xnUrlService.selectXnUrl(xnUrl);
        return xnUrls;
    }
}
