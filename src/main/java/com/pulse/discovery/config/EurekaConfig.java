package com.pulse.discovery.config;

import com.netflix.appinfo.AmazonInfo;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("aws") // AWS 환경에서만 적용
public class EurekaConfig {

    /**
     * @param inetUtils InetUtils 객체
     * @return EurekaInstanceConfigBean
     * @apiNote Eureka 인스턴스 설정을 반환합니다.
     */
    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        // Eureka 인스턴스 설정
        EurekaInstanceConfigBean instanceConfig = new EurekaInstanceConfigBean(inetUtils);

        // EC2 환경 정보를 자동으로 인식하여 설정
        AmazonInfo amazonInfo = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        instanceConfig.setDataCenterInfo(amazonInfo);

        // Eureka 인스턴스 설정 반환
        return instanceConfig;
    }

}
