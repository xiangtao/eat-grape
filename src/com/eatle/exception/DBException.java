/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
 *
 * @Company:sxit_chongqing
 */
package com.eatle.exception;

/**
 * @Title:
 * @Description:
 * @Author:xt
 * @Since:2012-6-18
 * @Version:1.1.0
 */
public class DBException extends RuntimeException {

     /**
     * 
     */
     public DBException() {
          super();
     }

     /**
      * @param message
      * @param cause
      */
     public DBException(String message, Throwable cause) {
          super(message, cause);
     }

     /**
      * @param message
      */
     public DBException(String message) {
          super(message);
     }

     /**
      * @param cause
      */
     public DBException(Throwable cause) {
          super(cause);
          // TODO Auto-generated constructor stub
     }

     private static final long serialVersionUID = 3137325332494188646L;

}
