package com.github.tachesimazzoca.java.examples.jpa;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang.reflect.MethodUtils;

public class VersioningListener {
    @PrePersist
    public static void createdAt(Object o) throws
            IllegalAccessException,
            InvocationTargetException {
        long t = System.currentTimeMillis();
        setCurrentTime(o, "setCreatedAt", t);
        setCurrentTime(o, "setUpdatedAt", t);
    }

    @PreUpdate
    public static void updatedAt(Object o) throws
            IllegalAccessException,
            InvocationTargetException {
        long t = System.currentTimeMillis();
        setCurrentTime(o, "setUpdatedAt", t);
    }
    
    private static void setCurrentTime(Object o, String methodName, long t) throws
            IllegalAccessException,
            InvocationTargetException {
        Method m = MethodUtils.getAccessibleMethod(o.getClass(),
                methodName, java.util.Date.class);
        if (m != null)
            m.invoke(o, new java.util.Date(t));
    }
}
