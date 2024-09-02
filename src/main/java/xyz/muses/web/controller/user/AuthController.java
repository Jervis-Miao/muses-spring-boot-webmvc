package xyz.muses.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "用户认证接口", tags = {"用户管理"})
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
    @ApiOperation(value = "发送短信验证码", notes = "发送登录短信验证码")
    public BaseResponseDTO<Object> sendSmsCode(@ApiParam(value = "请求ID", required = true) String id,
        @ApiParam(value = "请求手机号", required = true) @RequestParam String phoneNumber) throws Exception {
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
