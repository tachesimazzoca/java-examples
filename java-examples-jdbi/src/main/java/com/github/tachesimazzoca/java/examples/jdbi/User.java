package com.github.tachesimazzoca.java.examples.jdbi;

public class User {
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
