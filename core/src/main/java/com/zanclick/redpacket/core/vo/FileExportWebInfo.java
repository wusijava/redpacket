package com.zanclick.redpacket.core.vo;

import com.zanclick.redpacket.common.model.Identifiable;
import lombok.Data;

/*
wusi 3.9 文件导出
* */
@Data
public class FileExportWebInfo implements Identifiable<Long> {

    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 导出时间
     */
    private String exprotTime;

    /**
     * 状态-1-导出失败  0 正在导出  1导出成功
     */
    private Integer state;
    private String stateStr;

    /**
     导出人
     */
    private String user;

    private Integer type;

    /**
     * 导出结束时间
     */
    private String finishTime;
}
