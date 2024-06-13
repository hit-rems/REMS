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
}
