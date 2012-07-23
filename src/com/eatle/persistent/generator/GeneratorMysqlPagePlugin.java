package com.eatle.persistent.generator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
  
/**
 * 
 *@Title: 分页支持
 *@Description:
 *@Author:xt
 *@Since:2012-7-3
 *@Version:1.1.0
 */
public class GeneratorMysqlPagePlugin extends PluginAdapter {  
  
    @Override  
    public boolean validate(List<String> warnings) {  
        return true;  
    }  
    
    @Override  
    public boolean clientGenerated(Interface interfaze,  
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {  
  
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();  
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getExampleType());
        importedTypes.add(type);  
  
        Method method = new Method();  
        method.setVisibility(JavaVisibility.PUBLIC);  
  
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("long");
        method.setReturnType(returnType);  
  
        method.setName("selectCount"+introspectedTable.getSelectByExampleStatementId().substring(6));
        method.addParameter(new Parameter(type, "example")); //$NON-NLS-1$  
        method.addJavaDocLine("//统计总的记录数");
        
        interfaze.addImportedTypes(importedTypes);  
        interfaze.addMethod(method);  
  
        return true;  
    }  
    
  
    private void addField(String fieldName, FullyQualifiedJavaType fieldType,  
            TopLevelClass topLevelClass,String javaDocLine) {  
        Field tmpField = new Field(fieldName, fieldType);  
        tmpField.setVisibility(JavaVisibility.PRIVATE);  
        tmpField.addJavaDocLine(javaDocLine);
        topLevelClass.addField(tmpField);  
  
        Method setMethod = new Method();  
        setMethod.setName("set" + fieldName.toUpperCase().substring(0, 1)  
                + fieldName.substring(1));  
        Parameter param = new Parameter(fieldType, fieldName);  
        setMethod.addParameter(param);  
        setMethod.setVisibility(JavaVisibility.PUBLIC);  
        setMethod.addBodyLine("this." + fieldName + "=" + fieldName + ";");  
  
        topLevelClass.addMethod(setMethod);  
  
        Method getMethod = new Method();  
        getMethod.setName("get" + fieldName.toUpperCase().substring(0, 1)  
                + fieldName.substring(1));  
  
        getMethod.setReturnType(fieldType);  
        getMethod.setVisibility(JavaVisibility.PUBLIC);  
        getMethod.addBodyLine("return this." + fieldName + ";");  
  
        topLevelClass.addMethod(getMethod);  
  
    }  
  
    @Override  
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,  
            IntrospectedTable introspectedTable) {  
        FullyQualifiedJavaType intType = FullyQualifiedJavaType  
                .getIntInstance();  
        addField("startIndex", intType, topLevelClass,"//开始索引号");  
        addField("pageSize", intType, topLevelClass,"//每页大小");  
  
        Method newConstructorMethod = new Method();  
        newConstructorMethod.setConstructor(true);  
        newConstructorMethod.addParameter(new Parameter(intType, "pageSize"));  
        newConstructorMethod.addParameter(new Parameter(intType, "startIndex"));  
        newConstructorMethod.addBodyLine("this();");  
        newConstructorMethod.addBodyLine("this.pageSize=pageSize;");  
        newConstructorMethod.addBodyLine("this.startIndex=startIndex;");  
        newConstructorMethod.setVisibility(JavaVisibility.PUBLIC);  
        newConstructorMethod.setName(topLevelClass.getType().getShortName());  
  
        topLevelClass.addMethod(newConstructorMethod);  
        return true;  
    }  
  
  
    @Override  
    public boolean sqlMapDocumentGenerated(Document document,  
            IntrospectedTable introspectedTable) {  
  
        XmlElement parentElement = document.getRootElement();  
        
        //生成select count(*)...获得总记录数
        String fqjt = introspectedTable.getExampleType();
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                        "id", "selectCount"+introspectedTable.getSelectByExampleStatementId().substring(6))); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                "resultType", "long")); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", fqjt)); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        answer.addElement(new TextElement("select count(1) ")); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getExampleIncludeElement(introspectedTable));
        parentElement.addElement(answer);
        return true;  
    }  
    
    protected XmlElement getBaseColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getBaseColumnListId()));
        return answer;
    }
    
    protected XmlElement getBlobColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getBlobColumnListId()));
        return answer;
    }
    
    protected XmlElement getExampleIncludeElement(IntrospectedTable introspectedTable) {
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "_parameter != null")); //$NON-NLS-1$ //$NON-NLS-2$

        XmlElement includeElement = new XmlElement("include"); //$NON-NLS-1$
        includeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getExampleWhereClauseId()));
        ifElement.addElement(includeElement);

        return ifElement;
    }
    
    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
    		XmlElement element, IntrospectedTable introspectedTable) {
    	
    	/**为selectExample 加分页的代码**/
    	XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "pageSize > 0")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("limit ${startIndex},${pageSize}")); //$NON-NLS-1$
        element.addElement(ifElement);		
    	return true;
    }
    
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
    		XmlElement element, IntrospectedTable introspectedTable) {
    	/**为selectExample 加分页的代码**/
    	XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "pageSize > 0")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("limit ${startIndex},${pageSize}")); //$NON-NLS-1$
        element.addElement(ifElement);		
    	return true;
    }
  
}  