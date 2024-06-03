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

    @Insert("insert into user(username,password,nickname,department,type) values(#{username},#{password},#{nickname},#{department},#{type})")
    void add(User user);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=now() where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update user set password=#{newPwd},update_time=now() where id=#{id}")
    void updatePwd(String newPwd,Integer id);
}
