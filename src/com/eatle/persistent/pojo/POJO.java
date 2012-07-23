package com.eatle.persistent.pojo;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.eatle.utils.Inflector;


/**
 *@Title:数据库对象的基类
 *@Description:
 *@Author:xt
 *@Since:2012-6-19
 *@Version:1.1.0
 */
public class POJO implements Serializable{

     private static final long serialVersionUID = 1L;
     private long id;
     public long getId() {
          return id;
     }
     public void setId(long id) {
          this.id = id;
     }
     
     /**
       * 列出要插入到数据库的域集合，子类可以覆盖此方法
       * @return
      */
      @SuppressWarnings("unchecked")
    public Map<String, Object> listInsertableFields() {
          try {
              Map<String, Object> props = BeanUtils.describe(this);
              if(getId() <= 0)
                  props.remove("id");
              props.remove("class");
              return props ;
          } catch (Exception e) {
              throw new RuntimeException("Exception when Fetching fields of " + this);
          }
    }
      
      /**
       * 返回默认的对象对应的表名
       * @return
       */
      public static String tableName(Class clazz) {
        return "t_" + Inflector.getInstance().tableize(clazz);
      }
}
