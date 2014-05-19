package com.liang.rmi.service.impl;

import java.rmi.RemoteException;

import com.liang.rmi.service.StockQuote;

public class StockQuoteImpl implements StockQuote {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -3352733314755799972L;

    @Override
    public void quote(String stockSymbol, double price) throws RemoteException {
        System.out.println(stockSymbol + ":" + price);
    }

}
