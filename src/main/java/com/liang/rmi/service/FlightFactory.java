package com.liang.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @ClassName: FlightFactory
 * @Description:(工厂类负责生产具体的航班信息)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年4月1日
 * @version
 * @since
 */
public interface FlightFactory extends Remote {
    public Flight getFlight(String flightNumber) throws RemoteException;
}
 