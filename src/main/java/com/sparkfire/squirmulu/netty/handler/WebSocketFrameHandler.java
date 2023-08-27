package com.sparkfire.squirmulu.netty.handler;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.netty.service.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Autowired
    ObjectMapper mapper;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 处理文本帧
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) frame).text();
            Invocation invocation = mapper.readValue(request,Invocation.class);
            System.out.println("Received message: " + invocation.getMessage());

            // 发送响应消息
            ctx.channel().writeAndFlush(new TextWebSocketFrame("Message received: "));
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
