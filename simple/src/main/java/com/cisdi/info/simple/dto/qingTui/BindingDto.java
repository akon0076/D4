package com.cisdi.info.simple.dto.qingTui;

/**
 * @ClassName bindingDto
 * @Author 龚翔
 * @Date 2018-09-27 19:49
 **/
public class BindingDto {
    private String name;
    private String password;
    private String uId;
    private String openId;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
