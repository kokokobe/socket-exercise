package com.liang.http;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;

public class FileContent implements Content {
    //��·��
    private static File ROOT=new File("E://root");
    private File file;
    public FileContent(URI uri) {
        file=new File(ROOT,uri.getPath().replace('/',File.separatorChar));
    }
    private String type=null;
    private FileChannel fileChannel=null;
    //�ļ�����
    private long length=-1;
    //�ļ��ĵ�ǰλ��
    private long position=-1;
    /*ȷ���ļ�����*/
    @Override
    public String type() {
        if(type!=null){
            return type;
        }
        String nm=file.getName();
        if(nm.endsWith(".html")||nm.endsWith(".htm")){
            type="text/html;charset=utf-8";  //HTML��ҳ
        }
        //���ı��ļ�
        else if(nm.indexOf('.')<0||nm.endsWith(".txt")){
            type="text/plain;charset=utf-8";
        }
        //�������ļ�
        else{
            type="application/octet-stream";
        }
        return type;
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public void prepare() throws IOException{
        if(fileChannel==null){
            //ֻ��
            fileChannel=new RandomAccessFile(file,"r").getChannel();
        }
        length=fileChannel.size();
        position=0;
    }
    @Override
    public boolean send(ChannelIO cio) throws IOException {
        if(fileChannel==null){
            throw new IllegalStateException();
        }
        if(position<0){
            throw new IllegalStateException();
        }
        if(position>=length){
            return false;
        }
        position+=cio.tranferTo(fileChannel, position,length-position);
        return (position<length);
    }

    @Override
    public void release() throws IOException {
        if(fileChannel!=null){
            fileChannel.close();
            fileChannel=null;
        }
    }

}
