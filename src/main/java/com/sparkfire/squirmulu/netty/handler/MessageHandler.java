package com.sparkfire.squirmulu.netty.handler;

import com.sparkfire.squirmulu.netty.message.Message;
import com.sparkfire.squirmulu.netty.message.chat.UserEnterRoomRequest;
import io.netty.channel.Channel;

public interface MessageHandler<T extends Message> {

    /**
     * 执行处理消息
     *
     * @param channel 通道
     * @param message 消息
     */
    void execute(Channel channel, T message);

    /**
     * @return 消息类型，即每个 Message 实现类上的 TYPE 静态字段
     */
    String getType();

}

