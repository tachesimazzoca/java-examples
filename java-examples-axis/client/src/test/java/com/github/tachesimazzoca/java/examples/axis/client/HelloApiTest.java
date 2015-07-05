package com.github.tachesimazzoca.java.examples.axis.client;

import org.junit.Test;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class HelloApiTest {
    @Test
    public void testAdd() throws ServiceException, RemoteException {
        HelloApiService service = new HelloApiServiceLocator();
        HelloApi_PortType port = service.getHelloApi();
        assertEquals(5, port.add(2, 3));
    }

    @Test
    public void testAbs() throws ServiceException, RemoteException {
        HelloApiService service = new HelloApiServiceLocator();
        HelloApi_PortType port = service.getHelloApi();
        assertEquals(1, port.abs(2, 3));
        assertEquals(3, port.abs(8, 5));
    }
}
