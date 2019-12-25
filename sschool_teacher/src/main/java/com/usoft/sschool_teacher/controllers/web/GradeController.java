package com.usoft.sschool_teacher.controllers.web;

import com.usoft.smartschool.util.MyResult;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : 陈秋
 * time : 2019-05-20
 */
@CrossOrigin
@RestController
@RequestMapping("/teacher/web")
public class GradeController {

    /**
     * 导出成绩模板
     * @param request
     * @param response
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcle(HttpServletRequest request, HttpServletResponse response)  {

        try {
            //获取文件的路径
            String excelPath = request.getSession().getServletContext().getRealPath("xx.xls");
            String fileName = "xx.xls".toString(); // 文件的默认保存名
            // 读到流中
            InputStream inStream = new FileInputStream(excelPath);//文件的存放路径
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("xx.xls", "UTF-8"));
            // 循环取出流中的数据
            byte[] b = new byte[200];
            int len;

            while ((len = inStream.read(b)) > 0){
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* URL save = Thread.currentThread().getContextClassLoader().getResource("");
        String str = save.toString();
        str = str.substring(6, str.length());
        str = str.replaceAll("%20", " ");
        int num = str.lastIndexOf("sschool-teacher");// wgbs 为项目名，应用到不同的项目中，这个需要修改！
        str = str.substring(0, num + "sschool-teacher".length());
        str = str + "/demo/grade.xlsx";// Excel模板所在的路径。
        String path = request.getSession().getServletContext().getRealPath("/demo");
        File f = new File(str);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(("成绩导入模板" + ".xlsx").getBytes(), "iso-8859-1"));// 下载文件的名称
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream out;
        try {
            out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(f));
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/
    }

    /**
     * 导入日程模板
     * @param request
     * @param meetingId
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public MyResult uploadExcel(HttpServletRequest request, String meetingId,String classId,String subject) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        List<Map<String, Object>> listData = new ArrayList<>();
        StringBuilder errorRow = new StringBuilder("");
        if (suffix.equalsIgnoreCase("xls")) {
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }

                Map<String, Object> rowMap = new HashMap<>();
                rowMap.put("meetingId", meetingId);
                rowMap.put("loginUserId", "AB4383E6-E818-4235-8D22-C59870B01564");
                String customerName = row.getCell(1) == null ? "" : row.getCell(0).getStringCellValue().trim();
                if ("".equals(customerName)) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                String customerHospital = row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue().trim();
                if ("".equals(customerHospital)) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }

                /*List<String> customerIds = customerService.getCustomerId(customerName, customerHospital);
                if (customerIds.size() ==0) {
                    continue;
                }
                rowMap.put("customerId", customerIds.get(0));*/
                String scheduleDate = row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue().trim();
                if ("".equals(scheduleDate) || scheduleDate.trim().length() != 10) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                rowMap.put("scheduleDate", scheduleDate);
                String role = row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue().trim();
                if ("".equals(role)) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                Integer roleType = null;
               /* if (role.equals("主持")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHRDULEZHUCHI;
                } else if (role.equals("主席")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHEDULEZHUXI;
                } else if (role.equals("讲者")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHEDULEJIANGZHE;
                } else if (role.equals("嘉宾")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHEDULEJIABIN;
                } else if (role.equals("评委")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHEDULEPINGWEI;
                }*/
                rowMap.put("relationType", roleType);
                String luntan1 = row.getCell(4) == null ? "" : row.getCell(4).getStringCellValue().trim();
                rowMap.put("luntan1", luntan1);
                String luntan2 = row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue().trim();
                rowMap.put("luntan2", luntan2);
                String room = row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue().trim();
                rowMap.put("room", room);
                String detailTimeStr = row.getCell(7) == null ? ""
                        : row.getCell(7).getStringCellValue().trim().replace("：", ":");
                if ("".equals(detailTimeStr) || detailTimeStr.split("-").length != 2) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                String[] arr = detailTimeStr.split("-");
                rowMap.put("startTime", scheduleDate + " " + arr[0].trim() + ":00");
                rowMap.put("endTime", scheduleDate + " " + arr[1].trim() + ":00");
                String title = row.getCell(8) == null ? "" : row.getCell(8).getStringCellValue().trim();
                if ("".equals(title)) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                rowMap.put("resourceTitle", title);
                listData.add(rowMap);
            }
        } else if (suffix.equalsIgnoreCase("xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                Map<String, Object> rowMap = new HashMap<>();
                rowMap.put("meetingId", meetingId);
                rowMap.put("loginUserId", "AB4383E6-E818-4235-8D22-C59870B01564");
                String customerName = row.getCell(1) == null ? "" : row.getCell(0).getStringCellValue().trim();
                if ("".equals(customerName)) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                String customerHospital = row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue().trim();
                if ("".equals(customerHospital)) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                /*List<String> customerIds = customerService.getCustomerId(customerName, customerHospital);
                if (customerIds.size() ==0) {
                    continue;
                }
                rowMap.put("customerId", customerIds.get(0));*/
                String scheduleDate = row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue().trim();
                if ("".equals(scheduleDate) || scheduleDate.trim().length() != 10) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                rowMap.put("scheduleDate", scheduleDate);
                String role = row.getCell(3) == null ? "" : row.getCell(3).getStringCellValue().trim();
                if ("".equals(role)) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                Integer roleType = null;
               /* if (role.equals("主持")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHRDULEZHUCHI;
                } else if (role.equals("主席")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHEDULEZHUXI;
                } else if (role.equals("讲者")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHEDULEJIANGZHE;
                } else if (role.equals("嘉宾")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHEDULEJIABIN;
                } else if (role.equals("评委")) {
                    roleType = ConstantsData.RELATION_TYPE_SCHEDULEPINGWEI;
                }*/
                rowMap.put("relationType", roleType);
                String luntan1 = row.getCell(4) == null ? "" : row.getCell(4).getStringCellValue().trim();
                rowMap.put("luntan1", luntan1);
                String luntan2 = row.getCell(5) == null ? "" : row.getCell(5).getStringCellValue().trim();
                rowMap.put("luntan2", luntan2);
                String room = row.getCell(6) == null ? "" : row.getCell(6).getStringCellValue().trim();
                rowMap.put("room", room);
                String detailTimeStr = row.getCell(7) == null ? ""
                        : row.getCell(7).getStringCellValue().trim().replace("：", ":");
                if ("".equals(detailTimeStr) || detailTimeStr.split("-").length != 2) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                String[] arr = detailTimeStr.split("-");
                rowMap.put("startTime", scheduleDate + " " + arr[0].trim() + ":00");
                rowMap.put("endTime", scheduleDate + " " + arr[1].trim() + ":00");
                String title = row.getCell(8) == null ? "" : row.getCell(8).getStringCellValue().trim();
                if ("".equals(title)) {
                    errorRow.append("，" + (i + 1));
                    continue;
                }
                rowMap.put("resourceTitle", title);
                listData.add(rowMap);
            }
        }
        int i=0;
       /* if (listData.size() > 0) {
            i = sysResource.insertListImport(listData);
        }
        if (!"".equals(errorRow.toString())) {
            result.setCode(-1);
            result.setMsg("第 " + errorRow.toString().substring(1) + " 行数据有误，请重新导入（只需导入错误行！）");
        }*/
        if (i>0) {
            return new MyResult(1,"success",null);
        }
        return null;
    }
}
