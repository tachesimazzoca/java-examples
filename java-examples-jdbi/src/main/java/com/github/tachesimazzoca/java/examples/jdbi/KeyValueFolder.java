package com.github.tachesimazzoca.java.examples.jdbi;

import org.skife.jdbi.v2.Folder2;
import org.skife.jdbi.v2.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Map;

public class KeyValueFolder implements Folder2<Map<String, String>> {
    private final String keyName;
    private final String valueName;

    public KeyValueFolder(String keyName, String valueName) {
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
