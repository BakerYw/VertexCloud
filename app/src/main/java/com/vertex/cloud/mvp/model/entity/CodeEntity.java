package com.vertex.cloud.mvp.model.entity;

/**
 * @Author CHEESE
 * @Date 2021-10-22 19:25
 * @Version 1.0
 * @Describe
 **/
public class CodeEntity {

    /**
     * img : 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
     * captchaOnOff : true
     * uuid : a3ded006f8fc4aca9ed6bfeddd28e2e8
     */

    private String img;
    private boolean captchaOnOff;
    private String uuid;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isCaptchaOnOff() {
        return captchaOnOff;
    }

    public void setCaptchaOnOff(boolean captchaOnOff) {
        this.captchaOnOff = captchaOnOff;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
