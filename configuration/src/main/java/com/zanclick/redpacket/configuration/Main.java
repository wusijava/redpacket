package com.zanclick.redpacket.configuration;

import com.zanclick.redpacket.common.generator.CodeGenerator;
import com.zanclick.redpacket.configuration.entity.AliPayConfiguration;
import com.zanclick.redpacket.configuration.entity.App;


/**
 * @author duchong
 * @description
 * @date
 */
public class Main {

    public static void main(String[] args) {
        String basePack = Main.class.getPackage().getName();
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.generateMybatisXml(basePack, AliPayConfiguration.class);
        codeGenerator.generateDao(basePack, AliPayConfiguration.class);
        codeGenerator.generateService(basePack, AliPayConfiguration.class);
//        codeGenerator.generateCreateSqlForPackage(basePack);
    }
}
