package com.github.tachesimazzoca.java.examples.jdbi;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.HandleCallback;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class UsersFixture {
    public static void createTable(DBI dbi) {
        dbi.withHandle(new HandleCallback<Void>() {
            public Void withHandle(Handle h) {
                h.execute("DROP TABLE IF EXISTS users");
                h.execute("CREATE TABLE users (" +
                        "id int(11) NOT NULL auto_increment," +
                        "name varchar(255) NOT NULL default ''," + 
                        "PRIMARY KEY(id))");
                return null;
            }
        });
    }

    public static Map<Integer, User> createTable(DBI dbi, int num) {
        createTable(dbi);
        final int max = num;
        return dbi.withHandle(new HandleCallback<Map<Integer, User>>() {
            public Map<Integer, User> withHandle(Handle h) {
                ImmutableMap.Builder<Integer, User> builder =
                        ImmutableMap.<Integer, User> builder();
                for (int n = 1; n <= max; n++) {
                    User user = new User(n, "user" + n);
                    builder.put(n, user);
                    h.execute("INSERT INTO users (id, name) VALUES (?, ?)",
                            user.id, user.name);
                }
                return builder.build();
            }
        });
    }
}
