package com.liang.rmi.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

import com.liang.rmi.service.Flight;
import com.liang.rmi.service.FlightFactory;


public class FlightFactoryImpl extends UnicastRemoteObject implements FlightFactory {
    /*线程安全的*/
    protected Hashtable<String,Flight> flights;
    private static final long serialVersionUID = 4634043808956446217L;

    public FlightFactoryImpl() throws RemoteException {
        flights=new Hashtable<>();
    }

    @Override
    public Flight getFlight(String flightNumber) throws RemoteException {
        Flight flight=flights.get(flightNumber);
        if(flight!=null){
            return flight;
        }
        flight=new Flight(flightNumber, null, null, null, null);
        flights.put(flightNumber, flight);
        return flight;
    }

}
 