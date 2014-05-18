package com.liang.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.liang.rmi.service.impl.StockQuoteRegistryImpl;

/**
 * 
* @ClassName: SimpleStockServer
* @Description:(股票报价服务)
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014年4月2日
* @version  
* @since
 */
public class SimpleStockServer {
    public static void main(String[] args) {
        StockQuoteRegistryImpl registry=new StockQuoteRegistryImpl();
        Context namingContext;
        try {
            LocateRegistry.createRegistry(1099);
            /*手动发布到指定端口*/
            UnicastRemoteObject.exportObject(registry,0);
            namingContext = new InitialContext();
            namingContext.rebind("rmi:StockQuoteRegistry", registry);
            System.out.println("服务器注册了一个StockQuoteRegistry对象");
            new Thread(registry).start();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
}
 