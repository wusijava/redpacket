package com.zanclick.redpacket.common.model;

import java.io.Serializable;

/**
 * @author duchong
 * @description
 * @date
 */
public abstract class RequestParam extends Param implements Serializable {
    /**
     * 参数检查
     *
     * @return 返回检查结果
     */
    public abstract String check();
}
