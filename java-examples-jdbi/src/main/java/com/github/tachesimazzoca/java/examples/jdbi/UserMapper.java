package com.github.tachesimazzoca.java.examples.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {
    public User map(int row, ResultSet rs, StatementContext ctx)
            throws SQLException {
        return new User(rs.getInt("id"), rs.getString("name"));
    }
}
