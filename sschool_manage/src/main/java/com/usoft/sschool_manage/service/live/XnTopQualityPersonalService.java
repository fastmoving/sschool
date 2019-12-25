package com.usoft.sschool_manage.service.live;

import com.usoft.smartschool.util.MyResult;

/**
 * 精品课堂表
 * @Author jijh
 * @Date 2019/7/9 14:38
 */
public interface XnTopQualityPersonalService {

    /**
     * 精品课堂列表
     * @return
     */
    MyResult listXnTopQualityPersonalClass(Integer pageNo, Integer pageSize);

    /**
     * 精品课堂添加或者修改
      * @param id 精品课堂id 修改时需要
     * @param subject 科目
     * @param tid 教师id
     * @param tPrice 教师价格，
     * @param sPrice
     * @param videoUrl
     * @param isPersonal
     * @return
     */
    MyResult addOrUpdateXnTopQualityPersonalClass(Integer id,String subject, Integer tid, String tPrice, String sPrice,
                                                  String videoUrl, String isPersonal);
}
