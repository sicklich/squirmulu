package com.sparkfire.squirmulu.netty.handler;

import com.sparkfire.squirmulu.netty.service.NettyChannelManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyChannelManager channelManager;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 从管理器中添加
        channelManager.add(ctx.channel());
        logger.info("[channelActive][连接({}) 激活]", ctx.channel().id());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 连接断开时（包括正常关闭和异常断开），从管理器中移除
        logger.info("[channelInactive][连接({}) 断开]", ctx.channel().id());
        channelManager.remove(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        // 双重保险：确保 channel 被清理
        logger.info("[channelUnregistered][连接({}) 注销]", ctx.channel().id());
        channelManager.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("[exceptionCaught][连接({}) 发生异常: {}]", ctx.channel().id(), cause.getMessage(), cause);
        // 断开连接
        ctx.channel().close();
    }

}