package com.zanclick.redpacket.core.query;


import com.zanclick.redpacket.core.entity.FileExport;
import lombok.Data;

/*
wusi 3.9 文件导出
* */
@Data
public class FileExportQuery extends FileExport {

    private Integer page;

    private Integer limit;

    private Integer offset;

    private String startTime;

    private String endTime;
}
