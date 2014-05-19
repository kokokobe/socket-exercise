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
 * @Description:(描述这个类的作用)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年4月1日
 * @version
 * @since
 */
public class SimpleServer {
    public static void main(String[] args) {
        try {
            HelloService service1 = new HelloServiceImpl("service1");
            HelloService service2 = new HelloServiceImpl("service2");
            LocateRegistry.createRegistry(1099);
            /* java 命名服务 */
            Context nameingContext = new InitialContext();
            nameingContext.rebind("rmi:HelloService1", service1);
            nameingContext.rebind("rmi:HelloService2", service2);
            System.out.println("服务器注册了两个HelloService");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
