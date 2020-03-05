package service;

import domain.User;

import java.util.List;

public interface UserService {

    /**
     * 用户登录
     * @param user 传入含有用户名和密码的user对象
     * @return
     */
    User login(User user);

    /**
     * 查询用户
     * @param userId
     * @return
     */
    User getUserById(int userId);

    /**
     * 更新用户信息
     * @param user 新用户对象
     * @return
     */
    int updateUserInfo(User user);

    /**
     * 添加用户
     * @param user 用户对象
     * @return
     */
    String addUser(User user);

    /**
     * 获得所有用户信息
     * @return List<User>
     */
    List<User> getAllUser();

    /**
     * 修改用户状态
     * @param user_id 用户id
     * @param user_status 用户新状态
     * @return
     */
    String modifyUserStatus(int user_id, int user_status);

    /**
     * 根据用户名获取用户信息
     * @param  user_name 用户名
     * @return 用户对象
     */
    User getUserByUserName(String user_name);

    /**
     * 修改用户最近登录时间
     * 2020-03-05 11:55
     * @param user 用户对象
     * @return 0成功，-1用户不存在，-2更新失败
     */
    int modifyUserLastLoginTime(User user);
}
