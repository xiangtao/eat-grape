package com.eatle.persistent.pojo.admin;

import java.util.List;

public class PrivTree{
	
    private Priv priv; //当前权限
    private PrivTree parent;//父权限
    private List<PrivTree> childPrivs;//所有一级子权限
    
	public Priv getPriv() {
		return priv;
	}
	public void setPriv(Priv priv) {
		this.priv = priv;
	}
	public PrivTree getParent() {
		return parent;
	}
	public void setParent(PrivTree parent) {
		this.parent = parent;
	}
	public List<PrivTree> getChildPrivs() {
		return childPrivs;
	}
	public void setChildPrivs(List<PrivTree> childPrivs) {
		this.childPrivs = childPrivs;
	}

}