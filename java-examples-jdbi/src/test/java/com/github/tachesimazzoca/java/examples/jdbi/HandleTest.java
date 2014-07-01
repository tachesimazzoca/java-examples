package com.github.tachesimazzoca.java.examples.jdbi;

import static org.junit.Assert.*;
import org.junit.Test;

import org.h2.jdbcx.JdbcConnectionPool;

import org.skife.jdbi.v2.Folder2;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.HandleCallback;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class HandleTest {

    private static JdbcConnectionPool createJdbcConnectionPool() {
        JdbcConnectionPool pool = JdbcConnectionPool.create(
                "jdbc:h2:mem:test", "", "");
        pool.setMaxConnections(5);
        pool.setLoginTimeout(1);
        return pool;
    }

    private static Map<Integer, User> createUsersTable(DBI dbi) {
        return dbi.withHandle(new HandleCallback<Map<Integer, User>>() {
            public Map<Integer, User> withHandle(Handle h)
                    throws Exception {
                h.execute("DROP TABLE IF EXISTS users");
                h.execute("CREATE TABLE users (id int" +
                        ", name varchar(255) NOT NULL default '')");
                ImmutableMap.Builder<Integer, User> builder =
                        ImmutableMap.<Integer, User> builder();
                for (int n = 1; n < 10; n++) {
                    User user = new User(n, "user" + n);
                    builder.put(n, user);
                    h.execute("INSERT INTO users (id, name) VALUES (?, ?)",
                            user.id, user.name);
                }
                return builder.build();
            }
        });
    }

    @Test
    public void testWithHandle() throws Exception {
        JdbcConnectionPool pool = createJdbcConnectionPool();

        DBI dbi = new DBI(pool);
        Handle preH = dbi.withHandle(new HandleCallback<Handle>() {
            public Handle withHandle(Handle h) throws Exception {
                h.execute("CREATE TABLE users (id int, name varchar(255) NOT NULL default '')");
                h.execute("INSERT INTO users (id, name) VALUES (?, ?)", 1,
                        "foo");
                h.execute("INSERT INTO users (id, name) VALUES (?, ?)", 2,
                        "bar");
                return h;
            }
        });
        assertTrue("The handle should be closed automatically", preH
                .getConnection().isClosed());

        Handle h = dbi.open();
        List<Map<String, Object>> rs = h.select("SELECT name FROM users");
        assertEquals(2, rs.size());
        assertEquals("foo", rs.get(0).get("name"));
        assertEquals("bar", rs.get(1).get("name"));
        h.close();

        pool.dispose();
    }

    public class KVFolder implements Folder2<Map<String, String>> {
        private final String keyName;
        private final String valueName;

        public KVFolder(String keyName, String valueName) {
            this.keyName = keyName;
            this.valueName = valueName;
        }

        public Map<String, String> fold(Map<String, String> acc,
                ResultSet rs, StatementContext ctx) throws SQLException {
            acc.put(rs.getString(keyName),
                    rs.getString(valueName));
            return acc;
        }
    }

    @Test
    public void testFolder() {
        JdbcConnectionPool pool = createJdbcConnectionPool();
        DBI dbi = new DBI(pool);
        Map<Integer, User> usersTable = createUsersTable(dbi);

        Handle h = dbi.open();
        Map<String, String> userMap = h
                .createQuery("SELECT id, name FROM users")
                .fold(new HashMap<String, String>(), new KVFolder("id", "name"));
        for (Map.Entry<Integer, User> entry : usersTable.entrySet()) {
            assertEquals(entry.getValue().name,
                    userMap.get(entry.getKey().toString()));
        }

        pool.dispose();
    }

    public static class User {

        public final int id;
        public final String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof User))
                return false;
            User a = (User) o;
            return (a.id == this.id && a.name.equals(this.name));
        }

        @Override
        public int hashCode() {
            int h = 17;
            h = 31 * h + id;
            h = 31 * h + name.hashCode();
            return h;
        }
    }

    public static class UserMapper implements ResultSetMapper<User> {
        private static final UserMapper singleton = new UserMapper();

        private UserMapper() {
        }

        public static UserMapper getInstance() {
            return singleton;
        }

        public User map(int row, ResultSet rs, StatementContext ctx)
                throws SQLException {
            return new User(rs.getInt("id"), rs.getString("name"));
        }
    }

    @Test
    public void testMapper() {
        JdbcConnectionPool pool = createJdbcConnectionPool();
        DBI dbi = new DBI(pool);
        Map<Integer, User> usersTable = createUsersTable(dbi);

        Handle h = dbi.open();
        Query<User> users = h
                .createQuery("SELECT id, name FROM users")
                .map(UserMapper.getInstance());

        for (User user : users) {
            assertTrue(user.equals(usersTable.get(user.id)));
        }
        h.close();

        pool.dispose();
    }
}
