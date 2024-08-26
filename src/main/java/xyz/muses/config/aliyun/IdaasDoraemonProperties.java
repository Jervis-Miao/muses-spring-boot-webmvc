package xyz.muses.config.aliyun;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 应用身份服务(IDaaS)配置
 * 
 * @author jervis
 * @date 2024/8/19.
 */
@ConfigurationProperties(prefix = "aliyun.idaas.doraemon")
public class IdaasDoraemonProperties {
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    // 强烈建议不要把 AccessKey 和 AccessKeySecret 保存到代码里，会存在密钥泄漏风险
    private String accessKeyId;
    private String accessKeySecret;
    private String regionId;
    private String product;
    private String endpoint;
    private String applicationExternalId;
    private String applicationExternalSecret;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getApplicationExternalId() {
        return applicationExternalId;
    }

    public void setApplicationExternalId(String applicationExternalId) {
        this.applicationExternalId = applicationExternalId;
    }

    public String getApplicationExternalSecret() {
        return applicationExternalSecret;
    }

    public void setApplicationExternalSecret(String applicationExternalSecret) {
        this.applicationExternalSecret = applicationExternalSecret;
    }
}
