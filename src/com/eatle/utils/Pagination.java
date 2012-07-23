/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
	*
 * @Company:sxit_chongqing
 */
package com.eatle.utils;

import java.util.List;


/**
 *@Title: 封装分页对象
 *@Description:
 *@Author:xt
 *@Since:2012-6-22
 *@Version:1.1.0
 */
public class Pagination {
	public static int PAGESIZE  = 15;
	public static int CURRENTPAGE  = 1;
	
	private int pageSize = PAGESIZE;    //每页记录数
	private int currentPage = CURRENTPAGE;  //当前页号
	private int totalCount;       //总记录数
	private List<?> items;        //记录对象列表
	
	
	public Pagination() {
		super();
	}
	
	/**
	 * @param pageSize
	 * @param currentPage
	 * @param totalCount
	 * @param items
	 */
	public Pagination(int pageSize, int currentPage, int totalCount,
			List<?> items) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.items = items;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}
	
	
}
