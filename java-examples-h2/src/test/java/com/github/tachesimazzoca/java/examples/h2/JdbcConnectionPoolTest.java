package com.github.tachesimazzoca.java.examples.h2;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.Connection;
import org.h2.jdbcx.JdbcConnectionPool;

public class JdbcConnectionPoolTest {
    @Test
    public void testActiveConnections() throws Exception {
        JdbcConnectionPool pool = JdbcConnectionPool.create(
                "jdbc:h2:mem:test?MODE=MYSQL", "", "");
        final int MAX = 3;
        pool.setMaxConnections(MAX);

        assertEquals(0, pool.getActiveConnections());

        Connection[] conn = new Connection[MAX];
        for (int i = 0; i < MAX; i++) {
            conn[i] = pool.getConnection();
            assertEquals(i + 1, pool.getActiveConnections());
        }
        for (int i = 0; i < MAX; i++) {
            conn[i].close();
            assertEquals(MAX - (i + 1), pool.getActiveConnections());
        }

        pool.dispose();
    }
}
