package cn.cao.travel.mapper;

import cn.cao.travel.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author cao
 * @create 2020/8/31 - 22:07
 */
public interface UserMapper {
    //用户注册
    int userReg(User user);

    //登录
    User login(User user);

    //查询用户是否存在
    User queryUserByUsername(@Param("username") String username);


}
