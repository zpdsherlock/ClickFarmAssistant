package com.zpdsherlock.clickfarmassistant.action;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public abstract class BaseAutomaticAction extends AutomaticAction {
    private static final String TAG = "BaseAutomaticAction";

    public BaseAutomaticAction(AutomaticManager automaticManager) {
        super(automaticManager);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityService service, AccessibilityEvent event) {
        if (!onCheckAutomaticActionEnable(service, event)) {
            return;
        }
        int type = event.getEventType();
        Log.d(TAG, "getEventType: " + type);
        switch (type) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                onWindowStateChanged(service, event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                onWindowContentChanged(service, event);
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                onWindowChanged(service, event);
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                break;
            case AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT:
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                onViewClicked(service, event);
                break;
            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED:
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                onViewSelected(service, event);
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                break;
        }
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

    protected void onWindowChanged(AccessibilityService service, AccessibilityEvent event) {
    }

    //
    // NOTIFICATION TYPES
    //

    public void onNotificationStateChanged(AccessibilityService service, AccessibilityEvent event) {
    }

    //
    // EXPLORATION TYPES
    //

    protected void onViewClicked(AccessibilityService service, AccessibilityEvent event) {
    }

    protected void onViewSelected(AccessibilityService service, AccessibilityEvent event) {
    }

    //
    // MISCELLANEOUS TYPES
    //

}
