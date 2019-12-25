package com.usoft.sschool_manage;

/**
 * @Author jijh
 * @Date 2019/4/29 11:06
 */

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SschoolManageApplication.class);
    }

}


