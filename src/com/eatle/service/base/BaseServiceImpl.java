/**
 * @Copyright:Copyright (c) 2009 深讯信息发展股份有限公司（农网基地）
 *
 * @Company:sxit_chongqing
 */
package com.eatle.service.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.eatle.db.QueryHelper;
import com.eatle.exception.DBException;
import com.eatle.persistent.pojo.POJO;

/**
 * @Title:
 * @Description:
 * @Author:xt
 * @Since:2012-6-19
 * @Version:1.1.0
 */
public class BaseServiceImpl<T extends POJO> implements IBaseService<T> {

    private Class<T> clazz;

    /**
     * 通过子类的泛型定义取得对象类型Class.
     */
    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        this.clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public long add(T entity) {
        Map<String, Object> pojo_bean = ((POJO) entity).listInsertableFields();
        String[] fields = pojo_bean.keySet().toArray(new String[pojo_bean.size()]);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(POJO.tableName(clazz));
        sql.append('(');
        for (int i = 0; i < fields.length; i++) {
            if (i > 0)
                sql.append(',');
            sql.append(fields[i]);
        }
        sql.append(") VALUES(");
        for (int i = 0; i < fields.length; i++) {
            if (i > 0)
                sql.append(',');
            sql.append('?');
        }
        sql.append(')');
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = QueryHelper.getConnection().prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < fields.length; i++) {
                ps.setObject(i + 1, pojo_bean.get(fields[i]));
            }
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getLong(1) : -1;
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(ps);
            sql = null;
            fields = null;
            pojo_bean = null;
        }
    }

    @Override
    public void remove(Serializable id) {
        QueryHelper.update("DELETE FROM " + POJO.tableName(clazz) + " WHERE id=?", id);
    }


    @Override
    public T getById(Serializable id) {
        String sql = "SELECT * FROM " + POJO.tableName(clazz) + " WHERE id=?";
        return QueryHelper.read(clazz, sql, id);
    }

    @Override
    public long getResultNumberBySql(StringBuilder sql, Object... objects) {
        return QueryHelper.stat(sql.toString(), objects);
    }

    @Override
    public List getPagenationResultBySql(int currentPage, int pageSize, StringBuilder sql, Object... objects) {
        if(currentPage<=0){
            currentPage = 1 ;
        }
        if(pageSize<=0){
            pageSize = 10 ;
        }
        return QueryHelper.query_slice(clazz, sql.toString(), currentPage, pageSize , objects);
    }


    @Override
    public List getResultBySql(StringBuilder query, Object... objects) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List getResultBySql(int start, int pageSize, StringBuilder query, Object... objects) {
        // TODO Auto-generated method stub
        return null;
    }

}
