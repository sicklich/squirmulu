package com.sparkfire.squirmulu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @time: 2023/08/14 17:30
 * @description:
 */
@ApiModel(value = "")
public class SysUser implements Serializable {
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     */
    @ApiModelProperty(value = "密码")
    private String pwd;

    /**
     */
    @ApiModelProperty(value = "手机号")
    private String telephone;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     */
    @ApiModelProperty(value = "")
    private Date createTime;

    /**
     */
    @ApiModelProperty(value = "")
    private Date updateTime;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date lastSignInTime;

    /**
     */
    @ApiModelProperty(value = "")
    private String birthday;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String image;

    /**
     * 权限,0-3代表游客，普通用户，会员，管理员
     */
    @ApiModelProperty(value = "权限,0-3代表游客，普通用户，会员，管理员")
    private Integer auth;

    /**
     */
    @ApiModelProperty(value = "")
    private String description;

    /**
     * ip
     */
    @ApiModelProperty(value = "ip")
    private String ip;

    private static final long serialVersionUID = 1L;

    public SysUser() {
    }

    public SysUser(String email, String pwd, String telephone, String nickname) {
        this.email = email;
        this.pwd = pwd;
        this.telephone = telephone;
        this.nickname = nickname;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getLastSignInTime()
    {
        return lastSignInTime;
    }

    public void setLastSignInTime(Date lastSignInTime)
    {
        this.lastSignInTime = lastSignInTime;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public Integer getAuth()
    {
        return auth;
    }

    public void setAuth(Integer auth)
    {
        this.auth = auth;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }
}