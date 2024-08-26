package xyz.muses.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.muses.constants.MvcConstant;
import xyz.muses.web.controller.BaseController;
import xyz.muses.web.model.dto.BaseResponseDTO;
import xyz.muses.web.model.dto.CaptchaAnswerRateDTO;
import xyz.muses.web.model.dto.CaptchaQuestionDTO;
import xyz.muses.web.service.user.AuthService;

/**
 * @author : jervis
 * @since : 2024-08-26
 */
@RestController
@RequestMapping(MvcConstant.AUTH_URL_PREFIX)
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

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
        return this.returnWithSuccess(authService.sendSmsCode(id, phoneNumber));
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
        return this.returnWithSuccess(authService.verifySmsCode(id, smsCode));
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
        return this.returnWithSuccess(authService.getCaptchaCode(id));
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
        return this.returnWithSuccess(authService.verifyCaptchaCode(id, captchaCode));
    }
}
