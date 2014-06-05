package com.zhoujf.test.mina.chat;

import org.apache.mina.core.session.IoSession;

public interface ServerCallback {

    void callback(String message);
    
    void log(String message);
    
    void broadcast(String message);
    
    void sendMessage(IoSession session, String message);
}
