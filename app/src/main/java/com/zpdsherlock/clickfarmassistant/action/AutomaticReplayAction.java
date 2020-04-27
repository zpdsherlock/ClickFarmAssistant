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
            searchTargetNode(windowInfo, new Function<AccessibilityNodeInfo, Boolean>() {
                @Override
                public Boolean apply(AccessibilityNodeInfo node) {
                    LinkedList<RecordNode> nodes = RecordStore.getInstance().getNodes();
                    RecordNode target = nodes.peek();
                    if (target != null) {
                        if (!Objects.equals(target.getPackageName(), node.getPackageName().toString())) {
                            return false;
                        }
                        if (!Objects.equals(target.getClassName(), node.getClassName().toString())) {
                            return false;
                        }
                        if (Objects.equals(target.getText(), node.getText().toString())) {
                            return true;
                        }
                    }
                    return false;
                }
            });
            windowInfo.recycle();
        }
    }

    private void searchTargetNode(AccessibilityNodeInfo node, Function<AccessibilityNodeInfo, Boolean> nodeFilter) {
        if (!nodeFilter.apply(node)) {
            for (int i = 0; i < node.getChildCount(); ++i) {
                searchTargetNode(node.getChild(i), nodeFilter);
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
        automaticManager.setMode(AutomaticMode.None);
    }
}
