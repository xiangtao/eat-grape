/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
 *
 * @Company:sxit_chongqing
 */
package com.eatle.cache;

import com.eatle.exception.CacheException;

/**
 * @Title:
 * @Description:
 * @Author:asus
 * @Since:2012-6-18
 * @Version:1.1.0
 */
public interface CacheProvider {
     /**
      * Configure the cache
      * 
      * @param regionName
      *             the name of the cache region
      * @param autoCreate
      *             autoCreate settings
      * @throws CacheException
      */
     public Cache buildCache(String regionName, boolean autoCreate) throws CacheException;

     /**
      * Callback to perform any necessary initialization of the underlying cache implementation during SessionFactory construction.
      * 
      * @param properties
      *             current configuration settings.
      */
     public void start() throws CacheException;

     /**
      * Callback to perform any necessary cleanup of the underlying cache implementation during SessionFactory.close().
      */
     public void stop();
}
