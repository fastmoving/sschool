package com.usoft.sschool_manage.service.schoolset;

import com.usoft.smartschool.util.MyResult;

/**
 * 课时业务层
 *
 * @Author jijh
 * @Date 2019/4/23 16:25
 */
public interface HlCurriculumsetService {

    /**
     * 学校的课时
     * @param sid
     * @return
     */
    MyResult listCurriculumsetMessage(Integer sid);
}
