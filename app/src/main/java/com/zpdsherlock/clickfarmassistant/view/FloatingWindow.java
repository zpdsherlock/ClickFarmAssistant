package com.zpdsherlock.clickfarmassistant.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class FloatingWindow implements View.OnTouchListener {
    public static FloatingWindow with(Context context) {
        return new FloatingWindow(context);
    }

    private WindowManager windowManager;

    private WindowManager.LayoutParams windowParams;

    private int x, y;

    private View mView;

    private FloatingWindow(Context context) {
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            windowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            windowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowParams.format = PixelFormat.RGBA_8888;
        windowParams.gravity = Gravity.START | Gravity.TOP;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.x = 300;
        windowParams.y = 300;
    }

    public FloatingWindow use(View view) {
        this.mView = view;
        return this;
    }

    public void show() {
        mView.setOnTouchListener(this);
        windowManager.addView(mView, windowParams);
    }

    public void dismiss() {
        windowManager.removeView(mView);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int nowX = (int) event.getRawX();
                int nowY = (int) event.getRawY();
                int movedX = nowX - x;
                int movedY = nowY - y;
                x = nowX;
                y = nowY;
                windowParams.x = windowParams.x + movedX;
                windowParams.y = windowParams.y + movedY;
                windowManager.updateViewLayout(view, windowParams);
                break;
            default:
                break;
        }
        return false;
    }
}
