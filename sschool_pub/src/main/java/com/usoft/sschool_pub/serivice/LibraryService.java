package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

public interface LibraryService {
    //查询所有图书
    MyResult allBooks(Integer pageNo, Integer pageSize,String bookName,Integer conditionId);
    //查询分类条件
    MyResult conditionClass(Integer parentId);
}
