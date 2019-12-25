package com.usoft.sschool_manage.service.schoolset.Impl;

import com.usoft.smartschool.pojo.XnIntegralRule;
import com.usoft.smartschool.pojo.XnIntegralRuleExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.XnIntegralRuleMapper;
import com.usoft.sschool_manage.service.schoolset.XnIntegralRuleService;
import com.usoft.sschool_manage.util.SystemParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/5/7 17:31
 */
@Service
public class XnIntegralRuleServiceImpl implements XnIntegralRuleService {

    @Resource
    private XnIntegralRuleMapper xnIntegralRuleMapper;


    /**
     * 教师获取积分功能
     */
    private final static int SENDMISSION = 1;//发布作业

    private final static int CLASSCHECK = 2;//班级圈审核

    private final static int UPLOADCORE = 3;//上传成绩

    private final static int CLASSDCIM = 4;//班级相册

    private final static int ASKFORLEAVECHECK = 5;//请假审批



    @Override
    public MyResult listXnIntegralRule() {
        Integer schoolId = SystemParam.getSchoolId();
        XnIntegralRuleExample xnIntegralRuleExample = new XnIntegralRuleExample();
        XnIntegralRuleExample.Criteria criteria = xnIntegralRuleExample.createCriteria();
        criteria.andSchoolidEqualTo(schoolId);
        List<XnIntegralRule> xnIntegralRules = xnIntegralRuleMapper.selectByExample(xnIntegralRuleExample);
        List<Map<String,Object>> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if(ObjectUtil.isEmpty(xnIntegralRules)){
          for(int i=1; i<=5; i++){
              result.add(getIntegralRule(null,i,null,null));

          }
        }else{
          for(XnIntegralRule xnIntegralRule : xnIntegralRules){
              result.add(getIntegralRule(xnIntegralRule.getId(),xnIntegralRule.getFunction()
                      ,xnIntegralRule.getIntegralnumer(),xnIntegralRule.getIsopen()));
              sb.append(String.valueOf(xnIntegralRule.getFunction()));
          }
          for(int i=1; i<=5; i ++){
              if(!sb.toString().contains(String.valueOf(i))){
                  result.add(getIntegralRule(null,i,null,null));
              }
          }

        }


        return MyResult.success(result);
    }

    @Override
    public MyResult editXnIntegralRule(List<XnIntegralRule> xnIntegralRuleList) {



        Integer userId = SystemParam.getUserId();
        Integer schoolId = SystemParam.getSchoolId();
        for(XnIntegralRule xnIntegralRule : xnIntegralRuleList){
            if(ObjectUtil.isEmpty(xnIntegralRule.getId())){
                xnIntegralRule.setCreatetime(new Date());
                xnIntegralRule.setUserid(userId);
                xnIntegralRule.setSchoolid(schoolId);
                xnIntegralRuleMapper.insert(xnIntegralRule);
            }else{
                XnIntegralRule xnIntegralRule1 = xnIntegralRuleMapper.selectByPrimaryKey(xnIntegralRule.getId());
                if(ObjectUtil.isEmpty(xnIntegralRule1))return MyResult.failure("未发现当前数据");
                xnIntegralRuleMapper.updateByPrimaryKeySelective(xnIntegralRule);
            }


        }
//        if(ObjectUtil.isEmpty(function)) return MyResult.failure("请选择功能");
//        if(ObjectUtil.isEmpty(isOpen)) return MyResult.failure("请选择是否开启");
//        if(isOpen == 1){
//            if(ObjectUtil.isEmpty(integralNumber))return MyResult.failure("请选择获取积分的数量");
//        }else{
//            integralNumber = (integralNumber == null ? 0 : integralNumber);
//        }
//        Integer schoolId = SystemParam.getSchoolId();
//        XnIntegralRuleExample xnIntegralRuleExample = new XnIntegralRuleExample();
//        XnIntegralRuleExample.Criteria criteria = xnIntegralRuleExample.createCriteria();
//        criteria.andSchoolidEqualTo(schoolId);
//        criteria.andFunctionEqualTo(function);
//        List<XnIntegralRule> xnIntegralRules = xnIntegralRuleMapper.selectByExample(xnIntegralRuleExample);
//        try{
//            if(ObjectUtil.isEmpty(xnIntegralRules)){
//                XnIntegralRule xnIntegralRule = new XnIntegralRule();
//                xnIntegralRule.setSchoolid(schoolId);
//                xnIntegralRule.setCreatetime(new Date());
//                int io = isOpen;
//                xnIntegralRule.setIsopen((byte)io);
//                xnIntegralRule.setFunction(function);
//                xnIntegralRule.setIntegralnumer(integralNumber);
//                xnIntegralRule.setUserid(SystemParam.getUserId());
//                xnIntegralRuleMapper.insert(xnIntegralRule);
//            }else{
//                XnIntegralRule xnIntegralRule = xnIntegralRules.get(0);
//                xnIntegralRule.setIntegralnumer(integralNumber);
//                int io = isOpen;
//                xnIntegralRule.setIsopen((byte)io);
//                xnIntegralRuleMapper.updateByPrimaryKeySelective(xnIntegralRule);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            MyResult.failure("操作失败");
//        }

        return MyResult.success("操作成功");
    }


    /**
     * 工具方法，获取当前所有功能
     * @param id
     * @param type
     * @param integral
     * @param isopen
     * @return
     */
    private Map<String,Object> getIntegralRule(Integer id,Integer type, Integer integral, Byte isopen){

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        switch (type){
            case SENDMISSION: map.put("function",1);break;
            case CLASSCHECK : map.put("function",2);break;
            case UPLOADCORE : map.put("function",3);break;
            case CLASSDCIM  : map.put("function",4);break;
            case ASKFORLEAVECHECK :map.put("function",5);break;

        }
        map.put("integralnumer",integral);
        map.put("isopen",isopen);
        return map;
    }
}
