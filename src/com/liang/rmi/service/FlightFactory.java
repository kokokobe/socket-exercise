package com.liang.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
* @ClassName: FlightFactory
* @Description:(�����ฺ����������ĺ�����Ϣ)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014��4��1��
* @version  
* @since
 */
public interface FlightFactory extends Remote {
    public Flight getFlight(String flightNumber) throws RemoteException;
}
 