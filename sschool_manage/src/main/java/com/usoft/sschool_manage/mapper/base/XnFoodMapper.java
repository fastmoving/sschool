package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.XnFood;
import com.usoft.smartschool.pojo.XnFoodExample;
import com.usoft.smartschool.pojo.XnFoodKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;


public interface XnFoodMapper {
    int countByExample(XnFoodExample example);

    int deleteByExample(XnFoodExample example);

    int deleteByPrimaryKey(XnFoodKey key);

    int insert(XnFood record);

    int insertSelective(XnFood record);

    List<XnFood> selectByExample(XnFoodExample example);

    XnFood selectByPrimaryKey(XnFoodKey key);

    int updateByExampleSelective(@Param("record") XnFood record, @Param("example") XnFoodExample example);

    int updateByExample(@Param("record") XnFood record, @Param("example") XnFoodExample example);

    int updateByPrimaryKeySelective(XnFood record);

    int updateByPrimaryKey(XnFood record);

    /************************************************************/
    /**
     * 根据学校查看菜谱
     * @param sid 学校id
     * @param foodTime 饭点
     * @param week 周几
     * @return
     */
    List<XnFood> listFoodBySchool(@Param("sid") Integer sid,@Param("foodTime") Integer foodTime,@Param("week") Integer week);
}