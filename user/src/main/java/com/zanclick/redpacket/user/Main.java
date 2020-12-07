package com.zanclick.redpacket.user;


import com.zanclick.redpacket.common.generator.CodeGenerator;
import com.zanclick.redpacket.user.entity.Role;

/**
 * @author duchong
 * @description
 * @date
 */
public class Main {

    public static void main(String[] args) {
        String basePack = Main.class.getPackage().getName();
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.generateMybatisXml(basePack, Role.class);
        codeGenerator.generateDao(basePack, Role.class);
        codeGenerator.generateService(basePack, Role.class);
//        codeGenerator.generateCreateSqlForPackage("com.click.jd.merchant.modules");
    }
}
