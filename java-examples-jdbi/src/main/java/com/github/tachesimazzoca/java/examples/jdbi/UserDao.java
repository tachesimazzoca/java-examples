package com.github.tachesimazzoca.java.examples.jdbi;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

public class UserDao {
    private final Handle h;

    public UserDao(Handle h) {
        this.h = h;
    }

    public void close() {
        h.close();
    }

    public User findById(int id) {
        return h.createQuery("SELECT * FROM users WHERE id = :id")
                .bind("id", id)
                .map(new UserMapper()).first();
    }

    public int count() {
        return h.createQuery("SELECT COUNT(*) FROM users")
                .map(IntegerMapper.FIRST).first();
    }

    public Iterable<User> findAll() {
        return h.createQuery("SELECT * FROM users ORDER BY id")
                .map(new UserMapper());
    }
}
