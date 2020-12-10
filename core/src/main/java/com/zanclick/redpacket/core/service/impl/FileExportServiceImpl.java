package com.zanclick.redpacket.core.service.impl;

import com.zanclick.redpacket.common.base.dao.mybatis.BaseMapper;
import com.zanclick.redpacket.common.base.service.impl.BaseMybatisServiceImpl;
import com.zanclick.redpacket.core.entity.FileExport;
import com.zanclick.redpacket.core.mapper.FileExportMapper;
import com.zanclick.redpacket.core.service.FileExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 27720
 * @date 2020-12-08 16:24:36
 **/
@Service
public class FileExportServiceImpl extends BaseMybatisServiceImpl<FileExport,Long> implements FileExportService {

    @Autowired
    private FileExportMapper fileExportMapper;


    @Override
    protected BaseMapper<FileExport, Long> getBaseMapper() {
        return fileExportMapper;
    }
}
