/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
 *
 * @Company:sxit_chongqing
 */
package com.eatle.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.eatle.exception.DBException;

/**
 * @Title:
 * @Description:
 * @Author:asus
 * @Since:2012-6-18
 * @Version:1.1.0
 */
public class Configurations {

     /**
      * @Description:
      * @return
     * @throws SQLException 
      */
     public static Connection getConnection(){
         try {
            return DBManager.getConnection();
        } catch (SQLException e) {
            throw new DBException(e);
        }
     }

}
