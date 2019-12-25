package com.usoft.sschool_student.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_student.serivice.IndexService;
import com.usoft.sschool_student.util.SystemParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author wlw
 * @data 2019/4/23 0023-15:57
 */
@ResponseBody
@Controller
@RequestMapping("/student/index")
@CrossOrigin
public class IndexController {
    private static final Logger log= LoggerFactory.getLogger(IndexController.class);
    @Resource
    private IndexService indexService;

    /**
     * 显示学信息
     * @return
     */
    @GetMapping("/stuIfo")
    public MyResult stuIfo(){
        Integer schoolId = SystemParam.getSchoolId();
        Integer childId = SystemParam.getChildId();
        return indexService.stuIfo(childId,schoolId);
    }

    /**
     * 轮播图
     * @param type app=1?web=2
     * @return
     */
    @GetMapping("carousel")
    public MyResult carousel(Byte type,Byte position){
        return indexService.carousel(type,position);
    }

}
