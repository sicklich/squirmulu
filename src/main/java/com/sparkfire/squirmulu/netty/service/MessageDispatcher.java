package com.sparkfire.squirmulu.netty.service;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
@Component
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    private final ExecutorService executor =  Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation invocation) {
        // 获得 type 对应的 MessageHandler 处理器
        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(invocation.getType());
        // 获得  MessageHandler 处理器 的消息类
        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
        // 解析消息
        Message message = JSON.parseObject(invocation.getMessage(), messageClass);
        // 执行逻辑
        executor.submit(new Runnable() {

            @Override
            public void run() {
                // noinspection unchecked
                messageHandler.execute(ctx.channel(), message);
            }

        });
    }

}
