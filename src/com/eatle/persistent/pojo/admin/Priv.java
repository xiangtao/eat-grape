package com.eatle.persistent.pojo.admin;

import java.io.Serializable;

public class Priv implements Serializable {
    private Long id;

    private String privName;

    private String meueName;

    private String action;

    private String description;

    private Byte isShow;

    private Long pId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivName() {
        return privName;
    }

    public void setPrivName(String privName) {
        this.privName = privName;
    }

    public String getMeueName() {
        return meueName;
    }

    public void setMeueName(String meueName) {
        this.meueName = meueName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    public Long getPId() {
        return pId;
    }

    public void setPId(Long pId) {
        this.pId = pId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Priv other = (Priv) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPrivName() == null ? other.getPrivName() == null : this.getPrivName().equals(other.getPrivName()))
            && (this.getMeueName() == null ? other.getMeueName() == null : this.getMeueName().equals(other.getMeueName()))
            && (this.getAction() == null ? other.getAction() == null : this.getAction().equals(other.getAction()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
            && (this.getPId() == null ? other.getPId() == null : this.getPId().equals(other.getPId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPrivName() == null) ? 0 : getPrivName().hashCode());
        result = prime * result + ((getMeueName() == null) ? 0 : getMeueName().hashCode());
        result = prime * result + ((getAction() == null) ? 0 : getAction().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
        result = prime * result + ((getPId() == null) ? 0 : getPId().hashCode());
        return result;
    }
}