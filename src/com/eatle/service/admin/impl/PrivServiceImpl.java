/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
	*
 * @Company:sxit_chongqing
 */
package com.eatle.service.admin.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eatle.persistent.mapper.PrivMapper;
import com.eatle.persistent.pojo.admin.Priv;
import com.eatle.persistent.pojo.admin.PrivCriteria;
import com.eatle.persistent.pojo.admin.PrivCriteria.Criteria;
import com.eatle.persistent.pojo.admin.PrivTree;
import com.eatle.service.admin.IPrivService;
import com.eatle.utils.Pagination;


/**
 *@Title:
 *@Description:
 *@Author:xt
 *@Since:2012-6-19
 *@Version:1.1.0
 */
@Service("privService")
public class PrivServiceImpl implements IPrivService {

	@Resource
	private PrivMapper privMapper;
	
	@Override
	public Pagination findPagination(Map<String, Object> queryMap,
			int currentPage, int pageSize) {
		PrivCriteria privCriteria = new PrivCriteria();
		Criteria criteria = privCriteria.createCriteria();
		if(queryMap!=null){
			if(queryMap.containsKey("privName")){
				criteria.andPrivNameLike("%"+(String)queryMap.get("privName")+"%");
			}
			if(queryMap.containsKey("menuName")){
				criteria.andMeueNameLike((String)queryMap.get("menuName"));
			}
			if(queryMap.containsKey("pidisnull")){
				criteria.andPIdIsNull();
			}
		}
		//设置分页参数
		privCriteria.setPageSize(pageSize);
		privCriteria.setStartIndex((currentPage-1)*pageSize);
		
		List<Priv> items = privMapper.selectByCriteria(privCriteria);
		int totalCount = (int)privMapper.selectCountByCriteria(privCriteria);
		
		
		
		return new Pagination(pageSize, currentPage, totalCount, items);
	}
	
	
	@Override
	public List<Priv> findByCriteria(PrivCriteria criteria){
		return privMapper.selectByCriteria(criteria);
	}
	
	@Override
	public List<Priv> findAll(){
		return privMapper.selectByCriteria(null);
	}
	
	@Override
    public Priv findById(long id){
    	return privMapper.selectByPrimaryKey(id);
    }
	@Override
	public void add(Priv priv){
		privMapper.insert(priv);
	}
	@Override
	public void update(Priv priv){
		privMapper.updateByPrimaryKey(priv);
	}
	
	@Override
	public void delete(Priv priv){
		privMapper.deleteByPrimaryKey(priv.getId());
	}
	@Override
	public List<PrivTree> findPrivTree(){
		List<Priv> list = privMapper.selectByMap(null);
		List<PrivTree> ptList = new ArrayList<PrivTree>();
		if(list==null) return null;
		for(int i=0;i<list.size();i++){
			Priv pri = list.get(i);
			if(pri.getPId() == null){
				ptList.add(findAllChildPrivs(pri,null,list));
			}
		}
		return ptList;
	}
	
	private Priv findParentPriv(Priv priv,List<Priv> list){
		for(int i=0;i<list.size();i++){
			Priv pri = list.get(i);
			if(priv.getPId()==pri.getId()){
				return pri;
			}
		}
		return null;
	}
	
	private PrivTree findAllChildPrivs(Priv cur,PrivTree ptree,List<Priv> list){
		PrivTree topTree = new PrivTree();
		topTree.setParent(ptree);	
		topTree.setPriv(cur);
		
		List<PrivTree> temp = new ArrayList<PrivTree>();
		for(int i=0;i<list.size();i++){
			Priv pri = list.get(i);
			if(cur.getId()==pri.getPId()){
				temp.add(findAllChildPrivs(pri,topTree,list));
			}
		}
		if(temp.size()==0){
			topTree.setChildPrivs(null);
		}else{
			topTree.setChildPrivs(temp);
		}
		return topTree;
	}
	
	private List<Priv> findChildPrivs(Priv priv,List<Priv> list){
		List<Priv> temp = new ArrayList<Priv>();
		for(int i=0;i<list.size();i++){
			Priv pri = list.get(i);
			if(priv.getId()==pri.getPId()){
				temp.add(pri);
			}
		}
		return temp;
	}
	
}
