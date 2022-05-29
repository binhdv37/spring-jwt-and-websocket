package com.example.jwtandwebsocket.controller;

import com.example.jwtandwebsocket.common.constant.RespCode;
import com.example.jwtandwebsocket.common.exception.MyAppException;
import com.example.jwtandwebsocket.utils.exceptionHandler.MyExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;

public abstract class WebsocketBaseController {

    @Autowired
    protected MyExceptionHandler myExceptionHandler;

    @MessageExceptionHandler
    public MyAppException handleException(Exception ex) {
        return new MyAppException("@MessageMapping co loi roi: " + ex.getMessage(), RespCode.INTERNAL);
    }
}
