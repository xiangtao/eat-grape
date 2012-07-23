<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<form id="pagerForm" method="post" action="system/priv/showSetSubPriv.htm">
	<!--【可选】其它查询条件，业务有关，有什么查询条件就加什么参数。
      			也可以在searchForm上设置属性rel=”pagerForm”，js框架会自动把searchForm搜索条件复制到pagerForm中 -->
	<input type="hidden" name="userName" value="${param.userName}" />
	<input type="hidden" name="email" value="${param.email}" />
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="system/priv/showAdd.htm?dialogId=${param.dialogId}&pid=${param.pid}" target="dialog"  mask="true" rel="${param.dialogId}_add"><span>添加权限</span></a></li>
			<li><a class="delete" href="system/priv/delete.htm?priv.id={sid}&navTabId=${param.navTabId}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="system/priv/showUpdate.htm?priv.id={sid}&navTabId=${param.navTabId}" target="dialog"  mask="true"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table"  layoutH="138">
		<thead>
			<tr>
				<th width="180">权限名称</th>
				<th width="200">菜单名称</th>
				<th width="200">动作标识</th>
				<th width="200">描述</th>
				<th width="200">是否显示</th>
				<th width="120">操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list" var="item">
				<tr target="sid" rel="<s:property value="#item.id" />">
					<td><s:property value="#item.privName" /></td>
					<td><s:property value="#item.meueName" /></td>
					<td><s:property value="#item.action" /></td>
					<td><s:property value="#item.description" /></td>
					<td><s:if test="#item.isShow == 1">是</s:if><s:else>否</s:else></td>
					<td>
						<a title="设置子权限" target="dialog" rel="<s:property value="#item.id" />" mask=true href="system/priv/showSetSubPriv.htm?pid=<s:property value='#item.id' />">设置子权限</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
