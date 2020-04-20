package com.example.myapplication.ouwenbin.model.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {
    private String name;
    private String content;
    private long date;
    private int head;

    public Chat() {
    }

    public Chat(String name, String content, long date,int head) {
        this.name = name;
        this.content = content;
        this.date = date;
        this.head=head;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        //获取当前系统日期和时间
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date=new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", head=" + head +
                '}';
    }
}
