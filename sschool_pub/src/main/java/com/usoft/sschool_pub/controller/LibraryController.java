package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.serivice.LibraryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/pub/library")
public class LibraryController {
    @Resource
    private LibraryService libraryService;

    /**
     * 查询所有图书或关键字搜索
     * @param pageNo
     * @param pageSize
     * @param bookName
     * @return
     */
    @GetMapping("allBooks")
    public MyResult allBooks(Integer pageNo,Integer pageSize,String bookName,Integer conditionId){
        return libraryService.allBooks(pageNo,pageSize,bookName,conditionId);
    }

    /**
     * 筛选条件
     * @param parentId
     * @return
     */
    @GetMapping("conditionClass")
    public MyResult conditionClass(Integer parentId){
        return libraryService.conditionClass(parentId);
    }
}
