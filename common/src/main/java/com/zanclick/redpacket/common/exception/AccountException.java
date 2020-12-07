package com.zanclick.redpacket.common.exception;

import com.zanclick.redpacket.common.enums.EnumInterface;
import lombok.Data;

import java.text.MessageFormat;

/**
 * @description 相关错误
 * @author duchong
 * @date 2020-5-26 15:18:16
 */
@Data
public class AccountException extends BizException {

    private String msg;
    private String code;

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String code, String msgFormat, Object... args) {
        super(MessageFormat.format(msgFormat, args));
        this.code = code;
        this.msg = MessageFormat.format(msgFormat, args);
    }

    public AccountException(EnumInterface enumInterface, Object... args) {
        super(MessageFormat.format(enumInterface.getMsg(), args));
        this.code = enumInterface.getCode();
        this.msg = MessageFormat.format(enumInterface.getMsg(), args);
    }
}
