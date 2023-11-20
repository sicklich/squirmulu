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
public class UserEnterRoomHandler implements MessageHandler<UserEnterRoomRequest> {

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
    public void execute(Channel channel, UserEnterRoomRequest message) {
        try {
            //先做是否在房的校验
            RoomEnterResponse sendResponse = new RoomEnterResponse();
            RoomInfo room = roomService.getRoomInfo(message.getRoom_id() + "");
            if (roomService.userInRoom(message.getRoom_id() + "", message.getUser_id() + "")) {
                sendResponse = sendResponse.setCode(1);
                //已经在房间也要加入
                channelManager.enterRoom(channel, message.getRoom_id(), message.getUser_id());
            } else if(room.getKp_id() == message.getUser_id() ){
                //如果是房主直接进房
                channelManager.enterRoom(channel, message.getRoom_id(), message.getUser_id());
                sendResponse = sendResponse.setCode(0);
            }else if (room.isApprove_required()) {
                //给房主发消息
                long kpId = room.getKp_id();
                Channel kpChannel = channelManager.getUser(kpId + "");
                if (null == kpChannel) {
                    sendResponse = sendResponse.setCode(-1);
                    channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(RoomEnterResponse.TYPE, sendResponse))));
                    return;
                }
                UserEnterRoomNtf ntf = new UserEnterRoomNtf(message.getRoom_id(), message.getUser_id(), message.getRoomname()
                        , message.getNickname(), message.getCard_id(), System.currentTimeMillis() / 1000);
                kpChannel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(UserEnterRoomNtf.TYPE, ntf))));
                //再给玩家发消息
                sendResponse = sendResponse.setCode(2);
                //加入玩家缓存
                channelManager.addUser(channel, message.getUser_id()+"");
            } else {
                sendResponse = sendResponse.setCode(0);
                channelManager.enterRoom(channel, message.getRoom_id(), message.getUser_id());
            }
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(RoomEnterResponse.TYPE, sendResponse))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getType() {
        return UserEnterRoomRequest.TYPE;
    }

}
