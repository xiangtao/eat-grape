<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

	<ul class="tree treeFolder treeCheck collapse" oncheck="kkk">
		<%=request.getAttribute("treestr") %>
	</ul>
