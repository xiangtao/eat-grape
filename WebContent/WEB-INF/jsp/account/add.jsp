<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="system/account/add.htm?navTabId=${param.navTabId}" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名称：</label>
				<input name="user.userName" class="required" type="text" size="30" value="张慧华" alt="请输入用户名称"/>
			</p>
			<p>
				<label>密码：</label>
				<input name="user.pwd" class="required" type="text" size="30"  alt="请输入密码"/>
			</p>
			<p>
				<label>邮箱：</label>
				<input type="text"  value="" name="user.email" class="textInput" alt="请输入邮箱">
			</p>
			<p>
				<label>用户类型：</label>
				<select name="user.type" class="required combox">
					<option value="1">个人</option>
					<option value="2" selected>公司</option>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
