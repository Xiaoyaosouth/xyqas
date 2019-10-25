package service.impl;

import domain.User;
import mapper.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        Logger logger = Logger.getLogger(UserServiceImpl.class);
        logger.info(user.getUser_name() + "尝试登录");
        return userMapper.selUser(user);
    }

    @Override
    public User getUserById(int userId) {
        Logger logger = Logger.getLogger(UserServiceImpl.class);
        User user = null;
        logger.info("尝试查询ID为" + userId + "的用户");
        user = userMapper.selUserByUserId(userId);
        return user;
    }

    @Override
    public int updateUserInfo(User user) {
        Logger logger = Logger.getLogger(UserServiceImpl.class);
        logger.info("尝试修改ID为" + user.getUser_id() + "的用户信息");
        int result = userMapper.updUserInfo(user);
        return result;
    }

    @Override
    public String addUser(User user) {
        Logger logger = Logger.getLogger(UserServiceImpl.class);
        // 先检查要注册的用户是否存在
        logger.info("尝试检查用户名为" + user.getUser_name() + "是否存在...");
        User tmpUser = userMapper.selUser(user);
        if (tmpUser != null) {
            return "注册失败！用户已存在！";
        }
        String resultStr = null;
        switch (user.getUser_type()) {
            case 1:
                logger.info("尝试添加新管理员，新用户名：" + user.getUser_name());
                if (userMapper.insUser(user) > 0) {
                    resultStr = new String("注册管理员成功！");
                } else {
                    resultStr = new String("注册管理员失败！");
                }
                break;
            case 2:
                logger.info("尝试添加新用户，新用户名：" + user.getUser_name());
                if (userMapper.insUser(user) > 0) {
                    resultStr = new String("注册成功！");
                } else {
                    resultStr = new String("注册失败！");
                }
                break;
            default:
                break;
        }
        return resultStr;
    }

    @Override
    public List<User> getAllUser() {
        Logger logger = Logger.getLogger(UserServiceImpl.class);
        logger.info("尝试获取所有的用户信息...");
        List<User> userList = userMapper.selUserAll();
        if (userList != null){
            return userList;
        }
        return null;
    }

    /**
     * 修改用户状态
     * @param user_id 用户id
     * @param user_status 用户新状态：0正常，1禁用，2锁定
     * @return
     */
    @Override
    public String modifyUserStatus(int user_id, int user_status) {
        Logger logger = Logger.getLogger(UserServiceImpl.class);
        // 先检查用户是否存在
        logger.info("尝试检查id为" + user_id + "的用户是否存在...");
        if (userMapper.selUserByUserId(user_id) == null){
            return "错误：用户不存在";
        }
        String resultStr = null; // 保存结果
        switch (user_status){
            case 0: // 正常
                logger.info("尝试启用id为" + user_id + "的用户...");
                if (userMapper.updUserStatus(user_id, user_status) > 0){
                    resultStr = new String("启用成功！");
                }else{
                    resultStr = new String("启用失败！");
                }
               break;
            case 1: // 禁用
                logger.info("尝试禁用id为" + user_id + "的用户...");
                if (userMapper.updUserStatus(user_id, user_status) > 0){
                    resultStr = new String("禁用成功！");
                }else{
                    resultStr = new String("禁用失败！");
                }
                break;
            case 2:
                logger.info("尝试锁定id为" + user_id + "的用户...");
                if (userMapper.updUserStatus(user_id, user_status) > 0){
                    resultStr = new String("锁定成功！");
                }else{
                    resultStr = new String("锁定失败！");
                }
                break;
        }
        return resultStr;
    }

    @Override
    public User getUserByUserName(String user_name) {
        Logger logger = Logger.getLogger(UserServiceImpl.class);
        logger.info("尝试查询用户名为" + user_name + "的用户信息...");
        return userMapper.selUserByUserName(user_name);
    }
}
