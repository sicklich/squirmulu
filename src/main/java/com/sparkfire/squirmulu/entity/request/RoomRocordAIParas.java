package com.sparkfire.squirmulu.entity.request;

import java.math.BigDecimal;

public class RoomRocordAIParas {
    private String prompt;
    private BigDecimal temperature;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }
}
