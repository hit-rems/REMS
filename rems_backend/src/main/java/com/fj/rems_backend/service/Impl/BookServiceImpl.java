package com.fj.rems_backend.service.Impl;

import com.fj.rems_backend.mapper.BookMapper;
import com.fj.rems_backend.pojo.Book;
import com.fj.rems_backend.service.BookService;
import com.fj.rems_backend.service.EquipmentService;
import com.fj.rems_backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public void add(Map<String, Object> map) {
        Book book = new Book();
        Map<String, Object> mapInfo = ThreadLocalUtil.get();
        book.setUserId((Integer) mapInfo.get("id"));
        book.setEquipmentId((Integer) map.get("id"));
        book.setStatus("待审核");
        book.setReason((String) map.get("reason"));
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        //取出时间
        LocalDateTime startTime = LocalDateTime.now();
        int day = (int) map.get("day");
        startTime = startTime.plusDays(day);
        startTime = startTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Boolean> timelist = (List<Boolean>) map.get("timelist");
        for (int i = 0; i < 6; i++) {
            if (timelist.get(i)) {
                book.setStartTime(startTime.plusHours(i * 4));
                book.setEndTime(startTime.plusHours(i * 4 + 4));
                bookMapper.add(book);
            }
        }
    }

    @Override
    public List<Boolean> query(Map<String, Object> map) {
        int id = (int) map.get("id");
        //取出时间
        int time = (int) map.get("time");
        //查询该设备号的当天之后预约的时间
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusDays(time);
        //将当天的时间设置为0
        localDateTime = localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Book> books = bookMapper.query(id, localDateTime);
        //将一天分成6个时间段，返回一个boolean数组
        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            //如books的开始时间与当天时间加i*4小时相等，则返回true
            boolean flag = false;
            for (Book book : books) {
                if (book.getStartTime().equals(localDateTime.plusHours(i * 4))) {
                    flag = true;
                    break;
                }
            }
            list.add(flag);
        }
        return list;
    }
}
