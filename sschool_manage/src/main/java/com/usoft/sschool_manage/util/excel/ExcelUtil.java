package com.usoft.sschool_manage.util.excel;


import com.usoft.smartschool.pojo.HlStudentinfo;
import com.usoft.smartschool.pojo.XnFood;
import com.usoft.smartschool.pojo.XnScore;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel文件解析
 * @Author jijh
 * @Date 2019/5/17 11:18
 */

public class ExcelUtil {


    /**
     * 教师
     */
    private static final int TEACHER = 1;

    /**
     * 班级
     */
    private static final int CLASS = 2;

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

    /**
     * 教师信息导入
     * @param schoolId
     * @param userId
     * @param file
     * @return
     */
    public static MyResult teacherInformation(Integer schoolId, Integer userId, File file){
        XSSFWorkbook workbook = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            List<Map<String,Object>> hlteachers = new ArrayList<>();
            for(int i = 2; i<rowCount; i++){
                XSSFRow row = sheet.getRow(i);
                Map<String,Object> map = new HashMap<>();
                for(int j=0; j<7; j++){
                    XSSFCell cell = row.getCell(j);
                    switch(j){
                        case 0:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                if(!ObjectUtil.isEmpty(cell.getStringCellValue())){
                                    return MyResult.failure("第"+i+"行，第"+1+"列数据有误");
                                }else{
                                    return MyResult.success(hlteachers);
                                }

                            }else{
                                map.put("name",cell.getStringCellValue());
                            }
                        };break;
                        case 1:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+2+"列数据有误");
                            }else{
                                map.put("birthday",cell.getStringCellValue());
                            }
                        };break;
                        case 2:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+3+"列数据有误");
                            }else{
                                map.put("sex",(int)cell.getNumericCellValue());
                            }
                        };break;
                        case 3:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+4+"列数据有误");
                            }else{
                                map.put("role",(int)cell.getNumericCellValue());
                            }
                        };break;
                        case 4:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+5+"列数据有误");
                            }else{
                                map.put("subject",cell.getStringCellValue());
                            }
                        };break;
                        case 5:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+6+"列数据有误");
                            }else{
                                map.put("phone",cell.getStringCellValue());
                            }
                        };break;
                        case 6:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+7+"列数据有误");
                            }else{
                                map.put("password",cell.getStringCellValue());
                                hlteachers.add(map);
                            }
                        };break;



                    }
                }
            }
            return MyResult.success(hlteachers);

        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("解析excel时出现错误");
        }
    }


    /**
     * 班级信息导入
     * @param schoolId
     * @param userId
     * @param file
     * @return
     */
    public static MyResult classInformation(Integer schoolId, Integer userId, File file){
        XSSFWorkbook workbook = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            List<Map<String,Object>> hlSchclasses = new ArrayList<>();
            for(int i = 2; i<rowCount; i++){
                XSSFRow row = sheet.getRow(i);
               Map<String,Object> map = new HashMap<>();
                for(int j=0; j<6; j++){
                    XSSFCell cell = row.getCell(j);
                    switch(j){
                        case 0:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                if(!ObjectUtil.isEmpty(cell.getStringCellValue())){
                                    return MyResult.failure("第"+i+"行，第"+1+"列数据有误");
                                }else{
                                    return MyResult.success(hlSchclasses);
                                }

                            }else{
                                map.put("className",cell.getStringCellValue());
                            }
                        };break;
                        case 1:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+2+"列数据有误");
                            }else{
                                map.put("teacherId",(int)cell.getNumericCellValue());
                            }
                        };break;
                        case 2:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+3+"列数据有误");
                            }else{
                                map.put("teacherName",cell.getStringCellValue());
                            }
                        };break;
                        case 3:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+4+"列数据有误");
                            }else{
                                map.put("grade",(int)cell.getNumericCellValue());
                            }
                        };break;
                        case 4:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+5+"列数据有误");
                            }else{
                                map.put("classNum",(int)cell.getNumericCellValue());
                            }
                        };break;
                        case 5:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+6+"列数据有误");
                            }else{
                                map.put("description",cell.getStringCellValue());
                                hlSchclasses.add(map);
                            }
                        };break;

                    }
                }
            }
            return MyResult.success(hlSchclasses);

        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("解析excel时出现错误");
        }
    }


    /**
     * 课程信息导入
     * @param schoolId
     * @param userId
     * @param file
     * @return
     */
    public static MyResult courseInformation(Integer schoolId, Integer userId, File file){
        XSSFWorkbook workbook = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            List<Map<String,Object>> courses = new ArrayList<>();
            for(int i = 2; i<rowCount; i++){
                XSSFRow row = sheet.getRow(i);
               Map<String,Object> map = new HashMap<>();

                for(int j=0; j<5; j++){
                    XSSFCell cell = row.getCell(j);
                    switch(j){
                        case 0:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                if(!ObjectUtil.isEmpty(cell.getStringCellValue())){
                                    return MyResult.failure("第"+i+"行，第"+1+"列数据有误");
                                }else{
                                    return MyResult.success(courses);
                                }

                            }else{
                               map.put("classId",cell.getNumericCellValue());
                            }
                        };break;
                        case 1:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+2+"列数据有误");
                            }else{
                                map.put("week",cell.getNumericCellValue());
                            }
                        };break;
                        case 2:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+3+"列数据有误");
                            }else{
                                map.put("lessonId",cell.getNumericCellValue());
                            }
                        };break;
                        case 3:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+4+"列数据有误");
                            }else{
                                map.put("subject",cell.getNumericCellValue());
                            }
                        };break;
                        case 4:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+5+"列数据有误");
                            }else{
                                map.put("teacherId",cell.getNumericCellValue());
                            }
                            courses.add(map);
                        };break;

                    }
                }
            }
            return MyResult.success(courses);

        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("解析excel时出现错误");
        }
    }

    /**
     * 菜单导入
     *
     * @param schoolId
     * @param userId
     * @param file
     * @return
     */
    public static MyResult foodInformation(Integer schoolId, Integer userId, File file) {
        //workbook = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            List<XnFood> xnFoods = new ArrayList<>();
            for (int i = 2; i < rowCount; i++) {
                XSSFRow row = sheet.getRow(i);
                XnFood xnFood = new XnFood();
                xnFood.setSid(schoolId);
                for (int j = 0; j < 3; j++) {
                    XSSFCell cell = row.getCell(j);
                    switch (j) {
                        case 0: {
                            if (cell.getCellTypeEnum() != CellType.NUMERIC) {
                                if (!ObjectUtil.isEmpty(cell.getStringCellValue())) {
                                    return MyResult.failure("第" + i + "行，第" + 1 + "列数据有误");
                                } else {
                                    return MyResult.success(xnFoods);
                                }

                            } else {
                                xnFood.setWeek((int) cell.getNumericCellValue());
                            }
                        }
                        ;
                        break;
                        case 1: {
                            if (cell.getCellTypeEnum() != CellType.NUMERIC) {
                                return MyResult.failure("第" + i + "行，第" + 2 + "列数据有误");
                            } else {
                                xnFood.setFoodtime((byte) cell.getNumericCellValue());
                            }
                        }
                        ;
                        break;
                        case 2: {
                            if (cell.getCellTypeEnum() != CellType.STRING) {
                                return MyResult.failure("第" + i + "行，第" + 3 + "列数据有误");
                            } else {
                                xnFood.setFoodname(cell.getStringCellValue().trim());
                            }
                            xnFoods.add(xnFood);
                        }
                        ;
                        break;
                    }
                }

            }
            return MyResult.success(xnFoods);

        } catch (Exception e) {
            e.printStackTrace();
            return MyResult.failure("解析excel时出现错误");
        }
    }


    /**
     * 成绩导入
     * @param schoolId
     * @param userId
     * @param file
     * @return
     */
    public static MyResult scoreInformation(Integer schoolId, Integer userId, File file){
        XSSFWorkbook workbook = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            List<Map<String,Object>> xnScores = new ArrayList<>();
            for(int i = 2; i<rowCount; i++){
                XSSFRow row = sheet.getRow(i);
                Map<String,Object> map = new HashMap<>();
                for(int j=0; j<8; j++){
                    XSSFCell cell = row.getCell(j);
                    switch(j){
                        case 0:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                if(!ObjectUtil.isEmpty(cell.getStringCellValue())){
                                    return MyResult.failure("第"+i+"行，第"+1+"列数据有误");
                                }else{
                                    return MyResult.success(xnScores);
                                }

                            }else{
                                map.put("studentId",cell.getNumericCellValue());
                            }
                        };break;
                        case 1:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+2+"列数据有误");
                            }else{
                                map.put("studentName",cell.getStringCellValue().trim());
                            }
                        };break;
                        case 2:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+3+"列数据有误");
                            }else{
                                map.put("classId",cell.getNumericCellValue());
                            }
                        };break;
                        case 3:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+4+"列数据有误");
                            }else{
                                map.put("term",cell.getStringCellValue().trim());
                            }
                        };break;
                        case 4:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+5+"列数据有误");
                            }else{
                                map.put("testName",cell.getStringCellValue().trim());
                            }
                        };break;
                        case 5:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+6+"列数据有误");
                            }else{
                                map.put("subject",cell.getStringCellValue());
                            }
                        };break;
                        case 6:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+7+"列数据有误");
                            }else{
                                map.put("score",cell.getNumericCellValue());
                            }
                        };break;
                        case 7:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+8+"列数据有误");
                            }else{
                                map.put("wrongNumber",cell.getNumericCellValue());
                            }
                            xnScores.add(map);
                        };break;
                    }
                }

            }
            return MyResult.success(xnScores);

        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("解析excel时出现错误");
        }
    }

    /**
     * 学生信息导入
     * @param schoolId
     * @param userId
     * @param file
     * @return
     */
    public static MyResult studentInformation(Integer schoolId, Integer userId, File file){
        XSSFWorkbook workbook = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            List<Map<String,Object>> hlStudentinfos = new ArrayList<>();
            for(int i = 2; i<rowCount; i++){
                XSSFRow row = sheet.getRow(i);
                Map<String,Object> hlStudentinfo = new HashMap<>();
                for(int j=0; j<7; j++){
                    XSSFCell cell = row.getCell(j);
                    switch(j){
                        case 0:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                if(!ObjectUtil.isEmpty(cell.getStringCellValue())){
                                    return MyResult.failure("第"+i+"行，第"+1+"列数据有误");
                                }else{
                                    return MyResult.success(hlStudentinfos);
                                }

                            }else{
                                hlStudentinfo.put("name",cell.getStringCellValue());
                            }
                        };break;
                        case 1:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+2+"列数据有误");
                            }else{
                                hlStudentinfo.put("grade",(int)cell.getNumericCellValue());
                            }
                        };break;
                        case 2:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+3+"列数据有误");
                            }else{
                                hlStudentinfo.put("class",(int)cell.getNumericCellValue());
                            }
                        };break;
                        case 3:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+4+"列数据有误");
                            }else{
                                hlStudentinfo.put("birthday",cell.getStringCellValue());
                            }
                        };break;
                        case 4:{
                            if(cell.getCellTypeEnum()!=CellType.NUMERIC){
                                return MyResult.failure("第"+i+"行，第"+5+"列数据有误");
                            }else{
                                hlStudentinfo.put("sex",(int)cell.getNumericCellValue());
                            }
                        };break;
                        case 5:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+6+"列数据有误");
                            }else{
                                hlStudentinfo.put("phone",cell.getStringCellValue());
                            }
                        };break;
                        case 6:{
                            if(cell.getCellTypeEnum()!=CellType.STRING){
                                return MyResult.failure("第"+i+"行，第"+7+"列数据有误");
                            }else{
                                hlStudentinfo.put("role",cell.getStringCellValue());
                                hlStudentinfos.add(hlStudentinfo);
                            }
                        };break;

                    }
                }
            }
            return MyResult.success(hlStudentinfos);

        }catch (Exception e){
            e.printStackTrace();
            return MyResult.failure("解析excel时出现错误");
        }
    }
}
