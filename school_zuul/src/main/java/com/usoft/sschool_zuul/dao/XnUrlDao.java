package com.usoft.sschool_zuul.dao;

import com.usoft.sschool_zuul.entity.XnUrl;

import java.util.List;

public interface XnUrlDao {

    List<XnUrl> select(XnUrl xnUrl);
}
