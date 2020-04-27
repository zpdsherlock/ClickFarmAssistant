package com.zpdsherlock.clickfarmassistant.action;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public abstract class AutomaticAction {
    public static final String KEY_ACTION = "mode";

    protected final AutomaticManager automaticManager;

    public AutomaticAction(AutomaticManager automaticManager) {
        this.automaticManager = automaticManager;
        this.automaticManager.setMode(AutomaticMode.Prepare);
    }

    public abstract void onAccessibilityEvent(AccessibilityService service, AccessibilityEvent event);

    public abstract void onInterrupt();
}
