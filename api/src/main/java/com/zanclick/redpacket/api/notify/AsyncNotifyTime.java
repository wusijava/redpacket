package com.zanclick.redpacket.api.notify;


import com.zanclick.redpacket.api.util.SystemClock;

/**
 * @author duchong
 * @description 轮询时间控制
 * @date 2020-8-25 14:54:16
 */
public class AsyncNotifyTime {

    /**
     * 轮询时间间隔 单位毫秒
     */
    private Long time;

    /**
     * 开始轮询时间 单位 毫秒
     */
    private Long now;


    public AsyncNotifyTime(Long time) {
        this.time = time;
    }

    public void now() {
        this.now = SystemClock.millisClock().now();
    }

    public Long sleepTime() {
        return this.time - (this.now - SystemClock.millisClock().now());
    }
}
