package com.zhoujf.test.mina.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class SendXmlTextHandler extends IoHandlerAdapter {
    public static String xml = null;
    
    static{
        URL url = SendXmlTextHandler.class.getClassLoader().getResource("crossdomain.xml");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
            xml = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        session.write(xml);
        print("messageReceived:" + session.getRemoteAddress());
    }
    
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        // TODO Auto-generated method stub
        super.messageSent(session, message);
        print("messageSent");
        session.close(false);
    }
    
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionCreated(session);
        print("sessionCreated");
    }
    
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionClosed(session);
        print("sessionClosed");
    }
    
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        print("sessionOpened");
    }
    
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        // TODO Auto-generated method stub
        super.sessionIdle(session, status);
        
        print("sessionIdle:" + session.getIdleCount(status));
    }
    
    public static void print(Object msg) {
        System.out.println(msg);
    }
}
