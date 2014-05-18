package com.liang.http;

public class StringContent implements Content {

    public StringContent(String string) {
        // TODO Auto-generated constructor stub
    }

    public StringContent(Exception e) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String type() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long length() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void prepare() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean send(ChannelIO cio) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void release() {
        // TODO Auto-generated method stub

    }

}
