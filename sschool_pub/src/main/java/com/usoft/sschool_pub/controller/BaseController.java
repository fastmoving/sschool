package com.usoft.sschool_pub.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.usoft.smartschool.util.MyResult;
import com.usoft.sschool_pub.exception.BaseException;
import com.usoft.sschool_pub.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Braycep
 * @date 2019/2/26 11:08
 */
@RestController
public class BaseController implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(BaseException.class);

    @ExceptionHandler(BaseException.class)
    public MyResult handleException(BaseException be) {
        ErrorCode errorCode = be.getErrorCode();
        logger.warn(be.toString());
        switch (errorCode) {
            case REQUEST_PARAMETER_ERROR:
                return MyResult.failure("请检查您提交的参数是否正确");
            case AGREEMENT_VERSION_INVALID:
                return MyResult.failure("客户端协议版本不正确");
            default:
                return MyResult.failure("服务器正忙，请稍后重试");
        }
    }
}
