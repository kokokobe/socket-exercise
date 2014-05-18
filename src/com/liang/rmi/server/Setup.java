package com.liang.rmi.server;

import java.io.IOException;
import java.rmi.MarshalledObject;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.liang.rmi.client.SimpleClient;
import com.liang.rmi.service.HelloService;

/**
 * 
 * @ClassName: Setup
 * @Description:(��̬����)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014��4��3��
 * @version
 * @since
 */
public class Setup {
    public static void main(String[] args) {
        System.setSecurityManager(new RMISecurityManager());
        Properties prop=new Properties();
        prop.put("java.security.policy", SimpleClient.class.getResource("server.policy").toString());
        ActivationGroupDesc group=new ActivationGroupDesc(prop, null);
        /*ע��ActivationGroup */
        try {
            LocateRegistry.getRegistry(1099);
            System.setProperty("java.rmi.activation.port","1100");
            ActivationGroupID id=ActivationGroup.getSystem().registerGroup(group);
            String classURL=System.getProperty("java.rmi.server.codebase");
            MarshalledObject<?> param1=new MarshalledObject<Object>("service1");
            MarshalledObject<?> param2=new MarshalledObject<Object>("service2");
            ActivationDesc desc1=new ActivationDesc(id,"com.liang.rmi.service.impl.HelloServiceImpl",classURL,param1);
            ActivationDesc desc2=new ActivationDesc(id,"com.liang.rmi.service.impl.HelloServiceImpl",classURL,param2);
            /*��rmidע�������������*/
            HelloService s1=(HelloService) Activatable.register(desc1);
            HelloService s2=(HelloService) Activatable.register(desc2);
            System.out.println(s1.getClass().getSimpleName());
            Context namingContext=new InitialContext();
            namingContext.rebind("rmi:HelloService1",s1);
            namingContext.rebind("rmi:HelloService2",s2);
            System.out.println("������ע���������ɼ����HelloService����");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ActivationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        
        
    }
}
