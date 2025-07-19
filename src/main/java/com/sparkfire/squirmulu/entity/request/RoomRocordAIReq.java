package com.sparkfire.squirmulu.entity.request;

public class RoomRocordAIReq {
    private String purpose;
    private RoomRocordAIParas ai_paras;
    private String model;
    private RoomRocordAILogObj log_obj;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public RoomRocordAIParas getAi_paras() {
        return ai_paras;
    }

    public void setAi_paras(RoomRocordAIParas ai_paras) {
        this.ai_paras = ai_paras;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public RoomRocordAILogObj getLog_obj() {
        return log_obj;
    }

    public void setLog_obj(RoomRocordAILogObj log_obj) {
        this.log_obj = log_obj;
    }
}
