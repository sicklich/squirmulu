package com.sparkfire.squirmulu.netty.handler;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.netty.message.Message;
import com.sparkfire.squirmulu.netty.service.Invocation;
import com.sparkfire.squirmulu.netty.service.NettyChannelManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@ChannelHandler.Sharable
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    @Autowired
    private NettyChannelManager channelManager;

    private final ExecutorService executor =  Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 处理文本帧
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) frame).text();
            Invocation invocation = mapper.readValue(request,Invocation.class);
            System.out.println("Received message: " + invocation.getMessage() + "type:" + invocation.getType());
            // 获得 type 对应的 MessageHandler 处理器
            MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(invocation.getType());
            // 获得  MessageHandler 处理器 的消息类
            Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
            // 解析消息
            Message message = JSON.parseObject(invocation.getMessage(), messageClass);
            // 执行逻辑
            executor.submit(() -> {
                // noinspection unchecked
                messageHandler.execute(ctx.channel(), message);
            });

        } else {
            // 不支持的帧类型
            String message = "Unsupported frame type: " + frame.getClass().getName();
            System.err.println(message);
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
