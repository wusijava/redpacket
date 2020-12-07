package com.zanclick.redpacket.common.exception;

import com.zanclick.redpacket.common.enums.EnumInterface;
import lombok.Data;

/**
 * @author duchong
 * @description 业务异常
 * @date 2019-11-29 11:36:11
 */
@Data
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, String message) {
        super(code, message);
    }

    public BusinessException(EnumInterface enums, Object... args) {
        super(enums.getCode(), enums.getMsg(), args);
    }
}
