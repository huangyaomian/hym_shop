package com.hym.shop.common.exception;

/**
 * 自定义api异常
 * @author Mika.
 * @created 2020/10/12 19:00.
 */
public class ApiException extends BaseException {

    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }
}
