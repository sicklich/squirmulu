package com.sparkfire.squirmulu.netty.messageHandler.heartbeat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.netty.message.chat.RoomEnterResponse;
import com.sparkfire.squirmulu.netty.message.heartbeat.HeartbeatRequest;
import com.sparkfire.squirmulu.netty.message.heartbeat.HeartbeatResponse;
import com.sparkfire.squirmulu.netty.service.Invocation;
import com.sparkfire.squirmulu.netty.handler.MessageHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatRequest> {

    @Autowired
    ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Channel channel, HeartbeatRequest message) {
        logger.info("[execute][收到连接({}) 的心跳请求]", channel.id());
        // 响应心跳
        HeartbeatResponse response = new HeartbeatResponse().setCode(0);
        try {
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(HeartbeatResponse.TYPE, response))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getType() {
        return HeartbeatRequest.TYPE;
    }

}
