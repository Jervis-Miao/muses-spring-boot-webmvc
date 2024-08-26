package xyz.muses.web.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.idaas_doraemon.model.v20210520.ServiceInvokeResponse;
import com.google.common.collect.ImmutableMap;

import xyz.muses.constants.MvcConstant;
import xyz.muses.external.aliyun.idaas.DoraemonManager;
import xyz.muses.framework.common.utils.JsonMapper;
import xyz.muses.web.controller.BaseController;
import xyz.muses.web.model.dto.BaseResponseDTO;
import xyz.muses.web.model.dto.CaptchaAnswerRateDTO;
import xyz.muses.web.model.dto.CaptchaQuestionDTO;

/**
 * @author : jervis
 * @since : 2024-08-26
 */
@RestController
@RequestMapping(MvcConstant.LOGIN_URL_PREFIX)
public class AuthController extends BaseController {

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
    @ResponseBody
    @RequestMapping("/sendSmsCode")
    public BaseResponseDTO<Object> sendSmsCode(String id, @RequestParam String phoneNumber) throws Exception {
        logger.info("send sms code to '{}'", phoneNumber);
        ImmutableMap<String, Object> params = ImmutableMap.of(
            "id", id, "businessId", "QUICK_LOGIN", "phoneNumber", phoneNumber);
        ServiceInvokeResponse smsResponse = doraemonManager.invoke("SendSmsCode", params);
        if (smsResponse.getCode().endsWith("Overflow.Captcha")) {
            return this.returnWithSuccess(ImmutableMap.of("captcha", true));
        }
        if (smsResponse.getCode().endsWith("Overflow.Pause")) {
            return this.returnWithSuccess(ImmutableMap.of("pause", true));
        }
        if (smsResponse.getCode().equals("Params.Illegal")) {
            return this.returnWithSuccess(ImmutableMap.of("restart", true));
        }
        return this
            .returnWithSuccess(jsonMapper.getMapper().readValue(smsResponse.getData(), CaptchaQuestionDTO.class));
    }

    /**
     * 校验短信验证码
     *
     * @param id
     * @param smsCode
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping({"/verifySmsCode"})
    public BaseResponseDTO<CaptchaAnswerRateDTO> verifySmsCode(@RequestParam String id, @RequestParam String smsCode)
        throws Exception {
        logger.info("verify sms code '{}' for id '{}'", smsCode, id);
        Map<String, Object> params = ImmutableMap.of("id", id, "answer", smsCode);
        ServiceInvokeResponse acsResponse = doraemonManager.invoke("VerifySmsCode", params);
        return this
            .returnWithSuccess(jsonMapper.getMapper().readValue(acsResponse.getData(), CaptchaAnswerRateDTO.class));
    }

    /**
     * 获取图形验证码
     *
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getCaptchaCode")
    public BaseResponseDTO<CaptchaQuestionDTO> getCaptchaCode(String id) throws Exception {
        logger.info("get captcha code by id '{}'", id);
        Map<String, Object> params = ImmutableMap.of("id", id);
        ServiceInvokeResponse acsResponse = doraemonManager.invoke("GetCaptchaCode", params);
        return this
            .returnWithSuccess(jsonMapper.getMapper().readValue(acsResponse.getData(), CaptchaQuestionDTO.class));
    }

    /**
     * 验证图形验证码
     *
     * @param id
     * @param captchaCode
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/verifyCaptchaCode")
    public BaseResponseDTO<CaptchaAnswerRateDTO> verifyCaptchaCode(@RequestParam String id,
        @RequestParam String captchaCode) throws Exception {
        logger.info("verify captcha code '{}' for id '{}'", captchaCode, id);
        Map<String, Object> params = ImmutableMap.of("id", id, "answer", captchaCode);
        ServiceInvokeResponse response = doraemonManager.invoke("VerifyCaptchaCode", params);
        return this.returnWithSuccess(jsonMapper.getMapper().readValue(response.getData(), CaptchaAnswerRateDTO.class));
    }
}
