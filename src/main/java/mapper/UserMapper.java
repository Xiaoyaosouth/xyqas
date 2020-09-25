package mapper;

import domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 更新用户最近登录时间
     * 2020-03-05 11:49
     * @param user 用户对象（需有用户id和最近登录时间）
     * @return
     */
    @Update("UPDATE user SET user_lastLoginTime = #{user_lastLoginTime} WHERE user_id = #{user_id}")
    int updUserLastLoginTime(User user);

    /**
     * 模糊查询用户（ID/用户名/昵称）
     * @param keyword 关键词
     * @return
     *
     * 2020-07-25 19:38 新增
     * 2020-09-20 16:00
     *  修复#{keyword}自带引号无法识别变量报错的问题，
     *  修复There is no getter for property named 'keyword' in class java.lang.String的问题（变量用@Param）
     * 2020-09-24 11:27 修复模糊搜索id显示id带同数字的所有用户（例如搜id=1显示了1和10）
     */
    @Select("SELECT * FROM user WHERE " +
            "user_id = ${keyword} OR " +
            "user_name LIKE '%${keyword}%' OR " +
            "user_nick LIKE '%${keyword}%'")
    List<User> selUserFuzzy(@Param(value="keyword") String keyword);

    /**
     * 更新用户昵称
     * @param user 用户对象，需包含id和nick
     * @return
     * 2020-09-25 16:44 新增
     */
    @Update("UPDATE user SET user_nick = #{user_nick} WHERE user_id = #{user_id}")
    int updUserNickName(User user);
}
