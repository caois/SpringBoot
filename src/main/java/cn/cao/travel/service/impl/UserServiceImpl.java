package cn.cao.travel.service.impl;

import cn.cao.travel.entity.User;
import cn.cao.travel.mapper.UserMapper;
import cn.cao.travel.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cao
 * @create 2020/8/31 - 22:23
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int userReg(User user) {

        if(userMapper.queryUserByUsername(user.getUsername()) == null){

            return userMapper.userReg(user);

        }else {
            throw new RuntimeException("用户名已存在---");
        }

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {

        return userMapper.login(user);
    }
}
