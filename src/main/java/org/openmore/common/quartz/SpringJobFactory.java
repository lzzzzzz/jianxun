package org.openmore.common.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**Quartz  Job中无法使用autoware注入，需要此类帮助*/
@Component
public class SpringJobFactory extends AdaptableJobFactory {
 
  @Autowired
  private AutowireCapableBeanFactory capableBeanFactory;
 
  @Override
  protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
    Object jobInstance = super.createJobInstance(bundle);
    capableBeanFactory.autowireBean(jobInstance);
    return jobInstance;
  }
}