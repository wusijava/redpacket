package com.zanclick.redpacket.core.controller;

import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.core.entity.FileExport;
import com.zanclick.redpacket.core.query.FileExportQuery;
import com.zanclick.redpacket.core.service.FileExportService;
import com.zanclick.redpacket.core.vo.FileExportWebInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huze
 * @Date: 2020/12/8 16:26
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/web/fileExport")
public class FileExportController {
    /**
     * 获取显示Modal
     *
     * @param record
     * @return
     */
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private FileExportService fileExportService;
    @RequestMapping(value = "/getFileList")
    public Response<Page<FileExportWebInfo>> getFileList(FileExportQuery query) {
        if (DataUtils.isEmpty(query.getPage())) {
            query.setPage(0);
        }
        if (DataUtils.isEmpty(query.getLimit())) {
            query.setLimit(10);
        }
        //查询当前用户的文件导出  user表中没有UUID  先用用户名代替
        LoginContext.RequestUser user = LoginContext.getCurrentUser();
        query.setUser(user.getUsername());
        try {
            Pageable pageable = PageRequest.of(query.getPage(), query.getLimit());
            Page<FileExport> page = fileExportService.queryPage(query, pageable);
            List<FileExportWebInfo> voList = new ArrayList<>();
            for (FileExport fileExport : page.getContent()) {
                voList.add(getFileExportWebInfo(fileExport));
            }
            Page<FileExportWebInfo> voPage = new PageImpl<FileExportWebInfo>(voList, pageable, page.getTotalElements());
            return Response.ok(voPage);
        } catch (Exception e) {
            log.error("获取文件列表异常:{}", e);
            return Response.fail("查询失败");
        }
    }
    private FileExportWebInfo getFileExportWebInfo(FileExport fileExport) {
        FileExportWebInfo fileExportWebInfo = new FileExportWebInfo();
        fileExportWebInfo.setId(fileExport.getId());
        fileExportWebInfo.setFileName(fileExport.getFileName());
        fileExportWebInfo.setExprotTime(sdf.format(fileExport.getExprotTime()));
        fileExportWebInfo.setState(fileExport.getState());
        fileExportWebInfo.setStateStr(fileExport.getStateDesc());
        fileExportWebInfo.setUser(fileExport.getUser());
        fileExportWebInfo.setType(fileExport.getType());
        fileExportWebInfo.setFinishTime(fileExport.getFinishTime() == null ? null : sdf.format(fileExport.getFinishTime()));
        return fileExportWebInfo;
    }
}
