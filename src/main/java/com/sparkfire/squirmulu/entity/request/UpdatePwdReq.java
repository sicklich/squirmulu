package com.sparkfire.squirmulu.entity.request;

import java.util.Date;

public class UpdatePwdReq {
    private String email;
    private String telephone;
    private String new_pwd;

    private Date update_time;

    public UpdatePwdReq() {
    }

    public UpdatePwdReq(String email, String telephone, String new_pwd, Date update_time) {
        this.email = email;
        this.telephone = telephone;
        this.new_pwd = new_pwd;
        this.update_time = update_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNew_pwd() {
        return new_pwd;
    }

    public void setNew_pwd(String new_pwd) {
        this.new_pwd = new_pwd;
    }
}
