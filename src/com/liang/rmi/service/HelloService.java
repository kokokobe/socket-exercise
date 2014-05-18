package com.liang.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * 
 * @ClassName: HelloService
 * @Description:(RMI 远程接口)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年3月31日
 * @version 1.0
 * @since
 */
public interface HelloService extends Remote {
    /**
     * 
     * @Title: echo
     * @Description:(响应客户端请求)
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
     * @Description:(获取远程服务器时间)
     * @author: BriLiang
     * @return Date
     * @throws RemoteException
     * @since 1.0
     */
    public Date getTime() throws RemoteException;
}
