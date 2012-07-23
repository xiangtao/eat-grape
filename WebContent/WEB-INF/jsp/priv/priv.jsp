<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<form id="pagerForm" method="post" action="system/priv/showIndex.htm">
	
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="asc" />
	
	<!--【可选】其它查询条件，业务有关，有什么查询条件就加什么参数。
      			也可以在searchForm上设置属性rel=”pagerForm”，js框架会自动把searchForm搜索条件复制到pagerForm中 -->
	<input type="hidden" name="userName" value="${param.userName}" />
	<input type="hidden" name="email" value="${param.email}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="system/priv/showIndex.htm" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					权限名称：<input type="text" name="privName" />
				</td>
				<td>
					菜单名称：<input type="text" name="meueName" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="system/priv/showAdd.htm?navTabId=${param.navTabId}" target="dialog" mask="true"><span>添加权限</span></a></li>
			<li><a class="delete" href="system/priv/delete.htm?priv.id={sid}&navTabId=${param.navTabId}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="system/priv/showUpdate.htm?priv.id={sid}&navTabId=${param.navTabId}" target="dialog" mask="true"><span>修改</span></a></li>
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
			<s:iterator value="page.items" var="item">
				<tr target="sid" rel="<s:property value="#item.id" />">
					<td><s:property value="#item.privName" /></td>
					<td><s:property value="#item.meueName" /></td>
					<td><s:property value="#item.action" /></td>
					<td><s:property value="#item.description" /></td>
					<td><s:if test="#item.isShow == 1">是</s:if><s:else>否</s:else></td>
					<td>
						<a title="设置子权限" target="dialog" rel="<s:property value="#item.id" />_dialog" mask="true" href="system/priv/showSetSubPriv.htm?pid=<s:property value='#item.id' />&dialogId=<s:property value='#item.id' />_dialog">设置子权限</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount }" numPerPage="${page.pageSize }" pageNumShown="10" currentPage="${page.currentPage }"></div>

	</div>
</div>
