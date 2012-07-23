/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
	*
 * @Company:sxit_chongqing
 */
package com.eatle.action.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.eatle.action.BaseAction;
import com.eatle.persistent.pojo.admin.Priv;
import com.eatle.persistent.pojo.admin.PrivTree;
import com.eatle.persistent.pojo.admin.Role;
import com.eatle.service.admin.IPrivService;
import com.eatle.service.admin.IRoleService;
import com.eatle.utils.DwzAjaxJsonUtil;
import com.eatle.utils.Pagination;
import com.eatle.utils.TreeUtil;


/**
 *@Title:
 *@Description:
 *@Author:xt
 *@Since:2012-6-20
 *@Version:1.1.0
 */
public class RoleAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    
    @Resource
    private IRoleService roleService;
    @Resource
    private IPrivService privService;

    
    private Pagination page;
    
    private Role role;
    
    private List<PrivTree> topPrivTrees;
    private List<Priv> havePrivs;
    
    /**
     * 
     * @Description:显示首页
    	*
    	* @return
     */
   public String showIndex(){
       
	   Map<String,Object> params = super.getRequestParameters(request);
	   int pageNum = Pagination.CURRENTPAGE;
	   int pageSize = Pagination.PAGESIZE;
	   if(params.containsKey("pageNum")){
		   pageNum = Integer.parseInt((String)params.get("pageNum"));
	   }
	   if(params.containsKey("numPerPage")){
		   pageSize = Integer.parseInt((String)params.get("numPerPage"));
	   }
	   page = roleService.findPagination(params, pageNum, pageSize);
	   
       return "showIndex";
   }
   
   public String showAdd(){
	   return "showAdd";
   }
   
   public void add() throws IOException{
	   Map json = DwzAjaxJsonUtil.getDefaultAjaxJson();
	   json.put(DwzAjaxJsonUtil.KEY_NAVTABID, navTabId);
	   if(role==null){
		   json.put(DwzAjaxJsonUtil.KEY_STATUSCODE, 300);
	   }else{
		   roleService.add(role);
	   }
	   super.writeMap(json);
   }
   
   public void delete() throws IOException{
	   Map json = DwzAjaxJsonUtil.getDefaultAjaxJson();
	   json.put(DwzAjaxJsonUtil.KEY_NAVTABID, navTabId);
	   if(role==null){
		   json.put(DwzAjaxJsonUtil.KEY_STATUSCODE, 300);
	   }else{
		   roleService.delete(role);
	   }
	   super.writeMap(json);
	   
   }
   
   public String showUpdate(){
	   role = roleService.findById(role.getId());
	   return "showUpdate";
   }
   
   public void update() throws IOException{
	   Map json = DwzAjaxJsonUtil.getDefaultAjaxJson();
	   if(role==null){
		   json.put(DwzAjaxJsonUtil.KEY_STATUSCODE, 300);
	   }else{
		   roleService.update(role);
	   }
	   super.writeMap(json);
   }
   
   public String showSetPriv(){
	   havePrivs = roleService.findPrivsByRoleId(role.getId());
	   topPrivTrees = privService.findPrivTree();
	   request.setAttribute("treestr", TreeUtil.getTreeString(topPrivTrees,havePrivs).toString());
	   request.setAttribute("topPrivTrees", topPrivTrees);
	   return "showSetPriv";
   }
   
	public Pagination getPage() {
		return page;
	}
	public void setPage(Pagination page) {
		this.page = page;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Priv> getHavePrivs() {
		return havePrivs;
	}

	public void setHavePrivs(List<Priv> havePrivs) {
		this.havePrivs = havePrivs;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IPrivService getPrivService() {
		return privService;
	}

	public void setPrivService(IPrivService privService) {
		this.privService = privService;
	}

	public List<PrivTree> getTopPrivTrees() {
		return topPrivTrees;
	}

	public void setTopPrivTrees(List<PrivTree> topPrivTrees) {
		this.topPrivTrees = topPrivTrees;
	}

	
    
}
