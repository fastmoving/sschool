package com.usoft.sschool_student.serivice;

import com.usoft.smartschool.pojo.HlStudentinfo;
import com.usoft.smartschool.pojo.XnCarousel;
import com.usoft.smartschool.util.MyResult;

import java.util.List;

/**
 * @author wlw
 * @data 2019/4/22 0022-14:50
 */
public interface IndexService {
    /**
     *学生信息
     * @return
     */
    MyResult stuIfo(Integer stuId, Integer schoolId);

    /**
     * ；轮播图
     * @param type
     * @return
     */
    MyResult carousel(Byte type,Byte position);
}
