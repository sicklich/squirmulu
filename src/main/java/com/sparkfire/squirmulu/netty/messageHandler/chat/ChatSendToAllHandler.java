package com.sparkfire.squirmulu.netty.messageHandler.chat;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.dao.ChatDao;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.netty.message.chat.ChatSendResponse;
import com.sparkfire.squirmulu.netty.message.chat.ChatSendToAll;
import com.sparkfire.squirmulu.netty.service.Invocation;
import com.sparkfire.squirmulu.netty.handler.MessageHandler;
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
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Component
public class ChatSendToAllHandler implements MessageHandler<ChatSendToAll> {
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
    public void execute(Channel channel, ChatSendToAll message) {
        long msgID = SnowflakeGenerator.nextId();
        long now = System.currentTimeMillis()/1000;
        ChatSendResponse sendResponse = new ChatSendResponse().setMsgId(msgID).setCode(0);
        System.out.println(channel.id());
        try {
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(new Invocation(ChatSendResponse.TYPE, sendResponse))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        message.setId(msgID);
        message.setP_time(now);

        String key = (message.getChat_type() == CHAT ? RedisClient.room_chat_list : RedisClient.room_record_list) + message.getRoom_id();

        //记录到redis
        redisClient.zAdd(key,message,message.getP_time(), ChatSendToAll.class);

        //记录到数据库  分表
        // 将秒级时间戳转换为 LocalDateTime
        RoomInfo info = roomService.getRoomInfo(message.getRoom_id()+"");
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(info.getCreate_time()), ZoneId.systemDefault());

        // 使用 DateTimeFormatter 格式化日期为 "yyyymm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String formatted = dateTime.format(formatter);
        chatDao.insert("chat_"+formatted, message);

        // 创建转发的消息，并广播发送
        Set<Channel> channels = nettyChannelManager.getRoomChannel(message.getRoom_id());
        for(Channel userChannel : channels){
            try {
                System.out.println("chat_to_all"+userChannel.id());
                userChannel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(message)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getType() {
        return ChatSendToAll.TYPE;
    }

}
