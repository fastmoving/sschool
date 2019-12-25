package com.usoft.sschool_zuul.util;

/**
 *@file 数据返回的封装
 *@date 2018年7月23日
 *@author jijh
 */
public class MyResult {
	
	
	private Integer status;//状态码
	
	private String message;//文本信息
	
	private Object data;//数据
	
	private Exception e;//异常
	
	private Integer currentPage;//当前页
	
	private Integer totalPage; //总页数
	
	private Integer pageSize;//每页大小
	
	private Integer totalNumber;//总数量
	/**
	 * c成功
	 */
	private static final int SUCESS = 1;
	/**
	 * 失败
	 */
	private static final int FAIL =2;
	/**
	 * 错误
	 */
	private static final int ER=3;
	/**
	 * 权限不够
	 */
	private static final int RI = 4;

	/**
	 * token过期
	 */
	private static final int TOKENOUT =99;
	
	private static final String YES = "success";
	private static final String NO = "fail";
	private static final String ERROR ="error";
	
	/*
	 * getter and setter
	 */
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Exception getE() {
		return e;
	}
	public void setE(Exception e) {
		this.e = e;
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	public Integer getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	public MyResult(Integer status, String message, Object obj) {
		this.status = status;
		this.message = message;
		this.data = obj;
	}
	public MyResult(Exception e) {
		this.status = MyResult.ER;
		this.message =MyResult.ERROR;
		this.data = null;
		//this.e = e;
	}
	public MyResult(Exception e, String message) {
		this.status = MyResult.ER;
		this.message =message;
		this.data = null;
		//this.e = e;
	}
	
	public MyResult(Integer status, String message, Object obj, Integer currentPage, Integer totalPage, Integer pageSize,Integer totalNumber) {
		this.status = status;
		this.message = message;
		this.data = obj;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.pageSize = pageSize;
		this.totalNumber = totalNumber;
	}
	/*
	 * when success
	 */
	public static MyResult success() {
		return new MyResult(MyResult.SUCESS,MyResult.YES,null);
	}
	public static MyResult success(Object data) {
		return new MyResult(MyResult.SUCESS,MyResult.YES,data);
	}
	public static MyResult success(String message) {
		return new MyResult(MyResult.SUCESS,message,null);
	}
	public static MyResult success(String message, Object data) {
		return new MyResult(MyResult.SUCESS,message,data);
	}
	/**
	 * 
	 * @param message 文本信息
	 * @param obj 对象
	 * @param currentPage 当前页数
	 * @param totalPage 总页数
	 * @param pageSize 每页大小
	 * @return
	 */
	public static MyResult success(String message, Object obj, Integer currentPage, Integer totalPage,Integer pageSize,Integer totalNumber) {
		return new MyResult(MyResult.SUCESS,message,obj, currentPage,totalPage,pageSize,totalNumber);
	}

	/*
	 * when fail
	 */
	public static MyResult failure() {
		return new MyResult(MyResult.FAIL,MyResult.NO,null);
	}
	public static MyResult failure(String message) {
		return new MyResult(MyResult.FAIL,message,null);
	}
	/*
	 * when exception
	 */
	public static MyResult myException(Exception e) {
		return new MyResult(e);
	}
	public static MyResult myException(Exception e,String message) {
		return new MyResult(e,message);
	}
	/*
	 *when token is out of data
	 */
	public static MyResult outOfDate(String message) {
		return new MyResult(MyResult.TOKENOUT, message,null);
	}
	/*
	 * when the right is denied 
	 */
	public static MyResult RightDenied(String message) {
		return new MyResult(MyResult.RI,message,null);
		
	}
	
}
