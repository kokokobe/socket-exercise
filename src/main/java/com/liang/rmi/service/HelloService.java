package com.liang.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * 
 * @ClassName: HelloService
 * @Description:(RMI server)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014��3��31��
 * @version 1.0
 * @since
 */
public interface HelloService extends Remote {
    /**
     * 
     * @Title: echo
     * @Description:(��Ӧ�ͻ�������)
     * @author: BriLiang
     * @param msg
     * @return String
     * @throws RemoteException
     * @since 1.0
     */
    public String echo(String msg) throws RemoteException;

    /**
     * 
     * @Title: getTime
     * @Description:(��ȡԶ�̷�����ʱ��)
     * @author: BriLiang
     * @return Date
     * @throws RemoteException
     * @since 1.0
     */
    public Date getTime() throws RemoteException;
}
