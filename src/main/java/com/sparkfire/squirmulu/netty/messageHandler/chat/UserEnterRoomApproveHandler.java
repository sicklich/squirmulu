package com.sparkfire.squirmulu.netty.messageHandler.chat;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.netty.handler.MessageHandler;
import com.sparkfire.squirmulu.netty.message.chat.*;
import com.sparkfire.squirmulu.netty.service.Invocation;
import com.sparkfire.squirmulu.netty.service.NettyChannelManager;
import com.sparkfire.squirmulu.service.RoomService;
import com.sparkfire.squirmulu.util.RedisClient;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEnterRoomApproveHandler implements MessageHandler<RoomEnterApproveReq> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NettyChannelManager channelManager;

    @Autowired
    RoomService roomService;

    @Override
    public void execute(Channel channel, RoomEnterApproveReq message) {
        try {
            if (message.isApproved()) {
                //这个发给房主
                RoomEnterApproveRsp rsp = new RoomEnterApproveRsp().setCode(0);
                channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(RoomEnterApproveRsp.TYPE, rsp))));
                //这个发给申请人
                Channel userChannel = channelManager.getUser(message.getUser_id());
                UserEnterRoomApproveNtf ntf = new UserEnterRoomApproveNtf(0,"");
                userChannel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(UserEnterRoomApproveNtf.TYPE, ntf))));
                channelManager.enterRoom(userChannel, Long.parseLong(message.getRoom_id()), Long.parseLong(message.getUser_id()));

            } else {
                //这个发给房主
                RoomEnterApproveRsp rsp = new RoomEnterApproveRsp().setCode(0);
                channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(RoomEnterApproveRsp.TYPE, rsp))));
                //这个发给申请人
                Channel userChannel = channelManager.getUser(message.getUser_id());
                UserEnterRoomApproveNtf ntf = new UserEnterRoomApproveNtf(-1,"");
                userChannel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(UserEnterRoomApproveNtf.TYPE, ntf))));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getType() {
        return UserEnterRoomRequest.TYPE;
    }

}
