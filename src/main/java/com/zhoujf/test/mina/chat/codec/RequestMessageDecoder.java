package com.zhoujf.test.mina.chat.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMessageDecoder extends ProtocolDecoderAdapter {
    
    private static Logger logger = LoggerFactory.getLogger(RequestMessageDecoder.class);

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        logger.info("decode");
    }
    
    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        logger.info("finishDecode");
    }
    
    @Override
    public void dispose(IoSession session) throws Exception {
        logger.info("dispose");
    }
    
}
