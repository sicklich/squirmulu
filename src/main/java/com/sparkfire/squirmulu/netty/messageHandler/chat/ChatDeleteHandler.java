package com.sparkfire.squirmulu.netty.messageHandler.chat;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.dao.ChatDao;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
public class ChatDeleteHandler implements MessageHandler<ChatDelete> {
    public static final int CHAT = 1;
    public static final int RECORD = 2;

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ChatDao chatDao;


    @Override
    public void execute(Channel channel, ChatDelete message) {
        ChatDeleteResponse response = new ChatDeleteResponse().setId(message.getId()).setCode(0);

        String key = (message.getChat_type() == CHAT ? RedisClient.room_chat_list : RedisClient.room_record_list) + message.getRoom_id();


        //记录到数据库  分表
        // 将秒级时间戳转换为 LocalDateTime
        RoomInfo info = roomService.getRoomInfo(message.getRoom_id() + "");
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(info.getCreate_time()), ZoneId.systemDefault());

        // 使用 DateTimeFormatter 格式化日期为 "yyyymm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String formatted = dateTime.format(formatter);
        ChatSendToAll chat = chatDao.findById("chat_" + formatted, message.getId());

        //从redis删除
        Set<ChatSendToAll> set = redisClient.zRangeByScore(key, chat.getP_time(), chat.getP_time(), ChatSendToAll.class);
        System.out.println("roomid:"+message.getRoom_id()+"chat size:"+set.size()+" p_time: "+chat.getP_time());
        if(set.size() == 1){
            redisClient.zRemoveByScore(key, chat.getP_time(), chat.getP_time());
        }else{
            for(ChatSendToAll chatSendToAll : set) {
                if(chatSendToAll.getId() == chat.getId()) {
                    redisClient.zRemove(key, chatSendToAll, ChatSendToAll.class);
                }
            }
        }
        chatDao.delete("chat_" + formatted, message.getId());

        // 创建转发的消息，并广播发送
        Set<Channel> channels = nettyChannelManager.getRoomChannel(message.getRoom_id());
        for (Channel userChannel : channels) {
            System.out.println("roomid:"+message.getRoom_id()+"channel size:"+channels.size());
            try {
                userChannel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(ChatDeleteWithIDString.TYPE, new ChatDeleteWithIDString(
                        message.getId() + "", message.getChat_type(), message.getRoom_id() + "", message.getC_type())))));
                System.out.println("roomid:"+message.getRoom_id()+"channel:"+userChannel.id());
//                userChannel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(ChatDelete.TYPE, message))));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(
                    new Invocation(ChatDeleteResponse.TYPE, response))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getType() {
        return ChatDelete.TYPE;
    }

}
