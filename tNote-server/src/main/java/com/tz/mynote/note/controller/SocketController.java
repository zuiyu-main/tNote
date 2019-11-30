package com.tz.mynote.note.controller;

import com.tz.mynote.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author tz
 * @Classname SocketController
 * @Description
 * @Date 2019-07-30 20:43
 */
@Slf4j
@RestController
@RequestMapping("/checkCenter")
public class SocketController {
    /**
     *     页面请求
     */
    @GetMapping("/socket/{cid}")
    public Object socket(@PathVariable String cid) {
        return cid;
    }
    /**
     *     推送数据接口
     */
    @RequestMapping("/socket/push/{cid}")
    public Object pushToWeb(@PathVariable String cid,String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            log.error("推送消息出错[{}]",e.getMessage());
            return e.getMessage();
        }
        return message;
    }
}
