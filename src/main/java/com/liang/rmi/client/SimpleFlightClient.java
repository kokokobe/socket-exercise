package com.liang.rmi.client;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.liang.rmi.service.Flight;
import com.liang.rmi.service.FlightFactory;

/**
 *
 * @ClassName: SimpleFlightClient
 * @Description:(航班机场客户端)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年4月2日
 * @version
 * @since
 */
public class SimpleFlightClient {
    public static void main(String[] args) {
        try {
            String url="rmi://localhost/";
            Context namingContext=new InitialContext();
            FlightFactory factory=(FlightFactory) namingContext.lookup(url+"FlightFactory");
            Flight flight1=factory.getFlight("MH370");
            flight1.setOrigin("Guangzhou");
            flight1.setDestination("ShangHai");
            System.out.println("Flight "+flight1.getFlightNumber()+":");
            System.out.println("From "+flight1.getOrigin()+" to "+flight1.getDestination());
            Flight flight2=factory.getFlight("MH370");
            System.out.println("Flight "+flight2.getFlightNumber()+":");
            System.out.println("From "+flight2.getOrigin()+" to "+flight2.getDestination());
            System.out.println("flight1 是"+flight1.getClass().getSimpleName()+"的实例");
            System.out.println("flight2 是"+flight2.getClass().getSimpleName()+"的实例");
            System.out.println("flight1==flight2:"+(flight1==flight2));
            System.out.println("flight1.equals(flight2):"+(flight1.equals(flight2)));
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
 
