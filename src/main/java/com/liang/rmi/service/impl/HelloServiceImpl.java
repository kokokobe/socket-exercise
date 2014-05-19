package com.liang.rmi.service.impl;

import java.io.IOException;
import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationID;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;

import com.liang.rmi.service.HelloService;

/**
 *
 * @ClassName: HelloServiceImpl
 * @Description:(远程类实现)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年3月31日
 * @version
 * @since
 */
public class HelloServiceImpl implements HelloService {
    public HelloServiceImpl(ActivationID id, MarshalledObject<?> data) throws RemoteException {
        try {
            this.name=(String) data.get();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        UnicastRemoteObject.exportObject(this, 0);
        Activatable.exportObject(this, id, 0);
        System.out.println("创建"+name);
    }

    public HelloServiceImpl(String string) {

    }

    @SuppressWarnings("unused")
    private static final long serialVersionUID = -126326361175640875L;

    private String name;

    @Override
    public String echo(String msg) throws RemoteException {
        System.out.println(name+"調用echo()方法");
        return "echo:"+msg+" from "+name;
    }

    @Override
    public Date getTime() throws RemoteException {
        System.out.println(name+"調用getTime()方法");
        return Calendar.getInstance().getTime();
    }

}
