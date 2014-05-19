package com.liang.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.liang.rmi.service.FlightFactory;
import com.liang.rmi.service.impl.FlightFactoryImpl;


public class SimpleFlightServer {
    public static void main(String[] args) {
        try {
            FlightFactory factory=new FlightFactoryImpl();
            Context namingContext=new InitialContext();
            LocateRegistry.createRegistry(1099);
            namingContext.rebind("rmi:FlightFactory",factory);
            System.out.println("generate FlightFactory");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        
    }
}
 