package xyz.muses.external.aliyun.idaas;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.idaas_doraemon.model.v20210520.ServiceInvokeRequest;
import com.aliyuncs.idaas_doraemon.model.v20210520.ServiceInvokeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import xyz.muses.config.aliyun.IdaasDoraemonProperties;
import xyz.muses.framework.common.utils.JsonMapper;

/**
 * 阿里云应用身份服务(IDaaS)服务
 *
 * @author jervis
 * @date 2024/8/26
 */
@Component
public class DoraemonManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IdaasDoraemonProperties doraemonProperties;

    @Autowired
    private IAcsClient iAcsClient;

    @Autowired
    private JsonMapper jsonMapper;

    public ServiceInvokeResponse invoke(String action, Map<String, Object> params) throws JsonProcessingException {
        logger.debug("invoke '{}' with params '{}'", action, params);
        ServiceInvokeResponse acsResponse;
        try {
            ServiceInvokeRequest request = new ServiceInvokeRequest();
            request.setApplicationExternalId(doraemonProperties.getApplicationExternalId());
            request.setServiceCode("SMS");
            request.setDoraemonAction(action);
            request.setMobileExtendParamsJson(
                Base64.getEncoder().encodeToString(jsonMapper.getMapper().writeValueAsBytes(params)));
            request.setMobileExtendParamsJsonSign(
                encode(doraemonProperties.getApplicationExternalSecret(), request.getMobileExtendParamsJson()));
            acsResponse = iAcsClient.getAcsResponse(request);
        } catch (ClientException e) {
            logger.error("invoke error", e);
            acsResponse = new ServiceInvokeResponse();
            acsResponse.setRequestId(e.getRequestId());
            acsResponse.setSuccess(false);
            acsResponse.setMessage(e.getMessage());
            acsResponse.setCode(e.getErrCode());
        }
        logger.debug("got response '{}'", jsonMapper.toJson(acsResponse));
        return acsResponse;
    }

    private String encode(String key, String data) {
        try {
            Mac sha256H = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256H.init(secretKey);
            return Hex.encodeHexString(sha256H.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException(e);
        }
    }
}
