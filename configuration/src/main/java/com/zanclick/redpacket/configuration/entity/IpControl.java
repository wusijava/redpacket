package com.zanclick.redpacket.configuration.entity;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

import java.util.Objects;

/**
 * @author duchong
 * @description ip控制
 * @date 2020-8-21 21:34:25
 */
@Data
public class IpControl implements Identifiable<Long> {

    private Long id;
    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用状态 0 关闭的 1开启的
     */
    private String ip;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpControl control = (IpControl) o;
        return Objects.equals(ip, control.ip);
    }

    public IpControl(String ip) {
        this.ip = ip;
    }

    public IpControl() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appId, ip);
    }
}
