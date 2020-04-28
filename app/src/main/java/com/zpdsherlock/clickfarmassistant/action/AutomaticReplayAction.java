package com.zpdsherlock.clickfarmassistant.action;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.zpdsherlock.clickfarmassistant.store.RecordNode;
import com.zpdsherlock.clickfarmassistant.store.RecordStore;

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Function;

public class AutomaticReplayAction extends BaseAutomaticAction {
    private static final String TAG = "AutomaticReplayAction";

    public AutomaticReplayAction(AutomaticManager automaticManager) {
        super(automaticManager);
    }

    @Override
    protected boolean onCheckAutomaticActionEnable(AccessibilityService service, AccessibilityEvent event) {
        CharSequence packageName = event.getPackageName();
        return packageName != null && !"com.zpdsherlock.clickfarmassistant".equals(packageName.toString());
    }

    @Override
    protected void onPreAccessibilityEvent() {
        automaticManager.setMode(AutomaticMode.ReplayGoing);
    }

    @Override
    protected void onWindowStateChanged(AccessibilityService service, AccessibilityEvent event) {
        AccessibilityNodeInfo windowInfo = service.getRootInActiveWindow();
        if (windowInfo != null) {
            searchTargetNode(windowInfo);
            windowInfo.recycle();
        }
    }

    private void searchTargetNode(AccessibilityNodeInfo node) {
        boolean checker = false;
        LinkedList<RecordNode> nodes = RecordStore.getInstance().getNodes();
        RecordNode target = nodes.peek();
        if (target != null) {
            if (Objects.equals(target.getPackageName(), node.getPackageName().toString()) &&
                    Objects.equals(target.getClassName(), node.getClassName().toString())) {
                // match the text of the source's sub-tree.
//                if (target.getText().size() == node.getText()) {
//
//                }
//                for () {
//
//                }
//                if (Objects.equals(target.getText(), String.format("[%s]", node.getText() == null ? "" : node.getText().toString()))) {
//                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                    nodes.poll();
//                    checker = true;
//                }
            }
        }
        if (!checker) {
            for (int i = 0; i < node.getChildCount(); ++i) {
                searchTargetNode(node.getChild(i));
            }
        } else if (RecordStore.getInstance().getNodes().isEmpty()) {
            onInterrupt();
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
        automaticManager.setMode(AutomaticMode.None);
    }
}
