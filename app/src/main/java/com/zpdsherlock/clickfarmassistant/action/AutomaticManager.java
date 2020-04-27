package com.zpdsherlock.clickfarmassistant.action;

public class AutomaticManager {
    protected AutomaticMode mode;

    public AutomaticManager(AutomaticMode mode) {
        this.mode = mode;
    }

    public AutomaticMode getMode() {
        return mode;
    }

    public void setMode(AutomaticMode mode) {
        this.mode = mode;
    }
}
