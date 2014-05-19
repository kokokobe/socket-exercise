package com.liang.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StockQuoteRegistry extends Remote {
    /**
     * 
     * @Title: registryClient
     * @author: BriLiang
     * @param client
     * @throws RemoteException
     * @since
     */
    public void registryClient(StockQuote client) throws RemoteException;

    /**
     * 
     * @Title: unregisterClient
     * @author: BriLiang
     * @param client
     * @throws RemoteException
     * @since
     */
    public void unregisterClient(StockQuote client) throws RemoteException;
}
