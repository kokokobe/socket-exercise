package com.liang.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.liang.rmi.service.HelloService;
import com.liang.rmi.service.impl.HelloServiceImpl;

/**
 * 
 * @ClassName: SimpleServer
 * @Description:(��������������)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014��4��1��
 * @version
 * @since
 */
public class SimpleServer {
    public static void main(String[] args) {
        try {
            HelloService service1 = new HelloServiceImpl("service1");
            HelloService service2 = new HelloServiceImpl("service2");
            LocateRegistry.createRegistry(1099);
            /* java �������� */
            Context nameingContext = new InitialContext();
            nameingContext.rebind("rmi:HelloService1", service1);
            nameingContext.rebind("rmi:HelloService2", service2);
            System.out.println("������ע��������HelloService");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
