package org.openmore.common.storage;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSClientConfiguration {

    @Value("${aliyun.oss.endpoint}")
    private String ENDPOINT;
    @Value("${aliyun.oss.accessKeyId}")
    private String ACCESS_KEY;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ACCESS_SECRET;

    @Bean
    public OSSClient getOSSClient() {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY, ACCESS_SECRET);
        return ossClient;
    }
}