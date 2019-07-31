package com.tz.mynote.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author tz
 * @Classname WebSocketConfig
 * @Description socket 配置
 * @Date 2019-07-30 20:39
 */
@SpringBootConfiguration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
