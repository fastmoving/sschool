package com.usoft.sschool_manage.service;

import com.usoft.smartschool.pojo.*;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_manage.mapper.base.HlStudentinfoMapper;
import com.usoft.sschool_manage.mapper.base.XnFoodMapper;
import com.usoft.sschool_manage.mapper.base.XnScoreMapper;
import com.usoft.sschool_manage.service.schoolset.*;
import com.usoft.sschool_manage.util.SystemParam;
import com.usoft.sschool_manage.util.excel.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author jijh
 * @Date 2019/5/27 10:42
 */
@Service
public class UploadExcelUtil {

    /**
     * 教师
     */
    private static final int  TEACHER = 1;

    /**
     * 班级
     */
    private static final int  CLASS = 2;

    /**
     * 课程
     */
    private static final int COURSE = 3;

    /**
     * 菜单
     */
    private static final int FOOD = 4;

    /**
     * 成绩
     */
    private static final int SCORE = 5;

    /**
     * 学生
     */
    private static final int STUDENT = 6;


    @Resource
    private XnFoodMapper xnFoodMapper;


    @Resource
    private XnScoreService xnScoreService;

    @Resource
    private XnScoreMapper xnScoreMapper;

    @Resource
    private HlStudentinfoMapper hlStudentinfoMapper;

    @Resource
    private HlSchclassService hlSchclassService;

    @Resource
    private HlCurriculumService hlCurriculumService;

    @Resource
    private HlTeacherService hlTeacherService;

    @Resource
    private HlStudentInfoService hlStudentInfoService;


    public MyResult uploadExcel(Integer type, MultipartFile multipartFile, HttpServletRequest request){
        try{
            if(ObjectUtil.isEmpty(type)){
                return MyResult.failure("请选择上传的模板的类型");
            }
            if(multipartFile == null) return MyResult.failure("未检测到任何文件");
            String path = request.getSession().getServletContext().getRealPath("/temp/upload/");
            File newFilePath = new File(path);
            if (!newFilePath.exists()) {
                newFilePath.mkdirs();
            }
            String originalFilename = multipartFile.getOriginalFilename();
            if(!originalFilename.endsWith(".xlsx"))return MyResult.failure("文件格式有误");
            String filePath = path+originalFilename;
            File newFile = new File(path+originalFilename);
            if(!newFile.exists()){
                newFile.createNewFile();
            }
            multipartFile.transferTo(newFile);
            Integer schoolId = SystemParam.getSchoolId();
            Integer userId = SystemParam.getUserId();
            int num = 0;
            List<Map<String,Object>> m = new ArrayList<>();
            switch (type){
                case TEACHER:{
                    MyResult result = ExcelUtil.teacherInformation(schoolId,userId,newFile);
                    if(result.getStatus() != 1) return result;
                    List<Map<String,Object>> hlTeachers = (List<Map<String,Object>>)result.getData();
                    if(ObjectUtil.isEmpty(hlTeachers))return MyResult.failure("未发现有效数据");
                    for(int i=0; i<hlTeachers.size(); i++){
                        Map<String, Object> map = hlTeachers.get(i);
                        String  name = (String) map.get("name");
                        String  birthday = (String) map.get("birthday");
                        int sex = (int) map.get("sex");
                        int role = (int) map.get("role");
                        String  subject = (String) map.get("subject");
                        String  phone = (String) map.get("phone");
                        String  password = (String) map.get("password");
                        MyResult re = hlTeacherService.addOrUpdateTeacher(null,name,birthday,sex,role,subject,phone,password,null,null);
                        if(re.getStatus()!=1){
                            return re;
                        }else{
                            Map<String,Object> ts = (Map<String, Object>) re.getData();
                            ts.put("name",name);
                            m.add(ts);
                        }
                        num++;
                    }
                } break;
                case CLASS : {
                    MyResult result = ExcelUtil.classInformation(schoolId,userId,newFile);
                    if(result.getStatus() != 1) return result;
                    List<Map<String,Object>> hlSchclasses = (List<Map<String,Object>>)result.getData();
                    if(ObjectUtil.isEmpty(hlSchclasses))return MyResult.failure("未发现有效数据");
                    for(int i=0; i<hlSchclasses.size(); i++){
                        Map<String, Object> map = hlSchclasses.get(i);
                        String  className = (String) map.get("className");
                        int teacherId = (int) map.get("teacherId");
                        String  teacherName = (String) map.get("teacherName");
                        int grade = (int) map.get("grade");
                        int classNum = (int) map.get("classNum");
                        String  description = (String) map.get("description");
                        MyResult re = hlSchclassService.addOrupdateSchclass(null,className,schoolId,teacherId,teacherName,grade,classNum,description,null);
                        if(re.getStatus()!=1)return re;
                        num++;
                    }
                } break;
                case COURSE : {
                    MyResult result = ExcelUtil.courseInformation(schoolId,userId,newFile);
                    if(result.getStatus()!= 1)return result;
                    List<Map<String,Object>> courses = (List<Map<String,Object>>)result.getData();
                    if(ObjectUtil.isEmpty(courses))return MyResult.failure("未发现有效数据");
                    for(int i=0; i<courses.size(); i++){
                        Map<String, Object> course = courses.get(i);
                        int classId = (int)Double.parseDouble(course.get("classId").toString());
                        int week =  (int)Double.parseDouble(course.get("week").toString());
                        int lesson =  (int)Double.parseDouble(course.get("lessonId").toString());
                        int subject=  (int)Double.parseDouble(course.get("subject").toString());
                        int teacherId =  (int)Double.parseDouble(course.get("teacherId").toString());
                        MyResult re = hlCurriculumService.addCurriculum(null,classId,lesson,subject,teacherId,lesson,week);
                        if(re.getStatus()!=1) return re;
                        num++;
                    }
                };break;
                case FOOD : {
                    MyResult result = ExcelUtil.foodInformation(schoolId,userId, newFile);
                    if(result.getStatus()!=1)return result;
                    List<XnFood> xnFoods=  (List<XnFood>)result.getData();
                    if(ObjectUtil.isEmpty(xnFoods))return MyResult.failure("未发现有效数据");
                    Date date = new Date();

                    for(int i=0;i<xnFoods.size(); i++){
                        XnFood xnFood = xnFoods.get(i);
                        XnFoodExample xnFoodExample = new XnFoodExample();
                        XnFoodExample.Criteria criteria = xnFoodExample.createCriteria();
                        criteria.andSidEqualTo(xnFood.getSid());
                        criteria.andWeekEqualTo(xnFood.getWeek());
                        criteria.andFoodtimeEqualTo(xnFood.getFoodtime());
                        criteria.andFoodnameEqualTo(xnFood.getFoodname());
                        List<XnFood> xnFoods1 = xnFoodMapper.selectByExample(xnFoodExample);
                        if(ObjectUtil.isEmpty(xnFoods1)){
                            xnFood.setCreatetime(date);
                            xnFood.setCreateuser(userId);
                            xnFoodMapper.insert(xnFood);
                            num++;
                        }
                    }
                };break;
                case SCORE : {
                    MyResult result = ExcelUtil.scoreInformation(schoolId,userId, newFile);
                    if(result.getStatus()!=1)return result;
                    List<Map<String,Object>> xnScores = (List<Map<String,Object>>)result.getData();
                    if(ObjectUtil.isEmpty(xnScores)) MyResult.failure("未发现有效的数据");
                    for(int i = 0 ; i< xnScores.size(); i++){
                        Map<String,Object> map = xnScores.get(i);
                        int studentId = (int) Double.parseDouble(map.get("studentId").toString());
                        String studentName = (String) map.get("studentName");
                        int classId = (int) Double.parseDouble(map.get("classId").toString());
                        String term = (String)map.get("term");
                        String testName = (String) map.get("testName");
                        String subject = (String) map.get("subject");
                        //map.get("score").toString();
                        String score = map.get("score").toString().substring(0,map.get("score").toString().length()-2);
                        int wrongNumber = (int) Double.parseDouble(map.get("wrongNumber").toString());
                        XnScoreExample xnScoreExample = new XnScoreExample();
                        XnScoreExample.Criteria criteria = xnScoreExample.createCriteria();
                        criteria.andSidEqualTo(schoolId);
                        criteria.andStuidEqualTo(studentId);
                        criteria.andTermEqualTo(term);
                        criteria.andTestnameEqualTo(testName);
                        criteria.andTsubjectEqualTo(subject);
                        List<XnScore> xnScores1 = xnScoreMapper.selectByExample(xnScoreExample);
                        if(!ObjectUtil.isEmpty(xnScores1)){
                            XnScore sc = xnScores1.get(0);
                            MyResult r = xnScoreService.addOrUpdateXnScore(sc.getId(),studentId,studentName,schoolId,classId,term,testName,subject,score,wrongNumber);
                            if(r.getStatus()!=1){
                                //return MyResult.failure("第"+(i+1)+"条数据出现了问题");
                                return r;
                            }
                            num++;
                        }else{
                            MyResult r = xnScoreService.addOrUpdateXnScore(null,studentId,studentName,schoolId,classId,term,testName,subject,score,wrongNumber);
                            if(r.getStatus()!=1){
                                //return MyResult.failure("第"+(i+1)+"条数据出现了问题");
                                return r;
                            }
                            num++;
                        }
                    }

                }break;
                case STUDENT :{
                    MyResult result = ExcelUtil.studentInformation(schoolId,userId,newFile);
                    if(result.getStatus()!=1)return result;
                    List<Map<String,Object>> hlStudentinfos = (List<Map<String,Object>>)result.getData();
                    if(ObjectUtil.isEmpty(hlStudentinfos))return MyResult.failure("未发现有效数据");
                    for(int i=0; i<hlStudentinfos.size(); i++){
                        Map<String,Object> hlStudentinfo = hlStudentinfos.get(i);
                        String name = (String)hlStudentinfo.get("name");
                        int grade = (int)hlStudentinfo.get("grade");
                        int classId = (int)hlStudentinfo.get("class");
                        String birthday = (String)hlStudentinfo.get("birthday");
                        int sex = (int)hlStudentinfo.get("sex");
                        String phone = (String) hlStudentinfo.get("phone");
                        String role = (String)hlStudentinfo.get("role");
                        HlStudentinfoExample hlStudentinfoExample = new HlStudentinfoExample();
                        HlStudentinfoExample.Criteria criteria = hlStudentinfoExample.createCriteria();
                        criteria.andSchoolidEqualTo(schoolId);
                        criteria.andClassidEqualTo(classId);
                        criteria.andSnameEqualTo(name);
                        criteria.andPhoneEqualTo(phone);
                        List<HlStudentinfo> hlStudentinfos1 = hlStudentinfoMapper.selectByExample(hlStudentinfoExample);
                        if(ObjectUtil.isEmpty(hlStudentinfos1)){
                            hlStudentInfoService.addExcelHlStudent(null,name,schoolId,grade,classId,birthday,sex,role,phone,null,null);
                            num++;
                        }else{
                            HlStudentinfo hlStudentinfo1 = hlStudentinfos1.get(0);
                            Integer id = hlStudentinfo1.getId();
                            hlStudentInfoService.addExcelHlStudent(id,name,schoolId,grade,classId,birthday,sex,role,phone,null,null);
                            num++;
                        }
                    }
                } break;
                default: return MyResult.failure("未知的数据类型");

            }
            if(type==TEACHER){
                return MyResult.success("成功导入"+num+"条数据，登录账号在data中",m);
            }else{
                return MyResult.success("成功导入"+num+"条数据");
            }
        }catch (IOException e){
            e.printStackTrace();
            return MyResult.failure("出现错误");
        }
    }
}
