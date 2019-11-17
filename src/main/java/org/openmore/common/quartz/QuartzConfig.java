package org.openmore.common.quartz;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {
  @Autowired
  private SpringJobFactory springJobFactory;
 
  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() {
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
    //schedulerFactoryBean.setJobFactory(springJobFactory);
    try {
      schedulerFactoryBean.setQuartzProperties(quartzProperties());
      schedulerFactoryBean.setJobFactory(springJobFactory);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return schedulerFactoryBean;
  }

  //指定quartz.properties
  @Bean
  public Properties quartzProperties() throws IOException {
    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
    propertiesFactoryBean.afterPropertiesSet();
    return propertiesFactoryBean.getObject();
  }

  /*
   * quartz初始化监听器
   * 这个监听器可以监听到工程的启动，在工程停止再启动时可以让已有的定时任务继续进行
   */
  @Bean
  public QuartzInitializerListener executorListener() {
    return new QuartzInitializerListener();
  }

  @Bean
  public Scheduler scheduler() {
    return schedulerFactoryBean().getScheduler();
  }
}