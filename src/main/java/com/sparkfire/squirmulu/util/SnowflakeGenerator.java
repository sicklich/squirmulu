package com.sparkfire.squirmulu.util;

import java.time.Instant;

public class SnowflakeGenerator {

    private static final long NODE_ID;
    private static final long CUSTOM_EPOCH;

    private static final long SEQUENCE_BIT_LENGTH = 12L;
    private static final long NODE_ID_BIT_LENGTH = 10L;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BIT_LENGTH) - 1;
    private static final long MAX_NODE_ID = (1L << NODE_ID_BIT_LENGTH) - 1;

    private static long lastTimestamp = -1L;
    private static long sequence = 0L;

    static {
        // 在这里初始化 NODE_ID 和 CUSTOM_EPOCH
        NODE_ID = 1; // 分配一个唯一的节点ID
        CUSTOM_EPOCH = 1629470000000L; // 自定义纪元时间，例如：2021-08-20T12:00:00Z
    }

    public static synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards. Refusing to generate ID.");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                while (currentTimestamp == lastTimestamp) {
                    currentTimestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = currentTimestamp;

        long timestampPart = currentTimestamp - CUSTOM_EPOCH << (SEQUENCE_BIT_LENGTH + NODE_ID_BIT_LENGTH);
        long nodeIdPart = NODE_ID << SEQUENCE_BIT_LENGTH;
        long sequencePart = sequence;

        return timestampPart | nodeIdPart | sequencePart;
    }
}