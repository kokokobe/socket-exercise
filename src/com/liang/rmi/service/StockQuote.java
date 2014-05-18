package com.liang.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StockQuote extends Remote {
    /**
     * 
     * @Title: quote
     * @Description:(打印参数固定的股票价格)
     * @author: BriLiang
     * @param stockSymbol
     * @param price
     * @throws RemoteException
     * @since
     */
    public void quote(String stockSymbol, double price) throws RemoteException;
}
