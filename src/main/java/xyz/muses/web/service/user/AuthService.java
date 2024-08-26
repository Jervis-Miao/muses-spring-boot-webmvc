/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.web.service.user;

import com.aliyuncs.idaas_doraemon.model.v20210520.ServiceInvokeResponse;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.muses.external.aliyun.idaas.DoraemonManager;
import xyz.muses.framework.common.utils.JsonMapper;
import xyz.muses.web.model.dto.BaseResponseDTO;
import xyz.muses.web.model.dto.CaptchaAnswerRateDTO;
import xyz.muses.web.model.dto.CaptchaQuestionDTO;
import xyz.muses.web.service.BaseService;

import java.util.Map;

/**
 * @author jervis
 * @date 2024/8/26
 */
@Service
public class AuthService extends BaseService {

    @Autowired
    private DoraemonManager doraemonManager;

    @Autowired
    private JsonMapper jsonMapper;

    /**
     * 发送短信验证码
     *
     * @param id
     * @param phoneNumber
     * @return
     * @throws Exception
     */
    public Object sendSmsCode(String id, String phoneNumber) throws Exception {
        logger.info("send sms code to '{}'", phoneNumber);
        ImmutableMap<String, Object> params = ImmutableMap.of(
            "id", id, "businessId", "QUICK_LOGIN", "phoneNumber", phoneNumber);
        ServiceInvokeResponse smsResponse = doraemonManager.invoke("SendSmsCode", params);
        if (smsResponse.getCode().endsWith("Overflow.Captcha")) {
            return ImmutableMap.of("captcha", true);
        }
        if (smsResponse.getCode().endsWith("Overflow.Pause")) {
            return ImmutableMap.of("pause", true);
        }
        if (smsResponse.getCode().equals("Params.Illegal")) {
            return ImmutableMap.of("restart", true);
        }
        return jsonMapper.getMapper().readValue(smsResponse.getData(), CaptchaQuestionDTO.class);
    }

    /**
     * 校验短信验证码
     *
     * @param id
     * @param smsCode
     * @return
     * @throws Exception
     */
    public CaptchaAnswerRateDTO verifySmsCode(String id, String smsCode)
        throws Exception {
        logger.info("verify sms code '{}' for id '{}'", smsCode, id);
        Map<String, Object> params = ImmutableMap.of("id", id, "answer", smsCode);
        ServiceInvokeResponse acsResponse = doraemonManager.invoke("VerifySmsCode", params);
        return jsonMapper.getMapper().readValue(acsResponse.getData(), CaptchaAnswerRateDTO.class);
    }

    /**
     * 获取图形验证码
     *
     * @param id
     * @return
     * @throws Exception
     */
    public CaptchaQuestionDTO getCaptchaCode(String id) throws Exception {
        logger.info("get captcha code by id '{}'", id);
        Map<String, Object> params = ImmutableMap.of("id", id);
        ServiceInvokeResponse acsResponse = doraemonManager.invoke("GetCaptchaCode", params);
        return jsonMapper.getMapper().readValue(acsResponse.getData(), CaptchaQuestionDTO.class);
    }

    /**
     * 验证图形验证码
     * 
     * @param id
     * @param captchaCode
     * @return
     * @throws Exception
     */
    public CaptchaAnswerRateDTO verifyCaptchaCode(String id, String captchaCode) throws Exception {
        logger.info("verify captcha code '{}' for id '{}'", captchaCode, id);
        Map<String, Object> params = ImmutableMap.of("id", id, "answer", captchaCode);
        ServiceInvokeResponse response = doraemonManager.invoke("VerifyCaptchaCode", params);
        return jsonMapper.getMapper().readValue(response.getData(), CaptchaAnswerRateDTO.class);
    }
}
