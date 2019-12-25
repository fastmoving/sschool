package com.usoft.sschool_student.serivice;

import com.usoft.smartschool.util.MyResult;

public interface CommontService {
    //查询该班级的所有老师
    MyResult allTea(Integer schoolId,Integer childId);
    //查看我的评价
    MyResult searchCommontInfo(Integer cid,Integer schoolId);
    //评论老师
    MyResult commontTea(Integer teaId,Integer star,String des,Integer attr1,Integer schoolId,Integer childId);
}
