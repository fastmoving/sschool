package com.usoft.sschool_pub.controller;

import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.XnKindnessSchoolMapper;
import com.usoft.sschool_pub.serivice.KindnessService;
import com.usoft.sschool_pub.util.SystemParam;
import com.usoft.sschool_pub.util.UploadFileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pub/kindness")
public class KindnessController {
    @Resource
    private KindnessService kindnessService;
    @Resource
    private UploadFileUtil uploadFileUtil;
    /**
     * 根据学校id查询所有的校园爱心
     * @return
     */
    @GetMapping("allKindness")
    public MyResult allKindness(Integer pageNo,Integer pageSize){
        Integer schoolId = SystemParam.getSchoolId();
        return kindnessService.allKindness(schoolId,pageNo,pageSize);
    }

    /**
     * 爱心企业，人士
     * @param type
     * @return
     */
    @GetMapping("kindnessPersion")
    public MyResult kindnessPersion(Byte type){
        Integer schoolId = SystemParam.getSchoolId();
        return kindnessService.kindnessPersion(schoolId,type);
    }
    /**
     * 查看单个校园爱心详情
     * @param kid
     * @return
     */
    @GetMapping("kindnessMsg")
    public MyResult kindnessMsg(Integer kid){
        return kindnessService.kindnessMsg(kid);
    }

    /**
     * 评论校园爱心
     * @param kid
     * @param content
     * @param parentId
     * @return
     */
    @PostMapping("/commentKindness")
    public MyResult commentKindness(Integer kid,String content,Integer parentId){
        return kindnessService.commentKindness(kid,content,parentId);
    }
    /**
     * 查看爱心排行
     * @return
     */
    @GetMapping("kindnessRanking")
    public MyResult kindnessRanking(){
        Integer schoolId = SystemParam.getSchoolId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return kindnessService.kindnessRanking(schoolId,type);
    }

    /**
     * 我要做爱心
     * @param description
     * @param phone
     * @param goodsType
     * @return
     */
    @PostMapping("/addMyKindness")
    public MyResult addMyKindness(HttpServletRequest request, String description, String phone, Integer goodsType){
        if(description==null|| goodsType==null ){
            return MyResult.failure("请填写爱心信息和物品类型");
        }
        Integer schoolId = SystemParam.getSchoolId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return kindnessService.addMyKindness(schoolId,type,request,description,phone,goodsType);
    }

    /**
     * 查询我的爱心
     * @return
     */
    @GetMapping("/myKindness")
    public MyResult myKindness(Integer kindnessType,Integer pageNo,Integer pageSize){
        Integer schoolId = SystemParam.getSchoolId();
        Byte type = Byte.valueOf(SystemParam.getType().toString());
        return kindnessService.myKindness(kindnessType,schoolId,type,pageNo,pageSize);
    }

    /**
     * 修改我的爱心信息
     * @param kindnessId
     * @param description
     * @param phone
     * @param goodsType
     * @return
     */
    @PostMapping("changeMyKindness")
    public MyResult changeMyKindness(Integer kindnessId,String description,String phone,Integer goodsType){
        return kindnessService.changeMyKindness(kindnessId,description,phone,goodsType);
    }

    /**
     * 完成爱心
     * @param kindnessId
     * @return
     */
    @PostMapping("signMyKindness")
    public MyResult signMyKindness(Integer kindnessId){
        return kindnessService.signMyKindness(kindnessId);
    }

    /**
     * 删除校园爱心
     * @param kindnessId
     * @return
     */
    @PostMapping("deleteKindness")
    public MyResult deleteKindness(Integer kindnessId){
        return kindnessService.deleteKindness(kindnessId);
    }
}
