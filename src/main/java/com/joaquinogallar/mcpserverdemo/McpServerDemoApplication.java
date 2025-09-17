package com.joaquinogallar.mcpserverdemo;

import com.joaquinogallar.mcpserverdemo.service.WeatherService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class McpServerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerDemoApplication.class, args);
    }

    @Bean
    public List<ToolCallback> tools(WeatherService weatherService) {
        return List.of(ToolCallbacks.from(weatherService));
    }

}
