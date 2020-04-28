package com.zpdsherlock.clickfarmassistant.action;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.zpdsherlock.clickfarmassistant.store.RecordNode;
import com.zpdsherlock.clickfarmassistant.store.RecordStore;

import java.util.List;

public class AutomaticRecordAction extends BaseAutomaticAction {
    private static final String TAG = "AutomaticRecordAction";

    private AccessibilityNodeInfo windowInfo;

    public AutomaticRecordAction(AutomaticManager automaticManager) {
        super(automaticManager);
        RecordStore.getInstance().getNodes().clear();
    }

    @Override
    protected boolean onCheckAutomaticActionEnable(AccessibilityService service, AccessibilityEvent event) {
        CharSequence packageName = event.getPackageName();
        return packageName != null && !"com.zpdsherlock.clickfarmassistant".equals(packageName.toString());
    }

    @Override
    protected void onPreAccessibilityEvent() {
        automaticManager.setMode(AutomaticMode.RecordGoing);
    }

    @Override
    protected void onWindowsChanged(AccessibilityService service, AccessibilityEvent event) {
        reset();
        windowInfo = service.getRootInActiveWindow();
    }

    @Override
    protected void onViewClicked(AccessibilityService service, AccessibilityEvent event) {
        recordNodeFromEvent(service, event);
    }

    @Override
    protected void onViewSelected(AccessibilityService service, AccessibilityEvent event) {
        recordNodeFromEvent(service, event);
    }

    private void recordNodeFromEvent(AccessibilityService service, AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        RecordNode node = new RecordNode();
        node.setPackageName(packageName);
        node.setClassName(event.getClassName().toString());
        List<CharSequence> texts = event.getText();
        for (int i = 0; i < (texts == null ? 0 : texts.size()); ++i) {
            node.getText().add(texts.get(i).toString());
        }
        RecordStore.getInstance().getNodes().add(node);
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
        automaticManager.setMode(AutomaticMode.None);
        reset();
    }

    private void reset() {
        if (windowInfo != null) {
            windowInfo.recycle();
            windowInfo = null;
        }
    }
}
