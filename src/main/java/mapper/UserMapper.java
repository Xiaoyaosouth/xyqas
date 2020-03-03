package mapper;

import domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * MyBatis的用户DAO接口类
 */
public interface UserMapper {

    /**
     * 用户登录【已弃用，改用user_name查询返回User对象】
     * @param user
     * @return
     */
    @Deprecated
    @Select("select * from user where user_name=#{user_name} and user_password=#{user_password}")
    User selUser(User user);

    /**
     * 由用户ID查询用户
     * @param userId 用户ID
     * @return
     */
    @Select("select * from user where user_id=#{userId}")
    User selUserByUserId(int userId);

    /**
     * 修改用户信息
     * @param user 新用户对象
     * @return
     */
    @Update("UPDATE user SET "
            + "user_name=#{user_name},user_password=#{user_password},"
            + "user_nick=#{user_nick},user_status=#{user_status},"
            + "user_type=#{user_type}"
            + " WHERE user_id=#{user_id}")
    int updUserInfo(User user);

    /**
     * 添加用户数据（注册功能）
     * @param user 用户对象
     * @return
     */
    @Insert("INSERT INTO user " +
            "(user_name, user_nick, user_password, user_type ) " +
            "VALUES (#{user_name}, #{user_nick}, #{user_password}, #{user_type} " +
            ")")
    int insUser(User user);

    /**
     * 查询所有用户
     * @return List<User>
     */
    @Select("SELECT * FROM user")
    List<User> selUserAll();

    /**
     * 修改用户状态
     * @param userId 用户id
     * @param userStatus 用户新状态
     * @return
     */
    @Update("UPDATE user SET user_status = #{arg1} WHERE user_id = #{arg0}")
    int updUserStatus(int userId, int userStatus);

    /**
     * 由用户名查询用户
     * @param userName 用户名
     * @return 用户对象
     */
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User selUserByUserName(String userName);

}
