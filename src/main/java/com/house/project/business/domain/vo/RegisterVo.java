package com.house.project.business.domain.vo;

import com.house.project.business.domain.BizUser;

public class RegisterVo {

    private BizUser user;

    private VerifyVo smsVerify;

    private VerifyVo imgVerify;

    public BizUser getUser() {
        return user;
    }

    public void setUser(BizUser user) {
        this.user = user;
    }

    public VerifyVo getSmsVerify() {
        return smsVerify;
    }

    public void setSmsVerify(VerifyVo smsVerify) {
        this.smsVerify = smsVerify;
    }

    public VerifyVo getImgVerify() {
        return imgVerify;
    }

    public void setImgVerify(VerifyVo imgVerify) {
        this.imgVerify = imgVerify;
    }
}
