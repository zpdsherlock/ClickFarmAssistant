package com.zpdsherlock.clickfarmassistant.action;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.zpdsherlock.clickfarmassistant.store.RecordNode;
import com.zpdsherlock.clickfarmassistant.store.RecordStore;

public class AutomaticRecordAction extends BaseAutomaticAction {
    private static final String TAG = "AutomaticRecordAction";

    public AutomaticRecordAction(AutomaticManager automaticManager) {
        super(automaticManager);
        RecordStore.getInstance().getNodes().clear();
    }

    @Override
    protected boolean onCheckAutomaticActionEnable(AccessibilityService service, AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        return !"com.zpdsherlock.clickfarmassistant".equals(packageName);
    }

    @Override
    protected void onPreAccessibilityEvent() {
        automaticManager.setMode(AutomaticMode.RecordGoing);
    }

    @Override
    protected void onWindowStateChanged(AccessibilityService service, AccessibilityEvent event) {
        AccessibilityNodeInfo info = event.getSource();
        if (info != null) {
            info.recycle();
        }
    }

    @Override
    protected void onViewClicked(AccessibilityService service, AccessibilityEvent event) {
        recordNodeFromEvent(event);
    }

    @Override
    protected void onViewSelected(AccessibilityService service, AccessibilityEvent event) {
        recordNodeFromEvent(event);
    }

    private void recordNodeFromEvent(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        RecordNode node = new RecordNode();
        node.setPackageName(packageName);
        node.setClassName(event.getClassName().toString());
        node.setText(event.getText().toString());
        RecordStore.getInstance().getNodes().add(node);
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
        automaticManager.setMode(AutomaticMode.None);
    }
}
