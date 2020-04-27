package com.zpdsherlock.clickfarmassistant;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.zpdsherlock.clickfarmassistant.action.AutomaticAction;
import com.zpdsherlock.clickfarmassistant.action.AutomaticManager;
import com.zpdsherlock.clickfarmassistant.action.AutomaticMode;
import com.zpdsherlock.clickfarmassistant.action.AutomaticRecordAction;
import com.zpdsherlock.clickfarmassistant.action.AutomaticReplayAction;
import com.zpdsherlock.clickfarmassistant.view.FloatingWindow;

public class ClickFarmAssistantService extends AccessibilityService {
    private static final String TAG = "ClickFarmAssistantService";

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AutomaticMode mode = mAutomaticManager.getMode();
            if (mode == AutomaticMode.None) {
                int actionMode = intent.getIntExtra(AutomaticAction.KEY_ACTION, 0);
                if (actionMode == AutomaticMode.RecordGoing.getMode()) {
                    Log.d(TAG, "AutomaticMode RecordGoing");
                    showFloatWindow();
                    mAction = new AutomaticRecordAction(mAutomaticManager);
                    return;
                } else if (actionMode == AutomaticMode.ReplayGoing.getMode()) {
                    Log.d(TAG, "AutomaticMode ReplayGoing");
                    mAction = new AutomaticReplayAction(mAutomaticManager);
                    return;
                }
            }
            mAutomaticManager.setMode(AutomaticMode.None);
            synchronized (AutomaticAction.class) {
                mAction = null;
            }
        }
    };

    private AutomaticManager mAutomaticManager;

    private AutomaticAction mAction;

    private FloatingWindow mFloatingWindow;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mAutomaticManager = new AutomaticManager(AutomaticMode.None);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ClickFarmAssistantService.class.getSimpleName()));
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AutomaticMode mode = mAutomaticManager.getMode();
        Log.d(TAG, "onAccessibilityEvent: " + mode.name());
        if (mode != AutomaticMode.None) {
            synchronized (AutomaticAction.class) {
                if (mAction != null) {
                    mAction.onAccessibilityEvent(this, event);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
        mAction.onInterrupt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void showFloatWindow() {
        if (mFloatingWindow == null) {
            Button view = new Button(getApplicationContext());
            view.setWidth(300);
            view.setText(R.string.float_window_dismiss);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAction != null) {
                        mAction.onInterrupt();
                    }
                    mAutomaticManager.setMode(AutomaticMode.None);
                    dismissFloatWindow();
                }
            });
            mFloatingWindow = FloatingWindow.with(getApplicationContext()).use(view);
            mFloatingWindow.show();
        }
    }

    private void dismissFloatWindow() {
        if (mFloatingWindow != null) {
            mFloatingWindow.dismiss();
            mFloatingWindow = null;
        }
    }
}
