package com.usoft.sschool_pub.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


/**
 *@file UploadFileUtil.java
 *@date 2018年8月2日
 *@author jijh
 */
@SuppressWarnings("all")
@Component
@PropertySource("classpath:config/filebasedir.properties")
public class UploadFileUtil {


	private static String  fileBase;

	@Value("${file.base}")
	public void setFileBase(String fileBase){
		this.fileBase = fileBase;
	}

	/**
	 * 上传多个文件1.war包运行
	 */
	public MyResult uploadFiles(HttpServletRequest request) {


		String paths = request.getSession().getServletContext().getRealPath("");
		File basePath = new File(paths);
		String parentPath = basePath.getParent();
		paths = parentPath+File.separator+"sschool_manage"+File.separator;


//		String basePath =
//		File files = new File(paths);
//		String parentPath = files.getParent();
		String realPath = paths  + randomPackage();
		System.out.println("realPath:"+realPath);
		File newFile = new File(realPath);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		StringBuffer sb = new StringBuffer();
		//解析上传的文件
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			//转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			MultiValueMap<String,MultipartFile> multiFileMap = multiRequest.getMultiFileMap();
			List<MultipartFile> fileSet = new LinkedList<>();
			for(Entry<String, List<MultipartFile>> temp : multiFileMap.entrySet()){
				fileSet = temp.getValue();
				if(fileSet!=null) {
					for(MultipartFile fiN : fileSet){
						String newFileName = createNewName(fiN.getOriginalFilename());
						File file = new File(realPath+newFileName);
						if(!file.exists()) {
							try {
								file.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						try {
							fiN.transferTo(file);
							//Runtime.getRuntime().exec("chmod -R 777 /usr/local/smartschoolFile/sschoolManageFile");
							if(ObjectUtil.isEmpty(sb.toString())){
								sb.append(randomPackage()+newFileName);
							}else{
								sb.append(","+randomPackage()+newFileName);
							}
						}catch (IOException e) {
							e.printStackTrace();
							return MyResult.myException(e, "上传文件失败");
						}
					}
				}
			}

			return MyResult.success("上传成功",sb.toString());
		}else {
			return MyResult.failure();
		}

	}



	/**
	 * 生成随机目录列表
	 * @return
	 */
	private String randomPackage(){

		//每月一个文件夹
		Date today = new Date();
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMM");
		String thisMonth = sdf0.format(today);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String todayString = sdf.format(today);
		Integer da = Integer.valueOf(todayString)%100;
		String secondLevel = "";
		switch (da/10){
			case 1: secondLevel ="beginofmonth"; break;
			case 2: secondLevel ="middleofmonth";break;
			case 3: secondLevel = "endofmonth";break;
		}
		String pa  = new String(File.separator+"sschoolManageFile"+File.separator+thisMonth+File.separator+secondLevel+File.separator);
		return pa;
	}



	private String createNewName(String originalName){
		String sufix = originalName.substring(originalName.lastIndexOf("."));
		String uuid = UUID.randomUUID().toString();

		return uuid+sufix;
	}
}





















