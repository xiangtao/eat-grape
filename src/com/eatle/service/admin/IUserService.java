package com.eatle.service.admin;

import com.eatle.persistent.pojo.admin.User;
import com.eatle.persistent.pojo.admin.UserCriteria;
import com.eatle.utils.Pagination;
import java.util.List;
import java.util.Map;

public interface IUserService {
    /**
* @Description:
*
* @param entity
*/
    void add(User entity);

    /**
* @Description:
*
* @param entity
*/
    void delete(User entity);

    /**
* @Description:
*
* @param entity
*/
    void update(User entity);

    /**
* @Description:
*
* @param queryMap 查询参数
* @param currentPage 当前页
* @param pageSize 每页大小
*/
    Pagination findPagination(Map<String, Object> queryMap, int currentPage, int pageSize);

    /**
* @Description:
*
* @param id
*/
    User findById(long id);

    /**
* @Description:
*
*/
    List<User> findAll();

    /**
* @Description:
*
*/
    List<User> findByCriteria(UserCriteria criteria);
}