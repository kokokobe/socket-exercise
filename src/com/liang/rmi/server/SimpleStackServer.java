package com.liang.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.liang.rmi.service.Stack;
import com.liang.rmi.service.impl.StackImpl;

/**
 * 
* @ClassName: SimpleStackServer
* @Description:(堆栈消费者服务)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014年4月2日
* @version  
* @since
 */
public class SimpleStackServer {
    public static void main(String[] args) {
        
        try {
            Stack stack=new StackImpl("a stack");
            LocateRegistry.createRegistry(1099);
            UnicastRemoteObject.exportObject(stack,0);
            Context namingContext=new InitialContext();
            namingContext.rebind("rmi:MyStack", stack);
            System.out.println("服务器注册了Stack服务");
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
}
 