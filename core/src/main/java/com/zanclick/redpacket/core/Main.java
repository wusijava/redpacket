package com.zanclick.redpacket.core;


import com.zanclick.redpacket.common.generator.CodeGenerator;
import com.zanclick.redpacket.core.entity.RedPacketRecord;
import com.zanclick.redpacket.core.entity.TransferRecord;
import com.zanclick.redpacket.core.entity.Wallet;

/**
 * @author duchong
 * @description
 * @date
 */
public class Main {

    public static void main(String[] args) {
        String basePack = Main.class.getPackage().getName();
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.generateMybatisXml(basePack, TransferRecord.class);
        codeGenerator.generateDao(basePack, TransferRecord.class);
        codeGenerator.generateService(basePack, TransferRecord.class);
//        codeGenerator.generateCreateSqlForPackage("com.click.jd.merchant.modules");
    }
}
