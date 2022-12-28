/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 * Generated by the CloudDB ObjectType compiler.  DO NOT EDIT!
 */
package com.eminesa.dailyofspace.clouddb;

import com.huawei.agconnect.cloud.database.annotations.PrimaryKeys;
import com.huawei.agconnect.cloud.database.CloudDBZoneObject;
import com.huawei.agconnect.cloud.database.Text;

import java.util.Date;

/**
 * Definition of ObjectType ObjPhoto.
 *
 * @since 2022-12-27
 */
@PrimaryKeys({"userId"})
public final class ObjPhoto extends CloudDBZoneObject {
    private String userId;

    private String userName;

    private String photoAddDate;

    private String photoTitle;

    private String photoDesc;

    private String photoUrl;

    private String urlType;

    public ObjPhoto() {
        super(ObjPhoto.class);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPhotoAddDate(String photoAddDate) {
        this.photoAddDate = photoAddDate;
    }

    public String getPhotoAddDate() {
        return photoAddDate;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public void setPhotoDesc(String photoDesc) {
        this.photoDesc = photoDesc;
    }

    public String getPhotoDesc() {
        return photoDesc;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getUrlType() {
        return urlType;
    }

}
