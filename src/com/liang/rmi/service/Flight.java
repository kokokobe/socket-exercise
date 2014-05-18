package com.liang.rmi.service;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * 
 * @ClassName: Flight
 * @Description:(航班远程接口)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年4月1日
 * @version 1.0
 * @since 1.0
 */
public class Flight implements Serializable{
    protected String flightNumber;
    protected String origin;
    protected String destination;
    protected String skdDepature;
    protected String skdArrival;

    public Flight(String flightNumber, String origin, String destination, String skdDepature, String skdArrival)
            throws RemoteException {
        this.flightNumber=flightNumber;
        this.origin=origin;
        this.destination=destination;
        this.skdDepature=skdDepature;
        this.skdArrival=skdArrival;
    }

    private static final long serialVersionUID = -6504617105814664869L;

    public String getFlightNumber() throws RemoteException {
        System.out.println("调用getFlightNumber(),返回"+flightNumber);
        return flightNumber;
    }

    public String getOrigin() throws RemoteException {
        return origin;
    }

    public String getDestination() throws RemoteException {
        return destination;
    }

    public String getSkdDeparture() throws RemoteException {
        return skdDepature;
    }

    public String getSkdArrival() throws RemoteException {
        return skdArrival;
    }

    public void setOrigin(String origin) throws RemoteException {
        this.origin=origin;
    }

    public void setDestination(String destination) throws RemoteException {
        this.destination=destination;
    }

    public void setSkdDeparture(String skdDeparture) throws RemoteException {
        this.skdDepature=skdDeparture;
    }

    public void setSkdArrival(String skdArrival) throws RemoteException {
        this.skdArrival=skdArrival;
    }

}
