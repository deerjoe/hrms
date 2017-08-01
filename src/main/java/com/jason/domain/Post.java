package com.jason.domain;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;  // 公告编号
    private String title;  // 标题
    private String content;  // 内容
    private Admin admin;  // 发布人
    private Date date;  // 发布日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post:[id=" + id + ",title=" + title + ",content=" + content
                + ",admin=" + admin + ",date=" + date + "]";
    }
}
