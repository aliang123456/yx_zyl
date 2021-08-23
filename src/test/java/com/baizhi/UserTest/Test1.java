package com.baizhi.UserTest;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Test1 {

    @Autowired
    private UserDao userDao;

    @Test
    public void testDao(){
        List<User> users = userDao.selectRange(0, 2);
        for (User user : users) {
            System.out.println(user);
        }
    }

}
