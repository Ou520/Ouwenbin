package com.example.myapplication.ouwenbin.model.bean;

public class Contact {
    private String letter;
    private int head;
    private String name;
    private int i;

    public Contact() {
    }

    public Contact(String letter, String name,int head,int i ) {
        this.letter = letter;
        this.head = head;
        this.name = name;
        this.i=i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
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

    @Override
    public String toString() {
        return "Contact{" +
                "letter='" + letter + '\'' +
                ", head=" + head +
                ", name='" + name + '\'' +
                '}';
    }
}
