/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
	*
 * @Company:sxit_chongqing
 */
package com.eatle.service.base;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T> {

    /**
     * Add entity
     * @param entity
     */
    public long add(T entity);
    
    /**
     * Remove entity
     * @param clazz
     * @param id
     */
    public void remove(Serializable id);
    
    /**
     * Get entity by id
     * @param clazz
     * @param id
     * @return
     */
    public T getById(Serializable id);
    
    /**
     * 得到结果个数
     * @param sql
     * @param objects
     * @return
     */
    public long getResultNumberBySql(final StringBuilder sql, final Object... objects);

    /**
     * 得到分页数据
     * @param currentPage
     * @param pageSize
     * @param sql
     * @param ojbects
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List getPagenationResultBySql(int currentPage, int pageSize, StringBuilder sql, Object... objects);
    
    
    /**
     * Get result
     * @param start
     * @param pageSize
     * @param query
     * @param ojbects
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List getResultBySql(final int start, final int pageSize, final StringBuilder query, final Object... objects);
    
    /**
     * Get result by sql
     * @param query
     * @param ojbects
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List getResultBySql(final StringBuilder query, final Object... objects);
    
   }
