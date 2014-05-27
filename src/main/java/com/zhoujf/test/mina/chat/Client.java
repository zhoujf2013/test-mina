package com.zhoujf.test.mina.chat;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Client {
    
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8001;
    
    NioSocketConnector connector;
    
    public Client() {
        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10*1000);
        
        // 添加 filter
        if(System.getProperty("chat.logging") != null) {
            connector.getFilterChain().addLast("logging", new LoggingFilter());
        }
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        
        connector.setHandler(new ClientIoHandler());
        
        ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
        future.awaitUninterruptibly();
        future.getSession().write("aaaa");
        
    }

    public static void main(String[] args) {
        new Client();
    }
}
