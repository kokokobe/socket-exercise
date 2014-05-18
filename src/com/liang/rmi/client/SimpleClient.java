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
 * @Description:(rmi���ÿͻ���)
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014��3��31��
 * @version
 * @since
 */
public class SimpleClient {
    /**
     * 
    * @Title: showRemoteObjects
    * @Description:(��ȡrmiԶ��ע�����)
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
            /*��ȡԶ�̶���Ĵ������*/
            HelloService service1=(HelloService) namingContext.lookup(url+"/HelloService1");
            HelloService service2=(HelloService) namingContext.lookup(url+"/HelloService2");
            /*���Դ��������������*/
            Class<? extends HelloService> stubClass=service1.getClass();
            System.out.println("service1 ��"+stubClass.getName()+"��ʵ��");
            Class[] interfaces=stubClass.getInterfaces();
            for(Class inf:interfaces){
                System.out.println("�����ʵ����"+inf.getName());
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
