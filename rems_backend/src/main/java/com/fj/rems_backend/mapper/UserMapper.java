package com.fj.rems_backend.mapper;

import com.fj.rems_backend.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    @Select("select * from user where id=#{id}")
    User findByUserId(Integer id);

    @Insert("insert into user(username,password,nickname,department,type,url,email,code) values(#{username},#{password},#{nickname},#{department},#{type},#{url},#{email},#{code})")
    void add(User user);

    @Update("update user set nickname=#{nickname},department=#{department} where id=#{id}")
    void update(User user);

    @Update("update user set url=#{avatarUrl} where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update user set password=#{newPwd} where id=#{id}")
    void updatePwd(String newPwd,Integer id);

    @Update("update user set code='1' where username=#{username}")
    void active(String username);

    @Update("update user set password=#{password} where username=#{username}")
    void resetPwd(String username, String password);

    @Insert("insert into authorization(username,nickname,department,email,status,create_time) values(#{username},#{nickname},#{department},#{email},'待审核',now())")
    void adminActive(User user);
}
