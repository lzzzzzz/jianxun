package org.openmore.cms.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: CommuniteVisit
 * @description: 短信问候工作类
 * @author: lz
 * @create: 2018-09-07 17:49
 **/
public class SmsRecordJob implements Job {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String activityId = dataMap.getString("smsRecordId");
        logger.debug("==>send activity notify message id:" + activityId);
    }
}
