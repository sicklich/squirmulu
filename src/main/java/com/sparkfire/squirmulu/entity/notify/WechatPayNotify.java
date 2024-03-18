package com.sparkfire.squirmulu.entity.notify;

public class WechatPayNotify {
    private String id;
    private String create_time;
    private String resource_type;
    private String event_type;
    private String summary;
    private WechatResource resource;

    public WechatPayNotify() {
    }

    public WechatPayNotify(String id, String create_time, String resource_type, String event_type, String summary, WechatResource resource) {
        this.id = id;
        this.create_time = create_time;
        this.resource_type = resource_type;
        this.event_type = event_type;
        this.summary = summary;
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public WechatResource getResource() {
        return resource;
    }

    public void setResource(WechatResource resource) {
        this.resource = resource;
    }
}
