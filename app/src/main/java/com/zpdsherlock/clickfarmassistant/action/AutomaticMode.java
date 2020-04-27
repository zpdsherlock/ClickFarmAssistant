package com.zpdsherlock.clickfarmassistant.action;

public enum AutomaticMode {
    /**
     * 自动化关闭状态
     */
    None(0x0),
    /**
     * 准备中
     */
    Prepare(0x1),
    /**
     * 录制中
     */
    RecordGoing(0x2),
    /**
     * 回放中
     */
    ReplayGoing(0x4),
    /**
     * 目标判断
     */
    TargetChecking(0x8);

    private int mode;

    AutomaticMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
}
