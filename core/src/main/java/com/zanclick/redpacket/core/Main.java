package com.zanclick.redpacket.core;


import com.zanclick.redpacket.common.generator.CodeGenerator;
import com.zanclick.redpacket.core.entity.RedPacket;
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
        codeGenerator.generateMybatisXml(basePack, RedPacket.class);
        codeGenerator.generateDao(basePack, RedPacket.class);
        codeGenerator.generateService(basePack, RedPacket.class);
//        codeGenerator.generateCreateSqlForPackage("com.click.jd.merchant.modules");
    }
}
