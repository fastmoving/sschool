package com.usoft.sschool_pub.exception;

import java.io.Serializable;

/**
 * 定义所有的错误信息
 *
 * @author Braycep
 * @date 2019/2/26 11:19
 */
public enum ErrorCode implements Serializable {
    /*
     * 协议版本错误
     */
    AGREEMENT_VERSION_INVALID
    /*
      请求参数错误
     */, REQUEST_PARAMETER_ERROR
    /**
     * 数据库异常
     */
    , MySQL_EXCEPTION, DUPLICATE_IDCARD
    /**
     * 数据库异常
     */
    , UNKNOWN_ERROR

}
