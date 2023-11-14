package com.sparkfire.squirmulu.netty.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class NettyChannelManager {

    /**
     * {@link Channel#attr(AttributeKey)} 属性中，表示 Channel 对应的用户
     */
    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");

    private static final AttributeKey<Long> CHANNEL_ATTR_KEY_USER_ID = AttributeKey.newInstance("userID");
    private static final AttributeKey<Long> CHANNEL_ATTR_KEY_ROOM = AttributeKey.newInstance("room");

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Channel 映射
     */
    private ConcurrentMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();
    /**
     * 用户与 Channel 的映射。
     * <p>
     * 通过它，可以获取用户对应的 Channel。这样，我们可以向指定用户发送消息。
     */
    private ConcurrentMap<String, Channel> userChannels = new ConcurrentHashMap<>();

    //缓存起来
    private ConcurrentMap<String, Channel> userChannelsTmp = new ConcurrentHashMap<>();

    private ConcurrentMap<Long, List<Channel>> roomChannels = new ConcurrentHashMap<>();
    private ConcurrentMap<Long, Long> userRooms = new ConcurrentHashMap<>();

    /**
     * 添加 Channel 到 {@link #channels} 中
     *
     * @param channel Channel
     */
    public void add(Channel channel) {
        channels.put(channel.id(), channel);
        logger.info("[add][一个连接({})加入]", channel.id());
    }

    public void enterRoom(Channel channel, long roomID, long userID) {
        userRooms.put(userID, roomID);
        channel.attr(CHANNEL_ATTR_KEY_ROOM).set(roomID);
        channel.attr(CHANNEL_ATTR_KEY_USER_ID).set(userID);
        List<Channel> channels = roomChannels.getOrDefault(roomID,new ArrayList<>());
        channels.add(channel);
        roomChannels.put(roomID,channels);
        logger.info("[add][一个连接({})加入房间,用户{},房间{}]", channel.id(), userID, roomID);
    }

    public List<Channel> getRoomChannel(long roomID){
        return roomChannels.getOrDefault(roomID,new ArrayList<>());
    }

    /**
     * 添加指定用户到 {@link #userChannels} 中
     *
     * @param channel Channel
     * @param user    用户
     */
    public void addUser(Channel channel, String user) {
        Channel existChannel = channels.get(channel.id());
        if (existChannel == null) {
            logger.error("[addUser][连接({}) 不存在]", channel.id());
            return;
        }
        // 设置属性
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        // 添加到 userChannels
        userChannels.put(user, channel);
    }

    public void addUserTmp(Channel channel, String user) {
        Channel existChannel = channels.get(channel.id());
        if (existChannel == null) {
            logger.error("[addUser][连接({}) 不存在]", channel.id());
            return;
        }
        // 设置属性
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        // 添加到 userChannels
        userChannelsTmp.put(user, channel);
    }

    public Channel getTmpUser(String user) {
        return userChannelsTmp.get(user);
    }

    public Channel getUser(String user) {
        return userChannels.get(user);
    }

    /**
     * 将 Channel 从 {@link #channels} 和 {@link #userChannels} 中移除
     *
     * @param channel Channel
     */
    public void remove(Channel channel) {
        // 移除 channels
        channels.remove(channel.id());
        // 移除 userChannels
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
            userChannels.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());
        }
        logger.info("[remove][一个连接({})离开]", channel.id());
    }

    public void send(String user, Invocation invocation) {
        // 获得用户对应的 Channel
        Channel channel = userChannels.get(user);
        if (channel == null) {
            logger.error("[send][连接不存在]");
            return;
        }
        if (!channel.isActive()) {
            logger.error("[send][连接({})未激活]", channel.id());
            return;
        }
        // 发送消息
        channel.writeAndFlush(invocation);
    }

    /**
     * 向所有用户发送消息
     *
     * @param invocation 消息体
     */
    public void sendAll(Invocation invocation) {
        for (Channel channel : channels.values()) {
            if (!channel.isActive()) {
                logger.error("[send][连接({})未激活]", channel.id());
                return;
            }
            System.out.println(channel.id() + " channel size:" + channels.size());
            // 发送消息
            channel.writeAndFlush(invocation);
        }
    }
}