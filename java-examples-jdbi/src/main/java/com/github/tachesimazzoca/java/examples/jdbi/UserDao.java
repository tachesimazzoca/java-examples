package com.github.tachesimazzoca.java.examples.jdbi;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

import java.util.Map;

public class UserDao {
    public User findById(Handle h, int id) {
        return h.createQuery("SELECT * FROM users WHERE id = :id")
                .bind("id", id)
                .map(new UserMapper()).first();
    }

    public int count(Handle h) {
        return h.createQuery("SELECT COUNT(*) FROM users")
                .map(IntegerMapper.FIRST).first();
    }

    public Iterable<User> findAll(Handle h) {
        return h.createQuery("SELECT * FROM users ORDER BY id")
                .map(new UserMapper());
    }

    public User insert(Handle h, Map<String, Object> params) {
        h.execute("INSERT INTO users (name) VALUES (?)", (String) params.get("name"));
        int id = h.createQuery("SELECT LAST_INSERT_ID()").map(IntegerMapper.FIRST).first();
        return findById(h, id);
    }
}
