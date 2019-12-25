package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.BDictionary;
import com.usoft.smartschool.pojo.BDictionaryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BDictionaryMapper {
    int countByExample(BDictionaryExample example);

    int deleteByExample(BDictionaryExample example);

    int deleteByPrimaryKey(Integer dictid);

    int insert(BDictionary record);

    int insertSelective(BDictionary record);

    List<BDictionary> selectByExample(BDictionaryExample example);

    BDictionary selectByPrimaryKey(Integer dictid);

    int updateByExampleSelective(@Param("record") BDictionary record, @Param("example") BDictionaryExample example);

    int updateByExample(@Param("record") BDictionary record, @Param("example") BDictionaryExample example);

    int updateByPrimaryKeySelective(BDictionary record);

    int updateByPrimaryKey(BDictionary record);
}