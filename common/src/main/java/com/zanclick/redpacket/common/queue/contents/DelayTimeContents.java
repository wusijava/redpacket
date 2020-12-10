package com.zanclick.redpacket.common.queue.contents;

public class DelayTimeContents {

    /**
     * 1秒
     */
    public static final long ONE_SECOND = 1 * 1000L;

    /**
     * 1分钟
     */
    public static final long ONE_MINUTE = ONE_SECOND * 60;

    /**
     * 返回结果发送间隔
     * 5分钟，30分钟，2小时，5小时
     * */
    public final static long[] DELAYED_TIMES_LONG = {ONE_MINUTE * 5, ONE_MINUTE * 30, ONE_MINUTE * 60 * 2, ONE_MINUTE * 60 * 5};

    /**
     * 返回结果发送间隔
     * 10秒，1分钟，5分钟，20分钟
     * */
    public final static long[] DELAYED_TIMES_SHORT = {ONE_SECOND * 10, ONE_MINUTE * 1, ONE_MINUTE * 5, ONE_MINUTE * 20};



}
