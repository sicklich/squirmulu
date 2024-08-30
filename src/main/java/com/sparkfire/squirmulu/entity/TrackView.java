package com.sparkfire.squirmulu.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrackView {

    private Long id;

    @JsonProperty("track_type")
    private Integer trackType;

    @JsonProperty("event")
    private Integer event;

    @JsonProperty("url")
    private String url;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("session")
    private String session;

    @JsonProperty("referrer")
    private String referrer;

    @JsonProperty("remark")
    private String remark;
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTrackType() {
        return trackType;
    }

    public void setTrackType(Integer trackType) {
        this.trackType = trackType;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
