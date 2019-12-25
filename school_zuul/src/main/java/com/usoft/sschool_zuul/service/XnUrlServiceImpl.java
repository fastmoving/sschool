package com.usoft.sschool_zuul.service;

import com.usoft.sschool_zuul.dao.XnUrlDao;
import com.usoft.sschool_zuul.entity.XnUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class XnUrlServiceImpl implements  XnUrlService {

    @Resource
    private XnUrlDao xnUrlDao;

    @Override
    public List<XnUrl> selectXnUrl(XnUrl xnUrl) {
        return xnUrlDao.select(xnUrl);
    }
}
