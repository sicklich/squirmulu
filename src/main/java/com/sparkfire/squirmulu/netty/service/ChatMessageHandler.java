package com.sparkfire.squirmulu.netty.service;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageHandler implements MessageHandler{
    @Override
    public void execute(Channel channel, Message message) {
        System.out.println(message.toString());
    }

    @Override
    public String getType() {
        return "CHAT";
    }
}
