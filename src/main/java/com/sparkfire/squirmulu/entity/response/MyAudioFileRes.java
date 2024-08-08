package com.sparkfire.squirmulu.entity.response;

import java.util.List;

public class MyAudioFileRes {
    private List<AudioFileSimple> audio_list;

//    public PlayerCard() {
//    }


    public MyAudioFileRes(List<AudioFileSimple> audio_list) {
        this.audio_list = audio_list;
    }

    public List<AudioFileSimple> getAudio_list() {
        return audio_list;
    }

    public void setAudio_list(List<AudioFileSimple> audio_list) {
        this.audio_list = audio_list;
    }
}
