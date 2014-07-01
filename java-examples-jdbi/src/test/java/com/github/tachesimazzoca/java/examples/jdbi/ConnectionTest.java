package com.github.tachesimazzoca.java.examples.jdbi;

import static org.junit.Assert.*;

import org.junit.Test;

import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.sql.Connection;

public class ConnectionTest {

    private static JdbcConnectionPool createJdbcConnectionPool() {
        JdbcConnectionPool pool = JdbcConnectionPool.create(
                "jdbc:h2:mem:test", "", "");
        pool.setMaxConnections(5);
        pool.setLoginTimeout(1);
        return pool;
    }

    @Test
    public void testDataSourceConnections() throws Exception {
        JdbcConnectionPool pool = createJdbcConnectionPool();

        DBI dbi = new DBI(pool);
        Handle h1 = dbi.open();
        Connection conn1 = h1.getConnection();
        assertEquals(1, pool.getActiveConnections());

        Handle h2 = dbi.open();
        Connection conn2 = h2.getConnection();

        assertTrue(conn1 != conn2);
        assertEquals(2, pool.getActiveConnections());

        h1.close();
        assertEquals(1, pool.getActiveConnections());
        assertTrue(conn1.isClosed());
        assertFalse(conn2.isClosed());
        h2.close();
        assertEquals(0, pool.getActiveConnections());
        assertTrue(conn1.isClosed());
        assertTrue(conn2.isClosed());

        pool.dispose();
    }

    @Test
    public void testDirectConnections() throws Exception {
        DBI dbi = new DBI("jdbc:h2:mem:test");

        Handle h1 = dbi.open();
        Connection conn1 = h1.getConnection();
        Handle h2 = dbi.open();
        Connection conn2 = h2.getConnection();

        assertTrue(conn1 != conn2);

        h1.close();
        assertTrue(conn1.isClosed());
        assertFalse(conn2.isClosed());
        h2.close();
        assertTrue(conn1.isClosed());
        assertTrue(conn2.isClosed());
    }
}
