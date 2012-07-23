/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
	*
 * @Company:sxit_chongqing
 */
package com.eatle.utils;

import java.util.List;

import com.eatle.persistent.pojo.admin.Priv;
import com.eatle.persistent.pojo.admin.PrivTree;


/**
 *@Title:
 *@Description:
 *@Author:xt
 *@Since:2012-6-24
 *@Version:1.1.0
 */
public class TreeUtil {

	
	public static StringBuilder getTreeString(List<PrivTree> topTree){
		StringBuilder sb = new StringBuilder("");
//		sb.append("<ul class=\"tree treeFolder treeCheck expand\" oncheck=\"kkk\">");
		if(topTree == null) return sb;
		for(int i=0;i<topTree.size();i++){
			PrivTree pt = topTree.get(i);
			sb.append(outputStr(pt));
		}
//		sb.append("</ul>");
		return sb;
	}
	
	public static StringBuilder getTreeString(List<PrivTree> topTree,List<Priv> hasPrivs){
		StringBuilder sb = new StringBuilder("");
//		sb.append("<ul class=\"tree treeFolder treeCheck expand\" oncheck=\"kkk\">");
		if(topTree == null) return sb;
		for(int i=0;i<topTree.size();i++){
			PrivTree pt = topTree.get(i);
			sb.append(outputStr(pt,hasPrivs==null?false:hasPrivs.contains(pt)));
		}
//		sb.append("</ul>");
		return sb;
	}

	/**
	 * @Description:
		*
		* @param pt
		* @return
	 */
	private static String outputStr(PrivTree pt) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li>");
		sb.append("<a>");
		sb.append(pt.getPriv().getPrivName());
		sb.append("</a>");
		List<PrivTree> child = pt.getChildPrivs();
		if(child!=null){
			for(int i=0;i<child.size();i++){
				sb.append("<ul>");
				sb.append(outputStr(child.get(i)));
				sb.append("</ul>");
			}
		}
		sb.append("</li>");
		return sb.toString();
	}
	
	private static String outputStr(PrivTree pt,boolean checked) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li>");
		sb.append("<a	checked="+checked+">");
		sb.append(pt.getPriv().getPrivName());
		sb.append("</a>");
		List<PrivTree> child = pt.getChildPrivs();
		if(child!=null){
			for(int i=0;i<child.size();i++){
				sb.append("<ul>");
				sb.append(outputStr(child.get(i)));
				sb.append("</ul>");
			}
		}
		sb.append("</li>");
		return sb.toString();
	}
	
}
