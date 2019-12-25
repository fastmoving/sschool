package com.usoft.sschool_zuul.dao;

import com.usoft.sschool_zuul.entity.XnUrlLimits;

import java.util.List;

public interface XnUrlLimitsDao {


    List<XnUrlLimits> select(XnUrlLimits xnUrlLimits);
}
