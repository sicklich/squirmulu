package com.sparkfire.squirmulu.netty.handler;


import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    /**
     * 读空闲超时时间（秒）- 超过这个时间没收到任何数据就关闭连接
     * 客户端需要在此时间内发送心跳
     */
    private static final Integer READ_IDLE_TIMEOUT_SECONDS = 180;
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageDispatcher messageDispatcher;
    @Autowired
    private WebSocketFrameHandler webSocketFrameHandler;
    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(Channel ch) {
        // 添加 CorsHandler
        CorsConfig corsConfig = CorsConfigBuilder.forAnyOrigin()
//                .allowedRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS)
                .allowedRequestHeaders("content-type", "origin", "accept", "authorization")
                .allowNullOrigin()
                .build();
        // <1> 获得 Channel 对应的 ChannelPipeline
        ChannelPipeline channelPipeline = ch.pipeline();
        // <2> 添加一堆 NettyServerHandler 到 ChannelPipeline 中
        channelPipeline
                .addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(64 * 1024))
                .addLast(new ChunkedWriteHandler())
                // WebSocket 协议处理
                .addLast(new WebSocketServerProtocolHandler("/websocket"))
                // 空闲检测：90秒没收到数据就关闭连接（客户端需在此时间内发送心跳）
                .addLast(new IdleStateHandler(READ_IDLE_TIMEOUT_SECONDS, 0, 0, TimeUnit.SECONDS))
                // 处理空闲事件：读空闲超时则关闭连接
                .addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                        if (evt instanceof IdleStateEvent) {
                            logger.warn("[IdleStateHandler][连接({}) 读空闲超时({}秒无数据)，关闭连接]", 
                                    ctx.channel().id(), READ_IDLE_TIMEOUT_SECONDS);
                            ctx.close();
                        } else {
                            super.userEventTriggered(ctx, evt);
                        }
                    }
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                        logger.error("[IdleStateHandler][连接({}) 发生异常: {}]", ctx.channel().id(), cause.getMessage());
                        ctx.close();
                    }
                })
                .addLast(webSocketFrameHandler)
//                // 编码器
//                .addLast(new InvocationEncoder())
//                // 解码器
//                .addLast(new InvocationDecoder())
//                // 消息分发器
//                .addLast(messageDispatcher)
                // 服务端处理器
                .addLast(nettyServerHandler)
        ;
    }

}
