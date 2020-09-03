package cn.cao.travel.service;

import cn.cao.travel.entity.User;

/**
 * @author cao
 * @create 2020/8/31 - 22:21
 */
public interface UserService {

    //用户注册
    int userReg(User user);

    //登录/查询所有用户/查询一个用户是否存在
    User login(User user);

}
