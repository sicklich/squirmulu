package com.sparkfire.squirmulu.netty.messageHandler.chat;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.netty.handler.MessageHandler;
import com.sparkfire.squirmulu.netty.message.chat.*;
import com.sparkfire.squirmulu.netty.service.Invocation;
import com.sparkfire.squirmulu.netty.service.NettyChannelManager;
import com.sparkfire.squirmulu.util.RedisClient;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEnterRoomHandler implements MessageHandler<UserEnterRoomRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NettyChannelManager channelManager;

    @Override
    public void execute(Channel channel, UserEnterRoomRequest message) {
        channelManager.enterRoom(channel,message.getRoom_id(), message.getUser_id());

        RoomEnterResponse sendResponse = new RoomEnterResponse().setCode(0);
        try {
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(RoomEnterResponse.TYPE, sendResponse))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getType() {
        return UserEnterRoomRequest.TYPE;
    }

}
