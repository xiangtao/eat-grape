package com.eatle.service.admin.impl;

import com.eatle.persistent.mapper.UserMapper;
import com.eatle.persistent.pojo.admin.User;
import com.eatle.persistent.pojo.admin.UserCriteria.Criteria;
import com.eatle.persistent.pojo.admin.UserCriteria;
import com.eatle.service.admin.IUserService;
import com.eatle.utils.Pagination;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(User entity) {
        userMapper.insert(entity);
    }

    @Override
    public void delete(User entity) {
        userMapper.deleteByPrimaryKey(entity.getId());
    }

    @Override
    public void update(User entity) {
        userMapper.updateByPrimaryKey(entity);
    }

    @Override
    public Pagination findPagination(Map<String, Object> queryMap, int currentPage, int pageSize) {
        UserCriteria userCriteria = new UserCriteria();
        Criteria criteria = userCriteria.createCriteria();
        //if(queryMap!=null){
            //if(queryMap.containsKey("username")){
                //criteria.andUserNameLike("%"+(String)queryMap.get("username")+"%");
                //}
                //if(queryMap.containsKey("email")){
                    //criteria.andEmailLike((String)queryMap.get("email"));
                    //}
                    //}
                    //设置分页参数
                    userCriteria.setPageSize(pageSize);
                    userCriteria.setStartIndex((currentPage-1)*pageSize);
                    List<User> items = userMapper.selectByCriteria(userCriteria);
                    int totalCount = (int)userMapper.selectCountByCriteria(userCriteria);
                    return new Pagination(pageSize, currentPage, totalCount, items);
                }

    @Override
    public User findById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectByCriteria(null);
    }

    @Override
    public List<User> findByCriteria(UserCriteria criteria) {
        return userMapper.selectByCriteria(criteria);
    }
}