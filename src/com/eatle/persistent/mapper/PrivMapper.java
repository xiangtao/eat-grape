package com.eatle.persistent.mapper;

import com.eatle.persistent.pojo.admin.Priv;
import com.eatle.persistent.pojo.admin.PrivCriteria;
import java.util.List;
import java.util.Map;

public interface PrivMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Priv record);

    int insertSelective(Priv record);

    List<Priv> selectByCriteria(PrivCriteria example);

    Priv selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Priv record);

    int updateByPrimaryKey(Priv record);

    //统计总的记录数
    long selectCountByCriteria(PrivCriteria example);

    //通过map参数进行查询
    List<Priv> selectByMap(Map paramMap);
    
    List<Priv> selectByRoleId(long roleId);
}