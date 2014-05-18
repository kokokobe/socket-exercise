package com.liang.rmi.client;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.liang.rmi.service.StockQuote;
import com.liang.rmi.service.StockQuoteRegistry;
import com.liang.rmi.service.impl.StockQuoteImpl;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @ClassName: SimpleStockClient
 * @Description:(�ͻ��˹�Ʊ����)
 * @date 2014��4��2��
 */
public class SimpleStockClient {
    public static void main(String[] args) {
        System.setProperty("java.security.policy", SimpleStockClient.class.getResource("client.policy").toString());
        System.setSecurityManager(new RMISecurityManager());
        String url = "rmi://localhost:1099/";
        Context namingContext;
        try {
            namingContext = new InitialContext();
            StockQuoteRegistry registry = (StockQuoteRegistry) namingContext.lookup(url + "StockQuoteRegistry");
            StockQuote client = new StockQuoteImpl();
            //LocateRegistry.createRegistry(1100);
            UnicastRemoteObject.exportObject(client, 0);
            registry.registryClient(client);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
 