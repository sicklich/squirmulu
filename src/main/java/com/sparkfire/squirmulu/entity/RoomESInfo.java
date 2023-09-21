package com.sparkfire.squirmulu.entity;

import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Objects;

@Document(indexName = "room-index")
public class RoomESInfo {
    @Id
    @Field(index = false,type = FieldType.Long)
    private long id;
    @Field(analyzer = "ik_smart",searchAnalyzer = "ik_smart",store = true,type = FieldType.Text)
    private String r_name;
    @Field(analyzer = "ik_smart",searchAnalyzer = "ik_smart",store = true,type = FieldType.Text)
    private String r_des;

    public RoomESInfo(long id, String r_name, String r_des) {
        this.id = id;
        this.r_name = r_name;
        this.r_des = r_des;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getR_des() {
        return r_des;
    }

    public void setR_des(String r_des) {
        this.r_des = r_des;
    }

    public RoomESInfo() {
    }


}
