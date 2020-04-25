package com.usoft.sschool_teacher.job;

import com.alibaba.fastjson.JSON;
import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.enums.entity.XnTimingSchool;
import com.usoft.sschool_teacher.exception.CustomException;
import com.usoft.sschool_teacher.exception.MyException;
import com.usoft.sschool_teacher.mapper.HlStudentinfoMapper;
import com.usoft.sschool_teacher.mapper.HlTeacherMapper;
import com.usoft.sschool_teacher.mapper.XnMessageCenterMapper;
import com.usoft.sschool_teacher.service.IClassManagerService;
import com.usoft.sschool_teacher.util.ConstantsDate;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cq
 * @date 2020/4/23 23:10
 *
 * 定时器调用台
 */

@Component
public class SchoolTimingJob implements Job {

    @Resource
    private IClassManagerService classManagerService;//班级发送短信实现成

    @Resource
    private HlTeacherMapper teacherMapper;

    @Resource
    private HlStudentinfoMapper studentinfoMapper;

    @Resource
    private XnMessageCenterMapper messageCenterMapper;//消息存放

    /**
     * 调度器
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //解析参数信息
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        XnTimingSchool record = JSON.parseObject(jobDataMap.get("data").toString(),XnTimingSchool.class);

        //异步执行任务，加快执行速度
        Runnable runnable = ()-> this.execute(record);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 执行定时任务逻辑
     * @param record
     */
    private void execute(XnTimingSchool record) {

        String[] classId = record.getClassId().split(",");

        if(classId == null || classId.length <1){//全部班级
            //获取全部班级
            classId = this.classManagerService.getSchoolClassId(record.getSchoolId());
            this.insertClassOver(classId,record.getDescription(),record.getUserId(),record.getSchoolId());
        }else {//指定班级
            this.insertClassOver(classId,record.getDescription(),record.getUserId(),record.getSchoolId());
        }
    }

    /**
     * 一键放学
     * @param classIds 班级ID集合
     * @return
     */
    @Transactional
    public int insertClassOver(String[] classIds,String message,Integer userId,Integer schoolId){
        List classId = new ArrayList();
        for (String i : classIds){
            classId.add(Integer.parseInt(i));
        }
        Map key = new HashMap();
        key.put("list",classId);
        key.put("schoolId",SystemParam.getSchoolId());
        List dataFamily = this.studentinfoMapper.getStudentID(key);
        List<Map> data = this.teacherMapper.getTeacherIfo(userId);
        if (dataFamily.size()==0){
            return -2;
        }
        if (data.size()==0){
            return -3;
        }
        List key_data = this.classManagerService.getMessage(dataFamily,message,userId,
                data.get(0).get("Tname").toString(), ConstantsDate.MESSAGE_CLASS_OVER,schoolId.toString());
        int i = this.messageCenterMapper.insertClassOver(key_data);
        try {
            CustomException.customeIf(i);
        }catch (MyException e){
            return -1;
        }
        return i;
    }
}
