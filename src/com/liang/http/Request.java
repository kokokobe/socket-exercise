package com.liang.http;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    //ö���࣬��ʾHTTP��������
    public static class Action {
        private String name;
        private Action(String name){
            this.name=name;
        }
        public String toString(){
            return name;
        }
        public final static Action GET =new Action("GET");
        public final static Action PUT =new Action("PUT");
        public final static Action POST =new Action("POST");
        public final static Action HEAD =new Action("HEAD");
        public static Action parse(String s){
            if(s.equals("GET")){
                return GET;
            }
            if(s.equals("PUT")){
                return PUT;
            }
            if(s.equals("POST")){
                return POST;
            }
            if(s.equals("HEAD")){
                return HEAD;
            }
            throw new IllegalArgumentException(s+"����ʽ����");
        }
        
    }
    private Action action;
    private String version;
    private URI uri;
    public Action getAction() {
        return action;
    }

    public String getVersion() {
        return version;
    }

    public URI getUri() {
        return uri;
    }
    private Request(Action action,String version,URI uri){
        this.action=action;
        this.version=version;
        this.uri=uri;
    }
    public String toString(){
        return action+" "+version+" "+uri;
    }
    private static Charset requestCharset=Charset.forName("UTF-8");
    /**
     * 
    * @Title: isComplete
    * @Description:(�ж�ByteBuffer�Ƿ������HTTP�������������
    * HTTP������\r\n\r\n ��β)
    * @param ByteBuffer readBuf
    * @return ��������  boolean    
     */
    public static boolean isComplete(ByteBuffer readBuf) {
        ByteBuffer temp=readBuf.asReadOnlyBuffer();
        temp.flip();
        String data=requestCharset.decode(temp).toString();
        if(data.indexOf("\r\n\r\n")!=-1){
            return true;
        }
        return false;
    }
    /**
     * 
    * @Title: deleteConent
    * @Description: (ɾ���������ģ���������֧��GET ��HEAD����ʽ��������HTTP�����е����Ĳ���)
    * @param content
    * @return ��������  ByteBuffer    
    * @throws
     */
    private static ByteBuffer deleteConent(ByteBuffer content){
        ByteBuffer temp=content.asReadOnlyBuffer();
        String data=requestCharset.decode(temp).toString();
        if(data.indexOf("\r\n\r\n")!=-1){
            data=data.substring(0,data.indexOf("\r\n\r\n")+4);
            return requestCharset.encode(data);
        }
        return content;
    }
    //����
    private static Pattern requestPattern=Pattern.compile("\\A([A-Z]+)+([^]+)+HTTP/([0-9\\.]+)$"+".*^HOST:([^]+)$.*\r\n\r\n\\z",Pattern.MULTILINE|Pattern.DOTALL);
    /**
     * @throws MalformedRequestException 
     * 
    * @Title: parse
    * @Description:(����HTTP���󣬴�����Ӧ��Request����)
    * @param httpContent
    * @return ��������  Request    
    * @throws
     */
    public static Request parse(ByteBuffer httpContent) throws MalformedRequestException {
        //ɾ����������
        httpContent=deleteConent(httpContent);
        CharBuffer decodeData=requestCharset.decode(httpContent);
        //�����ַ���ƥ��
        Matcher m=requestPattern.matcher(decodeData);
        if(!m.matches()){
            throw new MalformedRequestException();
        }
        Action a;
        try {
            a=Action.parse(m.group(1));
        } catch (IllegalArgumentException e) {
            throw new MalformedRequestException();
        }
        URI u;
        try {
            u=new URI("http://"+m.group(4)+m.group(2));
        } catch (URISyntaxException e) {
            throw new MalformedRequestException();
        }
        return new Request(a,m.group(3),u);
    }
    
}
