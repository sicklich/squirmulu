package com.sparkfire.squirmulu.netty.messageHandler.chat;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.dao.MessageDao;
import com.sparkfire.squirmulu.entity.MessageDB;
import com.sparkfire.squirmulu.entity.RoomCardUpdateReq;
import com.sparkfire.squirmulu.entity.RoomCardUpdateTarget;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.netty.handler.MessageHandler;
import com.sparkfire.squirmulu.netty.message.chat.*;
import com.sparkfire.squirmulu.netty.service.Invocation;
import com.sparkfire.squirmulu.netty.service.NettyChannelManager;
import com.sparkfire.squirmulu.service.RoomService;
import com.sparkfire.squirmulu.util.RedisClient;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

    @Autowired
    MessageDao messageDao;

    @Override
    public void execute(Channel channel, RoomEnterApproveReq message) {
        try {
            if (message.isApproved()) {
                //这个发给房主
                RoomEnterApproveRsp rsp = new RoomEnterApproveRsp().setCode(0);
                channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(RoomEnterApproveRsp.TYPE, rsp))));
                //这个发给申请人
                Channel userChannel = channelManager.getUser(message.getUser_id());
                UserEnterRoomApproveNtf ntf = new UserEnterRoomApproveNtf(0,Long.parseLong(message.getRoom_id()),Long.parseLong(message.getUser_id()),message.getRoomname(),message.getNickname(),message.getCard_id());
                String ntfBody = objectMapper.writeValueAsString(new Invocation(UserEnterRoomApproveNtf.TYPE, ntf));
                //消息保存
                long id = SnowflakeGenerator.nextId();
                messageDao.insert(new MessageDB(id,Long.parseLong(message.getUser_id()),0,UserEnterRoomApproveNtf.TYPE,ntfBody,System.currentTimeMillis()/1000));
                if(null != userChannel) {
                    userChannel.writeAndFlush(new TextWebSocketFrame(ntfBody));
                    channelManager.enterRoom(userChannel, Long.parseLong(message.getRoom_id()), Long.parseLong(message.getUser_id()));
                }
                //另外需要在房间中加入人物卡
                roomService.updateRoomCard(new RoomCardUpdateReq(message.getRoom_id(), message.getCard_id(), message.getUser_id(), Arrays.asList(new RoomCardUpdateTarget("join","g_players"))));

            } else {
                //这个发给房主
                RoomEnterApproveRsp rsp = new RoomEnterApproveRsp().setCode(0);
                channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(RoomEnterApproveRsp.TYPE, rsp))));
                //这个发给申请人
                Channel userChannel = channelManager.getUser(message.getUser_id());
                UserEnterRoomApproveNtf ntf = new UserEnterRoomApproveNtf(-1,Long.parseLong(message.getRoom_id()),Long.parseLong(message.getUser_id()),message.getRoomname(),message.getNickname(),message.getCard_id());
                String ntfBody = objectMapper.writeValueAsString(new Invocation(UserEnterRoomApproveNtf.TYPE, ntf));
                //消息保存
                long id = SnowflakeGenerator.nextId();
                messageDao.insert(new MessageDB(id,Long.parseLong(message.getUser_id()),0,UserEnterRoomApproveNtf.TYPE,ntfBody,System.currentTimeMillis()/1000));
                if(null != userChannel) {
                    userChannel.writeAndFlush(new TextWebSocketFrame(ntfBody));
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getType() {
        return RoomEnterApproveReq.TYPE;
    }

}
