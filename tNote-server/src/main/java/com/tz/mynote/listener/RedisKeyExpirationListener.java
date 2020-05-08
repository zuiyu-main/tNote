package com.tz.mynote.listener;

import com.alibaba.fastjson.JSONObject;
import com.tz.mynote.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author tz
 * @Classname RedisKeyExpirationListener
 * @Description redis过期key提醒
 * @Date 2019-09-28 15:16
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        log.debug("redis 过期key监听,key=[{}]",expiredKey);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","token_expire");
        jsonObject.put("status", HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
        try {
            WebSocketServer.sendInfo(jsonObject.toJSONString(),null);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("redis expire key listener,expiredKey ={}",expiredKey);
        }
    }
}
