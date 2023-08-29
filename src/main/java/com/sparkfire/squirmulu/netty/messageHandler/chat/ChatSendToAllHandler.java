package com.sparkfire.squirmulu.netty.messageHandler.chat;


import com.sparkfire.squirmulu.netty.message.chat.ChatRedirectToUserRequest;
import com.sparkfire.squirmulu.netty.message.chat.ChatSendResponse;
import com.sparkfire.squirmulu.netty.message.chat.ChatSendToAllRequest;
import com.sparkfire.squirmulu.netty.service.Invocation;
import com.sparkfire.squirmulu.netty.handler.MessageHandler;
import com.sparkfire.squirmulu.netty.service.NettyChannelManager;
import com.sparkfire.squirmulu.util.RedisClient;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatSendToAllHandler implements MessageHandler<ChatSendToAllRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Autowired
    private RedisClient redisClient;

    @Override
    public void execute(Channel channel, ChatSendToAllRequest message) {
        long msgID = SnowflakeGenerator.nextId();
        long now = System.currentTimeMillis()/1000;
        // 这里，假装直接成功
        ChatSendResponse sendResponse = new ChatSendResponse().setMsgId(msgID).setCode(0);
        channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, sendResponse));
        message.setId(msgID);
        message.setP_time(now);

        //记录到redis


        // 创建转发的消息，并广播发送
//        ChatRedirectToUserRequest sendToUserRequest = new ChatRedirectToUserRequest().setMsgId(message.getMsgId())
//                .setContent(message.getContent());
        nettyChannelManager.sendAll(new Invocation(ChatRedirectToUserRequest.TYPE, message));
    }

    @Override
    public String getType() {
        return ChatSendToAllRequest.TYPE;
    }

}
