package com.fj.rems_backend.mapper;

import com.fj.rems_backend.pojo.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookMapper {
    @Insert("insert into book(user_id,equipment_id,start_time,end_time,status,reason,create_time,update_time)" +
            " values(#{userId},#{equipmentId},#{startTime},#{endTime},#{status},#{reason},#{createTime},#{updateTime})")
    void add(Book book);

    @Select("select * from book where equipment_id=#{id} and start_time >= #{localDateTime}")
    List<Book> query(int id, LocalDateTime localDateTime);
}
