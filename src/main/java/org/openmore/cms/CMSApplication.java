package org.openmore.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@ServletComponentScan("org.openmore.cms.service")
//@EnableAutoConfiguration
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@MapperScan(value = "org.openmore.cms.dao")
@ComponentScan(basePackages = "org.openmore.cms.service,org.openmore.cms.service.impl,org.openmore.cms.common," +
        "org.openmore.cms.controller,org.openmore.common.utils,org.openmore.common.redisSubscribe,org.openmore")
@SpringBootApplication
@EnableScheduling
public class CMSApplication extends SpringBootServletInitializer {
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(CMSApplication.class, args);
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container=new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
