package com.usoft.sschool_manage.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.util.FileEntity;
import com.usoft.sschool_manage.util.FileUploadTool;
import com.usoft.sschool_manage.util.UploadFileUtil;
import com.usoft.sschool_manage.util.ZimgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author jijh
 * @Date 2019/4/28 11:37
 */

@RestController
@RequestMapping("manage/file")
public class FileUploadController  extends BaseController {



    @Autowired
    private UploadFileUtil uploadFileUtil;

    @Autowired
    private FileUploadTool fileUploadTool;

    @RequestMapping("upload")
    public Object uploadFile(HttpServletRequest request){
        MyResult result = null;
        try{
            result =  uploadFileUtil.uploadFiles(request);
        }catch (Exception e ){
            e.printStackTrace();
            return MyResult.failure("上传失败");
        }
        return result;
    }


    @RequestMapping("zimg")
    public Object uploadFileByZimg(HttpServletRequest request, String fileTag){
        List<String> urlString = ZimgUtils.submitImage(request,fileTag);
        if(ObjectUtil.isEmpty(urlString))return MyResult.failure("上传失败");
        return MyResult.success("上传成功",urlString.get(0));

    }

    /**
     * 上传视频
     * @param request
     * @return
     */
    @RequestMapping("updateVideo")
    public Object updateVideo(HttpServletRequest request){
        FileEntity fileEntity = fileUploadTool.createFile(request);
        return MyResult.success(fileEntity);
    }
}
