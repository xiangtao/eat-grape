package com.eatle.persistent.generator;

import static org.mybatis.generator.internal.util.messages.Messages.getString;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
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
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
  
/**
 * 
 *@Title:	selectByMap mapper 生成器
 *@Description:
 *@Author:xt
 *@Since:2012-7-3
 *@Version:1.1.0
 */
public class GenSelectByMapPlugin extends PluginAdapter {  
  
    @Override  
    public boolean validate(List<String> warnings) {  
        return true;  
    }  
    @Override  
    public boolean clientGenerated(Interface interfaze,  
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {  
  
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();  
        FullyQualifiedJavaType type =  FullyQualifiedJavaType.getNewMapInstance();
        importedTypes.add(type);  
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());  
  
        Method method = new Method();  
        method.setVisibility(JavaVisibility.PUBLIC);  
  
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType  
                .getNewListInstance();  
        FullyQualifiedJavaType listType;  
        if (introspectedTable.getRules().generateBaseRecordClass()) {  
            listType = new FullyQualifiedJavaType(introspectedTable  
                    .getBaseRecordType());  
        } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {  
            listType = new FullyQualifiedJavaType(introspectedTable  
                    .getPrimaryKeyType());  
        } else {  
            throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$  
        }  
  
        importedTypes.add(listType);  
        returnType.addTypeArgument(listType);  
        method.setReturnType(returnType);  
  
        method.setName("selectByMap");
        method.addParameter(new Parameter(type, "paramMap")); //$NON-NLS-1$  
        method.addJavaDocLine("//通过map参数进行查询");
        
        interfaze.addImportedTypes(importedTypes);  
        interfaze.addMethod(method);  
  
        return true;  
    }  
  
    @Override  
    public boolean sqlMapDocumentGenerated(Document document,  
            IntrospectedTable introspectedTable) {  
  
        XmlElement parentElement = document.getRootElement();  
        
        //生成select count(*)...获得总记录数
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                        "id","selectByMap")); //$NON-NLS-1$
        answer.addAttribute(new Attribute(
                "resultMap", introspectedTable.getBaseResultMapId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", "Map")); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select * from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        
        XmlElement whereElement = new XmlElement("where"); //$NON-NLS-1$
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty()); //$NON-NLS-1$
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

            sb.setLength(0);
            sb.append("and ");
            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(
                    introspectedColumn, "")); //$NON-NLS-1$
            

            isNotNullElement.addElement(new TextElement(sb.toString()));
            
            whereElement.addElement(isNotNullElement);
        }
        answer.addElement(whereElement);
        
        parentElement.addElement(answer);
        return true;  
    }  
    
  
}  