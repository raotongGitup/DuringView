package com.example.duringlibrary.viewCircle;

/**
 * Created by ${raotong} on 2018/4/20.
 */

public class ElementBean {

    private String title;
    private int value;

    public ElementBean(String title, int value) {
        this.title = title;
        this.value = value;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
