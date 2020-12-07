package com.zanclick.redpacket.api;


import com.zanclick.redpacket.api.entity.NotifyMessage;
import com.zanclick.redpacket.common.generator.CodeGenerator;

/**
 * @author duchong
 * @description
 * @date
 */
public class Main {

    public static void main(String[] args) {
        String basePack = Main.class.getPackage().getName();
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.generateMybatisXml(basePack, NotifyMessage.class);
//        codeGenerator.generateDao(basePack, NotifyMessage.class);
//        codeGenerator.generateService(basePack, NotifyMessage.class);
//        codeGenerator.generateCreateSqlForPackage(basePack);
    }
}
