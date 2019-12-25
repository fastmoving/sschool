package com.usoft.sschool_pub.mapper;

import com.usoft.smartschool.pojo.BAttachfile;
import com.usoft.smartschool.pojo.BAttachfileExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BAttachfileMapper {
    int countByExample(BAttachfileExample example);

    int deleteByExample(BAttachfileExample example);

    int deleteByPrimaryKey(Integer attfileId);

    int insert(BAttachfile record);

    int insertSelective(BAttachfile record);

    List<BAttachfile> selectByExample(BAttachfileExample example);

    BAttachfile selectByPrimaryKey(Integer attfileId);

    int updateByExampleSelective(@Param("record") BAttachfile record, @Param("example") BAttachfileExample example);

    int updateByExample(@Param("record") BAttachfile record, @Param("example") BAttachfileExample example);

    int updateByPrimaryKeySelective(BAttachfile record);

    int updateByPrimaryKey(BAttachfile record);
    //查询所有图书
    List<BAttachfile> allBooks(@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    //模糊匹配查询
    List<BAttachfile> searchByName(String bookName);
    //条件+模糊匹配查询
    List<BAttachfile> searchByCondition(@Param("bookName") String bookName,@Param("condition")Integer condition);
}