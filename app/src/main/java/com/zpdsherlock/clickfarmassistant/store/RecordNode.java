package com.zpdsherlock.clickfarmassistant.store;

import android.graphics.Rect;

public class RecordNode {
    private String packageName;

    private String className;

    private String text;

    private Rect rect = new Rect();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Rect getRect() {
        return rect;
    }
}
