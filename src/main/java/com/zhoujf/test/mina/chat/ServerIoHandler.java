package com.zhoujf.test.mina.chat;

import java.net.SocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerIoHandler extends IoHandlerAdapter {
    
    public Logger logger = LoggerFactory.getLogger(getClass());
    
    Map<SocketAddress, Object> sessions = Collections.synchronizedMap(new HashMap<SocketAddress, Object>());
    
    private ServerCallback serverCallback;
    
    public ServerIoHandler(ServerCallback serverCallback) {
        this.serverCallback = serverCallback;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        logger.info("sessionCreated");
        
        sessions.put(session.getRemoteAddress(), session);
        
        
        serverCallback.log(session.toString());
        serverCallback.log(session.getRemoteAddress().toString());
    }
    
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        logger.info("sessionOpened");
    }
    
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        logger.info("messageReceived:[{}]", message);
    }
    
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        logger.info("messageSent");
    }
    
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("sessionClosed");
        session.close(true);
        logout(session);
    }
    
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        if(session.getIdleCount(status) == 3) {
            session.close(false);
            logger.info("sessionIdle: {}", session.getIdleCount(status));
            logout(session);
        }
    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        logger.error("exceptionCaught");
        if(session != null && session.isClosing()) {
            session.close(false);
            logout(session);
        }
    }
    
    public void logout(IoSession session){
        sessions.remove(session.getRemoteAddress());
        serverCallback.log("退出: " + session.getRemoteAddress());
    }
}
