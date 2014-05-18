package com.liang.rmi.service.impl;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.liang.rmi.service.StockQuote;
import com.liang.rmi.service.StockQuoteRegistry;

public class StockQuoteRegistryImpl implements StockQuoteRegistry, Runnable {
    /* ���StockQuoteԶ�̶���Ĵ������Ļ��� */
    protected HashSet<StockQuote> clients = new HashSet<>();

    @Override
    public void run() {
        /* ����һЩ��Ʊ���� */
        String[] symbols = new String[] { "SUNW", "MSFT", "DAL", "WUTK", "SAMY", "VIP", "APPLE" };
        Random rand = new Random();
        double values[] = new double[symbols.length];
        /* Ϊÿ����Ʊ��������ļ۸� */
        for (int i = 0; i < values.length; i++) {
            values[i] = 25.0 + rand.nextInt(100);
        }
        for (;;) {
            /* ���ȡ��һ����Ʊ */
            int symbo = rand.nextInt(symbols.length);
            /* �޸Ĺ�Ʊ�ļ۸� */
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
                    System.out.println("ɾ��һ����Ч�Ŀͻ�");
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
        System.out.println("����һ���ͻ�");
        clients.add(client);
    }

    @Override
    public void unregisterClient(StockQuote client) throws RemoteException {
        System.out.println("ע��һ���ͻ�");
        clients.remove(client);
    }

}
