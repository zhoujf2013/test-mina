package com.zhoujf.test.mina.chat;

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
    
    Map<String, Object> sessions = Collections.synchronizedMap(new HashMap<String, Object>());

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        logger.info("sessionCreated");
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
    }
    
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        if(session.getIdleCount(status) == 3) {
            session.close(false);
            logger.info("sessionIdle: {}", session.getIdleCount(status));
        }
    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        logger.error("exceptionCaught");
        if(session != null && session.isClosing()) {
            session.close(false);
        }
    }
}
