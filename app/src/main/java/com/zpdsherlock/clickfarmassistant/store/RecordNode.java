package com.zpdsherlock.clickfarmassistant.store;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class RecordNode {
    private String packageName;

    private String className;

    private List<String> text = new ArrayList<>();

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

    public List<String> getText() {
        return text;
    }

    public Rect getRect() {
        return rect;
    }
}
