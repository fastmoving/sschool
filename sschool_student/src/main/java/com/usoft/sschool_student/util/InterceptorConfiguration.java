package com.usoft.sschool_student.util;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *@file 配置拦截器
 *@date 2019年1月25日
 *@author jijh
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer{

	/**
	 * 配置拦截器
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                  .allowedOrigins("*")  
                  .allowCredentials(true)
                  .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                  .maxAge(3600);  
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AuthInterceptor())
				.excludePathPatterns("/**/login/**")
				.excludePathPatterns("/**/checkStu/**")
				.excludePathPatterns("/**/register/**")
        		.excludePathPatterns("/**/teacherLogin/**")
				.excludePathPatterns("/**/student/evaluate/aaa/**")
				.excludePathPatterns("/**/student/index/carousel/**");

    }
} 
