package xyz.muses.config.aliyun;

import java.util.Objects;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 阿里云ACS配置
 *
 * @author jervis
 * @date 2024/8/18
 */
@Configuration
@EnableConfigurationProperties(IdaasDoraemonProperties.class)
public class AcsConfig {

    /** 应用身份服务(IDaaS)配置 **/
    private final IdaasDoraemonProperties doraemonProperties;

    public AcsConfig(IdaasDoraemonProperties doraemonProperties) {
        this.doraemonProperties = Objects.requireNonNull(doraemonProperties);
    }

    @Bean
    public IAcsClient idaasDoraemonClient() {
        DefaultProfile profile = DefaultProfile.getProfile(
            doraemonProperties.getRegionId(),
            doraemonProperties.getAccessKeyId(),
            doraemonProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint(
            doraemonProperties.getRegionId(),
            doraemonProperties.getProduct(),
            doraemonProperties.getEndpoint());
        return new DefaultAcsClient(profile);
    }
}