package com.github.tachesimazzoca.java.examples.jdbi;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface UserQueries {
    @SqlQuery("SELECT * FROM users WHERE id = :id")
    @Mapper(UserMapper.class)
    User findById(@Bind("id") int id);

    @SqlQuery("SELECT COUNT(*) FROM users")
    int count();

    @SqlQuery("SELECT * FROM users")
    @Mapper(UserMapper.class)
    Iterable<User> findAll();

    void close();
}
