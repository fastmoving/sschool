package com.usoft.sschool_pub.util;

import java.io.*;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportExcelUtil{

	/**
	 * 导出Excel
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容
	 * @param wb HSSFWorkbook对象模板
	 * @return
	 */
	public static void getHSSFWorkbook(String sheetName, String fileName, String[] title, String[][] values, HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response){
		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}

		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		for (int m=0;m<title.length;m++){
			sheet.setColumnWidth(m,4800);
		}
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(20);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个水平居中格式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);//天蓝色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充
		HSSFFont font=wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short)10);
		style.setFont(font);
		//声明列对象
		HSSFCell cell = null;

		//创建标题
		for(int i=0;i<title.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			if (i==title.length-1){

				cell.setCellStyle(style);
			}else {
				cell.setCellStyle(style);
			}
		}

		//创建内容
		for(int i=0;i<values.length;i++){
			row = sheet.createRow(i + 1);
			for(int j=0;j<values[i].length;j++){
				HSSFCell cell1 = row.createCell(j);
				cell1.setCellStyle(style);
				cell1.setCellValue(values[i][j]);

			}
		}

		//响应到客户端
		try {
			setResponseHeader(request, response, fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//发送响应流方法
	public static void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String userAgent = request.getHeader("user-agent");//判断浏览器类型

			if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0
					|| userAgent.indexOf("Safari") >= 0) {
				fileName= new String((fileName).getBytes(), "ISO8859-1");
			} else {
				fileName= URLEncoder.encode(fileName,"UTF8"); //其他浏览器
			}
			response.setContentType("application/vnd.ms-excel;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Pargam", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}