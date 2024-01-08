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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserEnterRoomHandler implements MessageHandler<UserEnterRoomRequest> {

    private Logger logger = LoggerFactory.getLogger(getClass());

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
                logger.info("kp {} enter", message.getUser_id());
                sendResponse = sendResponse.setCode(0);
            }else if (room.isApprove_required()) {
                //给房主发消息
                long kpId = room.getKp_id();
                Channel kpChannel = channelManager.getUser(kpId + "");
//                if (null == kpChannel) {
//                    logger.error("can't find kp {}", kpId);
//                    sendResponse = sendResponse.setCode(-1);
//                    channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(RoomEnterResponse.TYPE, sendResponse))));
//                    return;
//                }
                UserEnterRoomNtf ntf = new UserEnterRoomNtf(message.getRoom_id(), message.getUser_id(), message.getRoomname()
                        , message.getNickname(), message.getCard_id(), System.currentTimeMillis() / 1000, message.getEnter_mode());
                String ntfBody = objectMapper.writeValueAsString(new Invocation(UserEnterRoomNtf.TYPE, ntf));
                if(kpChannel != null) {
                    kpChannel.writeAndFlush(new TextWebSocketFrame(ntfBody));
                }
                //消息保存
                long id = SnowflakeGenerator.nextId();
                long reqMsgId = SnowflakeGenerator.nextId();
                //给房主和申请人都保存一样的消息
                String reqBody = objectMapper.writeValueAsString(new Invocation(UserEnterRoomRequest.TYPE, message));
                messageDao.insert(new MessageDB(reqMsgId,message.getUser_id(),0,UserEnterRoomRequest.TYPE,reqBody,System.currentTimeMillis()/1000, 0));
                messageDao.insert(new MessageDB(id,kpId,0,UserEnterRoomNtf.TYPE,ntfBody,System.currentTimeMillis()/1000, 0));
                //再给玩家发消息
                sendResponse = sendResponse.setCode(2);
                //加入玩家缓存
                channelManager.addUser(channel, message.getUser_id()+"");
            } else if(roomService.roomEnough(message.getRoom_id()+"")){
                sendResponse = sendResponse.setCode(3);
            } else {
                sendResponse = sendResponse.setCode(0);
                channelManager.enterRoom(channel, message.getRoom_id(), message.getUser_id());
                //另外需要在房间中加入人物卡
                roomService.enterRoom(message.getRoom_id()+"", message.getCard_id(), message.getUser_id()+"", message.getEnter_mode());
            }
            String resBody = objectMapper.writeValueAsString(new Invocation(RoomEnterResponse.TYPE, sendResponse));
            channel.writeAndFlush(new TextWebSocketFrame(resBody));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getType() {
        return UserEnterRoomRequest.TYPE;
    }

}
