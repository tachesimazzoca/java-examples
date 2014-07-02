package com.github.tachesimazzoca.java.examples.jdbi;

import static org.junit.Assert.*;
import org.junit.Test;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.ImmutableMap;

public class UserDaoTest {
    @Test
    public void testFindById() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();
        DBI dbi = new DBI(factory);
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, 5);
        UserDao dao = new UserDao();
        Handle h = null;
        try {
            h = dbi.open();
            for (Map.Entry<Integer, User> entry : usersTable.entrySet()) {
                User a = entry.getValue();
                User b = dao.findById(h, a.id);
                assertTrue(b.equals(a));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (h != null)
                h.close();
            factory.dispose();
        }
    }

    @Test
    public void testCount() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();
        DBI dbi = new DBI(factory);
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, 5);
        UserDao dao = new UserDao();
        Handle h = null;
        try {
            h = dbi.open();
            assertEquals(usersTable.size(), dao.count(h));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (h != null)
                h.close();
            factory.dispose();
        }
    }

    @Test
    public void testFindAll() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();
        DBI dbi = new DBI(factory);
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, 5);
        UserDao dao = new UserDao();
        Handle h = null;
        try {
            h = dbi.open();
            Iterable<User> users = dao.findAll(h);
            for (User user : users) {
                assertTrue(user.equals(usersTable.get(user.id)));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (h != null)
                h.close();
            factory.dispose();
        }
    }

    class CreateUserTask implements Runnable {
        private final int num;
        private final Handle h;
        private final UserDao userDao;
        private final Set<Integer> idSet;

        public CreateUserTask(int num, Handle h,
                UserDao userDao, Set<Integer> idSet) {
            this.num = num;
            this.h = h;
            this.userDao = userDao;
            this.idSet = idSet;
        }

        public void run() {
            for (int i = 0; i < num; i++) {
                User u = userDao.insert(h, ImmutableMap.<String, Object> of(
                        "name", "user"));
                idSet.add(u.id);
            }
        }
    }

    @Test
    public void testInsert() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();
        DBI dbi = new DBI(factory);
        UsersFixture.createTable(dbi);
        UserDao dao = new UserDao();
        Handle h1 = null;
        Handle h2 = null;
        Set<Integer> idSet = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
        try {
            h1 = dbi.open();
            h2 = dbi.open();
            int max1 = 1000;
            int max2 = 1000;
            Thread t1 = new Thread(new CreateUserTask(max1, h1, dao, idSet));
            Thread t2 = new Thread(new CreateUserTask(max2, h2, dao, idSet));
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            assertEquals(max1 + max2, idSet.size());
            for (int id : idSet) {
                assertTrue(id >= 1 && id <= max1 + max2);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (h1 != null)
                h1.close();
            if (h2 != null)
                h2.close();
            factory.dispose();
        }
    }
}
