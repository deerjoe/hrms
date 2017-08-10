package com.jason.menu.domain;
import java.io.Serializable;
public class Menu implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer parentid;

    private String text;

    private String icon;

    private String url;

    private String targettype;

    private Boolean isheader;

    private Boolean isopen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargettype() {
        return targettype;
    }

    public void setTargettype(String targettype) {
        this.targettype = targettype;
    }

    public Boolean getIsheader() {
        return isheader;
    }

    public void setIsheader(Boolean isheader) {
        this.isheader = isheader;
    }

    public Boolean getIsopen() {
        return isopen;
    }

    public void setIsopen(Boolean isopen) {
        this.isopen = isopen;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", parentid=" + parentid +
                ", text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + url + '\'' +
                ", targettype='" + targettype + '\'' +
                ", isheader=" + isheader +
                ", isopen=" + isopen +
                '}';
    }
}
