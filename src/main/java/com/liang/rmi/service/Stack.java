package com.liang.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @ClassName: Stack
 * @Description:(堆栈消费者模式)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年4月2日
 * @version
 * @since
 */
public interface Stack extends Remote{
    public String getName() throws RemoteException;
    public int getPoint() throws RemoteException;
    public String pop() throws RemoteException;
    public void push(String goods) throws RemoteException;
}
 