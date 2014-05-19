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
* @author BriLiang(liangwen.liang@vipshop.com)
* @date 2014��4��2��
* @version  
* @since
 */
public class SimpleStockServer {
    public static void main(String[] args) {
        StockQuoteRegistryImpl registry=new StockQuoteRegistryImpl();
        Context namingContext;
        try {
            LocateRegistry.createRegistry(1099);
            /*手动发布*/
            UnicastRemoteObject.exportObject(registry,0);
            namingContext = new InitialContext();
            namingContext.rebind("rmi:StockQuoteRegistry", registry);
            System.out.println("registry StockQuoteRegistry");
            new Thread(registry).start();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
}
 