package com.zanclick.redpacket.core.query;

import com.zanclick.redpacket.core.entity.CorrelationConfiguration;
import lombok.Data;

/**
 * @Author: huze
 * @Date: 2020/12/9 14:54
 */
@Data
public class CorrelationConfigurationQuery extends CorrelationConfiguration {
    private Integer page;

    private Integer limit;

    private Integer offset;

    private String startTime;

    private String endTime;
}
