package com.liang.rmi.service.impl;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.liang.rmi.service.StockQuote;
import com.liang.rmi.service.StockQuoteRegistry;

public class StockQuoteRegistryImpl implements StockQuoteRegistry, Runnable {
    /* 存放StockQuote远程对象的存根对象的缓存 */
    protected HashSet<StockQuote> clients = new HashSet<>();

    @Override
    public void run() {
        /* 创建一些股票代码 */
        String[] symbols = new String[] { "SUNW", "MSFT", "DAL", "WUTK", "SAMY", "VIP", "APPLE" };
        Random rand = new Random();
        double values[] = new double[symbols.length];
        /* 为每个股票分配任意的价格 */
        for (int i = 0; i < values.length; i++) {
            values[i] = 25.0 + rand.nextInt(100);
        }
        for (;;) {
            /* 随机取出一个股票 */
            int symbo = rand.nextInt(symbols.length);
            /* 修改股票的价格 */
            int change = 100 - rand.nextInt(201);
            values[symbo] += change / 100.0;
            if (values[symbo] < 0) {
                values[symbo] = 0.01;
            }
            Iterator<StockQuote> iter = clients.iterator();
            while (iter.hasNext()) {
                StockQuote client = iter.next();
                try {
                    client.quote(symbols[symbo], values[symbo]);
                } catch (RemoteException e) {
                    System.out.println("删除一个无效的客户");
                    iter.remove();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registryClient(StockQuote client) throws RemoteException {
        System.out.println("加入一个客户");
        clients.add(client);
    }

    @Override
    public void unregisterClient(StockQuote client) throws RemoteException {
        System.out.println("注销一个客户");
        clients.remove(client);
    }

}
