package com.github.tachesimazzoca.java.examples.jdbi;

import static org.junit.Assert.*;
import org.junit.Test;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.tweak.HandleCallback;

import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HandleTest {
    @Test
    public void testWithHandle() throws Exception {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();

        DBI dbi = new DBI(factory);
        Handle preH = dbi.withHandle(new HandleCallback<Handle>() {
            public Handle withHandle(Handle h) throws Exception {
                h.execute("CREATE TABLE foo (id int)");
                return h;
            }
        });
        assertTrue("The handle should be closed automatically", preH
                .getConnection().isClosed());

        Handle h = dbi.open();
        List<Map<String, Object>> rows = h.select("SELECT * FROM foo");
        assertTrue(rows.isEmpty());
        h.close();

        factory.dispose();
    }

    @Test
    public void testFolder() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();

        DBI dbi = new DBI(factory);
        final int max = 5;
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, max);

        Handle h = dbi.open();
        Map<String, String> userMap = h
                .createQuery("SELECT id, name FROM users")
                .fold(new HashMap<String, String>(),
                        new KeyValueFolder("id", "name"));
        assertEquals(max, userMap.size());
        for (Map.Entry<Integer, User> entry : usersTable.entrySet()) {
            assertEquals(entry.getValue().name,
                    userMap.get(entry.getKey().toString()));
        }
        h.close();

        factory.dispose();
    }

    @Test
    public void testMapper() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();

        DBI dbi = new DBI(factory);
        final int max = 5;
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, max);

        Handle h = dbi.open();
        Query<User> users = h
                .createQuery("SELECT id, name FROM users")
                .map(new UserMapper());
        int n = 0;
        for (User user : users) {
            assertTrue(user.equals(usersTable.get(user.id)));
            n++;
        }
        assertEquals(max, n);
        h.close();

        factory.dispose();
    }

    @Test
    public void testQueryIterator() {
        ConnectionPoolFactory factory = new ConnectionPoolFactory();

        DBI dbi = new DBI(factory);
        final int max = 5;
        Map<Integer, User> usersTable = UsersFixture.createTable(dbi, max);

        Handle h = dbi.open();
        Iterator<User> it = h
                .createQuery("SELECT id, name FROM users")
                .map(new UserMapper())
                .iterator();
        for (int i = 0; i < max; i++) {
            assertTrue(it.hasNext());
            User user = it.next();
            assertTrue(user.equals(usersTable.get(user.id)));
        }
        h.close();

        factory.dispose();
    }
}
