package com.usoft.sschool_teacher.util;

import com.usoft.sschool_teacher.common.SystemParam;
import com.usoft.sschool_teacher.service.IClassManagerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 陈秋
 * @Date: 2019/6/11 13:52
 * @Version 1.0
 * 定时器
 */
@Component
public class ClassTimeUtil {
    @Resource
    private IClassManagerService classManagerService;

    public int setTimer(String[] classId ,String message,String time) {

        Runnable runnable = new Runnable() {
            public void run() {
                classManagerService.insertClassOver(classId,message,0,"0");
            }
        };
        // 开启定时任务
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 获取到时间
        Date times = new Date();
        SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
        String oldTime = date1 + " " + time;
        SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String newTime = date2.format(times);
        int caT = 0;
        try {
            long newT = date2.parse(newTime).getTime();
            long oldT = date2.parse(oldTime).getTime();
            if (oldT >= newT) {
                caT = (int) (oldT - newT) * 60 * 1000;
            } else {
                caT = (int) (newT - oldT) * 60 * 1000;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        long equipmentTimer = 24 * 60 * 60;
        service.scheduleAtFixedRate(runnable, caT, equipmentTimer, TimeUnit.SECONDS);
        return 1;
    }

    public  void timer1(String[] classId ,String message,Long time){
        Timer nTimer = new Timer();
        Long timeNew = System.currentTimeMillis();
        Long times = time-timeNew;
        int teacherId = SystemParam.getUserId();
        String schoolId = SystemParam.getSchoolId().toString();
        nTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                int i=0;
                for (;i<=0;){
                   i = classManagerService.insertClassOver(classId,message,teacherId,schoolId);
                }
            }
        },times);
    }
}
