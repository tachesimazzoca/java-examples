package com.github.tachesimazzoca.java.examples.jdbi;

import static org.junit.Assert.*;
import org.junit.Test;

import org.skife.jdbi.v2.DBI;

import java.util.Map;

public class UserDaoTest {
    @Test
    public void testFindById() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();
        DBI dbi = new DBI(factory);
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, 5);
        UserDao dao = new UserDao(dbi.open());
        for (Map.Entry<Integer, User> entry : usersTable.entrySet()) {
            User a = entry.getValue();
            User b = dao.findById(a.id);
            assertTrue(b.equals(a));
        }
        dao.close();
        factory.dispose();
    }

    @Test
    public void testCount() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();
        DBI dbi = new DBI(factory);
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, 5);
        UserDao dao = new UserDao(dbi.open());
        assertEquals(usersTable.size(), dao.count());
        dao.close();
        factory.dispose();
    }

    @Test
    public void testFindAll() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();
        DBI dbi = new DBI(factory);
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, 5);
        UserDao dao = new UserDao(dbi.open());
        Iterable<User> users = dao.findAll();
        for (User user : users) {
            assertTrue(user.equals(usersTable.get(user.id)));
        }
        dao.close();
        factory.dispose();
    }
}
