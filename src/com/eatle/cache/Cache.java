/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
 *
 * @Company:sxit_chongqing
 */
package com.eatle.cache;

import java.util.List;

import com.eatle.exception.CacheException;

/**
 * 缓存接口
 * 
 * @author Winter Lau
 */
public interface Cache {

     /**
      * Get an item from the cache, nontransactionally
      * 
      * @param key
      * @return the cached object or <tt>null</tt>
      * @throws CacheException
      */
     public Object get(Object key) throws CacheException;

     /**
      * Add an item to the cache, nontransactionally, with failfast semantics
      * 
      * @param key
      * @param value
      * @throws CacheException
      */
     public void put(Object key, Object value) throws CacheException;

     /**
      * Add an item to the cache
      * 
      * @param key
      * @param value
      * @throws CacheException
      */
     public void update(Object key, Object value) throws CacheException;

     @SuppressWarnings("rawtypes")
     public List keys() throws CacheException;

     /**
      * Remove an item from the cache
      */
     public void remove(Object key) throws CacheException;

     /**
      * Clear the cache
      */
     public void clear() throws CacheException;

     /**
      * Clean up
      */
     public void destroy() throws CacheException;

}
