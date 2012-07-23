/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
	*
 * @Company:sxit_chongqing
 */
package com.eatle.service.admin.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eatle.persistent.mapper.PrivMapper;
import com.eatle.persistent.mapper.RoleMapper;
import com.eatle.persistent.pojo.admin.Priv;
import com.eatle.persistent.pojo.admin.Role;
import com.eatle.persistent.pojo.admin.RoleCriteria;
import com.eatle.persistent.pojo.admin.RoleCriteria.Criteria;
import com.eatle.service.admin.IRoleService;
import com.eatle.utils.Pagination;


/**
 *@Title:
 *@Description:
 *@Author:xt
 *@Since:2012-6-19
 *@Version:1.1.0
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	@Resource
	private RoleMapper roleMapper;
	@Resource
	private PrivMapper privMapper;
	
	@Override
	public Pagination findPagination(Map<String, Object> queryMap,
			int currentPage, int pageSize) {
		RoleCriteria roleCriteria = new RoleCriteria();
		Criteria criteria = roleCriteria.createCriteria();
		if(queryMap!=null){
			if(queryMap.containsKey("roleName")){
				criteria.andRoleNameLike("%"+(String)queryMap.get("roleName")+"%");
			}
			if(queryMap.containsKey("description")){
				criteria.andDescriptionLike((String)queryMap.get("description"));
			}
		}
		//设置分页参数
		roleCriteria.setPageSize(pageSize);
		roleCriteria.setStartIndex((currentPage-1)*pageSize);
		
		List<Role> items = roleMapper.selectByCriteria(roleCriteria);
		int totalCount = (int)roleMapper.selectCountByCriteria(roleCriteria);
		
		
		
		return new Pagination(pageSize, currentPage, totalCount, items);
	}
	
	@Override
	public List<Role> findByCriteria(RoleCriteria criteria){
		return roleMapper.selectByCriteria(criteria);
	}
	
	@Override
	public List<Role> findAll(){
		return roleMapper.selectByCriteria(null);
	}
	
	@Override
    public Role findById(long id){
    	return roleMapper.selectByPrimaryKey(id);
    }
	@Override
	public void add(Role role){
		roleMapper.insert(role);
	}
	@Override
	public void update(Role role){
		roleMapper.updateByPrimaryKey(role);
	}
	
	@Override
	public void delete(Role role){
		roleMapper.deleteByPrimaryKey(role.getId());
	}

	@Override
	public List<Priv> findPrivsByRoleId(Long id) {
		return privMapper.selectByRoleId(id);
	}
}
