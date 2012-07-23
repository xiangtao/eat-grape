/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
 *
 * @Company:sxit_chongqing
 */
package com.eatle.exception;

/**
 * 缓存异常
 * 
 * @author Winter Lau
 */
public class CacheException extends RuntimeException {

     public CacheException(String s) {
          super(s);
     }

     public CacheException(String s, Throwable e) {
          super(s, e);
     }

     public CacheException(Throwable e) {
          super(e);
     }

}
