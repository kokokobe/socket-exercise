package com.liang.rmi.client;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.liang.rmi.service.HelloService;

/**
 *
 * @ClassName: SimpleClient
 * @Description:(rmi调用客户端)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014年3月31日
 * @version
 * @since
 */
public class SimpleClient {
    /**
     *
     * @Title: showRemoteObjects
     * @Description:(获取rmi远程注册对象)
     * @author: BriLiang
     * @param namingContext
     * @throws Exception
     * @since 1.0
     */
    public static void showRemoteObjects(Context namingContext) throws Exception{
        NamingEnumeration<NameClassPair> e=namingContext.list("rmi:");
        while(e.hasMore()){
            System.out.println(e.next().getName());
        }
    }
    public static void main(String[] args) {
        String url="rmi://localhost";
        try {
            Context namingContext=new InitialContext();
            /*获取远程对象的存根对象*/
            HelloService service1=(HelloService) namingContext.lookup(url+"/HelloService1");
            HelloService service2=(HelloService) namingContext.lookup(url+"/HelloService2");
            /*测试存根对象所属的类*/
            Class<? extends HelloService> stubClass=service1.getClass();
            System.out.println("service1 是"+stubClass.getName()+"的实例");
            Class[] interfaces=stubClass.getInterfaces();
            for(Class inf:interfaces){
                System.out.println("存根类实现了"+inf.getName());
            }
            System.out.println(service1.echo("hello"));
            System.out.println(service1.getTime());
            System.out.println(service2.echo("hello"));
            System.out.println(service2.getTime());
            showRemoteObjects(namingContext);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
