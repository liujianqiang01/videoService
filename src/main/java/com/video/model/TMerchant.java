package com.video.model;

import java.io.Serializable;

public class TMerchant implements Serializable {
     //
    private Integer id;

     //商户id
    private String menchantId;

     //
    private String menchantName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenchantId() {
        return menchantId;
    }

    public void setMenchantId(String menchantId) {
        this.menchantId = menchantId == null ? null : menchantId.trim();
    }

    public String getMenchantName() {
        return menchantName;
    }

    public void setMenchantName(String menchantName) {
        this.menchantName = menchantName == null ? null : menchantName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", menchantId=").append(menchantId);
        sb.append(", menchantName=").append(menchantName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}