package com.usoft.sschool_manage.mapper.base;

import com.usoft.smartschool.pojo.TerminalInfo;
import com.usoft.smartschool.pojo.TerminalInfoExample;
import com.usoft.smartschool.pojo.TerminalInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TerminalInfoMapper {
    int countByExample(TerminalInfoExample example);

    int deleteByExample(TerminalInfoExample example);

    int deleteByPrimaryKey(TerminalInfoKey key);

    int insert(TerminalInfo record);

    int insertSelective(TerminalInfo record);

    List<TerminalInfo> selectByExample(TerminalInfoExample example);

    TerminalInfo selectByPrimaryKey(TerminalInfoKey key);

    int updateByExampleSelective(@Param("record") TerminalInfo record, @Param("example") TerminalInfoExample example);

    int updateByExample(@Param("record") TerminalInfo record, @Param("example") TerminalInfoExample example);

    int updateByPrimaryKeySelective(TerminalInfo record);

    int updateByPrimaryKey(TerminalInfo record);
}