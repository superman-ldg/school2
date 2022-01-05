package com.ldg.config.exception;

/**
 * @author Administrator
 */
public interface BaseErrorInfo {
    /**
     * 错误码
     * @return
     */
    int Code();

    /**
     * 错误信息
     * @return
     */
    String Message();
}
