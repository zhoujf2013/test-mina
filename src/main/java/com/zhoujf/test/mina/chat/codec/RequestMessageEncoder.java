package com.zhoujf.test.mina.chat.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMessageEncoder extends ProtocolEncoderAdapter {
    
    private static Logger logger = LoggerFactory.getLogger(RequestMessageEncoder.class);

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        logger.info("encode");
    }
    
    @Override
    public void dispose(IoSession session) throws Exception {
        logger.info("dispose");
    }
    
}
