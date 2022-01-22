package com.github.tachesimazzoca.java.examples.jdbi;

import org.h2.jdbcx.JdbcConnectionPool;

import org.skife.jdbi.v2.tweak.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolFactory implements ConnectionFactory {
    private final JdbcConnectionPool pool;

    public ConnectionPoolFactory() {
        pool = JdbcConnectionPool.create("jdbc:h2:mem:test;MODE=MYSQL", "", "");
        pool.setMaxConnections(5);
        pool.setLoginTimeout(1);
    }

    public Connection openConnection() throws SQLException {
        return pool.getConnection();
    }

    public void dispose() {
        pool.dispose();
    }
}
