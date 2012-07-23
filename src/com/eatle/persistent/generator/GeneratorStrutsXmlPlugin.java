/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
	*
 * @Company:sxit_chongqing
 */
package com.eatle.persistent.generator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;


/**
 *@Title: 生成Action层 Struts xml 配置文件
 *@Description:
 *        配置文件总体结构是：
 *        每个一级模块一个struts配置文件:
 *        比如：系统管理为一级模块配置文件名为：struts-system.xml
 *        系统管理模块下有账号管理，角色管理，权限管理这些二级模块每个模块一个name="二级模块名" namespace="/一级模块名/二级模块名"
 *  	  action里保存CRUD,添加更新删除都是通过json操作所以这里没有配return,具体看Action里的方法    
 *       比如：账号管理
 *       <p>
 *       <!-- 账号管理 -->
 *		 <package name="account" extends="default" namespace="/system/account">
 *			<action name="*" class="com.eatle.action.admin.UserAction" method="{1}">
 *				<result name="showIndex">/WEB-INF/jsp/account/account.jsp</result>
 *				<result name="showAdd">/WEB-INF/jsp/account/add.jsp</result>
 *				<result name="showUpdate">/WEB-INF/jsp/account/update.jsp</result>
 *			</action>
 *		 </package>
 *
 *@Author:xt
 *@Since:2012-6-28
 *@Version:1.1.0
 */
public class GeneratorStrutsXmlPlugin extends PluginAdapter {

	private String STRUTS_PUBLIC_ID = "-//Apache Software Foundation//DTD Struts Configuration 2.1//EN";
	private String STRUTS_SYSTEM_ID = "http://struts.apache.org/dtds/struts-2.1.dtd";

	
	private String actionPackge ;
	private String strutsXmlFileName ;
	private String targetProject ;
	private String parentModulName;
	private String strutsXmlFilePath;
	private boolean isMergeable;
	

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	} 
	
	
	public GeneratorStrutsXmlPlugin(){
		super();
		Properties pro = new Properties();
		InputStream inStream = GeneratorServiceLayerPlugin.class.getResourceAsStream("generatorConfig.properties");
		try {
			pro.load(inStream);
			targetProject = pro.getProperty("targetProject");
			actionPackge = pro.getProperty("actionPackge");
		    strutsXmlFileName = pro.getProperty("strutsXmlFileName");
		    parentModulName  = pro.getProperty("parentModulName");
		    strutsXmlFilePath = pro.getProperty("strutsXmlFilePath");
		    String mg = pro.getProperty("isMergeableXml");
		    
		    if(mg==null){
		    	isMergeable = true;
		    }else{
		    	isMergeable = Boolean.parseBoolean(mg);
		    }
		    if(targetProject==null){
		    	targetProject = "src";
		    }
		    if(actionPackge==null){
		    	actionPackge = "com.eatle.action";
		    }
		    if(parentModulName==null){
		    	parentModulName = null;
		    }
		    if(strutsXmlFileName==null){
		    	if(parentModulName!=null){
		    		strutsXmlFileName = "struts-"+parentModulName+".xml";
			    }else{
			    	strutsXmlFileName = "struts-modul.xml";
			    }
		    }
		    if(strutsXmlFilePath==null){
		    	strutsXmlFilePath = actionPackge;
		    }
		    
		    
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(
			IntrospectedTable introspectedTable) {
		XmlElement parent = new XmlElement("struts"); //$NON-NLS-1$
		addPackageElement(parent,introspectedTable,parentModulName);
		Document document = new Document(STRUTS_PUBLIC_ID,STRUTS_SYSTEM_ID);
		document.setRootElement(parent);
		GeneratedXmlFile gxf = new GeneratedXmlFile(document, strutsXmlFileName+".xml", strutsXmlFilePath, targetProject, isMergeable);
		List<GeneratedXmlFile> gxfs = new ArrayList<GeneratedXmlFile>();
		gxfs.add(gxf);
		return gxfs;
	}

	private void addPackageElement(XmlElement parent,
			IntrospectedTable introspectedTable,String parentModulName) {
		String recordType = introspectedTable.getBaseRecordType();
		int idx = recordType.lastIndexOf(".");
    	if(idx!=-1){
    		recordType = recordType.substring(idx+1);
    	}
    	String recordLowerFullType = recordType.substring(0, 1).toLowerCase()+recordType.substring(1);
    	String namespace =   "/"+recordLowerFullType;
    	if(parentModulName!=null){
    		namespace = "/"+parentModulName+namespace;
    	}
    	XmlElement pkg = new XmlElement("package"); //$NON-NLS-1$
		pkg.addAttribute(new Attribute("name", recordLowerFullType)); //$NON-NLS-1$
		pkg.addAttribute(new Attribute("namespace", namespace)); //$NON-NLS-1$
        
		XmlElement action = new XmlElement("action"); //$NON-NLS-1$
        action.addAttribute(new Attribute("name", "*")); //$NON-NLS-1$
        action.addAttribute(new Attribute("class", recordType)); //$NON-NLS-1$
        action.addAttribute(new Attribute("method", "{1}")); //$NON-NLS-1$
        pkg.addElement(action);
        
        XmlElement result = new XmlElement("result"); //$NON-NLS-1$
        result.addAttribute(new Attribute("name", "showIndex")); //$NON-NLS-1$
        TextElement text = new TextElement("/WEB-INF/jsp/"+recordLowerFullType+"/"+recordLowerFullType+".jsp");
        result.addElement(text);
        action.addElement(result);
        
        result = new XmlElement("result"); //$NON-NLS-1$
        result.addAttribute(new Attribute("name", "showAdd")); //$NON-NLS-1$
        text = new TextElement("/WEB-INF/jsp/"+recordLowerFullType+"/add.jsp");
        result.addElement(text);
        action.addElement(result);
        
        result = new XmlElement("result"); //$NON-NLS-1$
        result.addAttribute(new Attribute("name", "showUpdate")); //$NON-NLS-1$
        text = new TextElement("/WEB-INF/jsp/"+recordLowerFullType+"/update.jsp");
        result.addElement(text);
        action.addElement(result);
        
        parent.addElement(pkg);
        
        
	}
	
}
