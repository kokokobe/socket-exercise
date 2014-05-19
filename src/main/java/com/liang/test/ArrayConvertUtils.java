package com.liang.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ArrayConvertUtils {
    public static byte[] longToByte(long[] data) throws IOException{
        ByteArrayOutputStream bao=new ByteArrayOutputStream();
        //bao.write(data); 不存在写入long 数据流，所以换成封装DataOutputStream
        DataOutputStream dataOutputStream=new DataOutputStream(bao);
        for(int i=0;i<data.length;i++){
            dataOutputStream.writeLong(data[i]);
        }
        dataOutputStream.close();
        return bao.toByteArray();
    }
    public static long[] byteToLong(byte[] data) throws IOException{
        //一个long占8个字节
        long[] result=new long[data.length/8];
        ByteArrayInputStream bai=new ByteArrayInputStream(data);
        DataInputStream dataInputStream=new DataInputStream(bai);
        for(int i=0;i<result.length;i++){
            result[i]=dataInputStream.readLong();
        }
/*        dataInputStream.close();
        bai.close();*/
        return result;
    }
    public static void main(String[] args) throws IOException {
        ArrayConvertUtils arrayUtils =new ArrayConvertUtils();
        long[] longData={15648918949L,15645315641564916L,2944315646L,158949134164L};
        byte[] convertByteData=arrayUtils.longToByte(longData);
        long[] convertedLongData=arrayUtils.byteToLong(convertByteData);
        for(long temLongData:convertedLongData){
            System.out.println(temLongData);
        }

    }
}
