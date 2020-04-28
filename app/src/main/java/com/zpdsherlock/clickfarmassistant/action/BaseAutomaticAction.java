package com.zpdsherlock.clickfarmassistant.action;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public abstract class BaseAutomaticAction extends AutomaticAction {
    private static final String TAG = "BaseAutomaticAction";

    public BaseAutomaticAction(AutomaticManager automaticManager) {
        super(automaticManager);
        onPreAccessibilityEvent();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityService service, AccessibilityEvent event) {
        int type = event.getEventType();
        if (type == AccessibilityEvent.TYPE_WINDOWS_CHANGED) {
            // 窗口改变
            onWindowsChanged(service, event);
        } else if (onCheckAutomaticActionEnable(service, event)) {
            if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                onWindowStateChanged(service, event);
            } else if (type == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                onWindowContentChanged(service, event);
            } else if (type == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                onViewClicked(service, event);
            } else if (type == AccessibilityEvent.TYPE_VIEW_SELECTED) {
                onViewSelected(service, event);
            }
        }
    }

    protected void onWindowsChanged(AccessibilityService service, AccessibilityEvent event) {
    }

    protected boolean onCheckAutomaticActionEnable(AccessibilityService service, AccessibilityEvent event) {
        return false;
    }

    protected abstract void onPreAccessibilityEvent();

    //
    // TRANSITION TYPES
    //

    protected void onWindowStateChanged(AccessibilityService service, AccessibilityEvent event) {
    }

    protected void onWindowContentChanged(AccessibilityService service, AccessibilityEvent event) {
    }

    //
    // EXPLORATION TYPES
    //

    protected void onViewClicked(AccessibilityService service, AccessibilityEvent event) {
    }

    protected void onViewSelected(AccessibilityService service, AccessibilityEvent event) {
    }
}
