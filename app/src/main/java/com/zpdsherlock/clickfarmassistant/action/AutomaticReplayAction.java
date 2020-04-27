package com.zpdsherlock.clickfarmassistant.action;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class AutomaticReplayAction extends BaseAutomaticAction {
    private static final String TAG = "AutomaticReplayAction";

    public AutomaticReplayAction(AutomaticManager automaticManager) {
        super(automaticManager);
    }

    @Override
    protected boolean onCheckAutomaticActionEnable(AccessibilityService service, AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        return !"com.zpdsherlock.clickfarmassistant".equals(packageName);
    }

    @Override
    protected void onPreAccessibilityEvent() {
        automaticManager.setMode(AutomaticMode.ReplayGoing);
    }

    @Override
    protected void onWindowStateChanged(AccessibilityService service, AccessibilityEvent event) {
        AccessibilityNodeInfo windowInfo = service.getRootInActiveWindow();
        if (windowInfo != null) {
            windowInfo.recycle();
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
        automaticManager.setMode(AutomaticMode.None);
    }
}
