package com.fruityuni;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FruityuniServerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // 测试Spring上下文是否正常加载
        assertNotNull(applicationContext, "应用上下文不应为空");
    }
    
    @Test
    void testEnvironment() {
        // 测试环境配置
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        System.out.println("当前激活的配置文件: " + String.join(", ", activeProfiles.length > 0 ? activeProfiles : new String[]{"default"}));
    }
}