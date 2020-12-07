package com.zanclick.redpacket.common.exception;

/**
 * @author duchong
 * @date 2020-05-24 18:20:33
 **/
public interface ExceptionHandlerResolver<T> {

   T  handler(Exception e);
}
