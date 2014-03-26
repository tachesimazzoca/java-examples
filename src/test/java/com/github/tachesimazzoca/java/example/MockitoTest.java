package com.github.tachesimazzoca.java.example;

import static org.junit.Assert.*;

import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.InOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MockitoTest {
    @Test
    public void testVerify() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        rs.next();
        rs.getDouble("id");
        rs.getString("name");
        rs.getDate("created_at");
        rs.next();

        // Verifies certain behavior happened once
        Mockito.verify(rs, Mockito.times(2)).next();
        Mockito.verify(rs).getDate("created_at");
        Mockito.verify(rs).getDouble("id");
        Mockito.verify(rs).getString("name");
        // Verifies that interaction did not happen
        Mockito.verify(rs, Mockito.never()).getDate("updated_at");
    }

    @Test
    public void testVerifyInOrder() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        @SuppressWarnings("unchecked")
        List<String> lines = Mockito.mock(List.class);

        rs.next();
        rs.getInt("id");
        rs.getString("name");
        lines.add("1: Foo");
        rs.close();

        // Allows verifying mocks in order
        InOrder inOrder = Mockito.inOrder(rs, lines);
        // Verifies interaction happened once in order
        inOrder.verify(rs).next();
        inOrder.verify(rs).getInt("id");
        inOrder.verify(rs).getString("name");
        inOrder.verify(lines).add("1: Foo");
        inOrder.verify(rs).close();
    }

    @Test
    public void testThenReturn() throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);

        Mockito.when(rs.next()).thenReturn(true, true, false);
        Mockito.when(rs.getInt("id")).thenReturn(1, 2);
        // might chain thenReturn ...
        Mockito.when(rs.getString("name")).thenReturn("1st").thenReturn("2nd");

        assertTrue(rs.next());
        assertEquals(1, rs.getInt("id"));
        assertEquals("1st", rs.getString("name"));
        assertTrue(rs.next());
        assertEquals(2, rs.getInt("id"));
        assertEquals("2nd", rs.getString("name"));
        assertFalse(rs.next());
    }
}
