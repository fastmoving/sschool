package com.usoft.sschool_manage.controller.setmeal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.usoft.sschool_manage.controller.BaseController;
import com.usoft.sschool_manage.service.setmeal.XnSetmealBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 绑定用户控制层
 * @Author jijh
 * @Date 2019/5/16 14:49
 */

@RestController
@RequestMapping("manage/binding")
public class XnSetmealBindingController  extends BaseController {

    @Autowired
    private XnSetmealBindService xnSetmealBindService;


    @GetMapping("list")
    public Object listBindingMessage(Integer classId,Integer pageNo, Integer pageSize){
        return xnSetmealBindService.listXnSeatmealBinding(classId,pageNo,pageSize);
    }


    /**
     * 分配用户
     * @param setId
     * @param stuId
     * @param person
     * @return
     */
    @PostMapping("bindperson")
    public Object bindPerson(@RequestParam("setId") Integer setId, @RequestParam("stuId") Integer stuId, @RequestParam("person") String person){
        List<Map<String,Object>> p = JSONObject.parseObject(person, List.class);

        return xnSetmealBindService.bindingPerson(setId, stuId, p);
    }


    /**
     * 修改授权用户
     * @param person
     * @return
     */
    @PostMapping("editperson")
    public Object editPerson(String person){
        return xnSetmealBindService.editPerson(person);
    }


    /**
     * 账户关联账号
     * @param stuId
     * @return
     */
    @GetMapping("selectPerson")
    public Object selectPerson(Integer stuId){
        return xnSetmealBindService.selectPerson(stuId);
    }
}
