/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
	*
 * @Company:sxit_chongqing
 */
package com.eatle.utils;

import java.util.HashMap;
import java.util.Map;


/**
 *@Title:
 *@Description:
 *@Author:xt
 *@Since:2012-6-23
 *@Version:1.1.0
 */
public class DwzAjaxJsonUtil {
	
	public static String KEY_STATUSCODE = "statusCode";
	public static String KEY_MESSAGE = "message";
	public static String KEY_NAVTABID = "navTabId";
	public static String KEY_REL = "rel";
	public static String KEY_CALLBACKTYPE = "callbackType";
	public static String KEY_FORWARDURL = "forwardUrl";
	public static Map<String,Object> getDefaultAjaxJson(){
		Map<String,Object> json = new HashMap<String,Object>();
		json.put("statusCode", 200);
		json.put("message","操作成功");
		json.put("navTabId","");
		json.put("rel","");
		json.put("callbackType","closeCurrent");
		json.put("forwardUrl","");
		return json;
	}

}
