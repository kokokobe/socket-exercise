package com.liang.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StockQuoteRegistry extends Remote {
    /**
     * 
     * @Title: registryClient
     * @Description:(注册一个客户)
     * @author: BriLiang
     * @param client
     * @throws RemoteException
     * @since
     */
    public void registryClient(StockQuote client) throws RemoteException;

    /**
     * 
     * @Title: unregisterClient
     * @Description:(注销一个客户)
     * @author: BriLiang
     * @param client
     * @throws RemoteException
     * @since
     */
    public void unregisterClient(StockQuote client) throws RemoteException;
}
