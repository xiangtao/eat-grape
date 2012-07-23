package com.eatle.persistent.mapper;

import com.eatle.persistent.pojo.admin.User;
import com.eatle.persistent.pojo.admin.UserCriteria;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByCriteria(UserCriteria example);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //统计总的记录数
    long selectCountByCriteria(UserCriteria example);

    //通过map参数进行查询
    List<User> selectByMap(Map paramMap);
}