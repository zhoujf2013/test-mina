package com.zhoujf.test.mina.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    
    public static Logger logger = LoggerFactory.getLogger(Server.class);
    
    public static int PORT = 8888;
    
    public static String PORT_KEY = "chat.port";
    
    public static void main(String[] args) throws IOException {
        
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        SocketSessionConfig ssc = acceptor.getSessionConfig();
        ssc.setIdleTime(IdleStatus.BOTH_IDLE, 3);
        
        // 添加 filter
        if(System.getProperty("chat.logging") != null) {
            acceptor.getFilterChain().addLast("logging", new LoggingFilter());
        }
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new ServerIoHandler(new ServerCallback() {
            
            @Override
            public void sendMessage(IoSession session, String message) {
                // TODO Auto-generated method stub
            }
            
            @Override
            public void log(String message) {
                // TODO Auto-generated method stub
            }
            
            @Override
            public void callback(String message) {
                // TODO Auto-generated method stub
            }
            
            @Override
            public void broadcast(String message) {
                // TODO Auto-generated method stub
            }
        }));
        
        // 设置监听端口
        int port = PORT;
        try{
            if(System.getProperty(PORT_KEY) != null) {
                port = Integer.parseInt(System.getProperty(PORT_KEY));
            }
            acceptor.bind(new InetSocketAddress(port));
        } catch(IOException e) {
            logger.error("端口一杯占用  port:{}", port);
            e.printStackTrace();
            System.exit(1);
        } catch(Exception e) {
            logger.error("端口错误:{}", System.getProperty(PORT_KEY));
            e.printStackTrace();
            System.exit(1);
        }
        
        logger.info("服务器启动成功 port:{}", port);
    }
    
}
