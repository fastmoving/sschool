package com.usoft.sschool_teacher.util;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: 陈秋
 * @Date: 2019/5/6 17:24
 * @Version 1.0
 */
public class UploadImgUtil {

    /**
     * 根据请求存照片
     * @param request
     * @return
     */
    public static List<Map<String, Object>> getPhotosPath(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        //创建文件存放地址
        String paxf = request.getSession().getServletContext().getRealPath("");
        File files = new File(paxf);
        String uploadFile = files.getParent()+File.separator+"homework"+File.separator;
       // String realPath = uploadFile + File.separator + "face" +File.separator;
        //创建新的文件目录
        File newPath = new File(uploadFile);
        if (!newPath.exists()) {
            newPath.mkdirs();//如果文件不存在就创建一个
        }
        //解析上传的文件
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        List<Map<String,Object>> fileList = new ArrayList<>();
        //检测文件是否为空
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            MultiValueMap<String, MultipartFile> multiFileMap = multiRequest.getMultiFileMap();
            List<MultipartFile> fileSet = new LinkedList<>();
            for(Map.Entry<String,List<MultipartFile>> temp : multiFileMap.entrySet()){
                fileSet = temp.getValue();
                if (fileSet != null) {
                    for (MultipartFile file : fileSet) {
                        String newFileName = getNewName(file);
                        //存放文件
                        File file2 = getFile(uploadFile+newFileName);
                        //将文件存放在制定目录
                        try {
                            file.transferTo(file2);
                            map.put("path", newFileName);
                            System.out.println("...."+uploadFile+newFileName);
                            fileList.add(map);
                        } catch (IllegalStateException | IOException e) {
                            System.out.println("文件存放失败");
                            e.printStackTrace();
                        }
                    }
                }
            }
            return fileList;
        }
        return null;
    }

    public static Map<String, Object> getPhotosPath(HttpServletRequest request,int state){
        //Map<String, Object> map = new HashMap<>();
        //创建文件存放地址
        String paxf = request.getSession().getServletContext().getRealPath("");
        File files = new File(paxf);
        String uploadFile = null;
        if(state == 1){
            uploadFile = files.getParent()+File.separator+"homework"+File.separator;
        }else if(state == 2){
            uploadFile = files.getParent()+File.separator+"comment"+File.separator;
        }
        // String realPath = uploadFile + File.separator + "face" +File.separator;
        //创建新的文件目录
        File newPath = new File(uploadFile);
        if (!newPath.exists()) {
            newPath.mkdirs();//如果文件不存在就创建一个
        }
        //解析上传的文件
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        Map<String,Object> fileList = new HashMap<>();
        //检测文件是否为空
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            MultiValueMap<String, MultipartFile> multiFileMap = multiRequest.getMultiFileMap();
            List<MultipartFile> fileSet = new LinkedList<>();
            for(Map.Entry<String,List<MultipartFile>> temp : multiFileMap.entrySet()){
                fileSet = temp.getValue();
                if (fileSet != null) {
                    for (MultipartFile file : fileSet) {
                        String newFileName = getNewName(file);
                        //存放文件
                        File file2 = getFile(uploadFile+newFileName);
                        //将文件存放在制定目录
                        try {
                            file.transferTo(file2);
                            if (state == 1){
                                fileList.put("path", File.separator+"homework"+File.separator+newFileName);
                            }else if (state == 2){
                                fileList.put("path", File.separator+"comment"+File.separator+newFileName);
                            }

                            //System.out.println("...."+uploadFile+newFileName);
                            //fileList.add(map);
                        } catch (IllegalStateException | IOException e) {
                            System.out.println("文件存放失败");
                            e.printStackTrace();
                        }
                    }
                }
            }
            return fileList;
        }
        return null;
    }

    public static String getNewName(MultipartFile file){
        //重新命名文件名字
        String fileName = file.getOriginalFilename();//获取到文件名
        //获取文件后缀名
        String prefix = fileName.substring(fileName.lastIndexOf(".")+1);
        //冲1-10000里面随机获取一个数据生成新名字
        Random random = new Random();
        Integer randomFileName = random.nextInt(1000);
        //文件新名字
        String newFileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+randomFileName+"."+prefix;
        return newFileName;
    }

    public static File getFile(String path){
        //存放文件
        File file2 = new File(path);
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                System.out.println("文件找不到");
                e.printStackTrace();
            }
        }
        return file2;
    }
}
